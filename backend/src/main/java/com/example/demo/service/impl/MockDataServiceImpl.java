package com.example.demo.service.impl;

import com.example.demo.model.Event;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.model.Venue;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MockDataService;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MockDataServiceImpl implements MockDataService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public MockDataServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void generateMockData() {
        Venue venue1 = new Venue(1L, "Teatrul Național", "Bd. Principal 123", 100,
                46.769379, 23.589954); // Cluj

        Venue venue2 = new Venue(2L, "Opera Română", "Str. Muzicii 45", 70,
                44.435597, 26.097130); // București

        Venue venue3 = new Venue(3L, "Sala Palatului", "Calea Victoriei 60", 80,
                45.646700, 25.589800); // Brașov

        Venue venue4 = new Venue(4L, "Teatrul de Comedie", "Str. Smârdan 10", 75,
                47.158454, 27.601442); // Iași

        LocalDate date1 = LocalDate.of(2025, 9, 17);
        LocalDate date2 = LocalDate.of(2025, 8, 30);
        LocalDate date3 = LocalDate.of(2025, 7, 15);
        LocalDate date4 = LocalDate.of(2025, 6, 10);
        LocalDate date5 = LocalDate.of(2025, 7, 5);
        LocalDate date6 = LocalDate.of(2025, 11, 20);

        Event event1 = new Event(1L, "Hamlet", "Tragedie de Shakespeare", date1, venue1,  35, 30);
        Event event2 = new Event(2L, "Lacul Lebedelor", "Balet clasic", date2, venue2,  70, 50);
        Event event3 = new Event(3L, "Carmina Burana", "Spectacol coral-simfonic", date3, venue3,12, 50);
        Event event4 = new Event(4L, "O scrisoare pierdută", "Comedie de I.L. Caragiale", date4, venue4, 40, 45);
        Event event5 = new Event(5L, "Flaut fermecat", "Operă de Mozart", date5, venue2, 0, 65);
        Event event6 = new Event(6L, "Rigoletto", "Operă de Verdi", date6, venue3, 0, 100);
        Event event7 = new Event(7L, "Nunta lui Figaro", "Operă comică", LocalDate.of(2025, 8, 12), venue1, 100, 55);
        Event event8 = new Event(8L, "Carmen", "Operă dramatică de Bizet", LocalDate.of(2025, 2, 8), venue4, 75, 60);
        Event event9 = new Event(9L, "Don Giovanni", "Operă în 2 acte de Mozart", LocalDate.of(2025, 3, 5), venue3, 3, 80);
        Event event10 = new Event(10L, "Concert de Crăciun", "Eveniment special de sărbători", LocalDate.of(2025, 1, 20), venue2, 0, 40);
        Event event11 = new Event(11L, "Macbeth", "Tragedie intensă de William Shakespeare", LocalDate.of(2025, 9, 10), venue1, 10, 45);
        Event event12 = new Event(12L, "Tosca", "Operă dramatică de Giacomo Puccini", LocalDate.of(2025, 10, 5), venue2, 43, 70);
        Event event13 = new Event(13L, "Spărgătorul de nuci", "Balet de Piotr Ilici Ceaikovski", LocalDate.of(2025, 12, 15), venue3, 61, 65);
        Event event14 = new Event(14L, "Aida", "Operă grandioasă de Verdi", LocalDate.of(2024, 11, 2), venue2, 69, 90);
        Event event15 = new Event(15L, "O noapte furtunoasă", "Comedie clasică românească de I.L. Caragiale", LocalDate.of(2022, 10, 28), venue4, 0, 35);
        Event event16 = new Event(16L, "Madama Butterfly", "Operă tragică de Puccini", LocalDate.of(2024, 11, 18), venue3, 80, 75);
        Event event17 = new Event(17L, "Trubadurul", "Operă de Giuseppe Verdi", LocalDate.of(2025, 7, 7), venue1, 24, 60);
        Event event18 = new Event(18L, "Bărbierul din Sevilla", "Comedie muzicală de Rossini", LocalDate.of(2024, 6, 25), venue4, 70, 50);
        Event event19 = new Event(19L, "Faust", "Operă în 5 acte de Charles Gounod", LocalDate.of(2024, 9, 22), venue2, 68, 80);
        Event event20 = new Event(20L, "Otello", "Operă dramatică de Verdi", LocalDate.of(2023, 8, 30), venue3, 80, 85);

        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);
        eventRepository.save(event4);
        eventRepository.save(event5);
        eventRepository.save(event6);
        eventRepository.save(event7);
        eventRepository.save(event8);
        eventRepository.save(event9);
        eventRepository.save(event10);
        eventRepository.save(event11);
        eventRepository.save(event12);
        eventRepository.save(event13);
        eventRepository.save(event14);
        eventRepository.save(event15);
        eventRepository.save(event16);
        eventRepository.save(event17);
        eventRepository.save(event18);
        eventRepository.save(event19);
        eventRepository.save(event20);

        User user1 = new User(1L,"Noemi Kulcsar","noemikulcsar@yahoo.com",new ArrayList<>(),"noemikulcsar","1234","0754617850","Strada Aleea Zorilor, Numarul 6, Bloc A-4, Scara 3, Ap. 2","/assets/profile-page.jpeg");
        List<Ticket> user1Tickets = new ArrayList<>();

        Ticket ticket1 = new Ticket(1L, "A1", event15.getPrice(), false, event15);
        Ticket ticket2 = new Ticket(2L, "B2", event16.getPrice(), false, event16);
        Ticket ticket3 = new Ticket(3L, "C3", event18.getPrice(), false, event18);
        Ticket ticket4 = new Ticket(4L, "D4", event19.getPrice(), false, event19);
        Ticket ticket5 = new Ticket(5L, "E5", event20.getPrice(), false, event20);

        user1Tickets.add(ticket1);
        user1Tickets.add(ticket2);
        user1Tickets.add(ticket3);
        user1Tickets.add(ticket4);
        user1Tickets.add(ticket5);

        user1.setTickets(user1Tickets);
        event15.setSoldTickets(event15.getSoldTickets() + 1);
        event16.setSoldTickets(event16.getSoldTickets() + 1);
        event18.setSoldTickets(event18.getSoldTickets() + 1);
        event19.setSoldTickets(event19.getSoldTickets() + 1);
        event20.setSoldTickets(event20.getSoldTickets() + 1);
        User user2 = User.builder()
                .id(2L)
                .name("Admin")
                .email("admin@example.com")
                .username("admin")
                .password("admin123")
                .tickets(new ArrayList<>())
                .phone("0712345678")
                .address("Bulevardul Muncii, Nr. 101")
                .profilePictureUrl("/assets/profile-page.jpeg")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
}
