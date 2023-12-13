package com.tasks.taskmanagement.domain.valueobject;

/**
 * The PageableImpl class implements the IPageable interface to specify pagination parameters.
 */
public class PageableImpl implements IPageable {

    private int page;
    private int size;

    /**
     * Constructs a PageableImpl instance with the specified page number and size.
     *
     * @param page The page number (0-based).
     * @param size The maximum number of elements per page.
     */
    public PageableImpl(int page, int size) {
        this.page = page;
        this.size = size;
    }

    /**
     * Get the page number (0-based).
     *
     * @return The page number.
     */
    @Override
    public int getPage() {
        return page;
    }

    /**
     * Set the page number (0-based).
     *
     * @param page The page number.
     */
    @Override
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Get the maximum number of elements per page.
     *
     * @return The maximum number of elements per page.
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Set the maximum number of elements per page.
     *
     * @param size The maximum number of elements per page.
     */
    @Override
    public void setSize(int size) {
        this.size = size;
    }
}
