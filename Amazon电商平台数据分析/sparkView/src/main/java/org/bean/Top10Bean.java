package org.bean;

public class Top10Bean {
    private String id;
    private Long view;
    private Long cart;
    private Long purchase;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Long getView() {
        return view;
    }
    
    public void setView(Long view) {
        this.view = view;
    }
    
    public Long getCart() {
        return cart;
    }
    
    public void setCart(Long cart) {
        this.cart = cart;
    }
    
    public Long getPurchase() {
        return purchase;
    }
    
    public void setPurchase(Long purchase) {
        this.purchase = purchase;
    }
}
