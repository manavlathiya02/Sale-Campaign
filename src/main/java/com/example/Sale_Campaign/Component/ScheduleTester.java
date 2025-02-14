package com.example.Sale_Campaign.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTester implements CommandLineRunner {

    @Autowired
    private PriceAdjustmentScheduler priceAdjustmentScheduler;

    @Override
    public void run(String...args) throws Exception{
        System.out.println("Manually running adjustProductPrice ...");
        priceAdjustmentScheduler.AdjustPrice();

        System.out.println("Manually running revert Price");
        priceAdjustmentScheduler.revertPrice();
    }


}

