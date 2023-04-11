package network.monki.utils.listeners;

import network.monki.utils.Gooey2Plugin;
import network.monki.utils.buttons.GuiButton;
import network.monki.utils.guis.Gui;
import network.monki.utils.guis.GuiAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private Gooey2Plugin plugin;
    public InventoryClickListener(Gooey2Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Inventory inventory = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();

        if(inventory.getHolder() instanceof Gui) {

            if (event.getWhoClicked() instanceof Player) {

                Player player = (Player) event.getWhoClicked();
                Gui gui = (Gui) inventory.getHolder();

                event.setCancelled(true);

                if(item != null && !item.getType().equals(Material.AIR)) {

                    GuiButton button = gui.getButton(event.getSlot());
                    GuiAction action = button.getAction();

                    if(action != null) {
                        action.run(player, gui);
                    }

                }
            }

        }
    }
}
