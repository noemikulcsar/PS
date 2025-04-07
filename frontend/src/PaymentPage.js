import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './PaymentPage.css';

function PaymentPage() {
    const location = useLocation();
    const navigate = useNavigate();
    const event = location.state?.selectedEvent;

    const [cardNumber, setCardNumber] = useState('');
    const [cardHolder, setCardHolder] = useState('');
    const [expirationDate, setExpirationDate] = useState('');
    const [cvv, setCvv] = useState('');
    const [error, setError] = useState('');

    const user = JSON.parse(localStorage.getItem('user'));

    const validateCard = () => {
        const cardRegex = /^\d{16}$/;
        const cvvRegex = /^\d{3}$/;
        const today = new Date();
        const [expYear, expMonth] = expirationDate.split('-') || [];

        if (!cardRegex.test(cardNumber)) return "Card number must be 16 digits.";
        if (!cvvRegex.test(cvv)) return "CVV must be 3 digits.";
        if (!cardHolder || cardHolder.toLowerCase() !== user.name.toLowerCase()) return "Card holder must match your name.";
        if (!expirationDate || new Date(expYear, expMonth) < today) return "Card has expired.";

        return null;
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const errorMsg = validateCard();
        if (errorMsg) {
            setError(errorMsg);
            return;
        }

        navigate('/payment/confirm', {
            state: {
                selectedEvent: event,
                cardData: { cardNumber, cardHolder, expirationDate, cvv }
            }
        });
    };

    if (!event) {
        return <p className="text-center mt-4">No event selected. Please go back and select an event.</p>;
    }

    return (
        <div className="payment-container">
            <div className="payment-card">
                <h2>Payment for: {event.name}</h2>
                <p><strong>Date:</strong> {event.date}</p>
                <p><strong>Location:</strong> {event.venue.name}</p>

                {error && <p className="error-msg">{error}</p>}

                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        placeholder="Card Number"
                        value={cardNumber}
                        onChange={e => setCardNumber(e.target.value)}
                        required
                    />
                    <input
                        type="text"
                        placeholder="Card Holder"
                        value={cardHolder}
                        onChange={e => setCardHolder(e.target.value)}
                        required
                    />
                    <input
                        type="month"
                        value={expirationDate}
                        onChange={e => setExpirationDate(e.target.value)}
                        required
                    />
                    <input
                        type="password"
                        placeholder="CVV"
                        maxLength={3}
                        value={cvv}
                        onChange={e => setCvv(e.target.value)}
                        required
                    />
                    <button type="submit" className="btn-pay">Continue</button>
                </form>
            </div>
        </div>
    );
}

export default PaymentPage;
