package com.timesale.product.domain;

import com.timesale.common.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.timesale.product.domain.exception.ProductErrorCode;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stockQuantity;

    // 타임 세일 오픈 시간 (이 시간 전에 주문 불가)
    @Column(nullable = false)
    private LocalDateTime openAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Product(String name, Integer price, Integer stockQuantity, LocalDateTime openAt) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.openAt = openAt;
    }

    public void decreaseQuantity(int quantity) {
        if (stockQuantity < quantity)
            throw new BusinessException(ProductErrorCode.OUT_OF_STOCK);
        stockQuantity -= quantity;
    }

}
