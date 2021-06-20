package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String orderId;

    private final List<Item> itemList = new ArrayList<Item>();

    public List<Item> getItemList() {
        return this.itemList;
    }

    private Item findLineById(String id) {
        for (Item line : this.itemList) {
            if (line.getProductInfo().getId().equals(id)) {
                return line;
            }
        }
        return null;
    }

    public void addProduct(ProductInfo productInfo, int quantity) {
        Item line = this.findLineById(productInfo.getId());

        if (line == null) {
            line = new Item();
            line.setQuantity(0);
            line.setProductInfo(productInfo);
            this.itemList.add(line);
        }
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.itemList.remove(line);
        } else {
            line.setQuantity(newQuantity);
        }
    }

    public void validate() {

    }

    public void updateProduct(String id, int quantity) {
        Item line = this.findLineById(id);

        if (line != null) {
            if (quantity <= 0) {
                this.itemList.remove(line);
            } else {
                line.setQuantity(quantity);
            }
        }
    }

    public void removeProduct(ProductInfo product) {
        Item line = this.findLineById(product.getId());
        if (line != null) {
            this.itemList.remove(line);
        }
    }

    public boolean isEmpty() {
        return this.itemList.isEmpty();
    }


    public int getQuantityTotal() {
        int quantity = 0;
        for (Item line : this.itemList) {
            quantity += line.getQuantity();
        }
        return quantity;
    }

    public double getAmountTotal() {
        double total = 0;
        for (Item line : this.itemList) {
            total += line.getAmount();
        }
        return total;
    }

    public void updateQuantity(Cart cartForm) {
        if (cartForm != null) {
            List<Item> lines = cartForm.getItemList();
            for (Item line : lines) {
                this.updateProduct(line.getProductInfo().getId(), line.getQuantity());
            }
        }

    }
}
