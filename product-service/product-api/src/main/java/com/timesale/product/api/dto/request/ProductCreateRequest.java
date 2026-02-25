package com.timesale.product.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ProductCreateRequest(
    @NotBlank String name,
    @NotNull @Min(0) Integer price,
    @NotNull @Min(1) Integer stockQuantity,
    @NotNull LocalDateTime openAt
    ) {
}
