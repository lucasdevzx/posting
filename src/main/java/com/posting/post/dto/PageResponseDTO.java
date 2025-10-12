package com.posting.post.dto;

import java.util.List;

public class PageResponseDTO<T> {

    private List<T> data;

    private int pageNumber;      // Número da página atual
    private int pageSize;        // Tamanho da página
    private long totalElements;  // Número total de itens (em todas as páginas)
    private int totalPages;      // Número total de páginas disponíveis
    private boolean isLast;      // Indica se é a última página

    public PageResponseDTO() {
    }

    public PageResponseDTO(List<T> data, int pageNumber, int pageSize, long totalElements, int totalPages, boolean isLast) {
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLast = isLast;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
