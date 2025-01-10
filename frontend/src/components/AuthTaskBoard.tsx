import React, { useState, useEffect } from 'react';
import checkIcon from '../assets/check.svg';
import arrow from '../assets/triangle.svg';
import { Task } from '../const';
import { DragDropContext, Droppable, Draggable } from '@hello-pangea/dnd';
import TaskDialog from './TaskDialog';
import dayjs from 'dayjs';
import { Sprint } from './TaskPage';
import { getTasks } from '../services/api';


export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
}

type AuthTaskBoardProps = {
  users: User[];
  setUsersState: React.Dispatch<React.SetStateAction<User[]>>;
  filterPriority: string;
  sprintOptionsState: Sprint[];
  sprintId: number  // передаем ID спринта
};

const AuthTaskBoard: React.FC<AuthTaskBoardProps> = ({
  users,
  filterPriority,
  sprintOptionsState,
  sprintId,  // получаем ID спринта
}) => {
  const [visibleTasks, setVisibleTasks] = useState<boolean[]>([]);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [editingTask, setEditingTask] = useState<Task | undefined>(undefined);
  const [tasks, setTasks] = useState<Task[]>([]);

  const allStatuses: Array<'APPOINTED' | 'IN_PROGRESS' | 'REVIEW' | 'COMPLETED'> = [
    'APPOINTED',
    'IN_PROGRESS',
    'REVIEW',
    'COMPLETED',
  ];
  
  // Загружаем задачи с сервера
  useEffect(() => {
    const fetchTasksForAllStatuses = async () => {
      try {
        // Делаем параллельные запросы для всех статусов
        const taskPromises = allStatuses.map((status) => getTasks(sprintId, status));
        const taskResults = await Promise.all(taskPromises);
  
        // Объединяем результаты
        const allTasks = taskResults.flat(); // flat() объединяет массив массивов в один массив
  
        console.log('Все задачи:', allTasks);
        // Здесь можно обновить состояние с задачами, если нужно
        // setTasks(allTasks);
      } catch (error) {
        console.error('Ошибка при загрузке задач:', error);
      }
    };
  
    if (sprintId) {
      fetchTasksForAllStatuses();
    }
  }, [sprintId]);

  useEffect(() => {
    setVisibleTasks(users.map(() => true));
  }, [users]);

  const handleTaskClick = (task: Task) => {
    setEditingTask(task);
    setIsDialogOpen(true);
  };

  const filterTasks = (tasks: Task[]) => {
    const normalizedPriority = filterPriority.trim().toLowerCase();
    return normalizedPriority === 'все приоритеты'
      ? tasks
      : tasks.filter((task) => task.priority.trim().toLowerCase() === normalizedPriority);
  };

  const handleDeleteBoard = (taskId: string) => {
    const updatedTasks = tasks.filter((task) => task.id !== taskId);
    setTasks(updatedTasks);
  };

  const handleSaveTask = (updatedTask: Task) => {
    const updatedTasks = tasks.map((task) => 
      task.id === updatedTask.id ? updatedTask : task
    );
    setTasks(updatedTasks);
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
    if (!destination) return;

    const draggedTask = tasks.find((task) => task.id === draggableId);
    if (!draggedTask) return;

    const updatedTasks = tasks.filter((task) => task.id !== draggableId);
    updatedTasks.push({
      ...draggedTask,
      status: destination.droppableId, // Обновляем статус в зависимости от колонки
    });

    setTasks(updatedTasks);
  };

  const calculateTaskDuration = (task: Task) => {
    if (task.appointmentDate && task.completionDate) {
      const appointment = dayjs(task.appointmentDate);
      const completion = dayjs(task.completionDate);
      const durationInDays = completion.diff(appointment, 'day');
      return `${durationInDays} дн.`;
    }
    return 'Не указано';
  };

  const handleCreateTask = (newTask: Task) => {
    const updatedTasks = [...tasks, newTask];
    setTasks(updatedTasks);
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
            {user.firstName} {user.lastName}
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
                <Droppable key={column} droppableId={column}>
                  {(provided) => (
                    <div
                      className="column"
                      ref={provided.innerRef}
                      {...provided.droppableProps}
                    >
                      {visibleTasks[userIndex] &&
                        tasks.filter(task => task.status === column).length ? (
                          filterTasks(tasks.filter(task => task.status === column)).map((task) => (
                            <Draggable
                              key={task.id}
                              draggableId={task.id}
                              index={tasks.indexOf(task)}
                            >
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
                                  </div>
                                  <div className="task-card-footer">
                                    <img src={checkIcon} alt="Галочка" className="check-icon" />
                                    <span className="time-text">{calculateTaskDuration(task)}</span>
                                  </div>
                                </div>
                              )}
                            </Draggable>
                          ))
                        ) : null}
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
        onCreateTask={handleCreateTask}
        onDeleteTask={handleDeleteBoard}
        initialTask={editingTask}
        sprintOptionsState={sprintOptionsState}
      />
    </div>
  );
};

export { AuthTaskBoard };