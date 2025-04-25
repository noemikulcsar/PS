import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from 'react-router-dom';
import Login from './Login';
import HomePage from './HomePage';
import PaymentPage from './PaymentPage';
import EventsPage from './EventsPage';
import TicketsPage from './TicketsPage';
import ConfirmPaymentPage from './ConfirmPaymentPage';
import 'leaflet/dist/leaflet.css';
import MapPage from './MapPage';
import SeatMapPage from './SeatMapPage';
import ProfilePage from './ProfilePage'
import FavoritesPage from './FavoritesPage';
import CalendarPage from './CalendarPage';
import RegisterPage from './RegisterPage';

function App() {
    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem('user');
        return storedUser ? JSON.parse(storedUser) : null;
    });

    useEffect(() => {
        const handleStorageChange = () => {
            const updatedUser = JSON.parse(localStorage.getItem('user'));
            setUser(updatedUser);
        };

        window.addEventListener('storage', handleStorageChange);
        return () => window.removeEventListener('storage', handleStorageChange);
    }, []);

    return (
        <Router>
            <Routes>
                <Route path="/login" element={user ? <Navigate to="/home" replace /> : <Login setUser={setUser} />} />
                <Route path="/home" element={user ? <HomePage setUser={setUser} /> : <Navigate to="/login" />} />
                <Route path="/events" element={user ? <EventsPage /> : <Navigate to="/login" />} />
                <Route path="/payment" element={user ? <PaymentPage /> : <Navigate to="/login" />} />
                <Route path="/" element={<Navigate to="/login" replace />} />
                <Route path="*" element={<Navigate to="/login" replace />} />
                <Route path="/tickets" element={user ? <TicketsPage /> : <Navigate to="/login" />} />
                <Route
                    path="/payment/confirm"
                    element={user ? <ConfirmPaymentPage /> : <Navigate to="/login" />}
                />
                <Route path="/map" element={user ? <MapPage /> : <Navigate to="/login" />} />
                <Route path="/seat-map" element={<SeatMapPage />} />
                <Route
                    path="/profile"
                    element={user ? <ProfilePage /> : <Navigate to="/login" />}
                />
                <Route path="/favorites" element={<FavoritesPage />} />
                <Route path="/calendar" element={user ? <CalendarPage /> : <Navigate to="/login" />} />
                <Route path="/register" element={<RegisterPage />} /> {/* new route */}
            </Routes>
        </Router>
    );
}

export default App;
