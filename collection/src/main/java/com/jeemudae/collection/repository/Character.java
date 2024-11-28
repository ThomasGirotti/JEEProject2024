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

    @Column(nullable = false)
    private String imagePath;
    @Column(nullable = false)
    private int price;
    @ManyToOne
    @JoinColumn(name = "collection_set_id")
    private CollectionSet collectionSet;
    
    public Character() {
    }
    
    public Character(String name, String imagePath, int price, int claimCount, int likeCount, CollectionSet collectionSet) {
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.collectionSet = collectionSet;
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
}