package org.example._14springtesting.service.impl;

import lombok.RequiredArgsConstructor;
import org.example._14springtesting.entity.Product;
import org.example._14springtesting.repository.ProductRepository;
import org.example._14springtesting.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found with id:"+id)
        );
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product exitsProduct = getProductById(product.getId());
        exitsProduct.setName(product.getName());
        exitsProduct.setPrice(product.getPrice());
        exitsProduct.setQuantity(product.getQuantity());
        return productRepository.save(exitsProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
