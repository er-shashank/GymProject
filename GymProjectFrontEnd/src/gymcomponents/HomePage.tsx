import { Home } from '@mui/icons-material';
import { Button } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { NavLink } from 'react-router-dom';
import { LogoutFunction } from '../utils/UtilityFunctions/LogoutFunction';
import { checkIsUserLoggedIn } from '../utils/UtilityFunctions/checkIsUserLoggedIn';
import { noOfWorkoutPlans } from '../utils/UtilityFunctions/noOfWorkoutPlans';
import { NestedModal } from '../utils/ModalView';
import CustomizedTables from '../models/GymplansTable';
import GymplanTable from '../models/GymplansTable';




function HomePage() {
  const [isUserLoggedIn, setIsUserLoggedIn] = useState(false);
  const [noOfWorkoutPlan, setNoOfWorkoutPlan] = useState(0);

  /**
   * below code runs only once becasue useEffect dependency is []
   */
  useEffect(() => {
    async function fetchData() {
      const loggedIn = checkIsUserLoggedIn();
      setIsUserLoggedIn(loggedIn);

      if (loggedIn) {
        try {
          const workoutPlan = await noOfWorkoutPlans();
          setNoOfWorkoutPlan(Number(workoutPlan));
        } catch (error) {
          console.error('Error fetching workout plans:', error);
        }
      }
    }

    fetchData();
  }, []);

  return (
    <div>
      <div style={{ textAlign: 'right', padding: '10px' }}>
        {isUserLoggedIn && (
          <NavLink to='/login'>
            <button onClick={LogoutFunction}>Logout</button>
          </NavLink>
        )}

        {!isUserLoggedIn && (
          <NavLink to="/signup">
            <button>Sign Up</button>
          </NavLink>
        )}

        {!isUserLoggedIn && (
          <NavLink to="/login">
            <button>Log In</button>
          </NavLink>
        )}

        {isUserLoggedIn && noOfWorkoutPlan > 0 && (
          <NavLink to="/workout">
            <button>WorkOut Plan</button>
          </NavLink>
        )}
      </div>
      <h1 style={{ fontSize: '3rem', textAlign: 'center' }}>Hi {localStorage.getItem('username')}!<br /> Your Fitness Tracker</h1>
      <p style={{ textAlign: 'center' }}>
        Track your fitness progress and achieve your goals!
      </p>

      {isUserLoggedIn && <div>
        you have {noOfWorkoutPlan} plans

        <>
        <NestedModal></NestedModal>
        </>
      </div>}

      
      {/* Additional content */}
      

     <GymplanTable></GymplanTable>
    </div>




      
  );
}

export default HomePage;

