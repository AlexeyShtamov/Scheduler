
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
      title: string
      time: string
      executor: string
      author: string | null
      description: string
      priority: string
      appointmentDate: string | null
      completionDate: string | null

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
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Исправление ошибок', 
              time: '2ч', 
              executor: 'Иван Иванов', 
              priority: 'Высокий', 
              appointmentDate: '2024-12-15', 
              completionDate: '2024-12-16',
              description: 'Исправление ошибок в коде по отчету тестировщика.',
              author: 'Иван Иванов' 
            },
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Обновление документации', 
              time: '1ч', 
              executor: 'Иван Иванов', 
              priority: 'Средний', 
              appointmentDate: '2024-12-14', 
              completionDate: '2024-12-14',
              description: 'Обновление документации проекта на основе новых требований.',
              author: 'Иван Иванов' 
            },
          ],
          inProgress: [
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Разработка новой функции', 
              time: '3ч', 
              executor: 'Мария Морозова', 
              priority: 'Низкий', 
              appointmentDate: '2024-12-14', 
              completionDate: '2024-12-14',
              description: 'Разработка функционала для нового модуля проекта.',
              author: 'Иван Иванов' 
            },
          ],
          review: [
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Код ревью коллеги', 
              time: '1,5ч', 
              executor: 'Петр Петров', 
              priority: 'Высокий', 
              appointmentDate: '2024-12-14', 
              completionDate: '2024-12-14',
              description: 'Ревью кода коллеги, исправление замечаний.',
              author: 'Иван Иванов' 
            },
          ],
          completed: [
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Деплой на сервер', 
              time: '2ч', 
              executor: 'Иван Иванов', 
              priority: 'Высокий', 
              appointmentDate: '2024-12-12', 
              completionDate: '2024-12-12',
              description: 'Деплой обновлений на сервер.',
              author: 'Иван Иванов' 
            },
          ],
        },
        avatar: avatar2,
      },
      {
        label: 'Петр Петров',
        tasks: {
          assigned: [
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Создание презентации', 
              time: '4ч', 
              executor: 'Петр Петров', 
              priority: 'Средний', 
              appointmentDate: '2024-12-13', 
              completionDate: '2024-12-13',
              description: 'Создание презентации для совещания с заказчиком.',
              author: 'Петр Петров' 
            },
          ],
          inProgress: [
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Тестирование новой версии', 
              time: '2ч', 
              executor: 'Петр Петров', 
              priority: 'Высокий', 
              appointmentDate: '2024-12-14', 
              completionDate: '2024-12-14',
              description: 'Тестирование новой версии системы.',
              author: 'Петр Петров' 
            },
          ],
          review: [],
          completed: [
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Настройка окружения', 
              time: '1,5ч', 
              executor: 'Иван Иванов', 
              priority: 'Низкий', 
              appointmentDate: '2024-12-10', 
              completionDate: '2024-12-10',
              description: 'Настройка окружения для нового проекта.',
              author: 'Петр Петров' 
            },
          ],
        },
        avatar: avatarIcon,
      },
      {
        label: 'Мария Морозова',
        tasks: {
          assigned: [
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Обновление базы данных', 
              time: '3ч', 
              executor: 'Мария Морозова', 
              priority: 'Средний', 
              appointmentDate: '2024-12-11', 
              completionDate: null,
              description: 'Обновление данных в базе для нового отчета.',
              author: 'Мария Морозова' 
            },
          ],
          inProgress: [],
          review: [
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Проверка данных для отчета', 
              time: '2ч', 
              executor: 'Петр Петров', 
              priority: 'Высокий', 
              appointmentDate: '2024-12-12', 
              completionDate: '2024-12-12',
              description: 'Проверка корректности данных для финансового отчета.',
              author: 'Мария Морозова' 
            },
          ],
          completed: [
            { 
              id: `task-${Math.floor(Math.random() * 1000000)}-${Date.now()}`, 
              title: 'Подготовка отчета', 
              time: '1ч', 
              executor: 'Мария Морозова', 
              priority: 'Средний', 
              appointmentDate: '2024-12-09', 
              completionDate: '2024-12-09',
              description: 'Подготовка отчета о проделанной работе за неделю.',
              author: 'Мария Морозова' 
            },
          ],
        },
        avatar: avatar2,
      },
    ];
    export {mainPagePriorityOptions, priorityOptions, users, sprintOptions, boardOptions, }