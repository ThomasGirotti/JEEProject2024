package com.jeemudae.collection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.service.TradeService;
import com.jeemudae.collection.service.UserService;

@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserService userService;

    @GetMapping("/createTrade")
public String createTradePage(Model model) {
    // Récupérer le nom d'utilisateur de l'utilisateur connecté
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName(); // Nom d'utilisateur de l'utilisateur connecté
    
    // Chercher l'utilisateur en base de données (avec la méthode existante)
    User user = userService.getUserByUsername(username); // Cette méthode renvoie directement un User ou lance une exception
    
    // Ajouter les personnages de l'utilisateur au modèle
    model.addAttribute("characters", user.getCollectionSet().getCharacters());
    return "createTrade";
}

    @PostMapping("/createTrade")
    public String createTrade(@RequestParam("username") String username) {
        // Crée un trade pour l'utilisateur donné
        tradeService.createTradeForUser(userService.getUserByUsername(username).getId());
        return "redirect:/tradePosted"; // Redirige vers une page où le trade est affiché
    }

    @PostMapping("/addCharacterToTrade")
    public String addCharacterToTrade(@RequestParam("tradeId") Long tradeId, @RequestParam("characterId") Long characterId) {
        tradeService.addCharacterToTrade(tradeId, characterId);
        return "redirect:/tradeDetails?tradeId=" + tradeId;
    }
}
