package com.example.demo.repository;

import com.example.demo.model.FavoriteEvent;
import com.example.demo.model.Event;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteEventRepository extends JpaRepository<FavoriteEvent, Long> {
    List<FavoriteEvent> findByUser(User user);
    Optional<FavoriteEvent> findByUserAndEvent(User user, Event event);
    void deleteByUserAndEvent(User user, Event event);

}
