import React, { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';
import axios from 'axios';
import './MapPage.css';
import 'leaflet/dist/leaflet.css';

delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

const customIcon = new L.Icon({
    iconUrl: 'https://cdn-icons-png.flaticon.com/512/854/854878.png',
    iconSize: [32, 32],
    iconAnchor: [16, 32],
});

function MapPage() {
    const [groupedEvents, setGroupedEvents] = useState({});

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const res = await axios.get('http://localhost:8080/events/map');
                const today = new Date();
                const upcomingEvents = res.data.filter((event) => new Date(event.date) > today);

                const grouped = {};
                upcomingEvents.forEach((event) => {
                    if (event.venue?.latitude && event.venue?.longitude) {
                        const key = `${event.venue.latitude},${event.venue.longitude}`;
                        if (!grouped[key]) {
                            grouped[key] = [];
                        }
                        grouped[key].push(event);
                    }
                });

                setGroupedEvents(grouped);
            } catch (err) {
                console.error('Error fetching events for map', err);
            }
        };

        fetchEvents();
    }, []);

    return (
        <div className="map-container">
            <h2 className="map-title">Event Locations</h2>
            <MapContainer
                center={[46.77, 23.59]}
                zoom={6}
                scrollWheelZoom={true}
                style={{ height: '600px', width: '100%' }}
            >
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a>'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {Object.entries(groupedEvents).map(([key, eventsAtLocation], index) => {
                    const [lat, lng] = key.split(',').map(Number);
                    return (
                        <Marker key={index} position={[lat, lng]} icon={customIcon}>
                            <Popup>
                                <div>
                                    <strong>{eventsAtLocation[0].venue.name}</strong><br />
                                    {eventsAtLocation.map((event) => (
                                        <div key={event.id} style={{ marginTop: '8px', borderTop: '1px solid #ccc', paddingTop: '5px' }}>
                                            <strong>{event.name}</strong><br />
                                            {event.description}<br />
                                            📅 {event.date}
                                        </div>
                                    ))}
                                </div>
                            </Popup>
                        </Marker>
                    );
                })}
            </MapContainer>
        </div>
    );
}

export default MapPage;
