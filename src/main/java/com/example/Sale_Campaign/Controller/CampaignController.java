package com.example.Sale_Campaign.Controller;

import com.example.Sale_Campaign.DTO.CampaignRequestDTO;
import com.example.Sale_Campaign.Model.Campaign;
import com.example.Sale_Campaign.Model.CampaignDiscount;
import com.example.Sale_Campaign.Model.ResponseDTO;
import com.example.Sale_Campaign.Service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("campaigns")
public class CampaignController {

    @Autowired
    CampaignService campaignService;
    @PostMapping("save-Campaign")
    public ResponseDTO<Campaign> saveAll(@RequestBody CampaignRequestDTO campaignRequestDTO) {
        return campaignService.saveCampaign(convertToEntity(campaignRequestDTO));
    }

    // Convert DTO to Entity
    private Campaign convertToEntity(CampaignRequestDTO dto) {
        Campaign campaign = new Campaign();
        campaign.setCampaignName(dto.getCampaignName());
        campaign.setStartDate(dto.getStartDate());
        campaign.setEndDate(dto.getEndDate());

        List<CampaignDiscount> discounts = dto.getCampaignDiscounts().stream().map(discountDTO -> {
            CampaignDiscount discount = new CampaignDiscount();
            discount.setProductId(discountDTO.getProductId());
            discount.setDiscount(discountDTO.getDiscount());
            discount.setCampaign(campaign);
            return discount;
        }).toList();

        campaign.setCampaignDiscounts(discounts);
        return campaign;
    }


    @GetMapping("get-All-campaign")
    public ResponseDTO<List<Campaign>> getAllCampaign() {
        return campaignService.getAllCampaigns();
    }

}
