package camp.pvp.utils.guis;

import camp.pvp.utils.Gooey2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class GuiUpdater implements Runnable {

    private Gooey2 plugin;
    public GuiUpdater(Gooey2 plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            InventoryView iv = player.getOpenInventory();
            if(iv != null) {
                Inventory inventory = iv.getTopInventory();
                if(inventory != null && inventory.getHolder() instanceof Gui) {
                    Gui gui = (Gui) inventory.getHolder();
                    if(gui.isAutoUpdate()) {
                        gui.updateGui();
                    }
                }
            }
        }
    }
}
