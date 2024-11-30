import {
  Autocomplete,
  Button,
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

const priorityOptions = [
  { label: 'НИЗКИЙ' },
  { label: 'СРЕДНИЙ' },
  { label: 'ВЫСОКИЙ' },
];

const boardOptions = [
  { label: 'МОЯ' },
  { label: 'КОМАНДА 1' },
  { label: 'КОМАНДА 2' },
];

const users = [
  { label: 'Иван Иванов' },
  { label: 'Петр Петров' },
  { label: 'Алексей Александров' },
  { label: 'Мария Морозова' },
  { label: 'Елена Смирнова' },
  { label: 'Дмитрий Дмитриев' },
];

const TaskPage: React.FC = () => {
  const [selectedPriority, setSelectedPriority] = useState<string>('');
  const [selectedBoard, setSelectedBoard] = useState<string>('МОЯ');
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedExecutor, setSelectedExecutor] = useState<string | null>(null);
  const [selectedAuthor, setSelectedAuthor] = useState<string | null>(null);
  const [files, setFiles] = useState<File[]>([]);

  const handleOpenDialog = () => setOpenDialog(true);
  const handleCloseDialog = () => setOpenDialog(false);
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
              <TextField fullWidth label="Наименование" variant="outlined" />
              <TextField fullWidth label="Описание" variant="outlined" multiline rows={6} />
              <div>
              <input
                type="file"
                id="file-upload"
                style={{ display: 'none' }}
                multiple 
                onChange={handleFileChange}
                ref={fileInputRef}
              />
                {/* 4. Кнопка для открытия диалога выбора файла */}
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
                <Button variant="contained" sx={{ backgroundColor: '#FF8513', borderRadius: '555px', marginTop: '50px' }}>
                  Создать
                </Button>
              </DialogActions>
            </div>

            <div style={{ display: 'flex', flexDirection: 'column', gap: '16px', fontFamily:'Roboto' }}>
              <Autocomplete
                options={priorityOptions}
                value={{ label: selectedPriority }}
                onChange={(_, value) => setSelectedPriority(value?.label || 'ВСЕ ПРИОРИТЕТЫ')}
                renderInput={(params) => <TextField {...params} label="Приоритет" />}
              />
              <div className="form-grid">
                <TextField fullWidth label="Дата назначения" type="date" InputLabelProps={{ shrink: true }} />
                <TextField fullWidth label="Спринт" variant="outlined" />
                <TextField fullWidth label="Дата выполнения" type="date" InputLabelProps={{ shrink: true }} />
                <TextField fullWidth label="Трудозатраты" variant="outlined" />
                <Autocomplete
                  options={users}
                  value={selectedExecutor ? { label: selectedExecutor } : null}
                  onChange={(_, newValue) => setSelectedExecutor(newValue?.label || '')}
                  renderInput={(params) => <TextField {...params} label="Исполнитель" />}
                />
                <Autocomplete
                  options={users} 
                  value={selectedAuthor ? { label: selectedAuthor } : null}
                  onChange={(_, newValue) => setSelectedAuthor(newValue?.label || '')}
                  renderInput={(params) => <TextField {...params} label="Автор задачи" />}
                />
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
              options={priorityOptions}
              className="custom-autocomplete"
              sx={{ width: 300 }}
              value={{ label: selectedPriority }}
              onChange={(_, value) => setSelectedPriority(value?.label || 'ВСЕ ПРИОРИТЕТЫ')}
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
    </div>
  );
};

export { TaskPage };