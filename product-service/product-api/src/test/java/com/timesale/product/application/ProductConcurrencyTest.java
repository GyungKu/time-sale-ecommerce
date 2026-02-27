package com.timesale.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.timesale.product.domain.Product;
import com.timesale.product.domain.port.ProductRepository;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductConcurrencyTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductLockFacade productLockFacade;

    private Long savedProductId;

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
            .name("한정판 운동화")
            .price(200000)
            .stockQuantity(100)
            .openAt(LocalDateTime.now())
            .build();
        savedProductId = productRepository.save(product).getId();
    }

    // 테스트 후 DB 초기화
    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("100명이 동시에 주문을 해도 비관적 락으로 인해 성공한다.")
    void decreaseStock_concurrency_success() throws InterruptedException {

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 100번의 재고 차감(주문) 요청을 비동기로 동시에 실행
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    productLockFacade.decreaseStock(savedProductId, 1);
                } finally {
                    latch.countDown(); // 스레드 작업이 하나 끝날 때마다 숫자 감소
                }
            });
        }

        latch.await(); // 100개의 작업이 모두 끝날 때까지 대기

        Product updatedProduct = productRepository.findById(savedProductId).orElseThrow();
        assertThat(updatedProduct.getStockQuantity()).isEqualTo(0);
    }

}