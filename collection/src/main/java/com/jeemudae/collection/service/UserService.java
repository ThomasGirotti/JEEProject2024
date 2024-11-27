package com.jeemudae.collection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean isUserFollowing(String currentUsername, String profileUsername) {
        User currentUser = userRepository.findByUsernameWithFollowing(currentUsername).orElseThrow(() -> new RuntimeException("Utilisateur actuel non trouvé"));
        User profileUser = userRepository.findByUsernameWithFollowing(profileUsername).orElseThrow(() -> new RuntimeException("Profil utilisateur non trouvé"));
        return currentUser.getFollowing().contains(profileUser);
    }

    @Transactional
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + username));
    }

    @Transactional
    public void followUser(String currentUsername, String targetUsername) {
        User currentUser = getUserByUsername(currentUsername);
        User targetUser = getUserByUsername(targetUsername);

        if (!currentUser.equals(targetUser)) {
            currentUser.follow(targetUser);
            userRepository.save(currentUser);
        }
    }

    @Transactional
    public void unfollowUser(String currentUsername, String targetUsername) {
        User currentUser = getUserByUsername(currentUsername);
        User targetUser = getUserByUsername(targetUsername);

        currentUser.unfollow(targetUser);
        userRepository.save(currentUser);
    }
}
