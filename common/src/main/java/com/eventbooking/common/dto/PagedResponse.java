package com.eventbooking.common.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;      // The actual list of items (Bookings, Events, etc.)
    private int pageNumber;       // Current page (0-indexed)
    private int pageSize;         // Items per page
    private long totalElements;   // Total items in database
    private int totalPages;       // Total pages available
    private boolean last;         // Is this the last page?
}