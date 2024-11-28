package com.jeemudae.collection.repository;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CollectionSet collectionSet;

    @ManyToMany
    @JoinTable(
        name = "user_follow",
        joinColumns = @JoinColumn(name = "follower_id"),
        inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    @SuppressWarnings("FieldMayBeFinal")
    private Set<User> following = new HashSet<>();

    @ManyToMany(mappedBy = "following")
    @SuppressWarnings("FieldMayBeFinal")
    private Set<User> followers = new HashSet<>();

    @Column(nullable = false)
    private int cash;

    public User() {
        this.role = Role.USER;
        this.cash = 0;
        this.collectionSet = new CollectionSet(this);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.USER;
        this.cash = 0;
        this.collectionSet = new CollectionSet(this);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CollectionSet getCollectionSet() {
        return collectionSet;
    }

    public void setCollectionSet(CollectionSet collectionSet) {
        this.collectionSet = collectionSet;
    }

    public void follow(User user) {
        this.following.add(user);
        user.getFollowers().add(this);
    }
    
    public void unfollow(User user) {
        this.following.remove(user);
        user.getFollowers().remove(this);
    }
    
    public Set<User> getFollowing() {
        return following;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public int getCash() {
        return cash;
    }

    public void updateCash(int cash) {
        this.cash = cash + this.cash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }
}