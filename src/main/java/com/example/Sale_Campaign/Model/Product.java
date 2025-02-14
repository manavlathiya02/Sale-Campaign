package com.example.Sale_Campaign.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.util.List;
import java.util.UUID;

@Entity
public class Product {
    @Id
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "mrp")
    private double mrp;

    @Column(name = "current_price")
    private double currentPrice;

    @Column(name = "discount")
    private  float discount;

    @Column(name = "inventory")
    private int inventory;


    public Product(int id, String title, double mrp, double currentPrice, float discount, int inventory) {
        this.id = id;
        this.title = title;
        this.mrp = mrp;
        this.currentPrice = currentPrice;
        this.discount = discount;
        this.inventory = inventory;
    }

    private int generateUniqueId(){
        String uuid= UUID.randomUUID().toString().replace("-","");
        String accNo=uuid.substring(0,6);
        return (int) (Integer.parseInt(accNo,16) %1000000L);
    }

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
      @JsonManagedReference
    private List<PriceHistory>priceHistories;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", mrp=" + mrp +
                ", currentPrice=" + currentPrice +
                ", discount=" + discount +
                ", inventory=" + inventory +
                '}';
    }

    public Product() {
        this.id = generateUniqueId();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }


    public List<PriceHistory> getPriceHistories() {
        return priceHistories;
    }

    public void setPriceHistories(List<PriceHistory> priceHistories) {
        this.priceHistories = priceHistories;
    }

}
