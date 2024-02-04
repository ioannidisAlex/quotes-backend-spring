package dev.ioalex.quote.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public class PaginationDTO {

    @Min(0)
    private int page;

    @Positive
    private int pageSize;

    public PaginationDTO(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
