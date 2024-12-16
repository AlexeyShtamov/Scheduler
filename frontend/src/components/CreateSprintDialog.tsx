import React, { useState } from 'react';
import { Dialog, DialogActions, DialogContent, DialogTitle, TextField, Button } from '@mui/material';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import dayjs from 'dayjs';

interface CreateSprintDialogProps {
    open: boolean;
    onClose: () => void;
    onCreateSprint: (newSprintName: string) => void;
}

const CreateSprintDialog: React.FC<CreateSprintDialogProps> = ({ open, onClose, onCreateSprint }) => {
    const [newSprintName, setNewSprintName] = useState('');
    const [appointmentDate, setAppointmentDate] = useState<dayjs.Dayjs | null>(null)
    const [completionDate, setCompletionDate] = useState<dayjs.Dayjs | null>(null)

    const handleCreate = () => {
        // Добавляем новый спринт через переданную функцию
        onCreateSprint(newSprintName);
        onClose();
        setNewSprintName('');
        setAppointmentDate(null);
        setCompletionDate(null); // Закрываем диалог
      };

    const handleClose = () => {
        // Сбрасываем поля при закрытии формы
        setNewSprintName('');
        setAppointmentDate(null);
        setCompletionDate(null);
        onClose(); // Закрываем диалог
    };

    return (
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>СОЗДАТЬ СПРИНТ</DialogTitle>
            <DialogContent>
                <TextField
                    fullWidth
                    label="Название спринта"
                    value={newSprintName}
                    sx={{ minWidth: '250px' }}
                    onChange={(e) => setNewSprintName(e.target.value)}
                />
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
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} color="primary">
                    Отмена
                </Button>
                <Button onClick={handleCreate} color="primary">
                    Создать
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default CreateSprintDialog;