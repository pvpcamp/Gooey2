package camp.pvp.utils;

import camp.pvp.utils.guis.Gui;
import camp.pvp.utils.guis.GuiUpdater;
import camp.pvp.utils.listeners.InventoryClickListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Gooey2Plugin extends JavaPlugin {

    public static @Getter Gooey2Plugin instance;
    public @Getter List<Gui> guis;

    @Override
    public void onEnable() {
        instance = this;
        this.guis = new ArrayList<>();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new GuiUpdater(this), 2, 2);
        new InventoryClickListener(this);

        getLogger().info("Gooey2 has been successfully initialized.");
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void createGui() {

    }

}
