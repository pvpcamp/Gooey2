package network.monki.utils.guis.paginated;

import network.monki.utils.buttons.GuiButton;
import network.monki.utils.guis.Gui;
import network.monki.utils.guis.GuiAction;
import org.bukkit.entity.Player;

public class GuiPreviousPageAction implements GuiAction {
    @Override
    public void run(Player player, Gui gui) {
        if(gui instanceof PaginatedGui) {
            PaginatedGui pgui = (PaginatedGui) gui;
            pgui.previousPage();
        }
    }
}
