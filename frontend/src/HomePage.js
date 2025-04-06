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

    return (
        <div className="homepage-wrapper">
            <nav className="navbar">
                <div className="navbar-brand">🎭 Teatru & Operă</div>
                <div className="navbar-menu">
                    <button className="nav-btn" onClick={() => navigate('/events')}>Events</button>
                    <button className="nav-btn" onClick={() => navigate("/tickets")}>Tickets</button>
                    <button className="nav-btn" onClick={() => alert("Go to Profile")}>Profile</button>
                    <button className="nav-btn logout" onClick={handleLogout}>Logout</button>
                </div>
            </nav>

            <div className="homepage-main">
                <h1>Welcome to Teatru & Operă</h1>
                <p>Explore our events, reserve your seats, and enjoy a world of culture and art.</p>
                <img
                    src={theatreImage}
                    alt="Theatre"
                    className="homepage-image"
                />
            </div>
        </div>
    );
}

export default HomePage;
