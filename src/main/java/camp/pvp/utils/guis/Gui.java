package camp.pvp.utils.guis;

import lombok.Data;
import camp.pvp.utils.Gooey2;
import camp.pvp.utils.buttons.GuiButton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public @Data abstract class Gui implements InventoryHolder {

    private String name;
    private boolean autoUpdate;
    private int slots;
    private ItemStack background;
    private Inventory inventory;
    private List<GuiButton> buttons;
    private Sound openSound, clickSound, closeSound;
    private GuiCloseAction closeAction;

    /***
     * Creates a new GUI with a specified amount of slots.
     * @param name
     * @param slots
     */
    public Gui(String name, int slots) {

        if(slots > 54 || slots < 9 || slots % 9 != 0) {
            throw new IllegalArgumentException("Slots must be a multiple of 9. (9, 18, 27, 36, 45, 54)");
        }

        this.slots = slots;
        this.buttons = new ArrayList<>();
        this.name = name;
        this.inventory = Bukkit.createInventory(this, slots, ChatColor.translateAlternateColorCodes('&', name));
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    /***
     * Adds a button to the GUI. Optionally updates the GUI as well.
     * @param button
     * @param update
     * @deprecated Use {@link #addButton(GuiButton)} instead. If you need to update the GUI, use {@link #updateGui()}.
     */
    @Deprecated
    public void addButton(GuiButton button, boolean update) {
        buttons.add(button);
        if (update) {
            this.updateGui();
        }
    }

    /***
     * Adds a button to the GUI.
     * @param button The button you want to add.
     */
    public void addButton(GuiButton button) {
        buttons.add(button);
    }

    public void setBackground(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        this.background = item;
    }

    public void setDefaultBackground() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        item.setDurability((short) 15);
        setBackground(item);
    }

    /***
     * Removes the specified button from the GUI.
     * @param button
     * @param update
     * @deprecated Use {@link #removeButton(GuiButton)} instead. If you need to update the GUI, use {@link #updateGui()}.
     */
    @Deprecated
    public void removeButton(GuiButton button, boolean update) {
        buttons.remove(button);
        if (update) {
            this.updateGui();
        }
    }

    public void removeButton(GuiButton button) {
        buttons.remove(button);
    }


    public GuiButton getButton(int slot) {
        for(GuiButton button : getButtons()) {
            if(button.getSlot() == slot && button.isVisible()) {
                return button;
            }
        }

        return null;
    }

    /***
     * Updates the amount of slots in the GUI.
     * This will also close the GUI for all players viewing it,
     * and reopen it with the new amount of slots.
     * @param slots
     */
    public void updateSlots(int slots) {

        if(slots % 9 != 0 || slots < 9 || slots > 54) {
            throw new IllegalArgumentException("Slots must be a multiple of 9. (9, 18, 27, 36, 45, 54)");
        }

        List<Player> players = new ArrayList<>();
        for(HumanEntity player : inventory.getViewers()) {
            Player p = (Player) player;
            players.add(p);
            p.closeInventory();
        }

        this.slots = slots;

        inventory = Bukkit.createInventory(this, slots, ChatColor.translateAlternateColorCodes('&', name));

        for(Player player : players) {
            open(player);
        }
    }

    /***
     * Opens the GUI for a specified player, as well as updates all the buttons within the GUI.
     * @param player
     */
    public void open(Player player) {
        updateGui();
        player.openInventory(getInventory());

        if(openSound != null) {
            player.playSound(player.getLocation(), openSound, 1, 1);
        }
    }

    /***
     * Updates the GUI with new buttons or updated information.
     */
    public abstract void updateGui();
}
