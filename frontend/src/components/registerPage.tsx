import React from "react";
import logo from '../assets/logo.svg';
import { TextField, Typography, Button} from "@mui/material";
import './css/LoginPage.css';
import faceBookIcon from '../assets/facebook-icon.svg';
import twitterIcon from '../assets/twitter-icon.svg';
import youtubeIcon from '../assets/youtube-icon.svg';
import { useNavigate } from "react-router-dom";


const RegisterPage: React.FC = () => {
    const navigate = useNavigate();

   return (
    <div className="App">
      <header className="header">
        <nav>
          <div className="logo">
            <img src={logo} alt="BrainStorm Logo" className="logo-image"></img>
            BrainStorm
          </div>
          </nav>
          </header>
          <div className="auth-form">
          <Typography className="enter-text" variant="h1" component="h2">Регистрация</Typography>
          <TextField className="form-item" type="text" id="name" label="Имя" variant="outlined" />
          <TextField className="form-item" type="text" id="surname" label="Фамилия" variant="outlined" />
          <TextField className="form-item" type="tel" id="tel" label="Номер телефона" variant="outlined" />
          <TextField className="form-item" type="email" id="Email" label="Почта" variant="outlined" />
          <TextField className="form-item" id="Password" label="Пароль"  type="password" variant="outlined" />
          <TextField className="form-item" id="Password" label="Повторный пароль"  type="password" variant="outlined" />
          <div className="form-footer">
          <Button  className="form-button" variant="contained">Зарегистрироваться</Button>
          <span className="form-span" onClick={() => navigate('/login')}>Есть аккаунт?</span>
          </div>
          </div>
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

export { RegisterPage };