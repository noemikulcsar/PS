import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './EventsPage.css';

function FavoritesPage() {
    const [favorites, setFavorites] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchFavorites = async () => {
            const user = JSON.parse(localStorage.getItem('user'));
            if (!user) return;
            try {
                const response = await axios.get(`http://localhost:8080/users/${user.id}/favorites`);
                setFavorites(response.data);
            } catch (error) {
                console.error('Failed to load favorite events:', error);
            }
        };

        fetchFavorites();
    }, []);

    const handleRemoveFavorite = async (eventId) => {
        const user = JSON.parse(localStorage.getItem("user"));
        if (!user) return;

        try {
            await axios.delete(`http://localhost:8080/users/${user.id}/favorites/${eventId}`);
            setFavorites(prev => prev.filter(event => event.id !== eventId));
        } catch (error) {
            console.error("Failed to remove favorite:", error);
        }
    };

    return (
        <div className="events-container">
            <h2 className="text-center mb-4">My Favorite Events</h2>
            {favorites.length === 0 ? (
                <p className="text-center text-muted">You haven't added any favorite events yet.</p>
            ) : (
                <div className="event-list">
                    {favorites.map(event => (
                        <div key={event.id} className="event-card" style={{ position: 'relative' }}>
                            {/* Stea scoasă în colț */}
                            <span
                                onClick={() => handleRemoveFavorite(event.id)}
                                style={{
                                    position: 'absolute',
                                    top: '10px',
                                    right: '10px',
                                    fontSize: '24px',
                                    cursor: 'pointer',
                                    color: '#FFD700'
                                }}
                                title="Remove from favorites"
                            >
                                ★
                            </span>

                            <h3>{event.name}</h3>
                            <p><strong>Description:</strong> {event.description}</p>
                            <p><strong>Date:</strong> {event.date}</p>
                            <p><strong>Location:</strong> {event.venue?.name ?? 'Unknown'} - {event.venue?.address ?? 'N/A'}</p>
                            <p><strong>Available Tickets:</strong> {(event.venue?.capacity ?? 0) - event.soldTickets}</p>
                            <p><strong>Price:</strong> {event.price} RON</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default FavoritesPage;
