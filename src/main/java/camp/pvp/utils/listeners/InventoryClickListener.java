package camp.pvp.utils.listeners;

import camp.pvp.utils.guis.Gui;
import camp.pvp.utils.Gooey2;
import camp.pvp.utils.buttons.GuiButton;
import camp.pvp.utils.guis.GuiAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private Gooey2 plugin;
    public InventoryClickListener(Gooey2 plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Inventory inventory = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();

        if(inventory != null && inventory.getHolder() instanceof Gui) {

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

                    if(button.isCloseOnClick()) {
                        player.closeInventory();
                    }
                }
            }

        }
    }
}
