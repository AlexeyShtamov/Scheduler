

const mainPagePriorityOptions = [
    {label: 'ВСЕ ПРИОРИТЕТЫ'},
    { label: 'НИЗКИЙ' },
    { label: 'СРЕДНИЙ' },
    { label: 'ВЫСОКИЙ' },
  ];
  
  const priorityOptions = [
    { label: 'LOW' },
    { label: 'MEDIUM' },
    { label: 'HIGH' },
  ];
  
  const boardOptions = [
    { label: 'МОЯ', id: '1' },
    { label: 'КОМАНДА 1' , id: '2' },
    { label: 'КОМАНДА 2', id: '3' },
  ];
  
  
  const sprintOptions= [
    { title : 'Начало', 
      id: '1', 
      appointmentDate: '2024-12-14', 
      completionDate: '2024-12-14'},
    {title: 'Разработка',
       id :'2', 
       appointmentDate: '2024-12-20', 
       completionDate: '2024-12-25' }
      ]
 
    const users = localStorage.getItem("users")
    
    export {mainPagePriorityOptions, priorityOptions, users, sprintOptions, boardOptions, }