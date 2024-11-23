package com.jeemudae.collection.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageUrl;
    private Short price;
    private Short claimCount;
    private Short likeCount;

    public Character() {
    }

    public Character(String name, String imageUrl, Short price, Short claimCount, Short likeCount) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.claimCount = claimCount;
        this.likeCount = likeCount;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Short getPrice() {
        return price;
    }

    public void setPrice(Short price) {
        this.price = price;
    }

    public Short getClaimCount() {
        return claimCount;
    }

    public void setClaimCount(Short claimCount) {
        this.claimCount = claimCount;
    }

    public Short getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Short likeCount) {
        this.likeCount = likeCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}