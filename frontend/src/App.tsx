import React from 'react'
import TaskBoard from './components/TaskBoard';
import { LoginPage } from './components/LoginPage';
import './App.css'
import logo from './assets/logo.svg';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';

const App: React.FC = () => {
  return(
    <Router>
      <Routes>
        <Route path="/" element={
          <>
        
    <div className="App">
      <header className="header">
        <nav>
          <div className="logo">
            <img src={logo} alt="BrainStorm Logo" className="logo-image"></img>
            BrainStorm
          </div>
          <div className="auth">
            <button>Вход</button>
            <button>Регистрация</button>
          </div>
        </nav>
      </header>
      <div className="header-line"></div>
      <main>
        <section className="hero">
          <h1>Удобное добавление задач</h1>
          <p>Все, что нужно для успешной работы</p>
          <button>Начать сейчас</button>
        </section>
        <TaskBoard />
      </main>
      <footer className='main-footer'>
      <div className="logo">
            <img src={logo} alt="BrainStorm Logo" className="logo-image"></img>
            BrainStorm
          </div>
        <p className='footer-text'>Политика конфиденциальности</p>
        <p className='footer-text'>Соглашение на обработку персональных данных</p>
      </footer>
    </div>
    </>
        } />
        <Route path="/login" element={<LoginPage/>}/>
    </Routes>
    </Router>
  );
}

export default App