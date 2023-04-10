package network.monki.utils.guis;

import lombok.Data;
import network.monki.utils.Gooey2Plugin;
import network.monki.utils.buttons.GuiButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public @Data abstract class Gui implements InventoryHolder {

    private String name;
    private int slots;
    private boolean autoUpdate, variableSize;
    private ItemStack background;
    private Inventory inventory;
    private List<GuiButton> buttons;

    /***
     * Creates a new GUI with variable slot sizes.
     * @param name
     * @param slots
     */
    public Gui(String name) {
        this.name = name;
        this.background = null;
        this.inventory = Bukkit.createInventory(this, slots);
        this.buttons = new ArrayList<>();

        Gooey2Plugin.getInstance().getGuis().add(this);

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
        player.openInventory(getInventory());
        updateGui();
    }

    /***
     * Updates the GUI with new buttons or updated information.
     */
    public abstract void updateGui();
}
