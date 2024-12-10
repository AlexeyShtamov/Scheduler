import React, { useState } from 'react';
import checkIcon from '../assets/check.svg'
import arrow from '../assets/triangle.svg'
import { User } from '../const';
import { DragDropContext, Droppable, Draggable } from '@hello-pangea/dnd';


type AuthTaskBoardProps = {
  users: User[];
  setUsersState: React.Dispatch<React.SetStateAction<User[]>>;
};

const AuthTaskBoard: React.FC<AuthTaskBoardProps> = ({ users, setUsersState }) => {
  const [visibleTasks, setVisibleTasks] = useState<boolean[]>(() => users.map(() => true));

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
                        user.tasks[column].map((task) => (
                          <Draggable key={task.id} draggableId={task.id} index={user.tasks[column].indexOf(task)}>
                            {(provided) => (
                              <div
                                className="task-card"
                                ref={provided.innerRef}
                                {...provided.draggableProps}
                                {...provided.dragHandleProps}
                              >
                                <div className="task-card-header">
                                  <span className="task-title">{task.title}</span>
                                  <img src={user.avatar} alt="Аватар" className="avatar-icon" />
                                </div>
                                <div className="task-card-footer">
                                  <img src={checkIcon} alt="Галочка" className="check-icon" />
                                  <span className="time-text">{task.time}</span>
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
    </div>
  );
};

export default AuthTaskBoard;