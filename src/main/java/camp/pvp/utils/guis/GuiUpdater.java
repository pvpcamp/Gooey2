package camp.pvp.utils.guis;

import camp.pvp.utils.Gooey2Plugin;
import org.bukkit.inventory.Inventory;

public class GuiUpdater implements Runnable {

    private Gooey2Plugin plugin;
    public GuiUpdater(Gooey2Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for(Gui gui : plugin.getGuis()) {
            if(gui.isAutoUpdate()) {
                Inventory inventory = gui.getInventory();
                if (inventory.getViewers().size() > 0) {
                    gui.updateGui();
                }
            }
        }
    }
}
