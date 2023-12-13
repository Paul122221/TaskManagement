package com.tasks.taskmanagement.domain.valueobject;

/**
 * The IPageable interface defines a value object for specifying pagination parameters.
 */
public interface IPageable {

    /**
     * Get the page number (0-based).
     *
     * @return The page number.
     */
    int getPage();

    /**
     * Set the page number (0-based).
     *
     * @param page The page number.
     */
    void setPage(int page);

    /**
     * Get the maximum number of elements per page.
     *
     * @return The maximum number of elements per page.
     */
    int getSize();

    /**
     * Set the maximum number of elements per page.
     *
     * @param size The maximum number of elements per page.
     */
    void setSize(int size);
}
