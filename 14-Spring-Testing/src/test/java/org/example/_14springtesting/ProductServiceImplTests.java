package org.example._14springtesting;

import org.example._14springtesting.entity.Product;
import org.example._14springtesting.repository.ProductRepository;
import org.example._14springtesting.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTests {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    public void setUp() {
        product = Product.builder().id(1L).name("Product 1").price(100.00).quantity(12).build();
    }

    @Test
    void shouldSaveProduct(){
        //arrange
        when(productRepository.save(any(Product.class))).thenReturn(product);
        //action
        Product saveProduct = productService.createProduct(product);
        //assert = ape test case eka pass da? failed da? kiyl blnne meken
        Assertions.assertNotNull(product);
        Assertions.assertEquals(product,saveProduct);
       // Assertions.assertEquals(1L,saveProduct.getId());
        verify(productRepository,times(1)).save(any(Product.class));

    }

    @Test
    void shouldUpdateProduct() {
        //arrange
        Product updateProduct = Product.builder().id(1L).name("Product 2").price(100.00).quantity(12).build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        //action
        Product result = productService.updateProduct(updateProduct);

        //assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(updateProduct,result);
        Assertions.assertEquals("Product 2",result.getName());
        Assertions.assertEquals(100.00,result.getPrice());
        Assertions.assertEquals(12,result.getQuantity());
        verify(productRepository,times(1)).findById(1L);
    }

    @Test
    void shouldReturnAllProducts() {
        Product product1 = Product.builder().id(1L).name("P1").price(11.11).quantity(2).build();
        Product product2 = Product.builder().id(2L).name("P2").price(22.22).quantity(2).build();
        List<Product> mockProductList = Arrays.asList(product1, product2);

        // Mocking the behavior of productRepository
        when(productRepository.findAll()).thenReturn(mockProductList);

        // Action
        List<Product> products = productService.getAllProducts();

        // Assertion
        Assertions.assertEquals(2, products.size());
        Assertions.assertEquals("P1", products.get(0).getName());
        Assertions.assertEquals("P2", products.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

}
