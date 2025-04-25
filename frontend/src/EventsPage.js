import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './EventsPage.css';
import './useWebSocket';
function EventsPage() {
    const [events, setEvents] = useState([]);
    const [filteredEvents, setFilteredEvents] = useState([]);
    const [minPrice, setMinPrice] = useState('');
    const [maxPrice, setMaxPrice] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [locations, setLocations] = useState([]);
    const [onlyAvailable, setOnlyAvailable] = useState(false);
    const [sortOption, setSortOption] = useState('');
    const navigate = useNavigate();
    const [favoriteIds, setFavoriteIds] = useState([]);

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const response = await axios.get('http://localhost:8080/events');
                const today = new Date();
                const upcoming = response.data.filter(event => new Date(event.date) > today);
                setEvents(upcoming);
                setFilteredEvents(upcoming);
            } catch (error) {
                console.error('Error fetching events:', error);
            }
        };

        const fetchFavorites = async () => {
            const user = JSON.parse(localStorage.getItem('user'));
            if (!user) return;
            try {
                const res = await axios.get(`http://localhost:8080/users/${user.id}/favorites`);
                setFavoriteIds(res.data.map(event => event.id));
            } catch (error) {
                console.error("Failed to fetch favorites:", error);
            }
        };

        fetchEvents();
        fetchFavorites();
    }, []);


    const handleBuyTicket = (event) => {
        navigate('/payment', { state: { selectedEvent: event } });
    };

    const handleFilter = async () => {
        try {
            const response = await axios.post("http://localhost:8080/events/filter", {
                minPrice: minPrice !== '' ? parseInt(minPrice) : null,
                maxPrice: maxPrice !== '' ? parseInt(maxPrice) : null,
                startDate: startDate !== '' ? startDate : null,
                endDate: endDate !== '' ? endDate : null,
                locations: locations.length > 0 ? locations : null,
                onlyAvailable: onlyAvailable,
                sortOption: sortOption !== '' ? sortOption : null,
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            });
            const today = new Date();
            today.setHours(0, 0, 0, 0);

            const upcoming = response.data.filter(event => {
                const eventDate = new Date(event.date);
                eventDate.setHours(0, 0, 0, 0);
                return eventDate >= today;
            });

            setFilteredEvents(upcoming);
        } catch (error) {
            console.error("Eroare la filtrarea evenimentelor:", error);
            alert("A apărut o eroare la filtrare. Verifică consola.");
        }
    };

    const handleReset = () => {
        setMinPrice('');
        setMaxPrice('');
        setStartDate('');
        setEndDate('');
        setLocations([]);
        setOnlyAvailable(false);
        setSortOption('');
        setFilteredEvents(events);
    };
    const calculateEventProgress = (eventDateStr) => {
        const now = new Date();
        const eventDate = new Date(eventDateStr);

        const startReference = new Date(eventDate);
        startReference.setDate(eventDate.getDate() - 100);

        const totalDuration = eventDate - startReference;
        const timeElapsed = now - startReference;

        const progress = 100 - (timeElapsed / totalDuration) * 100;
        return Math.max(0, Math.min(progress, 100));
    };

    const uniqueLocations = [...new Set(events.map(e => e.venue?.name).filter(Boolean))];
    const handleToggleFavorite = async (eventId) => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (!user) {
            alert("You need to be logged in to manage favorites.");
            return;
        }

        try {
            if (favoriteIds.includes(eventId)) {
                await axios.delete(`http://localhost:8080/users/${user.id}/favorites/${eventId}`);
                setFavoriteIds(prev => prev.filter(id => id !== eventId));
            } else {
                await axios.post(`http://localhost:8080/users/${user.id}/favorites/${eventId}`);
                setFavoriteIds(prev => [...prev, eventId]);
            }
        } catch (error) {
            console.error("Failed to toggle favorite:", error);
        }
    };

    return (
        <div className="events-container">
            <h2 className="text-center mb-4">Available Events</h2>

            <div className="filter-section mb-4">
                <div className="filter-group">
                    <label>Min Price:</label>
                    <input type="number" value={minPrice} onChange={(e) => setMinPrice(e.target.value)} />
                </div>
                <div className="filter-group">
                    <label>Max Price:</label>
                    <input type="number" value={maxPrice} onChange={(e) => setMaxPrice(e.target.value)} />
                </div>
                <div className="filter-group">
                    <label>Start Date:</label>
                    <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
                </div>
                <div className="filter-group">
                    <label>End Date:</label>
                    <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
                </div>
                <div className="filter-group">
                    <label>Sort by:</label>
                    <select value={sortOption} onChange={(e) => setSortOption(e.target.value)}>
                        <option value="">None</option>
                        <option value="priceAsc">Price: Low to High</option>
                        <option value="priceDesc">Price: High to Low</option>
                        <option value="dateAsc">Date: Soonest First</option>
                        <option value="dateDesc">Date: Latest First</option>
                    </select>
                </div>
                <div className="filter-group checkbox-group">
                    <label>
                        <input
                            type="checkbox"
                            checked={onlyAvailable}
                            onChange={(e) => setOnlyAvailable(e.target.checked)}
                        /> Show only available
                    </label>
                </div>

                <div className="location-filter-wrapper">
                    <label>Filter by Location(s):</label>
                    <div className="location-checkboxes">
                        {uniqueLocations.map(loc => (
                            <label key={loc}>
                                <input
                                    type="checkbox"
                                    value={loc}
                                    checked={locations.includes(loc)}
                                    onChange={(e) => {
                                        if (e.target.checked) {
                                            setLocations(prev => [...prev, loc]);
                                        } else {
                                            setLocations(prev => prev.filter(item => item !== loc));
                                        }
                                    }}
                                /> {loc}
                            </label>
                        ))}
                    </div>
                    <div className="button-group mt-3">
                        <button className="btn" onClick={handleFilter}>Apply Filters</button>
                        <button className="btn btn-secondary" onClick={handleReset}>Reset Filters</button>
                    </div>
                </div>
            </div>

            {filteredEvents.length === 0 ? (
                <p className="text-muted text-center">No events match your filters.</p>
            ) : (
                <div className="event-list">
                    {filteredEvents.map((event) => {
                        const soldOut = event.soldTickets >= (event.venue?.capacity ?? 0);
                        return (
                            <div key={event.id} className="event-card" style={{ position: 'relative' }}>
                                {/* Favorite Star */}
                                <span
                                    onClick={() => handleToggleFavorite(event.id)}
                                    style={{
                                        position: 'absolute',
                                        top: '10px',
                                        right: '10px',
                                        fontSize: '24px',
                                        cursor: 'pointer',
                                        color: favoriteIds.includes(event.id) ? '#FFD700' : '#ccc',
                                        transition: 'color 0.2s ease'
                                    }}
                                    title={favoriteIds.includes(event.id) ? 'Remove from favorites' : 'Add to favorites'}
                                >
            ★
        </span>

                                {/* Event Info */}
                                <h3>{event.name}</h3>
                                <p><strong>Description:</strong> {event.description}</p>
                                <p><strong>Date:</strong> {event.date}</p>
                                <p><strong>Location:</strong> {event.venue?.name ?? 'Unknown'} - {event.venue?.address ?? 'N/A'}</p>
                                <p><strong>Capacity:</strong> {event.venue?.capacity ?? 'N/A'}</p>
                                <p><strong>Available Tickets:</strong> {(event.venue?.capacity ?? 0) - event.soldTickets}</p>
                                <p><strong>Price:</strong> {event.price} RON</p>
                                {/* Progress bar for events */}
                                {event.date && (
                                    <div className="progress-wrapper mt-2">
                                        <div className="progress-bar-bg">
                                            <div
                                                className="progress-bar-fill"
                                                style={{ width: `${calculateEventProgress(event.date)}%` }}
                                            ></div>
                                        </div>
                                        <small style={{ fontSize: "0.75rem", color: "#666" }}>
                                            {Math.max(0, Math.ceil((new Date(event.date) - new Date()) / (1000 * 60 * 60 * 24)))} zile rămase
                                        </small>
                                    </div>
                                )}

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
