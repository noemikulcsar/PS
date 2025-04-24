import React, { useEffect, useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import './CalendarPage.css';
import axios from 'axios';
import maskImage from './assets/mask.jpg';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

function CalendarView() {
    const [value, setValue] = useState(new Date());
    const [events, setEvents] = useState([]);
    const [monthlyEvents, setMonthlyEvents] = useState([]);

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const response = await axios.get('http://localhost:8080/events');
                setEvents(response.data);
                updateMonthlyEvents(response.data, value);
            } catch (error) {
                console.error('Error fetching events:', error);
            }
        };
        fetchEvents();
    }, []);

    const updateMonthlyEvents = (allEvents, date) => {
        const currentMonth = date.getMonth();
        const currentYear = date.getFullYear();
        const filtered = allEvents.filter(e => {
            const eventDate = new Date(e.date);
            return eventDate.getMonth() === currentMonth && eventDate.getFullYear() === currentYear;
        });
        setMonthlyEvents(filtered);
    };

    const handleActiveStartDateChange = ({ activeStartDate }) => {
        updateMonthlyEvents(events, activeStartDate);
    };

    const tileClassName = ({ date, view }) => {
        const today = new Date();
        const isToday = date.toDateString() === today.toDateString();

        if (view === 'month') {
            const hasEvent = events.some(e => {
                const eventDate = new Date(e.date);
                return eventDate.toDateString() === date.toDateString();
            });

            return hasEvent ? (isToday ? 'highlight-today highlight-event' : 'highlight-event') : (isToday ? 'highlight-today' : null);
        }
    };

    const sliderSettings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1
    };

    return (
        <div className="calendar-container">
            <h2 className="text-center">📅 Calendar View</h2>

            <div className="calendar-flex-layout">
                <div className="monthly-events">
                    <h3>🎭 Events This Month</h3>
                    {monthlyEvents.length === 0 ? (
                        <p className="text-muted">No events this month.</p>
                    ) : (
                        <ul>
                            {monthlyEvents.map(e => (
                                <li key={e.id}>
                                    <strong>{e.name}</strong> – {e.date}
                                </li>
                            ))}
                        </ul>
                    )}
                </div>

                <Calendar
                    onChange={setValue}
                    value={value}
                    tileClassName={tileClassName}
                    onActiveStartDateChange={handleActiveStartDateChange}
                />
            </div>

            <div className="calendar-footer">
                <hr className="decor-line" />
                <div className="footer-layout">
                    <div className="footer-text">
                        <h3>🌟 Magia Spectacolelor</h3>
                        <p>Fiecare zi poate fi o scenă. Descoperă momente de neuitat în lumea teatrului și operei.</p>
                        <blockquote>
                            „Toată lumea e o scenă, iar oamenii sunt doar actori.” – William Shakespeare
                        </blockquote>
                    </div>
                    <img src={maskImage} alt="Mask" className="footer-mask" />
                </div>

                <div className="popular-events">
                    <h3>🎫 Popular Events</h3>
                    <Slider {...sliderSettings}>
                        <div><p>🎭 Hamlet – 2025-09-17</p></div>
                        <div><p>🎼 Carmen – 2025-11-01</p></div>
                        <div><p>🎄 Spărgătorul de Nuci – 2025-12-15</p></div>
                    </Slider>
                </div>
            </div>
        </div>
    );
}

export default CalendarView;
