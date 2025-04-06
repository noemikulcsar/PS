import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from 'react-router-dom';
import Login from './Login';
import HomePage from './HomePage';
import PaymentPage from './PaymentPage';
import EventsPage from './EventsPage';
import TicketsPage from './TicketsPage';

function App() {
    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem('user');
        return storedUser ? JSON.parse(storedUser) : null;
    });

    useEffect(() => {
        // Sync localStorage updates (optional if you allow logout)
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
            </Routes>
        </Router>
    );
}

export default App;
