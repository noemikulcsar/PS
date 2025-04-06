import React from 'react';
import { useNavigate } from 'react-router-dom';

function Navbar({ setUser }) {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('user');
        setUser(null);
        navigate('/login');
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container-fluid">
                <a className="navbar-brand" href="/">Teatru & Operă</a>
                <div className="d-flex">
                    <button className="btn btn-outline-light" onClick={handleLogout}>
                        Logout
                    </button>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;
