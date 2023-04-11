package network.monki.utils.guis;

import network.monki.utils.buttons.GuiButton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class StandardGui extends Gui{

    public StandardGui(String name, int slots) {
        super(name, slots);
    }

    @Override
    public void updateGui() {
        Inventory inv = getInventory();
        final List<HumanEntity> viewers = new ArrayList<>(inv.getViewers());
        if(inv.getSize() != this.getSlots()) {
            setInventory(Bukkit.createInventory(this, this.getSlots(), ChatColor.translateAlternateColorCodes('&', getName())));
            inv = getInventory();
            for(HumanEntity he : viewers) {
                if(he instanceof Player) {
                    Player player = (Player) he;
                    player.openInventory(inv);
                }
            }
        }

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
