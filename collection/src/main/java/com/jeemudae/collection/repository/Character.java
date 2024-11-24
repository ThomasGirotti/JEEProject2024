package com.jeemudae.collection.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "characters", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "user_id"})
})
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String imageUrl;
    private Short price;
    private Long claimCount;
    private Long likeCount;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public Character() {
    }
    
    public Character(String name, String imageUrl, Short price, Long claimCount, Long likeCount) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.claimCount = claimCount;
        this.likeCount = likeCount;
    }
    
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
    
    public Long getClaimCount() {
        return claimCount;
    }
    
    public void setClaimCount(Long claimCount) {
        this.claimCount = claimCount;
    }
    
    public Long getLikeCount() {
        return likeCount;
    }
    
    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}