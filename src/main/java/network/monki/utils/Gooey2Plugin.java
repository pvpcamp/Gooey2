package network.monki.utils;

import lombok.Getter;
import network.monki.utils.guis.Gui;
import network.monki.utils.guis.GuiUpdater;
import network.monki.utils.listeners.InventoryClickListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
