import React, { useState } from 'react';
import axios from 'axios';
import './LoginPage.css';

function Login({ setUser }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const baseUrl = 'http://localhost:8080';
            const response = await axios.post(`${baseUrl}/users/login`, {
                username,
                password
            });

            if (response.data) {
                localStorage.setItem('user', JSON.stringify(response.data));
                setUser(response.data);
            } else {
                alert('Invalid credentials');
            }
        } catch (error) {
            console.error(error);
            alert('Login failed. Please check your credentials.');
        }
    };

    return (
        <div className="login-container">
            <form onSubmit={handleLogin}>
                <h2>Teatru & Operă - Login</h2>
                <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
                <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
                <button type="submit">Login</button>
            </form>
        </div>
    );
}

export default Login;
