import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './TicketsPage.css';

function TicketsPage() {
    const [tickets, setTickets] = useState([]);
    const [filteredTickets, setFilteredTickets] = useState([]);

    const [minPrice, setMinPrice] = useState('');
    const [maxPrice, setMaxPrice] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [locations, setLocations] = useState([]);
    const [sortOption, setSortOption] = useState('');

    const navigate = useNavigate();

    useEffect(() => {
        const fetchTickets = async () => {
            const storedUser = JSON.parse(localStorage.getItem('user'));
            if (!storedUser?.id) {
                console.warn("User not found in localStorage");
                return;
            }

            try {
                const response = await axios.get(`http://localhost:8080/users/${storedUser.id}/tickets`);
                setTickets(response.data || []);
                setFilteredTickets(response.data || []);
            } catch (error) {
                console.error('Error fetching tickets:', error);
            }
        };

        fetchTickets();
    }, []);

    const handleFilter = () => {
        let filtered = tickets.filter(ticket => {
            const price = ticket.price;
            const date = new Date(ticket.event?.date);
            const location = ticket.event?.venue?.name;

            const matchesPrice = (!minPrice || price >= minPrice) && (!maxPrice || price <= maxPrice);
            const matchesDate = (!startDate || date >= new Date(startDate)) && (!endDate || date <= new Date(endDate));
            const matchesLocation = locations.length === 0 || locations.includes(location);

            return matchesPrice && matchesDate && matchesLocation;
        });

        if (sortOption === 'priceAsc') {
            filtered.sort((a, b) => a.price - b.price);
        } else if (sortOption === 'priceDesc') {
            filtered.sort((a, b) => b.price - a.price);
        } else if (sortOption === 'dateAsc') {
            filtered.sort((a, b) => new Date(a.event.date) - new Date(b.event.date));
        } else if (sortOption === 'dateDesc') {
            filtered.sort((a, b) => new Date(b.event.date) - new Date(a.event.date));
        }

        setFilteredTickets(filtered);
    };

    const handleReset = () => {
        setMinPrice('');
        setMaxPrice('');
        setStartDate('');
        setEndDate('');
        setLocations([]);
        setSortOption('');
        setFilteredTickets(tickets);
    };

    const uniqueLocations = [...new Set(tickets.map(t => t.event?.venue?.name).filter(Boolean))];

    return (
        <div className="tickets-container">
            <h2 className="text-center mb-4">My Tickets</h2>

            <div className="filter-section">
                <div className="filter-group">
                    <label>Min Price:</label>
                    <input type="number" value={minPrice} onChange={e => setMinPrice(e.target.value)} />
                </div>
                <div className="filter-group">
                    <label>Max Price:</label>
                    <input type="number" value={maxPrice} onChange={e => setMaxPrice(e.target.value)} />
                </div>
                <div className="filter-group">
                    <label>Start Date:</label>
                    <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} />
                </div>
                <div className="filter-group">
                    <label>End Date:</label>
                    <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)} />
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

            {filteredTickets.length === 0 ? (
                <p className="text-muted text-center">No tickets match your filters.</p>
            ) : (
                <div className="ticket-list">
                    {filteredTickets.map(ticket => (
                        <div key={ticket.id} className="ticket-card">
                            <h3>{ticket.event?.name || 'Unknown Event'}</h3>
                            <p><strong>Description:</strong> {ticket.event?.description || 'N/A'}</p>
                            <p><strong>Date:</strong> {ticket.event?.date || 'N/A'}</p>
                            <p><strong>Location:</strong> {ticket.event?.venue?.name || 'N/A'} - {ticket.event?.venue?.address || ''}</p>
                            <p><strong>Seat:</strong> {ticket.seatNumber}</p>
                            <p><strong>Price:</strong> {ticket.price} RON</p>
                            <button
                                className="btn btn-outline-primary mt-2"
                                onClick={() => navigate('/seat-map', { state: { ticket } })}
                            >
                                View Seat Map
                            </button>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default TicketsPage;
