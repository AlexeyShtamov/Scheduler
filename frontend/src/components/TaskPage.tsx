import React, { useState, useEffect } from 'react';
import { Fab, Autocomplete, TextField, Dialog, DialogActions, DialogContent, DialogTitle, Button } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import logo from '../assets/logo.svg';
import avatar from '../assets/avatar2.svg';
import AuthTaskBoard from './AuthTaskBoard';
import TaskDialog from './TaskDialog';
import { mainPagePriorityOptions, sprintOptions, users, Task } from '../const';
import CreateSprintDialog from './CreateSprintDialog';
import { getProjects, createBoard, getSprints, createSprint } from '../services/api';

interface Sprint {
  title: string;
  id: number;
  startDate: string;
  endDate: string;
  projectId: number
}

interface Board {
  id: number;
  boardName: string;
  users: string[];
  sprints: Sprint[];
}

const TaskPage: React.FC = () => {
  const [selectedBoard, setSelectedBoard] = useState('');
  const [mainPagePriority, setMainPagePriority] = useState('ВСЕ ПРИОРИТЕТЫ');
  const [usersState, setUsersState] = useState(users);
  const [isDialogOpen, setDialogOpen] = useState(false);
  const [isCreateSprintDialogOpen, setCreateSprintDialogOpen] = useState(false);
  const [sprintOptionsState, setSprintOptionsState] = useState<Sprint[]>([]);
  const [selectedSprint, setSelectedSprint] = useState<string>('');
  const [boards, setBoards] = useState<Board[]>([]); // Здесь изменяем тип на массив объектов досок
  const [isCreateBoardDialogOpen, setCreateBoardDialogOpen] = useState(false);
  const [newBoardName, setNewBoardName] = useState('');

  useEffect(() => {
  const loadBoards = async () => {
    try {
      const data = await getProjects(1); // Пример с projectId = 1
      console.log('Полученные данные:', data); // Добавьте логирование для проверки
      if (Array.isArray(data)) {
        setBoards(data);
      } else {
        console.error('Ответ от сервера не является массивом', data);
      }
    } catch (error) {
      console.error('Ошибка при загрузке досок:', error);
    }
  };

  loadBoards();
}, []);

  useEffect(() => {
  const loadSprints = async () => {
    try {
      const data = await getSprints(1);
      console.log('Спринты:', data); // Логирование полученных данных
      setSprintOptionsState(data);
    } catch (error) {
      console.error('Ошибка при загрузке спринтов:', error);
    }
  };

  loadSprints();
}, []);

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

  const handleCreateBoard = async () => {
    
      const newBoard = { boardName: newBoardName, id: 1 };
      
      try {
        // Отправляем запрос на сервер для создания доски
        await createBoard(newBoard); // Отправляем запрос без ожидания ответа, так как сервер возвращает 204
        
        // После создания доски перезагружаем список досок с сервера
        const updatedBoards = await getProjects(1); // Получаем обновленный список досок
        setBoards(updatedBoards); // Обновляем состояние досок
        setSelectedBoard(newBoardName);
        setCreateBoardDialogOpen(false);
        setNewBoardName('');
      } catch (error) {
        console.error('Ошибка при создании доски:', error);
      }

  };

  const handleCreateSprint = (newSprintName: string, startDate: string, endDate: string) => {
    const newSprint = { 
      title: newSprintName, 
      startDate, 
      endDate,
      projectId: 1,
      id: sprintOptionsState.length + 1 // Укажите id проекта, к которому относится спринт
    };
  
    setSprintOptionsState((prevSprints) => [...prevSprints, newSprint]); // Обновляем состояние с новым спринтом
  
    // Теперь отправим запрос на сервер
    try {
      createSprint(newSprint);  // Отправляем запрос для сохранения спринта на сервере
      setSelectedSprint(newSprintName);  // Выбираем только что созданный спринт
    } catch (error) {
      console.error('Ошибка при создании спринта:', error);
    }
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
              options={[...boards, { boardName: 'Создать', id: 1 }]}
              className="custom-autocomplete"
              sx={{ width: 300 }}
              value={{ boardName: selectedBoard, id: boards.length }}
              onChange={(_, value) => {
                if (value?.boardName === 'Создать') {
                  setCreateBoardDialogOpen(true); // Открываем диалог создания новой доски
                } else {
                  setSelectedBoard(value?.boardName || 'МОЯ');
                }
              }}
              getOptionLabel={(option) => option.boardName || option.boardName || ''}
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
        onDeleteTask={() => {}}
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
      <AuthTaskBoard 
        users={usersState} 
        setUsersState={setUsersState}
        filterPriority={mainPagePriority} 
      />
      <CreateSprintDialog
        open={isCreateSprintDialogOpen}
        onClose={handleCloseCreateSprintDialog}
        onCreateSprint={handleCreateSprint}
        projectId={1}
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
          <Button onClick={handleCreateBoard} sx={{backgroundColor: '#FF8513', borderRadius: '555px', color: 'white'}}>Создать</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export { TaskPage };