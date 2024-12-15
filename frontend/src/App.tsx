import React from 'react';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import { TaskPage } from './components/TaskPage';
import { LoginPage } from './components/LoginPage';
import { RegisterPage } from './components/registerPage';
import './App.css';
import { MainContent } from './components/MainContent';





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