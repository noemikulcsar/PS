import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './LoginPage.css';

function Register() {
    const [formData, setFormData] = useState({
        name: '',
        username: '',
        email: '',
        password: '',
        profilePictureUrl: '',
        address: '',
        phone: ''
    });
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleRegister = async (e) => {
        e.preventDefault();

        const requiredFields = ['name', 'username', 'email', 'password'];
        for (let field of requiredFields) {
            if (!formData[field].trim()) {
                alert(`Please fill in ${field}`);
                return;
            }
        }

        try {
            const response = await axios.post('http://localhost:8081/users/register', formData);
            alert('User registered successfully! You can now login.');
            navigate('/login');
        }
        catch (error) {
            console.error('Registration failed:', error);
            const message = error.response?.data || 'Registration failed. Try again.';
            alert(message);
        }
    };

    return (
        <div className="login-container">
            <form onSubmit={handleRegister}>
                <h2>Register Account</h2>
                <input className="register-input" type="text" name="name" placeholder="Full Name" value={formData.name} onChange={handleChange} required />
                <input className="register-input" type="text" name="username" placeholder="Username" value={formData.username} onChange={handleChange} required />
                <input className="register-input" type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} required />
                <input className="register-input" type="password" name="password" placeholder="Password" value={formData.password} onChange={handleChange} required />
                <input className="register-input" type="text" name="phone" placeholder="Phone (optional)" value={formData.phone} onChange={handleChange} />
                <input className="register-input" type="text" name="address" placeholder="Address (optional)" value={formData.address} onChange={handleChange} />
                <input className="register-input" type="text" name="profilePictureUrl" placeholder="Profile Picture URL (optional)" value={formData.profilePictureUrl} onChange={handleChange} />
                <button type="submit">Register</button>
            </form>
        </div>
    );
}

export default Register;
