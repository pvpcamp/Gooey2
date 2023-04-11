package network.monki.utils.buttons;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import network.monki.utils.guis.GuiAction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiButton extends ItemStack {

    private @Getter @Setter String name;
    private @Getter @Setter boolean visible;
    private @Getter @Setter int slot;
    private @Getter @Setter GuiAction action;
    private @Getter @Setter AbstractButtonUpdater buttonUpdater;

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
