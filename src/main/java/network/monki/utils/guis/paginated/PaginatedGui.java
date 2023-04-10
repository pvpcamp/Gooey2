package network.monki.utils.guis.paginated;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import network.monki.utils.buttons.AbstractButtonUpdater;
import network.monki.utils.buttons.GuiButton;
import network.monki.utils.guis.Gui;
import org.bukkit.Material;

public class PaginatedGui extends Gui {

    private @Getter @Setter int currentPage;
    private @Getter @Setter GuiButton nextPageButton, previousPageButton;

    /***
     * Creates a new GUI and imports it into the Gooey2 list.
     * @param name
     * @param slots
     */
    public PaginatedGui(String name, int slots) {
        super(name, slots);

        this.nextPageButton = new GuiButton(Material.ARROW);
        this.nextPageButton.setAction(new GuiNextPageAction());
        this.nextPageButton.setButtonUpdater((button, gui) -> {
            if(gui instanceof PaginatedGui) {
                // Page number code
            }
        });

        this.previousPageButton = new GuiButton(Material.ARROW);
        this.previousPageButton.setAction(new GuiPreviousPageAction());
        this.previousPageButton.setButtonUpdater((button, gui) -> {
            if(gui instanceof PaginatedGui) {
                // Page number code
            }
        });
    }

    @Override
    public GuiButton getButton(int slot) {

        if(getSlots() - 9 < slot) {
            int s = slot - (getSlots() - 10);
            if(nextPageButton.getSlot() == s) {
                return nextPageButton;
            }

            if(previousPageButton.getSlot() == s) {
                return previousPageButton;
            }

            return null;
        }

        if(isVariableSize()) {
            // Variable size code
        } else {
            int startFrom = getSlots() - (9 * (currentPage + 1));
            int i = startFrom + slot;
            if(getButtons().size() > i) {
                 return getButtons().get(i);
            } else {
                return null;
            }
        }

        return null;
    }

    @Override
    public void updateGui() {

        if(isVariableSize()) {
            // Variable size code
        } else {
            int startFrom = getSlots() - (9 * (currentPage + 1));
            final int totalPages = getPages();
            for(int slot = 0; slot < getSlots() - 9; slot++) {
                int x = startFrom + slot;
                if(getButtons().size() > x) {
                    GuiButton button = getButtons().get(x);
                    inventory.setItem(x, button);
                } else {
                    break;
                }
            }
        }

        final int s = getSlots() - 9;
        if(currentPage < totalPages) {
            nextPageButton.getButtonUpdater().update(nextPageButton, this);
            inventory.setItem(s + nextPageButton.getSlot(), nextPageButton);
        }

        if(currentPage != 0) {
            previousPageButton.getButtonUpdater().update(previousPageButton, this);
            inventory.setItem(s + previousPageButton.getSlot(), previousPageButton);
        }
    }

    public int getPages() {
        int i = 0;
        while(i * (this.getSize() - 9) < this.getButtons().size()) {
            i++;
        }
        return i;
    }

    public void goToPage(int page) {
        currentPage = page;
        updateGui();
    }

    public boolean nextPage() {
        if(currentPage < getPages()) {
            goToPage(currentPage + 1);
            return true;
        } else {
            return false;
        }
    }

    public boolean previousPage() {
        if(currentPage > 0) {
            goToPage(currentPage - 1);
            return true;
        } else {
            return false;
        }
    }
}
