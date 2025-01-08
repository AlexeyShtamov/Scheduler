import React, { useState, useEffect } from 'react';
import checkIcon from '../assets/check.svg';
import arrow from '../assets/triangle.svg';
import { Task } from '../const';
import { DragDropContext, Droppable, Draggable } from '@hello-pangea/dnd';
import TaskDialog from './TaskDialog';
import dayjs from 'dayjs';

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  avatar: string;
  tasks: {
    assigned: Task[];
    inProgress: Task[];
    review: Task[];
    completed: Task[];
  };
}

type AuthTaskBoardProps = {
  users: User[];
  setUsersState: React.Dispatch<React.SetStateAction<User[]>>;
  filterPriority: string;
};

export const AuthTaskBoard: React.FC<AuthTaskBoardProps> = ({
  users,
  setUsersState,
  filterPriority,
}) => {
  // Инициализируем visibleTasks с количеством пользователей
  const [visibleTasks, setVisibleTasks] = useState<boolean[]>([]);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [editingTask, setEditingTask] = useState<Task | undefined>(undefined);

  useEffect(() => {
    // Синхронизируем состояние visibleTasks при изменении данных о пользователях
    setVisibleTasks(users.map(() => true)); // Все задачи видимы по умолчанию
  }, [users]); // Обновляется только когда users изменяется

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
    const updatedUsers = users.map((user) => {
      const updatedTasks = { ...user.tasks };
      ['assigned', 'inProgress', 'review', 'completed'].forEach((column) => {
        const taskColumn = column as keyof User['tasks'];
        updatedTasks[taskColumn] = updatedTasks[taskColumn].filter((task) => task.id !== taskId);
      });
      return { ...user, tasks: updatedTasks };
    });

    setUsersState(updatedUsers); // Обновляем состояние
  };

  const handleSaveTask = (updatedTask: Task) => {
    const columnKeys: (keyof User['tasks'])[] = ['assigned', 'inProgress', 'review', 'completed'];

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

    const draggedTask = users[sourceUserIndex].tasks[sourceColumn].find(
      (task) => task.id === draggableId
    );

    if (!draggedTask) return;

    const updatedUsers = [...users];
    updatedUsers[sourceUserIndex].tasks[sourceColumn] = updatedUsers[sourceUserIndex].tasks[sourceColumn].filter(
      (task) => task.id !== draggableId
    );
    updatedUsers[sourceUserIndex].tasks[destinationColumn].push(draggedTask);

    setUsersState(updatedUsers);
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
                <Droppable key={column} droppableId={`${column}-${userIndex}`}>
                  {(provided) => (
                    <div
                      className="column"
                      ref={provided.innerRef}
                      {...provided.droppableProps}
                    >
                      {visibleTasks[userIndex] &&
                        user.tasks?.[column]?.length ? (
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
        onCreateTask={handleSaveTask}
        onDeleteTask={handleDeleteBoard}
        initialTask={editingTask}
        sprintOptionsState={[]}
      />
    </div>
  );
};
