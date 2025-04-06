import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './TicketsPage.css';

function TicketsPage() {
    const [tickets, setTickets] = useState([]);

    useEffect(() => {
        const fetchTickets = async () => {
            const storedUser = JSON.parse(localStorage.getItem('user'));
            if (!storedUser || !storedUser.id) {
                console.warn("User not found in localStorage");
                return;
            }

            try {
                const response = await axios.get(`http://localhost:8080/users/${storedUser.id}/tickets`);
                setTickets(response.data || []);
            } catch (error) {
                console.error('Error fetching tickets:', error);
            }
        };

        fetchTickets();
    }, []);

    return (
        <div className="tickets-container">
            <h2 className="text-center mb-4">My Tickets</h2>
            {tickets.length === 0 ? (
                <p className="text-muted text-center">You haven't bought any tickets yet.</p>
            ) : (
                <div className="ticket-list">
                    {tickets.map((ticket) => (
                        <div key={ticket.id} className="ticket-card">
                            <h3>{ticket.event?.name || 'Unknown Event'}</h3>
                            <p><strong>Description:</strong> {ticket.event?.description || 'N/A'}</p>
                            <p><strong>Date:</strong> {ticket.event?.date || 'N/A'}</p>
                            <p><strong>Location:</strong> {ticket.event?.venue?.name || 'N/A'} - {ticket.event?.venue?.address || ''}</p>
                            <p><strong>Seat:</strong> {ticket.seatNumber}</p>
                            <p><strong>Price:</strong> {ticket.price} RON</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default TicketsPage;
