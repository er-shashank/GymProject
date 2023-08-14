import React, { useState, useEffect } from 'react';

import './App.css';
import { SpinnerLoading } from './utils/SpinnerLoading';
import GymPlan from './models/GymPlan';
import { GymHistoryTable } from './gymcomponents/GymHistory/GymHistory';
import { WorkOutTable } from './utils/WorkOutTable';
import { Button, Toolbar } from '@mui/material';
import { NavLink, Redirect, Route } from 'react-router-dom';
import { NestedModal } from './utils/ModalView';
import { resetGymplanFunction } from './utils/UtilityFunctions/ResetGymplanFunction';
import { WorkOutPage } from './utils/WorkOutPage';
import LoginPage from './SecurityComponents/LoginPage';
import SignupPage from './SecurityComponents/SignUpPage';
import HomePage from './gymcomponents/HomePage';
import { checkIsUserLoggedIn } from './utils/UtilityFunctions/checkIsUserLoggedIn';



const label = { inputProps: { 'aria-label': 'Checkbox demo' } };
function App() {

  return (
    <div>

      <Route path='/login' exact>
        {!checkIsUserLoggedIn() ? (
          <>
            <LoginPage></LoginPage>
            <NavLink to='/signup'> <Button variant="contained" >SignUp </Button></NavLink>
          </>)
          :
          (
            <Redirect to="/" exact></Redirect>
          )}
      </Route>

      <Route path='/workout' exact>
        {checkIsUserLoggedIn() ? (
          <WorkOutPage></WorkOutPage>) :
          (
            <Redirect to="/login"></Redirect>
          )}
      </Route>

      <Route path='/signup' exact>
        {!checkIsUserLoggedIn() ? (
          <SignupPage></SignupPage>) :
          (
            <Redirect to="/" exact></Redirect>
          )}
      </Route>


      <Route path='/history' exact>
        {checkIsUserLoggedIn() ? (
          <GymHistoryTable></GymHistoryTable>) :
          (
            <Redirect to="/login"></Redirect>
          )}
      </Route>

      <Route path='/' exact><HomePage></HomePage> </Route>

    </div>

  );
}

export default App;
