package network.monki.utils.guis;

import lombok.Data;
import network.monki.utils.buttons.GuiButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public @Data class StandardGuiOld implements InventoryHolder {

    private String title;
    private int size;
    private boolean autoRefresh, update;
    private Map<Integer, GuiButton> buttons;
    private ItemStack background;
    private Inventory inventory;


    /***
     * Create a new GUI.
     * @param title
     * @param size Must be a number between 9-54. x % 9
     */
    public StandardGuiOld(String title, int size) {
        this.title = title;
        this.size = size;
        buttons = new HashMap<>();
        inventory = Bukkit.createInventory(this, size, title);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setButton(GuiButton button, int slot, boolean update) {
        this.buttons.put(slot, button);

        if(update) {
            update();
        }
    }

    public void removeButton(int slot, boolean update) {
        this.buttons.remove(slot);

        if(update) {
            this.update();
        }
    }

    public void removeButton(GuiButton button, boolean update) {
        int slot = -1;
        for(Map.Entry<Integer, GuiButton> entry : buttons.entrySet()) {
            int i = entry.getKey();
            GuiButton b = entry.getValue();
            if(button.equals(b)) {
                slot = i;
            }
            inventory.setItem(i, button);
        }

        if(slot < 0) {
            buttons.remove(slot);
        }

        if(update) {
            this.update();
        }
    }

    /***
     * Opens the gui for the specified player.
     * @param player
     */
    public void open(Player player) {
        this.update();
        player.openInventory(this.getInventory());
    }

    /***
     * Updates the inventory with new or removed items.
     * Method called whenever inventory is opened or when buttons have been added or removed.
     */
    public void update() {
        inventory.clear();

        for(Map.Entry<Integer, GuiButton> entry : buttons.entrySet()) {
            int i = entry.getKey();
            GuiButton button = entry.getValue();
            inventory.setItem(i, button);
        }
    }
}
