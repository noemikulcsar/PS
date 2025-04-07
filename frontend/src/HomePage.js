import React from 'react';
import './HomePage.css';
import { useNavigate } from 'react-router-dom';
import theatreImage from './assets/homepage.jpg';

function HomePage({ setUser }) {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('user');
        setUser(null);
        navigate('/login');
    };

    const topEvents = [
        { name: "Hamlet", date: "2025-09-17" },
        { name: "Carmen", date: "2025-11-01" },
        { name: "Spărgătorul de nuci", date: "2025-12-15" },
    ];

    return (
        <div className="homepage-wrapper">
            <nav className="navbar">
                <div className="navbar-brand">🎭 Teatru & Operă</div>
                <div className="navbar-menu">
                    <button className="nav-btn" onClick={() => navigate('/events')}>Events</button>
                    <button className="nav-btn" onClick={() => navigate("/tickets")}>Tickets</button>
                    <button className="nav-btn" onClick={() => navigate("/profile")}>Profile</button>
                    <button className="nav-btn" onClick={() => navigate("/map")}>View Map</button>
                    <button className="nav-btn logout" onClick={handleLogout}>Logout</button>
                </div>
            </nav>

            <div className="homepage-main">
                <div className="intro-section">
                    <h1>Welcome to Teatru & Operă</h1>
                    <p>Explore timeless classics and modern performances. Secure your seat and immerse yourself in a cultural journey like no other.</p>
                </div>

                <div className="image-with-sides">
                    <div className="left-facts">
                        <h3>📚 Știați că...?</h3>
                        <ul>
                            <li>Prima operă din lume a fost compusă în 1597.</li>
                            <li>Shakespeare a scris 37 de piese de teatru.</li>
                            <li>Opera Națională Română a fost fondată în 1921.</li>
                        </ul>
                    </div>

                    <img src={theatreImage} alt="Theatre" className="homepage-image" />

                    <div className="right-reviews">
                        <h3>🌟 Recenzii</h3>
                        <blockquote>„Un spectacol de neuitat! Emoție pură.”</blockquote>
                        <cite>– Andrei P.</cite>
                        <blockquote>„Rezervarea a fost ușoară, totul digitalizat.”</blockquote>
                        <cite>– Ioana L.</cite>
                    </div>
                </div>

                <div className="info-cards">
                    <div className="info-card">
                        <h3>🎟 Easy Reservations</h3>
                        <p>Book your favorite events in seconds. Just a few clicks and you're ready.</p>
                    </div>
                    <div className="info-card">
                        <h3>🗺 Event Map</h3>
                        <p>Find venues and events near you using our interactive map.</p>
                    </div>
                    <div className="info-card">
                        <h3>📄 Ticket Management</h3>
                        <p>Track and manage your bookings anytime with digital tickets and QR codes.</p>
                    </div>
                </div>

                <div className="top-events">
                    <h2>🔥 Top Upcoming Events</h2>
                    <ul>
                        {topEvents.map((event, idx) => (
                            <li key={idx}>
                                <strong>{event.name}</strong> – 📅 {event.date}
                            </li>
                        ))}
                    </ul>
                </div>
            </div>


            <footer className="homepage-footer">
                <p>© 2025 Teatru & Operă | <a href="mailto:support@teatruopera.ro">support@teatruopera.ro</a></p>
                <div className="socials">
                    <a href="https://facebook.com" target="_blank" rel="noreferrer">Facebook</a> |
                    <a href="https://instagram.com" target="_blank" rel="noreferrer"> Instagram</a> |
                    <a href="https://twitter.com" target="_blank" rel="noreferrer"> Twitter</a>
                </div>
            </footer>
        </div>
    );
}

export default HomePage;
