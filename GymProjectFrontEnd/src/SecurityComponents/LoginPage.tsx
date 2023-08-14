import React, { useState, ChangeEvent, FormEvent } from "react";
import "./LoginPage.css";
import LoginPayLoad from "../models/LoginPayLoad";
import { Home } from "@mui/icons-material";
import { Button } from "@mui/material";
import { NavLink } from "react-router-dom";

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleUsernameChange = (e: ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e: ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    // Perform login logic here
    // e.g., send login request to the server, validate credentials, etc.

    fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(new LoginPayLoad(username, password)),
    })
      .then((response) =>
      {
        if (!response.ok) {
          throw new Error('Login request failed');
        }
        return response.json();
      }
      )
      .then((data) => {
        // Store the received authentication token and username in the local storage of browser although it is not recommeneded to store there
        
        const responseData = data as { authenticationToken: string, username: string };
        localStorage.setItem('authenticationToken', responseData.authenticationToken);
        localStorage.setItem('username', responseData.username);
    

        window.location.href = "http://localhost:3000/";
      })
      .catch((error) => {
        // Handle login error (e.g., show an error message to the user)
        alert("Wrong Credentials!!!")
        console.error('Login failed:', error);
      });

    console.log("Login submitted");
    console.log("Username:", username);
    console.log("Password:", password);
    // Reset the form
    setUsername("");
    setPassword("");
  };

  return (
    <div className="container login-container">
      <h2 className="text-center">Login</h2>
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
        <button type="submit" className="btn btn-primary btn-block" disabled={!username || !password}>Login</button>
      </form>
      <NavLink to='/'>  <Button
        variant="contained"
        color="primary"
        startIcon={<Home />}
        className="home-button"
      ></Button> </NavLink>
    </div>
  );
};

export default LoginPage;
