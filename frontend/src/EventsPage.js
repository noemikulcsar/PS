import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './EventsPage.css';

function EventsPage() {
    const [events, setEvents] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const response = await axios.get('http://localhost:8080/events');
                const today = new Date();
                const upcomingEvents = response.data.filter(event => new Date(event.date) > today);
                setEvents(upcomingEvents);
            } catch (error) {
                console.error('Error fetching events:', error);
            }
        };

        fetchEvents();
    }, []);

    const handleBuyTicket = (event) => {
        navigate('/payment', { state: { selectedEvent: event } });
    };

    return (
        <div className="events-container">
            <h2 className="text-center mb-4">Available Events</h2>
            {events.length === 0 ? (
                <p className="text-muted text-center">No events available.</p>
            ) : (
                <div className="event-list">
                    {events.map((event) => {
                        const soldOut = event.soldTickets >= event.venue.capacity;
                        return (
                            <div key={event.id} className="event-card">
                                <h3>{event.name}</h3>
                                <p><strong>Description:</strong> {event.description}</p>
                                <p><strong>Date:</strong> {event.date}</p>
                                <p><strong>Location:</strong> {event.venue?.name} - {event.venue?.address}</p>
                                <p><strong>Capacity:</strong> {event.venue?.capacity}</p>
                                <p><strong>Available Tickets:</strong> {event.venue.capacity - event.soldTickets}</p>
                                <p><strong>Price:</strong> {event.price} RON</p>

                                {soldOut ? (
                                    <p style={{ color: 'red', fontWeight: 'bold' }}>Sold Out</p>
                                ) : (
                                    <button
                                        onClick={() => handleBuyTicket(event)}
                                        className="btn btn-primary mt-2"
                                    >
                                        Buy Ticket
                                    </button>
                                )}
                            </div>
                        );
                    })}
                </div>
            )}
        </div>
    );
}

export default EventsPage;
