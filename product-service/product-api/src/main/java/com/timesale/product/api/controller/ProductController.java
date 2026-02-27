package com.timesale.product.api.controller;

import com.timesale.product.api.dto.request.ProductCreateRequest;
import com.timesale.product.api.dto.response.ProductResponse;
import com.timesale.product.application.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody ProductCreateRequest request) {
        Long productId = productService.registerProduct(
            request.name(),
            request.price(),
            request.stockQuantity(),
            request.openAt()
        );

        return ResponseEntity.ok(productId);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> responses = productService.getProducts()
            .stream()
            .map(ProductResponse::from)
            .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{id}/decrease-stock")
    public ResponseEntity<?> decreaseStock(@PathVariable("id") Long productId,
        @RequestParam("quantity") Integer quantity
    ) {

        productService.decreaseProductStock(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/increase-stock")
    public ResponseEntity<?> increaseStock(@PathVariable("id") Long productId,
        @RequestParam("quantity") Integer quantity) {

        productService.increaseProductStock(productId, quantity);
        return ResponseEntity.ok().build();
    }

}
