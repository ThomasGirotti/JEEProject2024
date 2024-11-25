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
    private int value;
    @Column(nullable = false)
    private int claimCount;
    @Column(nullable = false)
    private int likeCount;
    @ManyToOne
    @JoinColumn(name = "collection_set_id", nullable = false)
    private CollectionSet collectionSet;
    
    public Character() {
    }
    
    public Character(String name, String imagePath, int value, int claimCount, int likeCount, CollectionSet collectionSet) {
        this.name = name;
        this.imagePath = imagePath;
        this.value = value;
        this.claimCount = claimCount;
        this.likeCount = likeCount;
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
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public int getClaimCount() {
        return claimCount;
    }
    
    public void setClaimCount(int claimCount) {
        this.claimCount = claimCount;
    }
    
    public int getLikeCount() {
        return likeCount;
    }
    
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public CollectionSet getCollectionSet() {
        return collectionSet;
    }

    public void setCollectionSet(CollectionSet collectionSet) {
        this.collectionSet = collectionSet;
    }
}