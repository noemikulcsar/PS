import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './LoginPage.css';

function Login({ setUser }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        if (!username.trim() || !password.trim()) {
            alert("Please enter both username and password.");
            return;
        }

        try {
            const response = await axios.post('http://localhost:8081/users/login', {
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
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type="submit">Login</button>
                <p className="register-link">
                    Don't have an account?{' '}
                    <span onClick={() => navigate('/register')} style={{ color: 'blue', cursor: 'pointer' }}>
                        Register
                    </span>
                </p>
            </form>
        </div>
    );
}

export default Login;
