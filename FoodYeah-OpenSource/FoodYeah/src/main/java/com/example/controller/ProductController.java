package com.example.controller;

import com.example.entity.Category;
import com.example.entity.Product;
import com.example.service.ProductService;
import com.example.util.ErrorMessage;
import com.example.util.Message;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name="categoryId",required = false)Long categoryId){
        List<Product> products=new ArrayList<>();
        if(null==categoryId){
            products=productService.listAllProduct();
            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            products=productService.findByCategory(Category.builder().id(categoryId).build());
            if(products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    @ApiOperation(value="Obtener un producto por su ID", notes="Provee un mecanismo para obtener todos los datos de un producto por su ID")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="OK", response=Product.class),
            @ApiResponse(code=404, message="Not Found", response= ErrorMessage.class),
            @ApiResponse(code=500, message="Internal Server Error", response=ErrorMessage.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product=productService.getProduct(id);
        if(null==product){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.formatMessage(result));
        }
        Product productCreate=productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        product.setId(id);
        Product productDB=productService.updateProduct(product);
        if(productDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product productDelete=productService.deleteProduct(id);
        if(productDelete==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id,
                                                      @RequestParam(name="quantity", required = true) Double quantity){
        Product product=productService.updateStock(id,quantity);
        if(product==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
}
