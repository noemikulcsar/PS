import React from 'react';
import { useLocation } from 'react-router-dom';
import './SeatMapPage.css';

function SeatMapPage() {
    const { state } = useLocation();
    const { ticket } = state || {};

    if (!ticket) return <p>Ticket info not available</p>;

    const capacity = ticket.event?.venue?.capacity || 0;
    const seatNumber = ticket.seatNumber;
    const seatsPerRow = 10;

    const totalRows = Math.ceil(capacity / seatsPerRow);
    const seatGrid = [];

    for (let row = 0; row < totalRows; row++) {
        const rowLetter = String.fromCharCode(65 + row);
        const seatsInRow = (row + 1 === totalRows && capacity % seatsPerRow !== 0)
            ? capacity % seatsPerRow
            : seatsPerRow;

        const rowSeats = [];
        const offset = seatsPerRow - seatsInRow;
        for (let i = 0; i < offset / 2; i++) {
            rowSeats.push(<div key={`spacer-${i}`} className="seat spacer" />);
        }

        for (let col = 1; col <= seatsInRow; col++) {
            const label = `${rowLetter}${col}`;
            const isUserSeat = label === seatNumber;

            rowSeats.push(
                <div key={label} className={`seat ${isUserSeat ? 'user-seat' : ''}`}>
                    {label}
                </div>
            );
        }

        seatGrid.push(
            <div key={rowLetter} className="seat-row">
                {rowSeats}
            </div>
        );
    }

    return (
        <div className="seat-map-container">
            <h2>{ticket.event.name} - Seat Map</h2>
            <p><strong>Date:</strong> {ticket.event.date}</p>
            <div className="stage">🎭 STAGE</div>
            <div className="seat-map">
                {seatGrid}
            </div>
        </div>
    );
}

export default SeatMapPage;
