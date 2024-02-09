package camp.pvp.utils.guis.paginated;

import camp.pvp.utils.buttons.GuiButton;
import camp.pvp.utils.guis.Gui;
import camp.pvp.utils.guis.GuiAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class GuiNextPageAction implements GuiAction
{
    @Override
    public void run(Player player, GuiButton button, Gui gui, ClickType click) {
        if(gui instanceof PaginatedGui) {
            PaginatedGui pgui = (PaginatedGui) gui;
            pgui.nextPage();
        }
    }
}
