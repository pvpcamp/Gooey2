package camp.pvp.utils.guis.paginated;

import camp.pvp.utils.guis.Gui;
import lombok.Getter;
import lombok.Setter;
import camp.pvp.utils.buttons.GuiButton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PaginatedGui extends Gui {

    private @Getter @Setter int currentPage;
    private @Getter @Setter GuiButton nextPageButton, previousPageButton;
    private @Getter List<GuiButton> navigationButtons = new ArrayList<>();
    private @Getter @Setter boolean border;

    /***
     * Creates a new GUI and imports it into the Gooey2 list.
     * @param name
     * @param slots
     */
    public PaginatedGui(String name, int slots) {
        super(name, slots);

        nextPageButton = new GuiButton(Material.ARROW, "&6&lNext Page");
        nextPageButton.setAction(new GuiNextPageAction());
        nextPageButton.setButtonUpdater((button, gui) -> {
            if(gui instanceof PaginatedGui paginatedGui) {
                int newPage = paginatedGui.getCurrentPage() + 2;
                button.setLore("&7Click to go to page &f" + newPage + "&7.");
            }
        });

        previousPageButton = new GuiButton(Material.ARROW, "&6&lPrevious Page");
        previousPageButton.setAction(new GuiPreviousPageAction());
        previousPageButton.setButtonUpdater((button, gui) -> {
            if(gui instanceof PaginatedGui paginatedGui) {
                int newPage = paginatedGui.getCurrentPage();
                button.setLore("&7Click to go to page &f" + newPage + "&7.");
            }
        });

        this.currentPage = 0;

        setDefaultBackground();
    }

    /***
     * Adds a button to the navigation bar.
     * @param button
     */
    public void addNavigationButton(GuiButton button) {
        navigationButtons.add(button);
    }

    @Override
    public GuiButton getButton(int slot) {
        if(slot < 9) {
            if(slot == 0) return previousPageButton;
            if(slot == 8) return nextPageButton;

            return navigationButtons.stream().filter(button -> button.getSlot() == slot).findFirst().orElse(null);
        } else {
            // Declares where to start looking for buttons in the list.
            int startPos = getButtonsPerPage() * currentPage;
            int s = slot - 9;
            if(border) {
                // If the border is enabled, we need to skip the border slots.
                if(slot == 9 || s % 9 == 0 || (s + 1) % 9 == 0) return null;

                // Adjustment to fix the slot number.
                s--;


                // If the slot is in the first or last column, we need to adjust the slot number.
                for(int i = 8; i < slot - 9; i++) {
                    if(i % 9 == 0 || (i + 1) % 9 == 0) {
                        s--;
                    }
                }
            }

            int buttonNumber = s + startPos;

            if(buttonNumber >= getButtons().size()) return null;

            return getButtons().get(s + (currentPage * getButtonsPerPage()));
        }
    }

    @Override
    public void updateGui() {

        getInventory().clear();

        final int totalPages = getPages();
        final int rows = getSlots() / 9;

        if(border) {
            // Form the border from the background item.
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < 9; column++) {
                    int slot = (row * 9) + column;
                    if (row == 0 || row + 1 == rows) {
                        if (getInventory().getItem(slot) == null) {
                            getInventory().setItem((row * 9) + column, getBackground());
                        }
                        continue;
                    }

                    if (column == 0 || column == 8) {
                        if (getInventory().getItem(slot) == null) {
                            getInventory().setItem(slot, getBackground());
                        }
                    }
                }
            }
        } else {
            // If border is not enabled, just fill the top row with the background item to
            // make it look like a navigation bar.
            for(int x = 0; x < 9; x++) {
                getInventory().setItem(x, getBackground());
            }
        }


        // Set the buttons in their positions.
        int buttonSlots = border ? getSlots() - 9 : getSlots();
        int buttonNumber = currentPage * getButtonsPerPage();
        for(int x = 9; x < buttonSlots; x++) {
            // Skip slots where the border sits.
            if(border && (x % 9 == 0 || (x + 1) % 9 == 0)) continue;

            if(getButtons().size() <= buttonNumber) break;

            GuiButton button = getButtons().get(buttonNumber);

            if(button.getButtonUpdater() != null) {
                button.getButtonUpdater().update(button, this);
            }

            getInventory().setItem(x, button);
            buttonNumber++;
        }

        for(GuiButton button : getNavigationButtons()) {
            getInventory().setItem(button.getSlot(), button);
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

    /***
     * Calculates the total number of pages.
     */
    public int getPages() {
        int i = 0;
        while(i * getButtonsPerPage() < this.getButtons().size()) {
            i++;
        }
        return i;
    }

    /***
     * Goes to the specified page, then calls {@link #updateGui()}.
     * @param page
     */
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

    /***
     * This number can change based on the border and navigation bar.
     * @return Number of buttons that can fit on a single page.
     */
    private int getButtonsPerPage() {
        int slots = getSlots() - 9;
        int rows = slots / 9;
        return border ? (slots - 9) - ((rows - 1) * 2) : slots;
    }
}
