package com.example.Sale_Campaign.Service;

import com.example.Sale_Campaign.Model.PriceHistory;
import com.example.Sale_Campaign.Model.Product;
import com.example.Sale_Campaign.Model.ResponseDTO;
import com.example.Sale_Campaign.Repository.CampaignRepository;
import com.example.Sale_Campaign.Repository.PriceHistoryRepository;
import com.example.Sale_Campaign.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository  productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;


  public ResponseDTO<List<Product>>saveAllProduct(List<Product>Products){
      try {
          List<Product> productsList = productRepository.saveAll(Products);
          for (Product products : productsList){
              double discountAmount =  (products.getCurrentPrice() * (products.getDiscount() / 100));
              saveHistory(products, products.getCurrentPrice(), LocalDate.now(), discountAmount);
          }
          return new ResponseDTO<>(productsList, HttpStatus.OK, "Successfully saved");
      } catch (Exception e) {
          return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to save product" + e.getMessage());
      }
  }


    public ResponseDTO<Product> deleteProductById(Integer id){
      productRepository.deleteById(id);
   return new ResponseDTO<>(null,HttpStatus.OK,"deleted Successfully");
  }

    public ResponseDTO<Product> updateProductPrice(int productId, double price) {
        try {
            Product product = productRepository.findById(productId).orElse(null);
            if (product == null) {
                return new ResponseDTO<>(null, HttpStatus.NOT_FOUND, "Product not found");
            }
            if (product.getCurrentPrice() != price) {
                product.setCurrentPrice(price);
                productRepository.save(product);

                // Fix: Round discount price properly
                double discountAmount = Math.round((product.getCurrentPrice() * (product.getDiscount() / 100.0)) * 100.0) / 100.0;
                saveHistory(product, price, LocalDate.now(), discountAmount);
            }
            return new ResponseDTO<>(product, HttpStatus.OK, "Product price updated successfully");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update product price: " + e.getMessage());
        }
    }


    public ResponseDTO<Page<Product>>getAllPaginated(int page, int size){
        try{
            Pageable pageable= PageRequest.of(page-1,size);
            Page<Product>housePage=productRepository.findAll(pageable);
            return new ResponseDTO<>(housePage,HttpStatus.OK,"get product");
        }catch (Exception e){
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR,"failed to get products"+e.getMessage());
        }

    }



    public ResponseDTO<Product> saveProduct(Product product){
        try {
            Product saveProduct=productRepository.save(product);
            double discountAmount = (product.getCurrentPrice() * (product.getDiscount() / 100.0));
           saveHistory(product,product.getCurrentPrice(),LocalDate.now(),discountAmount);
            return new ResponseDTO<>(saveProduct, HttpStatus.OK, "Product saved successfully");

        }catch (Exception e){
        return new ResponseDTO<>(null,HttpStatus.INTERNAL_SERVER_ERROR,"failed to save product" +e.getMessage());
        }
    }

    public ResponseDTO<List<Product>>getProductList(){
      try{
          return new ResponseDTO<>(productRepository.findAll(),HttpStatus.OK,"product List");

      }catch (Exception e){
      return new ResponseDTO<>(null,HttpStatus.INTERNAL_SERVER_ERROR,"failed to get " +e.getMessage());
      }
    }


   public void saveHistory(Product product, double Price, LocalDate date,double discountPrice){
       PriceHistory priceHistory=new PriceHistory();
       priceHistory.setProduct(product);
       priceHistory.setPrice(Price);
       priceHistory.setLocalDate(date);
       priceHistory.setDiscountPrice(discountPrice);
       priceHistoryRepository.save(priceHistory);
   }

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
