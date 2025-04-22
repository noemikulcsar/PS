package com.example.demo.service.impl;

import com.example.demo.model.Event;
import com.example.demo.model.FavoriteEvent;
import com.example.demo.model.User;
import com.example.demo.repository.FavoriteEventRepository;
import com.example.demo.service.FavoriteEventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteEventServiceImpl implements FavoriteEventService {

    private final FavoriteEventRepository favoriteEventRepository;

    public FavoriteEventServiceImpl(FavoriteEventRepository favoriteEventRepository) {
        this.favoriteEventRepository = favoriteEventRepository;
    }

    @Override
    public void addFavorite(User user, Event event) {
        if (!favoriteEventRepository.findByUserAndEvent(user, event).isPresent()) {
            favoriteEventRepository.save(new FavoriteEvent(null, user, event));
        }
    }

    @Override
    public void removeFavorite(User user, Event event) {
        Optional<FavoriteEvent> favorite = favoriteEventRepository.findByUserAndEvent(user, event);
        favorite.ifPresent(favoriteEventRepository::delete);
    }

    @Override
    public boolean isFavorite(User user, Event event) {
        return favoriteEventRepository.findByUserAndEvent(user, event).isPresent();
    }

    @Override
    public List<Event> getFavoritesForUser(User user) {
        return favoriteEventRepository.findByUser(user)
                .stream()
                .map(FavoriteEvent::getEvent)
                .collect(Collectors.toList());
    }
}
