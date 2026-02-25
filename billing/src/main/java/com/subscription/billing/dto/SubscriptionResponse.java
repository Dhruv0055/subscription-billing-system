package com.subscription.billing.dto;

public class SubscriptionResponse {

    private Long id;
    private String status;

    private Long userId;
    private String userName;

    private Long planId;
    private String planName;
    private double price;

    public SubscriptionResponse() {}

    // getters & setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
