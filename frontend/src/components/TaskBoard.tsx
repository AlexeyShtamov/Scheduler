import React from 'react';
import triangleIcon from '../assets/triangle.svg';
import avatarIcon from '../assets/avatar.svg';
import checkIcon from '../assets/check.svg';


const TaskBoard: React.FC = () => {
  return (
    <div className="task-board">
      <div className="board-title-controls">
      <div style={{ display: 'flex', alignItems: 'center' }}>
      <h2>Доска</h2>
      <button className="board-button">Моя команда
      <img src={triangleIcon} alt="Стрелка" className="triangle-icon" />
      </button>
        </div>
        <div className="board-controls">
        <div>
          <button>Все</button>
          <button>Мои</button>  
        </div>
      </div>
      </div>
      <div className='main-line'></div>
      <div className="task-columns">
        <div className="column">
          <h3>НАЗНАЧЕННЫЕ</h3>
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
        <div className="column">
          <h3>В РАБОТЕ</h3>
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
        <div className="column">
          <h3>ЗАВЕРШЕННЫЕ</h3>
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
  );
};

export default TaskBoard;