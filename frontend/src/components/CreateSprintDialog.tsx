import React, { useState } from 'react';
import { Dialog, DialogActions, DialogContent, DialogTitle, TextField, Button } from '@mui/material';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import dayjs from 'dayjs';

interface CreateSprintDialogProps {
    open: boolean;
    onClose: () => void;
    onCreateSprint: (newSprintName: string, appointmentDate: string, completionDate: string) => void;
}

const CreateSprintDialog: React.FC<CreateSprintDialogProps> = ({ open, onClose, onCreateSprint }) => {
    const [newSprintName, setNewSprintName] = useState('');
    const [appointmentDate, setAppointmentDate] = useState<dayjs.Dayjs | null>(null)
    const [completionDate, setCompletionDate] = useState<dayjs.Dayjs | null>(null)

    const handleCreate = () => {
        const formattedAppointmentDate = appointmentDate ? appointmentDate.format('YYYY-MM-DD') : '';
        const formattedCompletionDate = completionDate ? completionDate.format('YYYY-MM-DD') : '';
        console.log('Новый спринт создан:', {
            newSprintName,
            appointmentDate: formattedAppointmentDate,
            completionDate: formattedCompletionDate
        });
        onCreateSprint(newSprintName, formattedAppointmentDate, formattedCompletionDate);
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
            <DialogContent sx={{ display: 'flex', flexDirection: 'column', gap: '16px', mt: 2}} >
                <TextField
                    fullWidth
                    label="Название спринта"
                    value={newSprintName}
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
            <DialogActions sx={{ justifyContent: 'flex-start', px: 3 }}>
                <Button onClick={handleCreate} 
                sx={{ backgroundColor: '#FF8513', borderRadius: '555px', color: 'white'}}>
                    Создать
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default CreateSprintDialog;