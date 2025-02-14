package com.example.Sale_Campaign.DTO;

import java.time.LocalDate;
import java.util.List;

public class CampaignRequestDTO {
    private String campaignName;
    private LocalDate startDate;
    private LocalDate endDate;

    private List<CampaignDiscountRequestDTO> campaignDiscounts; // Optional list of discounts

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<CampaignDiscountRequestDTO> getCampaignDiscounts() {
        return campaignDiscounts;
    }

    public void setCampaignDiscounts(List<CampaignDiscountRequestDTO> campaignDiscounts) {
        this.campaignDiscounts = campaignDiscounts;
    }

    public static class CampaignDiscountRequestDTO {
        private int productId;
        private float discount;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public float getDiscount() {
            return discount;
        }

        public void setDiscount(float discount) {
            this.discount = discount;
        }
    }
}
