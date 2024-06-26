package camp.pvp.utils.guis;

import camp.pvp.utils.buttons.GuiButton;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class StandardGui extends Gui{

    @Getter @Setter
    private boolean border, navigationBar, arrangeInOrder;

    public StandardGui(String name, int slots) {
        super(name, slots);
    }

    @Override
    public void updateGui() {

        Inventory inv = getInventory();

        for(GuiButton button : getButtons()) {
            if(button.getSlot() != button.getOldSlot()) {
                inv.clear();
                break;
            }
        }

        for(GuiButton button : getButtons()) {
            if(button.isVisible()) {
                if(button.getButtonUpdater() != null) {
                    button.getButtonUpdater().update(button, this);
                }

                inv.setItem(button.getSlot(), button);
            }
        }

        if(getBackground() != null) {
            final int rows = getSlots() / 9;

            if(border) {
                for (int row = 0; row < rows; row++) {
                    for (int column = 0; column < 9; column++) {
                        int slot = (row * 9) + column;
                        if (row == 0 || row + 1 == rows) {
                            if (inv.getItem(slot) == null) {
                                inv.setItem((row * 9) + column, getBackground());
                            }
                            continue;
                        }

                        if (column == 0 || column == 8) {
                            if (inv.getItem(slot) == null) {
                                inv.setItem(slot, getBackground());
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < (isNavigationBar() ? 9 : inv.getSize()); i++) {
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, getBackground());
                    }
                }
            }
        }

        for(HumanEntity player : inv.getViewers()) {
            Player p = (Player) player;
            p.updateInventory();
        }
    }

    public void setDefaultBorder() {
        setDefaultBackground();
        border = true;
        navigationBar = false;
    }

    public void setDefaultNavigationBar() {
        setDefaultBackground();
        border = false;
        navigationBar = true;
    }
}
