import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './TicketsPage.css';
import QRCode from 'qrcode';

function TicketsPage() {
    const [tickets, setTickets] = useState([]);
    const [filteredTickets, setFilteredTickets] = useState([]);
    const [minPrice, setMinPrice] = useState('');
    const [maxPrice, setMaxPrice] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [locations, setLocations] = useState([]);
    const [sortOption, setSortOption] = useState('');
    const [visibleQrIndex, setVisibleQrIndex] = useState(null);
    const [qrCodes, setQrCodes] = useState({});
    const [favoriteIds, setFavoriteIds] = useState([]);

    const navigate = useNavigate();

    const user = JSON.parse(localStorage.getItem('user'));

    useEffect(() => {
        const fetchTickets = async () => {
            if (!user?.id) return;

            try {
                const response = await axios.get(`http://localhost:8080/users/${user.id}/tickets`);
                setTickets(response.data || []);
                setFilteredTickets(response.data || []);
            } catch (error) {
                console.error('Error fetching tickets:', error);
            }
        };

        const fetchFavorites = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/users/${user.id}/favorites`);
                setFavoriteIds(response.data.map(f => f.id)); // sau .eventId în funcție de DTO
            } catch (error) {
                console.error('Failed to fetch favorites:', error);
            }
        };

        fetchTickets();
        fetchFavorites();
    }, [user?.id]);

    const handleToggleFavorite = async (eventId) => {
        if (!user) {
            alert("Please login to manage favorites.");
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
            console.error("Error toggling favorite:", error);
        }
    };

    const handleFilter = async () => {
        try {
            const response = await axios.post(`http://localhost:8080/users/${user.id}/tickets/filter`, {
                minPrice: minPrice !== '' ? parseInt(minPrice) : null,
                maxPrice: maxPrice !== '' ? parseInt(maxPrice) : null,
                startDate: startDate !== '' ? startDate : null,
                endDate: endDate !== '' ? endDate : null,
                locations: locations.length > 0 ? locations : null,
                sortOption: sortOption !== '' ? sortOption : null,
            }, {
                headers: {
                    "Content-Type": "application/json"
                }
            });

            setFilteredTickets(response.data);
        } catch (error) {
            console.error("Eroare la filtrarea biletelor:", error);
        }
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

    const generateQr = async (ticket, index) => {
        const qrData = `Name: ${user.name}\nEvent: ${ticket.event.name}\nDate: ${ticket.event.date}\nSeat: ${ticket.seatNumber}`;
        try {
            const qrImage = await QRCode.toDataURL(qrData);
            setQrCodes(prev => ({ ...prev, [index]: qrImage }));
            setVisibleQrIndex(index);
        } catch (err) {
            console.error('Failed to generate QR code:', err);
        }
    };

    const uniqueLocations = [...new Set(tickets.map(t => t.event?.venue?.name).filter(Boolean))];

    const downloadXml = async () => {
        if (!user?.id) return;
        try {
            const response = await axios.get(`http://localhost:8080/users/${user.id}/tickets/xml`, {
                headers: { 'Accept': 'application/xml' },
                responseType: 'blob'
            });
            const blob = new Blob([response.data], { type: 'application/xml' });
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');

            link.href = url;
            link.download = `tickets_user_${user.username}.xml`;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(url);
        } catch (error) {
            console.error("Eroare la descărcarea XML:", error);
            alert("Nu s-a putut descărca fișierul XML.");
        }
    };

    return (
        <div className="tickets-container">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="mb-0">My Tickets</h2>
                <button className="btn btn-outline-info" onClick={downloadXml}>
                    Export Tickets (XML)
                </button>
            </div>

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
                    <select value={sortOption} onChange={e => setSortOption(e.target.value)}>
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
                    {filteredTickets.map((ticket, index) => (
                        <div key={ticket.id} className="ticket-card" style={{ position: 'relative' }}>
                            {/* Favorite Star */}
                            <span
                                onClick={() => handleToggleFavorite(ticket.event.id)}
                                style={{
                                    position: 'absolute',
                                    top: '10px',
                                    right: '10px',
                                    fontSize: '24px',
                                    cursor: 'pointer',
                                    color: favoriteIds.includes(ticket.event.id) ? '#FFD700' : '#ccc',
                                    transition: 'color 0.2s ease'
                                }}
                                title={favoriteIds.includes(ticket.event.id) ? 'Remove from favorites' : 'Add to favorites'}
                            >
                                ★
                            </span>

                            <h3>{ticket.event?.name || 'Unknown Event'}</h3>
                            <p><strong>Description:</strong> {ticket.event?.description || 'N/A'}</p>
                            <p><strong>Date:</strong> {ticket.event?.date || 'N/A'}</p>
                            <p><strong>Location:</strong> {ticket.event?.venue?.name || 'N/A'} - {ticket.event?.venue?.address || ''}</p>
                            <p><strong>Seat:</strong> {ticket.seatNumber}</p>
                            <p><strong>Price:</strong> {ticket.price} RON</p>

                            <div className="ticket-actions">
                                <button
                                    className="btn btn-outline-primary"
                                    onClick={() => navigate('/seat-map', { state: { ticket } })}
                                >
                                    View Seat Map
                                </button>
                                <div
                                    className="qr-wrapper"
                                    onMouseEnter={async () => {
                                        await generateQr(ticket, index);
                                        setVisibleQrIndex(index);
                                    }}
                                    onMouseLeave={() => setVisibleQrIndex(null)}
                                    onClick={() => {
                                        const image = qrCodes[index];
                                        if (image) {
                                            const link = document.createElement('a');
                                            link.href = image;
                                            link.download = `ticket_qr_${ticket.id}.png`;
                                            document.body.appendChild(link);
                                            link.click();
                                            document.body.removeChild(link);
                                        }
                                    }}
                                >
                                    <button className="btn btn-outline-success">View QR Code</button>
                                    {visibleQrIndex === index && qrCodes[index] && (
                                        <div className="qr-floating-box">
                                            <img src={qrCodes[index]} alt="QR Code" />
                                            <p style={{ fontSize: '0.75rem', marginTop: '5px' }}>Click to download</p>
                                        </div>
                                    )}
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default TicketsPage;
