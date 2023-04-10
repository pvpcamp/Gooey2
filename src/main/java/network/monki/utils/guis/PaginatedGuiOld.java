package network.monki.utils.guis;

public class PaginatedGuiOld extends StandardGuiOld {

    private int currentPage;

    /***
     * Create a new paginated GUI.
     * @param title
     * @param size
     */
    public PaginatedGuiOld(String title, int size) {
        super(title, size);
    }

    public int getPages() {
        int i = 0;
        while(i * (this.getSize() - 9) < this.getButtons().size()) {
            i++;
        }
        return i;
    }

    public void goToPage(int page) {

    }

    public void nextPage() {

    }

    public void previousPage() {

    }
}
