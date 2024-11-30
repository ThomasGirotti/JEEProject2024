package com.jeemudae.collection.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.following WHERE u.username = :username")
    Optional<User> findByUsernameWithFollowing(@Param("username") String username);
    @Query("SELECT u FROM User u JOIN u.collectionSet c JOIN c.characters ch GROUP BY u ORDER BY SUM(ch.price) DESC")
    List<User> findTopUsersByCollectionValue();

}
