package com.example.demo.service.impl;

import com.example.demo.model.Event;
import com.example.demo.model.Venue;
import com.example.demo.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class EventServiceImplTest {
    private static final String EVENT_NAME = "Hamlet";
    private static final String VENUE_NAME = "Teatrul Național";

    private Venue venue = new Venue(1L, VENUE_NAME, "Bd. Principal 123", 500);
    private Event event = new Event(1L, EVENT_NAME, "Tragedie de Shakespeare", LocalDate.of(2025, 3, 17), venue, null);

    // UUT (Unit Under Test)
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
        eventService = new EventServiceImpl(eventRepository);
    }

    @Test
    public void givenExistingEvent_whenFindEventById_thenReturnEvent() {
        // given
        when(eventRepository.findById(1L)).thenReturn(event);

        // when
        Event resultEvent = eventService.getEventById(1L);

        // then
        assertNotNull(resultEvent);
        assertEquals(EVENT_NAME, resultEvent.getName());
        assertEquals(VENUE_NAME, resultEvent.getVenue().getName());
        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(0)).findAll();
    }

    @Test
    public void givenNonExistingEvent_whenFindEventById_thenThrowException() {
        when(eventRepository.findById(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            eventService.getEventById(1L);
        });
    }

    @Test
    void givenValidEvent_whenUpdateEvent_thenReturnUpdatedEvent() {
        // given
        when(eventRepository.findById(1L)).thenReturn(event);
        Event updatedEvent = new Event(1L, "Updated Event", "Updated Description", LocalDate.of(2025, 5, 1), venue, null);
        when(eventRepository.update(any(Event.class))).thenReturn(updatedEvent);

        // when
        Event result = eventService.updateEvent(updatedEvent);

        // then
        assertNotNull(result);
        assertEquals("Updated Event", result.getName());
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    public void givenValidEvent_whenSaveEvent_thenReturnSavedEvent() {
        // given
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // when
        Event savedEvent = eventService.addEvent(event);

        // then
        assertNotNull(savedEvent);
        assertEquals(EVENT_NAME, savedEvent.getName());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    public void givenValidId_whenDeleteEvent_thenVerifyDeletion() {
        // given
        doNothing().when(eventRepository).deleteById(1L);

        // when
        eventService.deleteEvent(1L);

        // then
        verify(eventRepository, times(1)).deleteById(1L);
    }
}
