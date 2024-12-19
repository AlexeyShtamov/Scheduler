import React, { useState } from "react";
import logo from '../assets/logo.svg';
import { TextField, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { registerUser } from "../services/api";

const RegisterPage: React.FC = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    surname: "",
    phone: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.id]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Подготовка данных для отправки
    const userData = {
      firstName: formData.name,
      lastName: formData.surname,
      phone: formData.phone,
      email: formData.email,
      password: formData.password,
      repeatPassword: formData.confirmPassword,
    };

    try {
      const response = await registerUser(userData);
      console.log('Регистрация успешна:', response);
      navigate('/login'); // Перенаправление на страницу логина
    } catch (error) {
      console.error('Ошибка при регистрации:', error);
      alert(`Ошибка: ${error}`);
    }
  };

  return (
    <div className="App">
      <header className="header">
        <nav>
          <div className="logo" onClick={() => navigate('/')}>
            <img src={logo} alt="BrainStorm Logo" className="logo-image" />
            BrainStorm
          </div>
        </nav>
      </header>
      <form className="auth-form" onSubmit={handleSubmit}>
        <Typography className="enter-text" variant="h1" component="h2">
          Регистрация
        </Typography>
        <TextField
          className="form-item"
          type="text"
          id="name"
          label="Имя"
          variant="outlined"
          value={formData.name}
          onChange={handleChange}
        />
        <TextField
          className="form-item"
          type="text"
          id="surname"
          label="Фамилия"
          variant="outlined"
          value={formData.surname}
          onChange={handleChange}
        />
        <TextField
          className="form-item"
          type="tel"
          id="phone"
          label="Номер телефона"
          variant="outlined"
          value={formData.phone}
          onChange={handleChange}
        />
        <TextField
          className="form-item"
          type="email"
          id="email"
          label="Почта"
          variant="outlined"
          value={formData.email}
          onChange={handleChange}
        />
        <TextField
          className="form-item"
          id="password"
          label="Пароль"
          type="password"
          variant="outlined"
          value={formData.password}
          onChange={handleChange}
        />
        <TextField
          className="form-item"
          id="confirmPassword"
          label="Повторный пароль"
          type="password"
          variant="outlined"
          value={formData.confirmPassword}
          onChange={handleChange}
        />
        <div className="form-footer">
          <Button className="form-button" variant="contained" type="submit">
            Зарегистрироваться
          </Button>
          <span className="form-span" onClick={() => navigate('/login')}>
            Есть аккаунт?
          </span>
        </div>
      </form>
    </div>
  );
};

export { RegisterPage };