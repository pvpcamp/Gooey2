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

        int count = 0, divisor = isBorder() ? 7 : 9, buttonSize = buttonQueue.size();

        while(buttonSize % divisor != 0) {
            buttonSize++;
        }

        while (buttonSize % divisor == 0) {
            buttonSize -= divisor;
            count++;

            if(buttonSize == 0) break;
        }

        updateSlots((count + (isBorder() ? 2 : 0)) * 9);

        for (int i = isBorder() ? 10 : 0; i <= 53; i++) {

            if(isBorder() && skipNumbers.contains(i)) continue;

            if(buttonQueue.isEmpty()) break;

            GuiButton button = buttonQueue.poll();

            if(button.isOverrideGuiArrangement()) {
                i--;
                continue;
            }

            button.setSlot(i);
        }
    }
}
