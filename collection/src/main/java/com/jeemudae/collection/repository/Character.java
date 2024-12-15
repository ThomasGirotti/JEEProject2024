package com.jeemudae.collection.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "characterSet")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String imagePath;
    
    @Column(nullable = false)
    private int price;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "collection_set_id")
    private CollectionSet collectionSet;
    
    @Column(name = "in_trade", nullable = false, columnDefinition = "boolean default false")
    private boolean inTrade;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trade_id", nullable = true)
    private Trade trade;
    
    @Column(nullable=false)
    private int position;

    public Character() {
    }
    
    public Character(String name, String imagePath, int price, CollectionSet collectionSet) {
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.collectionSet = collectionSet;
    }
    
    public boolean isInTrade() {
        return inTrade;
    }
    
    public void setInTrade(boolean inTrade) {
        this.inTrade = inTrade;
    }
    
    public Trade getTrade() {
        return trade;
    }
    
    public void setTrade(Trade trade) {
        this.trade = trade;
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
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public CollectionSet getCollectionSet() {
        return collectionSet;
    }
    
    public void setCollectionSet(CollectionSet collectionSet) {
        this.collectionSet = collectionSet;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}