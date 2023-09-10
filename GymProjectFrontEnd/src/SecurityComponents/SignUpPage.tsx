import React, { useState, ChangeEvent, FormEvent } from "react";
import "./SignupPage.css";
import SignUpPayLoad from "../models/SignUpPayLoad";
import { Button } from "@mui/material";
import { NavLink } from "react-router-dom";
import { Home } from "@mui/icons-material";

const SignupPage: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [email, setEmail] = useState("");

  const handleUsernameChange = (e: ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleConfirmPasswordChange = (e: ChangeEvent<HTMLInputElement>) => {
    setConfirmPassword(e.target.value);
  };

  const handleEmailChange = (e: ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const checkValidRecord = () => {
    //To validate that signup data entered is valid or not
    const checkPassword = (password === confirmPassword) ? true : false;
    return (
      !checkPassword ||
      password == "" ||
      email == "" ||
      username == ""
    ) ? false : true;
  }


  const resetForm = () => {
    // Reset the form
    setUsername("");
    setPassword("");
    setConfirmPassword("");
    setEmail("");

  }


  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    // Perform signup logic here
    // e.g., send signup request to the server, validate input, etc.

    if (!checkValidRecord()) {
      alert("Invalid data Entered!!!!!!!!!!")
      resetForm();
      return;
    }

    const signUpPayLoad: SignUpPayLoad = new SignUpPayLoad(username, password, confirmPassword, email);
    console.log("Signup submitted " + signUpPayLoad.email);

    try {
      const singupCall = await fetch("http://localhost:8080/api/auth/signup",
        {
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            "username": signUpPayLoad.username,
            "password": signUpPayLoad.password,
            "email": signUpPayLoad.email
          })
        });

      if (!singupCall.ok) throw new Error("Username already exist");

      console.log("singup completed  " + singupCall);

      alert("sign up completed!!")
      window.location.href = "http://localhost:3000/";
    }
    catch (e) {
      alert("sign up incomplete!!  "+ e)
    }

    // fetch("http://localhost:8080/api/auth/signup",
    //   {
    //     method: 'POST',
    //     headers: {
    //       'Accept': 'application/json',
    //       'Content-Type': 'application/json'
    //     },
    //     body: JSON.stringify({
    //       "username": signUpPayLoad.username,
    //       "password": signUpPayLoad.password,
    //       "email": signUpPayLoad.email
    //     })
    //   }).then((result) => {
    //     if (!result.ok) {
    //       throw new Error("Username already exist")
    //     }
    //     console.log("singup completed  " + result);
    //   }).catch((err) => {
    //     console.error(err);
    //   });

    // resetForm();
    // alert("sign up completed!!")
    // window.location.href = "http://localhost:3000/";

  };

  return (
    <div className="container signup-container">
      <h2 className="text-center">Signup</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            className="form-control"
            value={username}
            onChange={handleUsernameChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            className="form-control"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="confirmPassword">Confirm Password</label>
          <input
            type="password"
            id="confirmPassword"
            className="form-control"
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="confirmEmail">Email</label>
          <input
            type="text"
            id="username"
            className="form-control"
            value={email}
            onChange={handleEmailChange}
          />
        </div>

        <button type="submit" className="btn btn-primary btn-block">Signup</button>
      </form>
      <div>
        <NavLink to='/login'> <Button variant="contained" >Login </Button></NavLink>
        <NavLink to='/'>  <Button
          variant="contained"
          color="primary"
          startIcon={<Home />}
          className="home-button"
        ></Button> </NavLink>
      </div>

    </div>
  );
};

export default SignupPage;
