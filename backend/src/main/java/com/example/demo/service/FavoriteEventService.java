package com.example.demo.service;

import com.example.demo.model.Event;
import com.example.demo.model.User;

import java.util.List;

public interface FavoriteEventService {
    void addFavorite(User user, Event event);
    void removeFavorite(User user, Event event);
    boolean isFavorite(User user, Event event);
    List<Event> getFavoritesForUser(User user);
}
