package network.monki.utils.guis.paginated;

import network.monki.utils.guis.Gui;
import network.monki.utils.guis.GuiAction;
import network.monki.utils.guis.PaginatedGuiOld;
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
