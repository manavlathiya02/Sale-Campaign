package com.example.Sale_Campaign.Controller;

import com.example.Sale_Campaign.Model.Product;
import com.example.Sale_Campaign.Model.ResponseDTO;
import com.example.Sale_Campaign.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("save-product")
    public ResponseDTO<Product> saveProduct(@RequestBody  Product product){
        return productService.saveProduct(product);
    }

    @PostMapping("save-All-Product")
    public ResponseDTO<List<Product>>saveAll(@RequestBody List<Product>products){
     return productService.saveAllProduct(products);
    }

    @GetMapping("get-All-Products")
      public ResponseDTO<List<Product>>getAllProduct(){
        return productService.getProductList();
    }

    @PutMapping("update-Price")
    public ResponseDTO<Product> updateProductPrice(
            @RequestParam("productId") int productId,
            @RequestParam("price") double price) {
        return productService.updateProductPrice(productId, price);
    }


    @GetMapping("get-All-Paginated")
    public ResponseDTO<Page<Product>>getAllProductPaginated(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "3")int size){
        return productService.getAllPaginated(page,size);
    }

    @DeleteMapping("deleteById/{id}")
  public ResponseDTO<Product>deleteProduct(@PathVariable Integer id){
        return productService.deleteProductById(id);
    }
}
