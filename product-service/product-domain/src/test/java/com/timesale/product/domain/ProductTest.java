package com.timesale.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.timesale.common.exception.BusinessException;
import com.timesale.product.domain.exception.ProductErrorCode;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("상품 재고를 차감하면 정상적으로 줄어든다.")
    void decreaseStock_success() {
        Product product = Product.builder()
            .name("한정판 운동화")
            .price(200000)
            .stockQuantity(100)
            .openAt(LocalDateTime.now().plusDays(1))
            .build();

        product.decreaseStock(10);

        assertThat(product.getStockQuantity()).isEqualTo(90);
    }

    @Test
    @DisplayName("현재 재고보다 많은 수량을 차감하려고 하면 예외가 발생한다.")
    void decreaseStock_fail_not_enough_stock() {
        Product product = Product.builder()
            .name("한정판 운동화")
            .price(200000)
            .stockQuantity(10)
            .openAt(LocalDateTime.now().plusDays(1))
            .build();

        assertThatThrownBy(() -> product.decreaseStock(11))
            .isInstanceOf(BusinessException.class)
            .hasMessage(ProductErrorCode.OUT_OF_STOCK.getMessage());
    }

}