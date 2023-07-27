package camp.pvp.utils.guis.paginated;

import camp.pvp.utils.guis.Gui;
import lombok.Getter;
import lombok.Setter;
import camp.pvp.utils.buttons.GuiButton;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
                PaginatedGui pgui = (PaginatedGui) gui;
                if(pgui.getCurrentPage() + 1 < getPages()) {
                    button.updateName("&aPage " + (pgui.getCurrentPage() + 2));
                } else {
                    button.updateName("&cFinal page reached.");
                }
            }
        });

        this.previousPageButton = new GuiButton(Material.ARROW);
        this.previousPageButton.setAction(new GuiPreviousPageAction());
        this.previousPageButton.setButtonUpdater((button, gui) -> {
            if(gui instanceof PaginatedGui) {
                PaginatedGui pgui = (PaginatedGui) gui;
                if(pgui.getCurrentPage() == 0) {
                    button.updateName("&cFinal page reached.");
                } else {
                    button.updateName("&aPage " + (pgui.getCurrentPage()));
                }
            }
        });

        this.currentPage = 0;
    }

    @Override
    public GuiButton getButton(int slot) {
        if(slot < 9) {
            switch(slot) {
                case 0:
                    return previousPageButton;
                case 8:
                    return nextPageButton;
            }
        } else {
            int start = slot + (currentPage * (this.getSlots() - 9));
            if(buttons.size() > start - 9) {
                return buttons.get(start - 9);
            }
        }
        return null;
    }

    @Override
    public void updateGui() {

        final int totalPages = getPages();

        getInventory().clear();

        int start = currentPage * (this.getSlots() - 9);
        for(int i = 0; i < this.getSlots() - 9; i++) {
            if(getButtons().size() > start + i) {
                GuiButton button = this.getButtons().get(start + i);
                if(button != null) {
                    if(button.getButtonUpdater() != null) {
                        button.getButtonUpdater().update(button, this);
                    }

                    getInventory().setItem(i + 9, button);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        for(int x = 0; x < 9; x++) {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
            item.setDurability((short) 15);
            getInventory().setItem(x, item);
        }

        if(currentPage + 1 < totalPages) {
            nextPageButton.getButtonUpdater().update(nextPageButton, this);
            getInventory().setItem(8, nextPageButton);
        }

        if(currentPage > 0) {
            previousPageButton.getButtonUpdater().update(previousPageButton, this);
            getInventory().setItem(0, previousPageButton);
        }
    }

    public int getPages() {
        int i = 0;
        while(i * (this.getSlots() - 9) < this.getButtons().size()) {
            i++;
        }
        return i;
    }

    public void goToPage(int page) {
        currentPage = page;
        updateGui();
    }

    public boolean nextPage() {
        if(currentPage + 1 < getPages()) {
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
