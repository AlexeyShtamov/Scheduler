import React, { useState, useEffect } from 'react';
import checkIcon from '../assets/check.svg';
import arrow from '../assets/triangle.svg';
import { Task } from './TaskDialog';
import { DragDropContext, Droppable, Draggable } from '@hello-pangea/dnd';
import TaskDialog from './TaskDialog';
import dayjs from 'dayjs';
import { Sprint } from './TaskPage';
import { getTasks } from '../services/api';

export interface User {
  id: Number;
  firstName: string;
  lastName: string;
  email: string;
}

type AuthTaskBoardProps = {
  users: User[];
  setUsersState: React.Dispatch<React.SetStateAction<User[]>>;
  filterPriority: string;
  sprintOptionsState: Sprint[];
  sprintId: number; // передаем ID спринта
};

const AuthTaskBoard: React.FC<AuthTaskBoardProps> = ({
  users,
  filterPriority,
  sprintOptionsState,
  sprintId,
}) => {
  const [visibleTasks, setVisibleTasks] = useState<boolean[]>([]);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [editingTask, setEditingTask] = useState<Task | undefined>(undefined);
  const [tasksByStatus, setTasksByStatus] = useState<Record<string, Task[]>>({
    APPOINTED: [],
    IN_PROGRESS: [],
    REVIEW: [],
    COMPLETED: [],
  });

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
        const taskPromises = allStatuses.map((status) => getTasks(sprintId, status));
        const taskResults = await Promise.all(taskPromises);

        const updatedTasksByStatus = allStatuses.reduce((acc, status, index) => {
          acc[status] = taskResults[index];
          return acc;
        }, {} as Record<string, Task[]>);

        setTasksByStatus(updatedTasksByStatus);

        // Логируем задачи для каждого статуса
        console.log('Загруженные задачи:');
        for (const status of allStatuses) {
          console.log(`${status}:`, updatedTasksByStatus[status]);
        }
      } catch (error) {
        console.error('Ошибка при загрузке задач:', error);
      }
    };

    fetchTasksForAllStatuses();
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

  const toggleTasksVisibility = (index: number) => {
    setVisibleTasks((prev) => {
      const newVisibility = [...prev];
      newVisibility[index] = !newVisibility[index];
      return newVisibility;
    });
  };

  const onDragEnd = (result: any) => {
    const { source, destination, draggableId } = result;
    if (!destination || source.droppableId === destination.droppableId) return;
  
    // Находим перемещаемую задачу по ID
    const draggedTask = Object.values(tasksByStatus).flat().find((task) => task.id.toString() === draggableId);
  
    if (!draggedTask) return;
  
    // Обновляем задачи
    setTasksByStatus((prev) => {
      const sourceTasks = [...prev[source.droppableId]];
      const destinationTasks = [...prev[destination.droppableId]];
  
      // Удаляем задачу из исходного списка
      const draggedTaskIndex = sourceTasks.findIndex((task) => task.id === draggedTask.id);
      if (draggedTaskIndex !== -1) {
        sourceTasks.splice(draggedTaskIndex, 1);
      }
  
      // Обновляем статус задачи и добавляем в новый список
      draggedTask.status = destination.droppableId;
      destinationTasks.push(draggedTask);
  
      return {
        ...prev,
        [source.droppableId]: sourceTasks,
        [destination.droppableId]: destinationTasks,
      };
    });
  };

  const calculateTaskDuration = (task: Task) => {
    if (task.startDate && task.endDate) {
      const appointment = dayjs(task.startDate);
      const completion = dayjs(task.endDate);
      const durationInDays = completion.diff(appointment, 'day');
      return `${durationInDays} дн.`;
    }
    return 'Не указано';
  };

  const handleCreateTask = (newTask: Task) => {
    const assignee = users.find(user => `${user.firstName} ${user.lastName}` === newTask.assignee);
    
    if (assignee) {
      // Создаём задачу только для выбранного пользователя
      setTasksByStatus((prev) => {
        const updatedTasks = { ...prev };
  
        // Добавляем задачу в правильный статус
        updatedTasks[newTask.status] = [
          ...updatedTasks[newTask.status],
          { ...newTask, assignee: assignee.firstName + ' ' + assignee.lastName }
        ];
  
        return updatedTasks;
      });
    } else {
      console.error('Задача должна быть назначена пользователю');
    }
  };

  const handleDeleteBoard = (taskId: Number) => {
    setTasksByStatus((prev) => {
      const updatedTasks = { ...prev };
      for (const status in updatedTasks) {
        updatedTasks[status] = updatedTasks[status].filter((task) => task.id !== taskId);
      }
      return updatedTasks;
    });
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
        {allStatuses.map((column) => (
          <Droppable key={column} droppableId={column}>
            {(provided) => (
              <div
                className="column"
                ref={provided.innerRef}
                {...provided.droppableProps}
              >
                {visibleTasks[userIndex] &&
                  tasksByStatus[column]?.length > 0 &&
                  filterTasks(tasksByStatus[column])
                    .filter((task) => task.assignee === `${user.firstName} ${user.lastName}`) // Фильтрация по исполнителю
                    .map((task, index) => (
                      <Draggable
                        key={task.id}
                        draggableId={task.id.toString()}
                        index={index}
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
                              <img
                                src={checkIcon}
                                alt="Галочка"
                                className="check-icon"
                              />
                              <span className="time-text">
                                {calculateTaskDuration(task)}
                              </span>
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
        onCreateTask={handleCreateTask}
        onDeleteTask={handleDeleteBoard}
        initialTask={editingTask}
        sprintOptionsState={sprintOptionsState}
      />
    </div>
  );
};

export { AuthTaskBoard };
