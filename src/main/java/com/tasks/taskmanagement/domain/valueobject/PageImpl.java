package com.tasks.taskmanagement.domain.valueobject;

import java.util.ArrayList;
import java.util.List;

/**
 * The PageImpl<T> class implements the IPage<T> interface to represent a paginated list of content.
 *
 * @param <T> The type of elements in the page.
 */
public class PageImpl<T> implements IPage<T> {

    private List<T> content;
    private long totalElements;
    private int number;
    private int size;

    /**
     * Constructs a PageImpl<T> instance with the specified content, total elements, page number, and size.
     *
     * @param content       The list of content elements.
     * @param totalElements The total number of elements across all pages.
     * @param number        The current page number (0-based).
     * @param size          The maximum number of elements per page.
     */
    public PageImpl(List<T> content, long totalElements, int number, int size) {
        this.content = content;
        this.totalElements = totalElements;
        this.number = number;
        this.size = size;
    }

    /**
     * Constructs an empty PageImpl<T> instance.
     */
    public PageImpl() {
        this.content = new ArrayList<>();
        this.totalElements = 0;
        this.number = 0;
        this.size = 0;
    }

    /**
     * Get the list of content elements in the page.
     *
     * @return The list of content elements.
     */
    @Override
    public List<T> getContent() {
        return content;
    }

    /**
     * Set the list of content elements in the page.
     *
     * @param content The list of content elements.
     */
    @Override
    public void setContent(List<T> content) {
        this.content = content;
    }

    /**
     * Get the total number of elements across all pages.
     *
     * @return The total number of elements.
     */
    @Override
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Set the total number of elements across all pages.
     *
     * @param totalElements The total number of elements.
     */
    @Override
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * Get the current page number (0-based).
     *
     * @return The current page number.
     */
    @Override
    public int getNumber() {
        return number;
    }

    /**
     * Set the current page number (0-based).
     *
     * @param number The current page number.
     */
    @Override
    public void setNumber(int number) {
        this.number = number;
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
