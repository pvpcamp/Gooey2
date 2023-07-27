package camp.pvp.utils;

import camp.pvp.utils.guis.Gui;
import camp.pvp.utils.guis.GuiUpdater;
import camp.pvp.utils.listeners.InventoryClickListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Gooey2 extends JavaPlugin {

    public static @Getter Gooey2 instance;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new GuiUpdater(this), 0, 4);
        new InventoryClickListener(this);

        getLogger().info("Gooey2 has been successfully initialized.");
    }

    @Override
    public void onDisable() {
        instance = null;
    }

}
