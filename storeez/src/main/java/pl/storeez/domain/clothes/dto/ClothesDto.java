package pl.storeez.domain.clothes.dto;

import java.math.BigDecimal;

public class ClothesDto {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int discount;
    private BigDecimal priceAfterDiscount;
    private int stock;
    private String category;
    private String subcategory;
    private boolean promoted;
    private String size;
    private String color;
    private String material;

    public ClothesDto() {
    }

    public ClothesDto(Long id, String name, String brand, String description, BigDecimal price, int discount, BigDecimal priceAfterDiscount, int stock, String category, String subcategory, boolean promoted, String size, String color, String material) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.priceAfterDiscount = priceAfterDiscount;
        this.stock = stock;
        this.category = category;
        this.subcategory = subcategory;
        this.promoted = promoted;
        this.size = size;
        this.color = color;
        this.material = material;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public BigDecimal getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(BigDecimal priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }
}
