package camp.pvp.utils.listeners;

import camp.pvp.utils.guis.Gui;
import camp.pvp.utils.Gooey2;
import camp.pvp.utils.buttons.GuiButton;
import camp.pvp.utils.guis.GuiAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListeners implements Listener {

    private Gooey2 plugin;
    public InventoryListeners(Gooey2 plugin) {
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

                    if(button != null) {
                        GuiAction action = button.getAction();

                        if (button.isCloseOnClick()) player.closeInventory();

                        if (action != null) action.run(player, button, gui, event.getClick());

                        if(gui.getClickSound() != null) player.playSound(player.getLocation(), gui.getClickSound(), 1F, 1F);
                    }
                }
            }

        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if(inventory != null && inventory.getHolder() instanceof Gui gui) {

            if(gui.getCloseSound() != null && event.getPlayer() instanceof Player player)
                player.playSound(player.getLocation(), gui.getCloseSound(), 1F, 1F);

            if(gui.getCloseAction() == null) return;

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                gui.getCloseAction().run((Player) event.getPlayer(), gui);
            }, 1L);
        }
    }
}
