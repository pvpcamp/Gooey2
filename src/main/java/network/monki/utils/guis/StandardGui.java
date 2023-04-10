package network.monki.utils.guis;

import network.monki.utils.buttons.GuiButton;
import org.bukkit.inventory.Inventory;

public class StandardGui extends Gui{

    public StandardGui(String name, int slots) {
        super(name, slots);
    }

    @Override
    public void updateGui() {
        Inventory inv = getInventory();

        inv.clear();

        for(GuiButton button : getButtons()) {
            if(button.isVisible()) {
                inv.setItem(button.getSlot(), button);
            }
        }

        if(getBackground() != null) {
            for(int i = 0; i < inv.getSize(); i++) {
                if(inv.getItem(i) == null) {
                    inv.setItem(i, getBackground());
                }
            }
        }
    }
}
