package camp.pvp.utils.guis;

import lombok.Data;
import camp.pvp.utils.Gooey2;
import camp.pvp.utils.buttons.GuiButton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public @Data abstract class Gui implements InventoryHolder {

    public String name;
    public int slots;
    public boolean autoUpdate, variableSize;
    public ItemStack background;
    public Inventory inventory;
    public List<GuiButton> buttons;

    /***
     * Creates a new GUI with variable slot sizes.
     * @param name
     */
    public Gui(String name) {
        this.name = name;
        this.background = null;
        this.inventory = Bukkit.createInventory(this, slots, ChatColor.translateAlternateColorCodes('&', name));
        this.buttons = new ArrayList<>();

        Gooey2.getInstance().getGuis().add(this);

        this.variableSize = true;
    }

    /***
     * Creates a new GUI with a specfied amount of slots.
     * @param name
     * @param slots
     */
    public Gui(String name, int slots) {
        this(name);
        this.slots = slots;
        this.variableSize = false;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    /***
     * Adds a button to the GUI at the next available position.
     * @param button
     * @param update
     */
    public void addButton(GuiButton button, boolean update) {
        buttons.add(button);
        if (update) {
            this.updateGui();
        }
    }

    public void setBackground(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
    }

    public void setDefaultBackground() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        item.setDurability((short) 15);
        setBackground(item);
    }

    /***
     * Removed a specified button from the GUI.
     * @param button
     * @param update
     */
    public void removeButton(GuiButton button, boolean update) {
        buttons.remove(button);
        if (update) {
            this.updateGui();
        }
    }

    public GuiButton getButton(int slot) {
        for(GuiButton button : getButtons()) {
            if(button.getSlot() == slot && button.isVisible()) {
                return button;
            }
        }

        return null;
    }

    public void open(Player player) {
        updateGui();
        player.openInventory(getInventory());
    }

    /***
     * Updates the GUI with new buttons or updated information.
     */
    public abstract void updateGui();
}
