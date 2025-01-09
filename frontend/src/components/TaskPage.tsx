import React, { useState, useEffect } from 'react';
import { Autocomplete, TextField, Dialog, DialogActions, DialogContent, DialogTitle, Button, Fab } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import logo from '../assets/logo.svg';
import avatar from '../assets/avatar2.svg';
import { AuthTaskBoard } from './AuthTaskBoard';
import TaskDialog from './TaskDialog';
import { mainPagePriorityOptions } from '../const';
import CreateSprintDialog from './CreateSprintDialog';
import { getProjects, createBoard, createSprint, getSprints } from '../services/api';

export interface Sprint {
  title: string;
  id: number;
  startDate: string;
  endDate: string;
  projectId: number;
}

interface Board {
  id: number;
  boardName: string;
  users: string[]; // Пользователи, связанные с доской
  sprints: Sprint[];
}

const TaskPage: React.FC = () => {
  const [selectedBoard, setSelectedBoard] = useState('');
  const [mainPagePriority, setMainPagePriority] = useState('ВСЕ ПРИОРИТЕТЫ');
  const [usersState, setUsersState] = useState<any[]>([]); // Используем any[] пока не знаем точную структуру
  const [isDialogOpen, setDialogOpen] = useState(false);
  const [isCreateSprintDialogOpen, setCreateSprintDialogOpen] = useState(false);
  const [sprintOptionsState, setSprintOptionsState] = useState<Sprint[]>([]);
  const [selectedSprint, setSelectedSprint] = useState<string>('');
  const [boards, setBoards] = useState<Board[]>([]); // Доски
  const [isCreateBoardDialogOpen, setCreateBoardDialogOpen] = useState(false);
  const [newBoardName, setNewBoardName] = useState('');



  useEffect(() => {
    const loadBoards = async () => {
      try {
        // Получаем данные по проекту с id 1
        const data = await getProjects(1); // Это остаётся, если вам всё ещё нужно загружать проект
  
        console.log('Полученные данные:', data);
  
        if (data && Array.isArray(data.users)) {
          localStorage.setItem("users", JSON.stringify(data.users));
          setUsersState(data.users); // Обновляем состояние пользователей
          console.log('Загруженные пользователи:', data.users); // Выводим пользователей
        }
  
        if (data && data.boardName) {
          setSelectedBoard(data.boardName);
          setBoards(prevBoards => [
            ...prevBoards.filter(board => board.boardName !== data.boardName),
            { 
              boardName: data.boardName, 
              id: data.id,
              users: data.users, 
              sprints: data.sprints  
            }
          ]);
        }
  
        // Загрузка спринтов для выбранного проекта
        const sprintsData = await getSprints(1);  // Используем getSprints для загрузки спринтов для проекта с id = 1
        if (sprintsData && Array.isArray(sprintsData)) {
          setSprintOptionsState(sprintsData); // Обновляем состояние с спринтами
          console.log('Загруженные спринты:', sprintsData); // Выводим спринты
        }
  
      } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
      }
    };
  
    loadBoards();
  }, []);

  const handleCreateBoard = async () => {
    const newBoard = { boardName: newBoardName, id: 1 };

    try {
      await createBoard(newBoard); // Отправляем запрос на создание доски
      const updatedBoards = await getProjects(1); // Получаем обновленные доски
      setBoards(updatedBoards); // Обновляем состояние
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
      id: sprintOptionsState.length + 1
    };

    setSprintOptionsState((prevSprints) => [...prevSprints, newSprint]);

    try {
      createSprint(newSprint);
      setSelectedSprint(newSprintName);
    } catch (error) {
      console.error('Ошибка при создании спринта:', error);
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
                  setCreateBoardDialogOpen(true);
                } else {
                  setSelectedBoard(value?.boardName || 'МОЯ');
                }
              }}
              getOptionLabel={(option) => option.boardName || ''}
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
            <Fab color="primary" aria-label="add" className="add-icon" onClick={() => setDialogOpen(true)}>
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
        onClose={() => setDialogOpen(false)}
        onCreateTask={() => {}}
        onDeleteTask={() => {}}
        sprintOptionsState={sprintOptionsState}
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
              setCreateSprintDialogOpen(true);
            } else {
              setSelectedSprint(value || '');
            }
          }}
          renderInput={(params) => <TextField {...params} label="Спринт" />}
        />
      </p>
      <div className="header-card-line"></div>
      <AuthTaskBoard 
        users={usersState}  // Передаем список пользователей
        setUsersState={setUsersState}  // Передаем setUsersState
        filterPriority={mainPagePriority} 
      />
      <CreateSprintDialog
        open={isCreateSprintDialogOpen}
        onClose={() => setCreateSprintDialogOpen(false)}
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
            sx={{ minWidth: '300px' }}
          />
        </DialogContent>
        <DialogActions sx={{ display: 'flex', justifyContent: 'flex-start', px: 3 }}>
          <Button onClick={handleCreateBoard} sx={{ backgroundColor: '#FF8513', borderRadius: '555px', color: 'white' }}>
            Создать
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export { TaskPage }