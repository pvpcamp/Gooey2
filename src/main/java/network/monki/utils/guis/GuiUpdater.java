package network.monki.utils.guis;

import network.monki.utils.Gooey2Plugin;
import network.monki.utils.guis.Gui;
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
