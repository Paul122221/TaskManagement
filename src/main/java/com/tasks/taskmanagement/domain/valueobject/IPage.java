package com.tasks.taskmanagement.domain.valueobject;

import java.util.List;

/**
 * The IPage interface defines a value object for paginated data.
 *
 * @param <T> The type of elements in the page.
 */
public interface IPage<T> {

    /**
     * Get the list of content elements in the page.
     *
     * @return The list of content elements.
     */
    List<T> getContent();

    /**
     * Set the list of content elements in the page.
     *
     * @param content The list of content elements.
     */
    void setContent(List<T> content);

    /**
     * Get the total number of elements across all pages.
     *
     * @return The total number of elements.
     */
    long getTotalElements();

    /**
     * Set the total number of elements across all pages.
     *
     * @param totalElements The total number of elements.
     */
    void setTotalElements(long totalElements);

    /**
     * Get the current page number (0-based).
     *
     * @return The current page number.
     */
    int getNumber();

    /**
     * Set the current page number (0-based).
     *
     * @param number The current page number.
     */
    void setNumber(int number);

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
