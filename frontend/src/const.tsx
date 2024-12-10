import avatarIcon from './assets/avatar.svg'
import avatar2 from './assets/avatar2.svg'

const mainPagePriorityOptions = [
    {label: 'ВСЕ ПРИОРИТЕТЫ'},
    { label: 'НИЗКИЙ' },
    { label: 'СРЕДНИЙ' },
    { label: 'ВЫСОКИЙ' },
  ];
  
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
  
  
  const sprintOptions= [
    { label : '03.12.24-14.12.24'},
    {label: '14.12.24-20.12.24'}]

    export type Task = {
      id: string
      title: string;
      time: string;
    };
    
    export type User = {
      label: string;
      tasks: {
        assigned: Task[];
        inProgress: Task[];
        review: Task[];
        completed: Task[];
      };
      avatar: string;
    };
    
    // Пользователи с примерами задач
    const users: User[] = [
      {
        label: 'Иван Иванов',
        tasks: {
          assigned: [
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Исправление ошибок', time: '2ч' },
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Обновление документации', time: '1ч' },
          ],
          inProgress: [
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Разработка новой функции', time: '3ч' },
          ],
          review: [
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Код ревью коллеги', time: '1,5ч' },
          ],
          completed: [
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Деплой на сервер', time: '2ч' },
          ],
        },
        avatar: avatar2,
      },
      {
        label: 'Петр Петров',
        tasks: {
          assigned: [
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Создание презентации', time: '4ч' },
          ],
          inProgress: [
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Тестирование новой версии', time: '2ч' },
          ],
          review: [],
          completed: [
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Настройка окружения', time: '1,5ч' },
          ],
        },
        avatar: avatarIcon,
      },
      {
        label: 'Мария Морозова',
        tasks: {
          assigned: [
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Обновление базы данных', time: '3ч' },
          ],
          inProgress: [],
          review: [
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Проверка данных для отчета', time: '2ч' },
          ],
          completed: [
            { id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, title: 'Подготовка отчета', time: '1ч' },
          ],
        },
        avatar: avatar2,
      },
    ];

    export {mainPagePriorityOptions, priorityOptions, users, sprintOptions, boardOptions, }