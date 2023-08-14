import { Home } from '@mui/icons-material';
import { Button } from '@mui/material';
import React from 'react';
import { NavLink } from 'react-router-dom';
import { LogoutFunction } from '../utils/UtilityFunctions/LogoutFunction';
import { checkIsUserLoggedIn } from '../utils/UtilityFunctions/checkIsUserLoggedIn';




function HomePage() {
    const isUserLoggedIn:boolean=checkIsUserLoggedIn();
    return (
        <div>
            <div style={{ textAlign: 'right', padding: '10px' }}>

                {isUserLoggedIn && 
                <NavLink to='/login'> <button onClick={LogoutFunction}>Logout </button ></NavLink>}

                {!isUserLoggedIn && (<NavLink to="/signup">
                    <button>Sign Up</button>
                </NavLink>)}


                {!isUserLoggedIn && (<NavLink to="/login">
                    <button>Log In</button>
                </NavLink>)}

                {isUserLoggedIn && (
                    <NavLink to="/workout">
                        <button>WorkOut Plan</button>
                    </NavLink>
                )}


            </div>
            <h1 style={{ fontSize: '3rem', textAlign: 'center' }}>Hi {localStorage.getItem('username')}!<br /> Your Fitness Tracker</h1>
            <p style={{ textAlign: 'center' }}>
                Track your fitness progress and achieve your goals!
            </p>
            {/* Additional content */}
        </div>
    );
}

export default HomePage;
