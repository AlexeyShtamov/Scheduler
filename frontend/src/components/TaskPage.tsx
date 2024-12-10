import {
  Autocomplete,Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Fab,
  TextField,
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import CloseButton from '../assets/close-button.svg';
import {useRef, useState } from 'react';
import logo from '../assets/logo.svg';
import avatar from '../assets/avatar2.svg';
import closer from '../assets/closer.png';
import '../components/css/LoginPage.css';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { DatePicker } from '@mui/x-date-pickers';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs/AdapterDayjs';
import dayjs from 'dayjs';
import AuthTaskBoard from './AuthTaskBoard';
import { users, sprintOptions, boardOptions, priorityOptions, mainPagePriorityOptions, Task} from '../const';




const TaskPage: React.FC = () => {
  const [mainPagePriority, setMainPagePriority] = useState<string>('ВСЕ ПРИОРИТЕТЫ'); // Для главной страницы
  const [dialogPriority, setDialogPriority] = useState<string>(''); // Для Dialog
  const [selectedBoard, setSelectedBoard] = useState<string>('МОЯ');
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedExecutor, setSelectedExecutor] = useState<string | null>(null);
  const [selectedAuthor, setSelectedAuthor] = useState<string | null>(null);
  const [files, setFiles] = useState<File[]>([]);
  const [appointmentDate, setAppointmentDate] = useState<dayjs.Dayjs | null>(null);
  const [completionDate, setCompletionDate] = useState<dayjs.Dayjs | null>(null);
  const [taskTitle, setTaskTitle] = useState('');
  const [taskTime, setTaskTime] = useState('');
  const [usersState, setUsersState] = useState(users || []);

  const handleCreateTask = () => {
    if (!selectedExecutor) {
      alert("Выберите исполнителя.");
      return;
    }
  
    const taskTitle = document.querySelector<HTMLInputElement>('#task-title')?.value || 'Без названия';
    const taskTime = document.querySelector<HTMLInputElement>('#task-time')?.value || 'Не указано';
  
    // Найти пользователя
    const executorIndex = usersState.findIndex((user) => user.label === selectedExecutor);
  
    if (executorIndex === -1) {
      alert("Выбранный пользователь не найден.");
      return;
    }
  
    // Создать новую задачу
    const newTask: Task = {
      id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`,
      title: taskTitle,
      time: taskTime,
    };
  
    setUsersState((prevUsers) => {
      const updatedUsers = [...prevUsers];
      updatedUsers[executorIndex] = {
        ...updatedUsers[executorIndex],
        tasks: {
          ...updatedUsers[executorIndex].tasks,
          assigned: [...updatedUsers[executorIndex].tasks.assigned, newTask],
        },
      };
      return updatedUsers;
    });
  
    setOpenDialog(false); // Закрыть диалог
  };


  const handleOpenDialog = () => setOpenDialog(true);
  const handleCloseDialog = () => {
    setDialogPriority('');
    setSelectedBoard('МОЯ');
    setSelectedExecutor(null);
    setSelectedAuthor(null);
    setFiles([]);
    setAppointmentDate(null);
    setCompletionDate(null);
    setOpenDialog(false);
  }
  const fileInputRef = useRef<HTMLInputElement | null>(null);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFiles = event.target.files; // Получаем список файлов
    if (selectedFiles) {
      setFiles(prevFiles => [
        ...prevFiles,
        ...Array.from(selectedFiles),
      ]);
    }

    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
  };

  const handleRemoveFile = (fileToRemove: File) => {
    setFiles(prevFiles => prevFiles.filter(file => file !== fileToRemove)); // Удаляем файл из массива
  };

  const renderDialog = () => {
    return (
      <Dialog
        open={openDialog}
        onClose={handleCloseDialog}
        fullWidth
        sx={{
          '& .MuiDialog-paper': {
            width: '60%',
            maxWidth: 'none',
          },
        }}
      >
        <DialogTitle>
          <div style={{ display: 'flex', alignItems: 'center', gap: '24px', fontSize: '35px', lineHeight:'41.02px' }}>
            <span>СОЗДАТЬ ЗАДАЧУ</span>
            <Button sx={{ position: 'absolute', right: '53px' }} onClick={handleCloseDialog}>
              <img src={CloseButton} alt="Close" />
            </Button>
            <Autocomplete
              disablePortal
              options={boardOptions}
              className="custom-autocomplete"
              sx={{ width: 300 }}
              value={{ label: selectedBoard }}
              onChange={(_, value) => setSelectedBoard(value?.label || 'МОЯ')}
              renderInput={(params) => <TextField {...params} label="Доска" />}
            />
          </div>
          <div className="header-line"></div>
        </DialogTitle>
        <DialogContent>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '85px', marginTop:'60px' }}>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
              <TextField fullWidth id="task-title" label="Наименование" variant="outlined" value={taskTitle} onChange={(evt)=>setTaskTitle(evt.target.value)}/>
              <TextField fullWidth label="Описание" id="task-description" variant="outlined" multiline rows={6} />
              <div>
              <input
                type="file"
                id="file-upload"
                style={{ display: 'none' }}
                multiple 
                onChange={handleFileChange}
                ref={fileInputRef}
              />
                {/* Кнопка для открытия диалога выбора файла */}
                <Button
                  variant="text"
                  sx={{ color: '#00000080' }}
                  onClick={() => document.getElementById('file-upload')?.click()}
                >
                  Добавить файлы
                </Button>
                {files.length > 0 && (
                  <div>
                    <span>Выбраны файлы:</span>
                    <ul className="file-list">
                      {files.map((file, index) => (
                        <li key={index}>
                          {file.name} 
                          <a href={URL.createObjectURL(file)} download={file.name}> Скачать</a>
                          <img
                            src={closer}
                            alt="Удалить"
                            style={{ cursor: 'pointer', width:'16px', height:'16px', alignItems: 'center', display:'flex' }}
                            onClick={() => handleRemoveFile(file)} // Удаляем файл при клике на крестик
                          />
                        </li>
                      ))}
                    </ul>
                  </div>
                )}
              </div>
              <DialogActions sx={{ justifyContent: 'flex-start' }}>
                <Button variant="contained" sx={{ backgroundColor: '#FF8513', borderRadius: '555px', marginTop: '50px' }} onClick={handleCreateTask}>
                  Создать
                </Button>
              </DialogActions>
            </div>

            <div style={{ display: 'flex', flexDirection: 'column', gap: '16px', fontFamily:'Roboto' }}>
              <Autocomplete
                options={priorityOptions}
                value={{ label: dialogPriority }}
                onChange={(_, value) => setDialogPriority(value?.label || 'ВСЕ ПРИОРИТЕТЫ')}
                renderInput={(params) => <TextField {...params} label="Приоритет" />}
              />
              <div className="form-grid">
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker label="Дата назначения"
                 value={appointmentDate}
                 onChange={(newDate: dayjs.Dayjs | null) => setAppointmentDate(newDate)}/>
                <Autocomplete
                disablePortal
                options={sprintOptions}
                sx={{ width: 300 }}
                renderInput={(params) => <TextField {...params} label="Спринт" />}
                  />
                <DatePicker label="Дата выполнения"
                value={completionDate}
                onChange={(newDate: dayjs.Dayjs | null) => setCompletionDate(newDate)}/>
                <TextField fullWidth label="Трудозатраты" id="task-time" variant="outlined" value={taskTime} onChange={(evt)=> setTaskTime(evt.target.value)} 
                 />
                <Autocomplete
                  options={users}
                  value={selectedExecutor ? users.find(user => user.label === selectedExecutor) || null:null}
                  onChange={(_, newValue) => setSelectedExecutor(newValue?.label || '')}
                  renderInput={(params) => <TextField {...params} label="Исполнитель" />}
                />
                <Autocomplete
                  options={users} 
                  value={selectedAuthor ? users.find(user => user.label === selectedAuthor) || null:null}
                  onChange={(_, newValue) => setSelectedAuthor(newValue?.label || '')}
                  renderInput={(params) => <TextField {...params} label="Автор задачи" />}
                />
                </LocalizationProvider>
              </div>
            </div>
          </div>
        </DialogContent>
      </Dialog>
    );
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
      {renderDialog()}
      <p className='header-card'> 
        Моя команда
        <Autocomplete
          disablePortal
          options={sprintOptions}
          sx={{ width: 300 }}
          renderInput={(params) => <TextField {...params} label="Спринт" />}
          />
        </p>
        <div className="header-card-line"></div>
        <AuthTaskBoard users={usersState} setUsersState={setUsersState}/>
    </div>
  );
};

export { TaskPage };