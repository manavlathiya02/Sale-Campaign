package com.example.Sale_Campaign.Component;
import com.example.Sale_Campaign.Model.Campaign;
import com.example.Sale_Campaign.Model.CampaignDiscount;
import com.example.Sale_Campaign.Model.PriceHistory;
import com.example.Sale_Campaign.Model.Product;
import com.example.Sale_Campaign.Repository.CampaignRepository;
import com.example.Sale_Campaign.Repository.PriceHistoryRepository;
import com.example.Sale_Campaign.Repository.ProductRepository;
import com.example.Sale_Campaign.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceAdjustmentScheduler {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    PriceHistoryRepository priceHistoryRepository;
    @Autowired
    ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(PriceAdjustmentScheduler.class);

    @Scheduled(cron = "*/5 * * * * *")

    public void AdjustPrice(){

        LocalDate today= LocalDate.now();
        List<Campaign> activeSales=campaignRepository.findAllByStartDate(today);

        logger.info("Running AdjustPrice() at {}", LocalDateTime.now());
        if (activeSales.isEmpty()) {
            logger.info("No active sales campaigns found for today.");
            return;
        }

        for(Campaign campaign :activeSales){
            List<CampaignDiscount>discounts=campaign.getCampaignDiscounts();

            for(CampaignDiscount discount:discounts){
                Product product=productRepository.findById(discount.getProductId()).orElse(null);
                if (product!=null){
                    double discountAmount=(product.getCurrentPrice()*(discount.getDiscount()/100));
                    double newPrice=(product.getCurrentPrice()-discountAmount);

                    if (newPrice >= 0) {
                        product.setCurrentPrice(newPrice);
                        product.setDiscount(discount.getDiscount());
                        productRepository.save(product);
                       productService.saveHistory(product, newPrice, LocalDate.now(), discountAmount);
                        logger.info("Adjusted price for Product ID {}: New Price = {}", product.getId(), newPrice);
                    }
                }
            }
        }
    }


    @Scheduled(cron = "*/5 * * * * *")
    public void revertPrice(){
   LocalDate today=LocalDate.now();
   List<Campaign>endedSales=campaignRepository.findAllByEndDate(today);

        logger.info("Running revertPrice() at {}", LocalDateTime.now());
        if (endedSales.isEmpty()) {
            logger.info("No campaigns ended today.");
            return;
        }

   for(Campaign  campaign:endedSales){
       List<CampaignDiscount>discounts=campaign.getCampaignDiscounts();
       for (CampaignDiscount discount:discounts){
           Product product=productRepository.findById(discount.getProductId()).orElse(null);
           if(product!=null){
               LocalDate date=campaign.getStartDate();
               PriceHistory priceHistory=priceHistoryRepository.findTopByProductIdAndDate(product.getId(),date);
               if(priceHistory!=null){
                   double previousPrice = priceHistory.getDiscountPrice();
                   double revertedPrice = previousPrice + priceHistory.getPrice();
                   product.setCurrentPrice(revertedPrice);
                   productRepository.save(product);
                   productService.saveHistory(product, revertedPrice, LocalDate.now(), priceHistory.getDiscountPrice());
                   logger.info("Reverted price for Product ID {}: Restored Price = {}", product.getId(), revertedPrice);
               }
           }
       }
      }
    }
}
