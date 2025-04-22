import { useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const useWebSocket = (onMessage) => {
    useEffect(() => {
        const client = new Client({
            webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
            reconnectDelay: 5000,
            onConnect: () => {
                client.subscribe('/topic/tickets', (message) => {
                    const data = JSON.parse(message.body);
                    onMessage(data);
                });
            },
            onStompError: (frame) => {
                console.error('STOMP error:', frame);
            }
        });

        client.activate();

        return () => {
            client.deactivate();
        };
    }, [onMessage]);
};

export default useWebSocket;
