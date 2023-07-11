package camp.pvp.utils.buttons;

import camp.pvp.utils.guis.GuiAction;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiButton extends ItemStack {

    private @Getter @Setter boolean visible, closeOnClick;
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

    public GuiButton(ItemStack item) {
        super(item);

        this.visible = true;
    }

    public GuiButton(ItemStack item, String name) {
        this(item);

        updateName(name);
    }

    public void updateName(String name) {
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.setItemMeta(meta);
    }

    public void setLore(String... strings) {
        ItemMeta meta = this.getItemMeta();
        List<String> list = new ArrayList<>();
        for(String s : strings) {
            list.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        meta.setLore(list);
        this.setItemMeta(meta);
    }
}
