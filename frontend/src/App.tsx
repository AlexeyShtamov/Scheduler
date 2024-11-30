import React from 'react';
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import TaskBoard from './components/TaskBoard';
import { TaskPage } from './components/TaskPage';
import { LoginPage } from './components/LoginPage';
import { RegisterPage } from './components/registerPage';
import './App.css';
import logo from './assets/logo.svg';
import faceBookIcon from './assets/facebook-icon.svg';
import twitterIcon from './assets/twitter-icon.svg';
import youtubeIcon from './assets/youtube-icon.svg';


const MainContent: React.FC = () => {
  const navigate = useNavigate(); 

  const handleLoginClick = () => {
    navigate('/login')
  }

  const handleRegisterClick = () => {
    navigate('/register')
  }

  return (
    <div className="App">
      <header className="header">
        <nav>
          <div className="logo">
            <img src={logo} alt="BrainStorm Logo" className="logo-image" />
            BrainStorm
          </div>
          <div className="auth">
            <button onClick={handleLoginClick}>Вход</button>
            <button onClick={handleRegisterClick}>Регистрация</button>
          </div>
        </nav>
      </header>
      <div className="header-line"></div>
      <main>
        <section className="hero">
          <h1>Удобное добавление задач</h1>
          <p>Все, что нужно для успешной работы</p>
          <button onClick={handleRegisterClick}>Начать сейчас</button>
        </section>
        <TaskBoard />
      </main>
      <footer className="main-footer">
        <div className="logo">
          <img src={logo} alt="BrainStorm Logo" className="logo-image" />
          BrainStorm
        </div>
        <p className="footer-text">Политика конфиденциальности</p>
        <p className="footer-text">Соглашение на обработку персональных данных</p>
        <div className="social-icons">
          <a href="#">
            <img src={youtubeIcon} alt="YouTube" className="social-icon" />
          </a>
          <a href="#">
            <img src={faceBookIcon} alt="Facebook" className="social-icon" />
          </a>
          <a href="#">
            <img src={twitterIcon} alt="Twitter" className="social-icon" />
          </a>
        </div>
      </footer>
    </div>
  );
}

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainContent />} /> 
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage/>}/>
        <Route path="/task" element={<TaskPage/>}/>
      </Routes>
    </Router>
  );
}

export default App;