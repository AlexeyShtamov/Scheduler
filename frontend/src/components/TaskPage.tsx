import React, { useState } from 'react';
import { Fab, Autocomplete, TextField, Dialog, DialogActions, DialogContent, DialogTitle, Button } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import logo from '../assets/logo.svg';
import avatar from '../assets/avatar2.svg';
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
  const [boards, setBoards] = useState(boardOptions);
  const [isCreateBoardDialogOpen, setCreateBoardDialogOpen] = useState(false);
  const [newBoardName, setNewBoardName] = useState('');

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

  const handleCreateBoard = () => {
    if (newBoardName.trim()) {
      const newBoard = { label: newBoardName, id: (boards.length + 1).toString() };
      setBoards((prevBoards) => [...prevBoards, newBoard]);
      setSelectedBoard(newBoardName);
      setCreateBoardDialogOpen(false); 
      setNewBoardName(''); 
    }
  };

  const handleCreateSprint = (newSprintName: string, appointmentDate: string, completionDate: string) => {
    const newSprint = { title: newSprintName, id: (sprintOptionsState.length + 1).toString(), appointmentDate, completionDate };
    setSprintOptionsState((prevSprints) => [...prevSprints, newSprint]);
    setSelectedSprint(newSprintName);
  };

  const handleCreateTask = (newTask: Task) => {
    const assignedUserIndex = usersState.findIndex((user) => user.label === newTask.executor);
    if (assignedUserIndex >= 0) {
      setUsersState((prevUsers) => {
        const updatedUsers = [...prevUsers];
        updatedUsers[assignedUserIndex] = {
          ...updatedUsers[assignedUserIndex],
          tasks: { ...updatedUsers[assignedUserIndex].tasks, assigned: [...updatedUsers[assignedUserIndex].tasks.assigned, newTask] },
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
              options={[...boards, { label: 'Создать', id: '' }]}
              className="custom-autocomplete"
              sx={{ width: 300 }}
              value={{ label: selectedBoard, id: boards.length.toString() }}
              onChange={(_, value) => {
                if (value?.label === 'Создать') {
                  setCreateBoardDialogOpen(true); // Открываем диалог создания новой доски
                } else {
                  setSelectedBoard(value?.label || 'МОЯ');
                }
              }}
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
          options={[...sprintOptionsState.map((option) => option.title), 'Добавить новый спринт']}
          sx={{ width: 300 }}
          value={selectedSprint}
          onChange={(_, value) => {
            if (value === 'Добавить новый спринт') {
              handleOpenCreateSprintDialog();
            } else {
              setSelectedSprint(value || '');
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

      {/* Диалог создания доски */}
      <Dialog open={isCreateBoardDialogOpen} onClose={() => setCreateBoardDialogOpen(false)}>
        <DialogTitle>Создать новую доску</DialogTitle>
        <DialogContent>
          <TextField
            label="Название доски"
            fullWidth
            value={newBoardName}
            onChange={(e) => setNewBoardName(e.target.value)}
            autoFocus
            sx={{minWidth: '300px'}}
          />
        </DialogContent>
        <DialogActions sx={{display: 'flex', justifyContent: 'flex-start', px: 3 }}>
          <Button onClick={handleCreateBoard} 
          sx={{backgroundColor: '#FF8513', borderRadius: '555px', color: 'white'}}>Создать</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export { TaskPage };