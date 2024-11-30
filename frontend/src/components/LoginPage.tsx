import React, { useState } from "react";
import logo from '../assets/logo.svg';
import { TextField, Typography, Button} from "@mui/material";
import './css/LoginPage.css';
import faceBookIcon from '../assets/facebook-icon.svg';
import twitterIcon from '../assets/twitter-icon.svg';
import youtubeIcon from '../assets/youtube-icon.svg';
import { useNavigate } from "react-router-dom";


const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")

  const handleSubmit = (evt: React.FormEvent) => {
    evt.preventDefault();
    console.log({email, password})
    navigate("/task");
  };

   return (
    <div className="App">
      <header className="header">
        <nav>
          <div className="logo" onClick={() => navigate('/')}>
            <img src={logo} alt="BrainStorm Logo" className="logo-image"></img>
            BrainStorm
          </div>
          </nav>
          </header>
          <form className="auth-form" onSubmit={handleSubmit}>
          <Typography className="enter-text" variant="h1" component="h2">Вход</Typography>
          <TextField className="form-item" type="email" id="Email" label="Почта" variant="outlined"  onChange={(e) => setEmail(e.target.value)}/>
          <TextField className="form-item" id="Password" label="Пароль"  type="password" variant="outlined" onChange={(e) => setPassword(e.target.value)} />
          <div className="form-footer">
          <Button  className="form-button" type="submit" variant="contained">Войти</Button>
          <span className="form-span" onClick={() => navigate('/register')}>Нет аккаунта?</span>
          </div>
          </form>
          <footer className='main-footer'>
      <div className="logo">
            <img src={logo} alt="BrainStorm Logo" className="logo-image"></img>
            BrainStorm
          </div>
        <p className='footer-text'>Политика конфиденциальности</p>
        <p className='footer-text'>Соглашение на обработку персональных данных</p>
        <div className="social-icons">
    <a href="#">
      <img src={youtubeIcon} alt="Facebook" className="social-icon" />
    </a>
    <a href="#">
      <img src={faceBookIcon} alt="Instagram" className="social-icon" />
    </a>
    <a href="#">
      <img src={twitterIcon} alt="Instagram" className="social-icon" />
    </a>
        </div>
      </footer>
    </div>
);
};

export { LoginPage };