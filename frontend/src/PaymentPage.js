import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './PaymentPage.css';

function PaymentPage() {
    const location = useLocation();
    const navigate = useNavigate();
    const event = location.state?.selectedEvent;

    if (!event) {
        return <p className="text-center mt-4">No event selected. Please go back and select an event.</p>;
    }

    const handlePaymentSubmit = (e) => {
        e.preventDefault();
        // Mock: afisare mesaj + redirectionare
        alert('Payment successful! Ticket purchased.');
        navigate('/home');
    };

    return (
        <div className="payment-container">
            <div className="payment-card">
                <h2 className="mb-3">Payment for: {event.name}</h2>
                <p><strong>Date:</strong> {event.date}</p>
                <p><strong>Location:</strong> {event.venue.name}</p>

                <form onSubmit={handlePaymentSubmit}>
                    <div className="form-group">
                        <label>Card Number</label>
                        <input type="text" className="form-control" required />
                    </div>
                    <div className="form-group">
                        <label>Card Holder</label>
                        <input type="text" className="form-control" required />
                    </div>
                    <div className="form-group">
                        <label>Expiration Date</label>
                        <input type="month" className="form-control" required />
                    </div>
                    <div className="form-group">
                        <label>CVV</label>
                        <input type="password" className="form-control" maxLength="3" required />
                    </div>
                    <button type="submit" className="btn btn-success w-100 mt-3">Pay</button>
                </form>
            </div>
        </div>
    );
}

export default PaymentPage;
