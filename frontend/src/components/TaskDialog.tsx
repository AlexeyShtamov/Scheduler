import React, { useState, useRef, useEffect } from 'react';
import {
    Autocomplete,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    TextField,
} from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { DatePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import CloseButton from '../assets/close-button.svg';
import closer from '../assets/closer.png';
import { priorityOptions } from '../const';
import DeleteIcon from '@mui/icons-material/Delete'
import { User } from './AuthTaskBoard';
import { Sprint } from './TaskPage';
import { createTask } from '../services/api';


interface CreateTaskDialogProps {
    open: boolean;
    onClose: () => void;
    onCreateTask: (newTask: Task) => void;
    onDeleteTask: (taskId: Number) => void;
    initialTask?: Task;
    sprintOptionsState: Sprint[];
}

export type Task = {
    id: number
    title: string
    ttz: string
    assignee: string | null
    author: string | null
    description: string
    priority: string
    startDate: string | null
    endDate: string | null
    sprint: Sprint | null
    files: File[] | []
    status: string
};

const TaskDialog: React.FC<CreateTaskDialogProps> = ({ open, onClose, onCreateTask, onDeleteTask, initialTask, sprintOptionsState }) => {
    const [dialogPriority, setDialogPriority] = useState<string>(initialTask?.priority || '');
    const [selectedExecutor, setSelectedExecutor] = useState<string | null>(initialTask?.assignee || null);
    const [selectedAuthor, setSelectedAuthor] = useState<string | null>(initialTask?.author || null);
    const [files, setFiles] = useState<File[]>(initialTask?.files || []);
    const [appointmentDate, setAppointmentDate] = useState<dayjs.Dayjs | null>(initialTask?.startDate ? dayjs(initialTask.startDate) : null);
    const [completionDate, setCompletionDate] = useState<dayjs.Dayjs | null>(initialTask?.endDate ? dayjs(initialTask.endDate) : null);
    const [taskTitle, setTaskTitle] = useState(initialTask?.title || '');
    const [taskTime, setTaskTime] = useState(initialTask?.ttz || '');
    const [taskDescription, setTaskDescription] = useState(initialTask?.description || '');
    const [sprint, setSprint] = useState<Sprint | null>(initialTask?.sprint || null);
    const fileInputRef = useRef<HTMLInputElement | null>(null);

    const formattedAppointmentDate = appointmentDate ? appointmentDate.format('YYYY-MM-DD') : null;
    const formattedCompletionDate = completionDate ? completionDate.format('YYYY-MM-DD') : null;
    const usersBoard = localStorage.getItem("users")
    const users = usersBoard ? JSON.parse(usersBoard) : [];

    useEffect(() => {
        if (appointmentDate && completionDate) {
            // Вычисление разницы в днях
            const duration = completionDate.diff(appointmentDate, 'day'); // Разница в днях
            setTaskTime(`${duration} дн.`); // Обновление поля трудозатрат
        }
    }, [appointmentDate, completionDate]);


    useEffect(() => {
        if (initialTask && open) {
            setDialogPriority(initialTask.priority || '');
            setSelectedExecutor(initialTask.assignee || null);
            setSelectedAuthor(initialTask.author || null);
            setAppointmentDate(initialTask.startDate ? dayjs(initialTask.startDate) : null);
            setCompletionDate(initialTask.endDate ? dayjs(initialTask.endDate) : null);
            setTaskTitle(initialTask.title || '');
            setTaskTime(initialTask.ttz || '');
            setTaskDescription(initialTask.description || '');
            setSprint(initialTask.sprint || null)
            setFiles(initialTask.files || []);
        } else if (!open) {
            resetFields(); // Если initialTask отсутствует, сбрасываем поля
        }

    }, [initialTask, open])

    const resetFields = () => {
        setDialogPriority('');
        setSelectedExecutor(null);
        setSelectedAuthor(null);
        setFiles([]);
        setAppointmentDate(null);
        setCompletionDate(null);
        setTaskTitle('');
        setTaskTime('');
        setTaskDescription('');
        setSprint(null);

    };

    const handleClose = () => {
        resetFields(); // Сбрасываем данные
        onClose(); // Закрываем диалог
    };

    const handleDeleteTask = () => {
        if (initialTask) {
            onDeleteTask(initialTask.id); // Передаем id задачи на удаление
            handleClose(); // Закрываем диалог после удаления
        }
    };

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const selectedFiles = event.target.files;
        if (selectedFiles) {
            setFiles(prevFiles => [...prevFiles, ...Array.from(selectedFiles)]);
        }

        if (fileInputRef.current) {
            fileInputRef.current.value = '';
        }
    };

    const handleRemoveFile = (fileToRemove: File) => {
        setFiles(prevFiles => prevFiles.filter(file => file !== fileToRemove));
    };

    const handleCreateTask = async () => {
        // Получаем список пользователей из localStorage

        const newTaskData = {
            id: initialTask?.id ? Number(initialTask.id) : Math.floor(Math.random() * 1000000),
            title: taskTitle || 'Без названия',
            description: taskDescription || '',
            priority: dialogPriority,
            startDate: formattedAppointmentDate,
            endDate: formattedCompletionDate,
            assignee: selectedExecutor,
            author: selectedAuthor,
            ttz: taskTime || 'Не указано',
            sprint: sprint ? {
                id: sprint.id,
                title: sprint.title,
                startDate: sprint.startDate,
                endDate: sprint.endDate,
                projectId: sprint.projectId
            } : null,
            status: 'APPOINTED',
            files: files
        };

        try {
            // Создаем задачу
            await createTask(newTaskData);
            onCreateTask(newTaskData);
            handleClose();
            console.log('Задача успешно создана:', newTaskData);
        } catch (error) {
            console.error('Ошибка при создании задачи:', error);
        } 
    };

    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullWidth
            sx={{ '& .MuiDialog-paper': { width: '60%', maxWidth: 'none' } }}
        >
            <DialogTitle>
                <div style={{ display: 'flex', alignItems: 'center', gap: '24px', fontSize: '35px', lineHeight: '41.02px' }}>
                    <span>{initialTask ? 'РЕДАКТИРОВАТЬ ЗАДАЧУ' : 'СОЗДАТЬ ЗАДАЧУ'}</span>
                    {initialTask && (
                        <IconButton
                            aria-label="delete"
                            size="large"
                            sx={{ position: 'absolute', right: '50px' }} // Позиционирование кнопки слева
                            onClick={handleDeleteTask} // Обработчик удаления
                        >
                            <DeleteIcon />
                        </IconButton>
                    )}

                    {/* Кнопка CloseButton справа */}
                    <Button sx={{ position: 'absolute', right: 0 }} onClick={onClose}>
                        <img src={CloseButton} alt="Close" />
                    </Button>
                </div>
                <div className="header-line"></div>
            </DialogTitle>
            <DialogContent>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '85px', marginTop: '60px' }}>
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                        <TextField
                            fullWidth
                            id="task-title"
                            label="Наименование"
                            variant="outlined"
                            value={taskTitle}
                            onChange={(evt) => setTaskTitle(evt.target.value)}
                        />
                        <TextField
                            fullWidth
                            label="Описание"
                            id="task-description"
                            variant="outlined"
                            multiline
                            rows={6}
                            value={taskDescription}
                            onChange={(evt) => setTaskDescription(evt.target.value)}
                        />
                        <div>
                            <input
                                type="file"
                                id="file-upload"
                                style={{ display: 'none' }}
                                multiple
                                onChange={handleFileChange}
                                ref={fileInputRef}
                            />
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
                                                    style={{ cursor: 'pointer', width: '16px', height: '16px', alignItems: 'center', display: 'flex' }}
                                                    onClick={() => handleRemoveFile(file)}
                                                />
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                            )}
                        </div>
                        <DialogActions sx={{ justifyContent: 'flex-start' }}>
                            <Button
                                variant="contained"
                                sx={{ backgroundColor: '#FF8513', borderRadius: '555px', marginTop: '50px' }}
                                onClick={handleCreateTask}
                            >
                                {initialTask ? 'Сохранить' : 'Создать'}
                            </Button>
                        </DialogActions>
                    </div>
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                        <Autocomplete
                            options={priorityOptions}
                            value={{ label: dialogPriority }}
                            onChange={(_, value) => setDialogPriority(value?.label || '')}
                            renderInput={(params) => <TextField {...params} label="Приоритет" />}
                            sx={{ maxWidth: '250px' }}
                        />
                        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px' }}>


                            {/* Вторая колонка */}
                            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px' }}>
                                <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                                        <DatePicker
                                            label="Дата назначения"
                                            value={appointmentDate}
                                            onChange={(newDate: dayjs.Dayjs | null) => setAppointmentDate(newDate)}
                                            sx={{ minWidth: '250px' }}
                                        />
                                    </LocalizationProvider>
                                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                                        <DatePicker
                                            label="Дата выполнения"
                                            value={completionDate}
                                            onChange={(newDate: dayjs.Dayjs | null) => setCompletionDate(newDate)}
                                            sx={{ minWidth: '250px' }}
                                        />
                                    </LocalizationProvider>
                                    <Autocomplete
                                        options={users}
                                        value={selectedExecutor ? users.find((user: User) => `${user.firstName} ${user.lastName}` === selectedExecutor) || null : null}
                                        onChange={(_, newValue) => setSelectedExecutor(newValue ? `${newValue.firstName} ${newValue.lastName}` : '')}
                                        renderInput={(params) => <TextField {...params} label="Исполнитель" />}
                                        getOptionLabel={(option) => `${option.firstName} ${option.lastName}`}
                                    />
                                </div>
                                <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                                    <Autocomplete
                                        disablePortal
                                        options={sprintOptionsState ? sprintOptionsState : []}
                                        sx={{ width: 300 }}
                                        value={sprint || null}
                                        onChange={(_, newValue) => setSprint(newValue || null)}
                                        getOptionLabel={(option) => option.title}
                                        renderInput={(params) => <TextField {...params} label="Спринт" />}
                                    />
                                    <TextField
                                        fullWidth
                                        label="Трудозатраты"
                                        id="task-time"
                                        variant="outlined"
                                        value={taskTime}
                                        onChange={(evt) => setTaskTime(evt.target.value)}
                                        sx={{ minWidth: '250px' }}
                                    />
                                    <Autocomplete
                                        options={users}
                                        value={selectedAuthor ? users.find((user: User) => `${user.firstName} ${user.lastName}` === selectedAuthor) || null : null}
                                        onChange={(_, newValue) => setSelectedAuthor(newValue ? `${newValue.firstName} ${newValue.lastName}` : '')}
                                        renderInput={(params) => <TextField {...params} label="Автор задачи" />}
                                        getOptionLabel={(option) => `${option.firstName} ${option.lastName}`}
                                        sx={{ minWidth: '250px' }}
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </DialogContent>
        </Dialog>
    );
};

export default TaskDialog;