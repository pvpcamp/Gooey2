package camp.pvp.utils.guis.paginated;

import camp.pvp.utils.guis.Gui;
import camp.pvp.utils.guis.GuiAction;
import org.bukkit.entity.Player;

public class GuiNextPageAction implements GuiAction
{
    @Override
    public void run(Player player, Gui gui) {
        if(gui instanceof PaginatedGui) {
            PaginatedGui pgui = (PaginatedGui) gui;
            pgui.nextPage();
        }
    }
}
