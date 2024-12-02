import React from 'react';
import avatarIcon from '../assets/avatar.svg';
import checkIcon from '../assets/check.svg';
import { users } from '../const';


const AuthTaskBoard: React.FC = () => {
    return (
      <div className="task-board task-board-auth">
        {/* Заголовки для колонок */}
        <div className="task-columns-header">
          <h3>НАЗНАЧЕННЫЕ</h3>
          <h3>В РАБОТЕ</h3>
          <h3>РЕВЬЮ</h3>
          <h3>ЗАВЕРШЕННЫЕ</h3>
        </div>
        
        {users.map((user, index) => (
          <div key={index} className="user-section">
            {/* Имя пользователя */}
            <div className='main-line'></div>
            <h2>{user.label}</h2>
  
            <div className='main-line'></div>
            <div className="task-columns">
              {/* Колонка для Назначенных */}
              <div className="column">
                <div className="task-card">
                  <div className="task-card-header">
                    <span className='task-title'>Исправление ошибок</span>
                    <img src={avatarIcon} alt="Аватар" className="avatar-icon" />
                  </div>
                  <div className="task-card-footer">
                    <img src={checkIcon} alt="Галочка" className="check-icon" />
                    <span className="time-text">1,5ч</span>
                  </div>
                </div>
              </div>
  
              {/* Колонка для В Работе */}
              <div className="column">
                <div className="task-card">
                  <div className="task-card-header">
                    <span className='task-title'>Исправление ошибок</span>
                    <img src={avatarIcon} alt="Аватар" className="avatar-icon" />
                  </div>
                  <div className="task-card-footer">
                    <img src={checkIcon} alt="Галочка" className="check-icon" />
                    <span className="time-text">1,5ч</span>
                  </div>
                </div>
              </div>
  
              {/* Колонка для Ревью */}
              <div className="column">
                <div className="task-card">
                  <div className="task-card-header">
                    <span className='task-title'>Исправление ошибок</span>
                    <img src={avatarIcon} alt="Аватар" className="avatar-icon" />
                  </div>
                  <div className="task-card-footer">
                    <img src={checkIcon} alt="Галочка" className="check-icon" />
                    <span className="time-text">1,5ч</span>
                  </div>
                </div>
              </div>
  
              {/* Колонка для Завершенных */}
              <div className="column">
                <div className="task-card">
                  <div className="task-card-header">
                    <span className='task-title'>Исправление ошибок</span>
                    <img src={avatarIcon} alt="Аватар" className="avatar-icon" />
                  </div>
                  <div className="task-card-footer">
                    <img src={checkIcon} alt="Галочка" className="check-icon" />
                    <span className="time-text">1,5ч</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    );
  };
  
  export default AuthTaskBoard;
