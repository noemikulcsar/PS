import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import bankIcon from './assets/bank-icon.png';
import { jsPDF } from 'jspdf';
import QRCode from 'qrcode';
import './ConfirmPaymentPage.css';
import './useWebSocket';
import useWebSocket from "./useWebSocket";
import confetti from 'canvas-confetti';

function ConfirmPaymentPage() {
    const location = useLocation();
    const navigate = useNavigate();
    useWebSocket((notification) => {
        alert(`🎟️ New ticket bought by ${notification.username} for ${notification.eventName}, seat ${notification.seatNumber}`);
    });
    const { selectedEvent, cardData } = location.state || {};
    if (!selectedEvent || !cardData) {
        return <p className="text-center mt-4">Missing payment information.</p>;
    }
    const launchConfetti = () => {
        const duration = 3 * 1000;
        const end = Date.now() + duration;

        (function frame() {
            confetti({
                particleCount: 5,
                angle: 60,
                spread: 55,
                origin: { x: 0 },
            });
            confetti({
                particleCount: 5,
                angle: 120,
                spread: 55,
                origin: { x: 1 },
            });

            if (Date.now() < end) {
                requestAnimationFrame(frame);
            }
        })();
    };
    const generatePDF = async (user, event, seatNumber) => {
        const doc = new jsPDF();
        const qrData = `Name: ${user.name}\nEvent: ${event.name}\nDate: ${event.date}\nSeat: ${seatNumber}`;
        const qrImage = await QRCode.toDataURL(qrData);

        doc.setFontSize(20);
        doc.text("Teatru & Opera - Ticket", 20, 20);
        doc.setFontSize(12);
        doc.text(`Name: ${user.name}`, 20, 40);
        doc.text(`Event: ${event.name}`, 20, 50);
        doc.text(`Date: ${event.date}`, 20, 60);
        doc.text(`Seat: ${seatNumber}`, 20, 70);
        doc.addImage(qrImage, 'PNG', 20, 80, 50, 50);

        doc.save(`Ticket_${event.name.replace(/\s+/g, '_')}.pdf`);
    };

    const handleConfirm = async () => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (!user) {
            alert("User not logged in");
            navigate("/login");
            return;
        }

        try {
            const ticketPayload = {
                eventId: selectedEvent.id
            };

            const response = await axios.post(`http://localhost:8080/users/${user.id}/tickets`, ticketPayload);
            const savedTicket = response.data;

            await generatePDF(user, selectedEvent, savedTicket.seatNumber);
            launchConfetti();

            const toast = document.createElement('div');
            toast.innerText = "✅ Payment successful!";
            toast.style.position = 'fixed';
            toast.style.bottom = '20px';
            toast.style.right = '20px';
            toast.style.backgroundColor = '#4CAF50';
            toast.style.color = 'white';
            toast.style.padding = '10px 20px';
            toast.style.borderRadius = '5px';
            toast.style.zIndex = '1000';
            document.body.appendChild(toast);

            setTimeout(() => {
                toast.remove();
                navigate("/home", { replace: true });  // 🔄 înlocuiește pagina curentă
            }, 1500);  // Dă timp toastului să fie vizibil

        } catch (error) {
            console.error("Payment failed", error);
            alert("Payment failed.");
        }
    };

    const handleRefuse = () => {
        navigate('/payment', { state: { selectedEvent } });
    };

    return (
        <div className="confirm-container">
            <div className="confirm-card">
                <img src={bankIcon} alt="Bank Icon" className="bank-icon" />
                <h3>Confirm your payment</h3>
                <p>
                    You're about to pay <strong>{selectedEvent.price} RON</strong> for the event: <strong>{selectedEvent.name}</strong>.
                </p>
                <div className="btn-group">
                    <button className="btn btn-success" onClick={handleConfirm}>Confirm</button>
                    <button className="btn btn-danger" onClick={handleRefuse}>Refuse</button>
                </div>
            </div>
        </div>
    );
}

export default ConfirmPaymentPage;
