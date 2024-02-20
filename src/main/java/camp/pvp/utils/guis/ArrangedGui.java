package camp.pvp.utils.guis;

import camp.pvp.utils.buttons.GuiButton;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArrangedGui extends StandardGui{

    public ArrangedGui(String name) {
        super(name, 9);
    }

    @Override
    public void open(Player player) {
        arrangeButtons();
        super.open(player);
    }

    private void arrangeButtons() {

        List<Integer> skipNumbers = Arrays.asList(0, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 53);

        Queue<GuiButton> buttonQueue = new LinkedList<>(getButtons());

        int count = 0, divisor = isBorder() ? 7 : 9, buttonSize = 0;

        for(GuiButton button : getButtons()) {
            if(!button.isOverrideGuiArrangement()) buttonSize++;
        }

        while(buttonSize % divisor != 0) {
            buttonSize++;
        }

        while (buttonSize % divisor == 0) {
            buttonSize -= divisor;
            count++;

            if(buttonSize == 0) break;
        }

        updateSlots((count + (isBorder() ? 2 : (isNavigationBar() ? 1 : 0))) * 9);

        List<Integer> occupiedSlots = new LinkedList<>();
        for(GuiButton button : getButtons()) {
            if(button.isOverrideGuiArrangement()) {
                occupiedSlots.add(button.getSlot());
            }
        }

        int startFrom = isBorder() ? 10 : 0;

        if(!isBorder()) {
            startFrom = isNavigationBar() ? 9 : 0;
        }

        for (int i = startFrom; i < 54; i++) {

            if(isBorder() && skipNumbers.contains(i)) continue;

            if(buttonQueue.isEmpty()) break;

            GuiButton button = buttonQueue.poll();

            if(button.isOverrideGuiArrangement()) {
                i--;
                continue;
            }

            boolean isOccupied = occupiedSlots.contains(i);
            if(isOccupied) {
                for(int o = i; o < 55; o++) {

                    if(o == 54) {
                        return;
                    }

                    if(!occupiedSlots.contains(o)) {
                        i = o;
                        break;
                    }
                }
            }

            button.setSlot(i);
            button.setOldSlot(i);
        }
    }
}
