import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ProfilePage.css';

function ProfilePage() {
    const [user, setUser] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({});
    const [selectedFile, setSelectedFile] = useState(null);

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('user'));
        if (storedUser) {
            setUser(storedUser);
            setFormData({
                id: storedUser.id,
                name: storedUser.name,
                email: storedUser.email,
                username: storedUser.username,
                phone: storedUser.phone || '',
                address: storedUser.address || '',
                profilePictureUrl: storedUser.profilePictureUrl || ''
            });
        }
    }, []);
    console.log("profilePictureUrl =", user?.profilePictureUrl);
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleFileChange = (e) => {
        setSelectedFile(e.target.files[0]);
    };

    const validateProfile = () => {
        if (!formData.name || formData.name.length < 3) return "Name must be at least 3 characters.";
        if (!formData.email || !/\S+@\S+\.\S+/.test(formData.email)) return "Please enter a valid email.";
        if (!formData.username || formData.username.length < 3) return "Username must be at least 3 characters.";
        if (formData.phone && !/^\d{9,15}$/.test(formData.phone)) return "Phone must contain only digits (9-15).";
        if (!formData.address || formData.address.length < 5) return "Address must be at least 5 characters.";
        return null;
    };

    const handleSave = async () => {
        const errorMsg = validateProfile();
        if (errorMsg) {
            alert(errorMsg);
            return;
        }

        try {
            let profilePictureUrl = formData.profilePictureUrl;
            if (selectedFile) {
                const uploadData = new FormData();
                uploadData.append("file", selectedFile);

                const uploadResponse = await axios.post("http://localhost:8080/upload", uploadData, {
                    headers: { "Content-Type": "multipart/form-data" }
                });
                profilePictureUrl = uploadResponse.data.url;
            }

            const updatedFormData = { ...formData, profilePictureUrl };
            const response = await axios.put(`http://localhost:8080/users/${user.id}`, updatedFormData);

            const updatedUser = { ...response.data, id: user.id };
            setUser(updatedUser);
            localStorage.setItem('user', JSON.stringify(updatedUser));
            setIsEditing(false);
            setSelectedFile(null);
        } catch (error) {
            console.error("Error updating user:", error);
            alert("Failed to save profile changes.");
        }
    };

    const handleCancel = () => {
        setFormData({ ...user });
        setSelectedFile(null);
        setIsEditing(false);
    };

    if (!user) return <p className="text-center mt-4">User not found.</p>;

    return (
        <div className="profile-container">
            <h2 className="text-center mb-4">My Profile</h2>
            <div className="profile-card">
                <img
                    src={`http://localhost:8080/${user.profilePictureUrl}`}
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

                    {isEditing && (
                        <>
                            <label>Upload Profile Picture:</label>
                            <input type="file" onChange={handleFileChange} accept="image/*" />
                        </>
                    )}

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
