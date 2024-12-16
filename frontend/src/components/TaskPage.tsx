import React, { useState } from 'react';
import { Fab, Autocomplete, TextField } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import logo from '../assets/logo.svg';
import avatar from '../assets/avatar2.svg'
import AuthTaskBoard from './AuthTaskBoard';
import TaskDialog from './TaskDialog';
import { boardOptions, mainPagePriorityOptions, sprintOptions, users, Task } from '../const';
import CreateSprintDialog from './CreateSprintDialog';

const TaskPage: React.FC = () => {
  const [selectedBoard, setSelectedBoard] = useState('МОЯ');
  const [mainPagePriority, setMainPagePriority] = useState('ВСЕ ПРИОРИТЕТЫ');
  const [usersState, setUsersState] = useState(users); 
  const [isDialogOpen, setDialogOpen] = useState(false);
  const [isCreateSprintDialogOpen, setCreateSprintDialogOpen] = useState(false);
  const [sprintOptionsState, setSprintOptionsState] = useState(sprintOptions);
  const [selectedSprint, setSelectedSprint] = useState<string>('');

  const handleOpenDialog = () => {
    setDialogOpen(true);
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
  };

  const handleOpenCreateSprintDialog = () => {
    setCreateSprintDialogOpen(true);

  };

  const handleCloseCreateSprintDialog = () => {
    setCreateSprintDialogOpen(false);
  };

  const handleCreateSprint = (newSprintName: string) => {
    const newSprint = { label: newSprintName };
    setSprintOptionsState((prevSprints) => [...prevSprints, newSprint]);
    setSelectedSprint(newSprintName); // Добавляем новый спринт в список
  };

  // Добавляем задачу в состояние пользователя
  const handleCreateTask = (newTask: Task) => {
    const assignedUserIndex = usersState.findIndex(
      (user) => user.label === newTask.executor
    );

    if (assignedUserIndex >= 0) {
      setUsersState((prevUsers) => {
        const updatedUsers = [...prevUsers];
        updatedUsers[assignedUserIndex] = {
          ...updatedUsers[assignedUserIndex],
          tasks: {
            ...updatedUsers[assignedUserIndex].tasks,
            assigned: [...updatedUsers[assignedUserIndex].tasks.assigned, newTask],
          },
        };
        return updatedUsers;
      });
    }
  };

  return (
    <div className="App">
      <header className="header">
        <nav className="header-nav">
          <div className="logo">
            <img src={logo} alt="BrainStorm Logo" className="logo-image" />
            BrainStorm
          </div>
          <div className="header-fields">
            <Autocomplete
              disablePortal
              options={boardOptions}
              className="custom-autocomplete"
              sx={{ width: 300 }}
              value={{ label: selectedBoard }}
              onChange={(_, value) => setSelectedBoard(value?.label || 'МОЯ')}
              renderInput={(params) => <TextField {...params} label="Доска" />}
            />
            <Autocomplete
              disablePortal
              options={mainPagePriorityOptions}
              className="custom-autocomplete"
              sx={{ width: 300 }}
              value={{ label: mainPagePriority }}
              onChange={(_, value) => setMainPagePriority(value?.label || 'ВСЕ ПРИОРИТЕТЫ')}
              renderInput={(params) => <TextField {...params} label="Приоритет" />}
            />
            <Fab color="primary" aria-label="add" className="add-icon" onClick={handleOpenDialog}>
              <AddIcon />
            </Fab>
          </div>
          <div className="header-avatar">
            <div className="avatar-wrapper">
              <img src={avatar} alt="User Avatar" className="avatar-image" />
            </div>
          </div>
        </nav>
      </header>
      <div className="header-line"></div>
      <TaskDialog
        open={isDialogOpen}
        onClose={handleCloseDialog}
        onCreateTask={handleCreateTask}
      />
      <p className="header-card">
        Моя команда
        <Autocomplete
          disablePortal
          options={[...sprintOptionsState, { label: 'Добавить новый спринт' }]}
          sx={{ width: 300 }}
          value={{label: selectedSprint}}
          onChange={(_, value) => {
            if (value?.label === 'Добавить новый спринт') {
              handleOpenCreateSprintDialog(); // Открываем диалог создания нового спринта
            }else{
              setSelectedSprint(value?.label || '' )
            }
          }}
          renderInput={(params) => <TextField {...params} label="Спринт" />}
        />
      </p>
      <div className="header-card-line"></div>
      <AuthTaskBoard users={usersState} setUsersState={setUsersState} />
      <CreateSprintDialog
        open={isCreateSprintDialogOpen}
        onClose={handleCloseCreateSprintDialog}
        onCreateSprint={handleCreateSprint}
      />
    </div>
  );
};

export { TaskPage };