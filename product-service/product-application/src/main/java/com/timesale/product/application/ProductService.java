package com.timesale.product.application;

import com.timesale.common.exception.BusinessException;
import com.timesale.product.domain.Product;
import com.timesale.product.domain.exception.ProductErrorCode;
import com.timesale.product.domain.port.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long registerProduct(String name, Integer price, Integer stockQuantity,
        LocalDateTime openAt) {

        Product product = Product.builder()
            .name(name)
            .price(price)
            .stockQuantity(stockQuantity)
            .openAt(openAt)
            .build();

        return productRepository.save(product).getId();
    }

    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @DistributedLock(key = "'product:lock:' + #productId")
    @Transactional
    public void decreaseProductStock(Long productId, Integer quantity) {
        Product product = getById(productId);
        product.decreaseStock(quantity);
    }

    @Transactional
    public void increaseProductStock(Long productId, Integer quantity) {
        Product product = getByIdWithPessimisticLock(productId);
        product.increaseStock(quantity);
    }

    private Product getById(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new BusinessException(ProductErrorCode.NOT_FOUND));
    }

    private Product getByIdWithPessimisticLock(Long productId) {
        return productRepository.findByIdWithPessimisticLock(productId)
            .orElseThrow(() -> new BusinessException(ProductErrorCode.NOT_FOUND));
    }

}
