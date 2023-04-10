package network.monki.utils.buttons;

import lombok.Data;
import network.monki.utils.guis.GuiAction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public @Data class GuiButton extends ItemStack {

    private String name;
    private boolean visible;
    private int slot;
    private GuiAction action;
    private AbstractButtonUpdater buttonUpdater;

    public GuiButton(Material material) {
        super(material);
        this.visible = true;
        slot = 0;
    }
    public GuiButton(Material material, String name) {
        this(material);
        updateName(name);
    }

    public void updateName(String name) {
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.setItemMeta(meta);
    }
}
