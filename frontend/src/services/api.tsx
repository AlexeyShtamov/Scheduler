import axios from "axios";

const API_BASE_URL = 'http://localhost:8081';

export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem("authToken")}` // Извлекаем токен из localStorage
  },
});

export const registerUser = async (userData: {
    firstName: string;
    lastName: string;
    phone: string;
    email: string;
    password: string;
    repeatPassword: string;
  }) => {
    try {
      const response = await api.post('/people', userData);
      return response.data; // Успешный ответ
    } catch (error: any) {
      throw error.response?.data || error.message; // Обработка ошибок
    }
  };

  export const loginUser = async (credentials: { email: string; password: string }) => {
    try {
      const response = await api.post('/people/auth', credentials);
      return response.data; // Успешный ответ
    } catch (error: any) {
      throw error.response?.data || error.message; // Обработка ошибок
    }
  };

  export const getProjects = async (projectId: number) => {
  try {
    const response = await api.get(`/api/projects/${projectId}`); // передаем projectId в путь
    return response.data;
  } catch (error) {
    console.error('Ошибка:', error);
    throw error;
  }
};

  export const createBoard = async (boardData: { boardName: string; id: number }) => {
    try {
      const response = await api.post('/api/projects', boardData); // Отправляем данные на сервер
      return response.data; // Успешный ответ
    } catch (error: any) {
      console.error('Ошибка при создании доски:', error);
      throw error.response?.data || error.message; // Обработка ошибок
    }
  };
  
  export const getSprints = async (projectId: number) => {
    try {
      const response = await api.get(`/api/sprints`, {
        params: { projectId },  // Передаем projectId как параметр запроса
      });
      return response.data; // Если все ок, возвращаем данные
    } catch (error: any) {
      console.error('Ошибка при загрузке спринтов:', error.response?.data || error.message);
      throw error; // Пробрасываем ошибку дальше
    }
  };

  export const createSprint = async (newSprint: { title: string; startDate: string; endDate: string; projectId: number }) => {
    const personID = localStorage.getItem("personId");
    console.log(personID); // Получаем id пользователя
  
    if (!personID) {
      throw new Error('Не удалось найти пользователя. Пожалуйста, авторизуйтесь.');
    }
  
    try {
      // Добавляем personID в тело запроса
      const response = await api.post('/api/sprints', {
        ...newSprint,
        personID, // Передаем personID в теле запроса
      });
  
      return response.data; // Возвращаем данные ответа, если запрос прошел успешно
    } catch (error: any) {
      console.error('Ошибка при создании спринта:', error.response?.data || error.message);
      throw error; // Пробрасываем ошибку
    }
  };
  
  export default api;
