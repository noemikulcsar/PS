import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ProfilePage.css';
import profile1 from './assets/profile-page.jpeg';
import profile2 from './assets/profile-page.jpeg';

const profilePictures = {
    'noemi': profile1,
    'admin': profile2
};

function ProfilePage() {
    const [user, setUser] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({});

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('user'));
        if (storedUser) {
            setUser(storedUser);
            setFormData({ ...storedUser }); // copie defensivă
        }
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSave = async () => {
        try {
            const response = await axios.put(`http://localhost:8080/users/${formData.id}`, formData);
            const updatedUser = response.data;
            setUser(updatedUser);
            setFormData(updatedUser);
            localStorage.setItem('user', JSON.stringify(updatedUser));
            setIsEditing(false);
        } catch (error) {
            console.error("Error updating user:", error);
            alert("Failed to save profile changes.");
        }
    };

    const handleCancel = () => {
        setFormData({ ...user }); // resetează la valorile originale
        setIsEditing(false);
    };

    if (!user) return <p className="text-center mt-4">User not found.</p>;

    return (
        <div className="profile-container">
            <h2 className="text-center mb-4">My Profile</h2>
            <div className="profile-card">
                <img
                    src={profilePictures[user.username] || profile1}
                    alt="Profile"
                    className="profile-picture"
                />

                <div className="profile-details">
                    <label>Name:</label>
                    {isEditing ? (
                        <input type="text" name="name" value={formData.name || ''} onChange={handleChange} />
                    ) : <p>{user.name}</p>}

                    <label>Email:</label>
                    {isEditing ? (
                        <input type="email" name="email" value={formData.email || ''} onChange={handleChange} />
                    ) : <p>{user.email}</p>}

                    <label>Username:</label>
                    {isEditing ? (
                        <input type="text" name="username" value={formData.username || ''} onChange={handleChange} />
                    ) : <p>{user.username}</p>}

                    <label>Phone:</label>
                    {isEditing ? (
                        <input type="text" name="phone" value={formData.phone || ''} onChange={handleChange} />
                    ) : <p>{user.phone || '-'}</p>}

                    <label>Address:</label>
                    {isEditing ? (
                        <input type="text" name="address" value={formData.address || ''} onChange={handleChange} />
                    ) : <p>{user.address || '-'}</p>}

                    <div className="profile-buttons">
                        {isEditing ? (
                            <>
                                <button className="btn btn-success" onClick={handleSave}>Save</button>
                                <button className="btn btn-secondary" onClick={handleCancel}>Cancel</button>
                            </>
                        ) : (
                            <button className="btn btn-primary" onClick={() => setIsEditing(true)}>Edit Profile</button>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProfilePage;
