package com.timesale.product.api.dto.response;

import com.timesale.product.domain.Product;
import java.time.LocalDateTime;

public record ProductResponse(
    Long id,
    String name,
    Integer price,
    Integer stockQuantity,
    LocalDateTime openAt
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getOpenAt()
        );
    }

}
