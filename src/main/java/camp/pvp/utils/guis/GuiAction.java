package camp.pvp.utils.guis;

import camp.pvp.utils.buttons.GuiButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface GuiAction {

    void run(Player player, GuiButton button, Gui gui, ClickType click);
}
