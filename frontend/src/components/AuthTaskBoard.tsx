import React, { useState } from 'react';
import checkIcon from '../assets/check.svg'
import arrow from '../assets/triangle.svg'
import { User, Task } from '../const';
import { DragDropContext, Droppable, Draggable } from '@hello-pangea/dnd';
import TaskDialog from './TaskDialog';
import dayjs from 'dayjs';


type AuthTaskBoardProps = {
  users: User[];
  setUsersState: React.Dispatch<React.SetStateAction<User[]>>;
  filterPriority: string;
};

const AuthTaskBoard: React.FC<AuthTaskBoardProps> = ({ users, setUsersState, filterPriority }) => {
  const [visibleTasks, setVisibleTasks] = useState<boolean[]>(() => users.map(() => true));
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [editingTask, setEditingTask] = useState<Task | undefined>(undefined);

  const handleTaskClick = (task : Task) =>{
    setEditingTask(task)
    setIsDialogOpen(true)
  }

  const filterTasks = (tasks: Task[]) => {
    const normalizedPriority = filterPriority.trim().toLowerCase(); 
    return normalizedPriority === 'все приоритеты' 
      ? tasks 
      : tasks.filter((task) => task.priority.trim().toLowerCase() === normalizedPriority);
  };

  const handleDeleteBoard = (taskId: string) => {
    const updatedUsers = users.map((user) => {
      const updatedTasks = { ...user.tasks };
      ['assigned', 'inProgress', 'review', 'completed'].forEach((column) => {
        const taskColumn = column as keyof User['tasks']; // Приводим к типу 'keyof User['tasks']'
        updatedTasks[taskColumn] = updatedTasks[taskColumn].filter((task) => task.id !== taskId);
      });
      return { ...user, tasks: updatedTasks };
    });
  
    setUsersState(updatedUsers); // Обновляем состояние
  };

  const handleSaveTask = (updatedTask: Task) => {
    const columnKeys: (keyof User['tasks'])[] = ['assigned', 'inProgress', 'review', 'completed']; // Задаем строгий тип для columnKeys

    const updatedUsers = users.map((user) => {
        const updatedTasks = { ...user.tasks };
        columnKeys.forEach((column) => {
            updatedTasks[column] = updatedTasks[column].map((task) =>
                task.id === updatedTask.id ? updatedTask : task
            );
        });
        return { ...user, tasks: updatedTasks };
    });

    setUsersState(updatedUsers); // Сохраняем обновленные данные в состоянии
};

  const toggleTasksVisibility = (index: number) => {
    setVisibleTasks((prev) => {
      const newVisibility = [...prev];
      newVisibility[index] = !newVisibility[index];
      return newVisibility;
    });
  };

  const onDragEnd = (result: any) => {
    const { source, destination, draggableId } = result;
    if (!destination) return; // Если элемент не был перенесен в другую позицию
  
    const sourceUserIndex = parseInt(source.droppableId.split('-')[1]);
    const sourceColumn = source.droppableId.split('-')[0] as keyof User['tasks'];
    const destinationColumn = destination.droppableId.split('-')[0] as keyof User['tasks'];
  
    // Найдем задачу, которую перетаскивают, используя draggableId (которое теперь является уникальным id задачи)
    const draggedTask = users[sourceUserIndex].tasks[sourceColumn].find(
      (task) => task.id === draggableId
    );
  
    // Если задача не найдена, прекращаем выполнение
    if (!draggedTask) return;
  
    const updatedUsers = [...users];
    // Убираем задачу из исходной колонки
    updatedUsers[sourceUserIndex].tasks[sourceColumn] = updatedUsers[sourceUserIndex].tasks[sourceColumn].filter(
      (task) => task.id !== draggableId
    );
  
    // Добавляем задачу в целевую колонку
    updatedUsers[sourceUserIndex].tasks[destinationColumn].push(draggedTask);
  
    setUsersState(updatedUsers);
  };

  const calculateTaskDuration = (task: Task) => {
    if (task.appointmentDate && task.completionDate) {
      const appointment = dayjs(task.appointmentDate);
      const completion = dayjs(task.completionDate);
      const durationInDays = completion.diff(appointment, 'day');
      return `${durationInDays} дн.`; // Выводим продолжительность в днях
    }
    return 'Не указано'; // Если нет дат, выводим "Не указано"
  };

  return (
    <div className="task-board task-board-auth">
      <div className="task-columns-header">
        <h3>НАЗНАЧЕННЫЕ</h3>
        <h3>В РАБОТЕ</h3>
        <h3>РЕВЬЮ</h3>
        <h3>ЗАВЕРШЕННЫЕ</h3>
      </div>

      {users.map((user, userIndex) => (
        <div key={userIndex} className="user-section">
          <div className="main-line"></div>
          <h2>
            {user.label}
            <img
              src={arrow}
              alt="arrow"
              onClick={() => toggleTasksVisibility(userIndex)}
              className={visibleTasks[userIndex] ? 'arrow-icon' : 'arrow-icon rotated'}
            />
          </h2>
          <div className="main-line"></div>
          <div className="task-columns">
            <DragDropContext onDragEnd={onDragEnd}>
              {(['assigned', 'inProgress', 'review', 'completed'] as const).map((column) => (
                <Droppable key={column} droppableId={`${column}-${userIndex}`}>
                  {(provided) => (
                    <div
                      className="column"
                      ref={provided.innerRef}
                      {...provided.droppableProps}
                    >
                      {visibleTasks[userIndex] &&
                        filterTasks(user.tasks[column]).map((task) => (
                          <Draggable key={task.id} draggableId={task.id} index={user.tasks[column].indexOf(task)}>
                            {(provided) => (
                              <div
                                className="task-card"
                                ref={provided.innerRef}
                                {...provided.draggableProps}
                                {...provided.dragHandleProps}
                                onClick={() => handleTaskClick(task)}
                              >
                                <div className="task-card-header">
                                  <span className="task-title">{task.title}</span>
                                  <img src={user.avatar} alt="Аватар" className="avatar-icon" />
                                </div>
                                <div className="task-card-footer">
                                  <img src={checkIcon} alt="Галочка" className="check-icon" />
                                  <span className="time-text">{calculateTaskDuration(task)}</span>
                                </div>
                              </div>
                            )}
                          </Draggable>
                        ))}
                      {provided.placeholder}
                    </div>
                  )}
                </Droppable>
              ))}
            </DragDropContext>
          </div>
        </div>
      ))}
      <TaskDialog
      open={isDialogOpen}
      onClose={() => setIsDialogOpen(false)}
      onCreateTask={handleSaveTask}
      onDeleteTask={handleDeleteBoard}
      initialTask={editingTask} // Передаём задачу в диалог
      />
    </div>
  );
};

export default AuthTaskBoard;