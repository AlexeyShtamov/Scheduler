import axios from "axios";

const API_BASE_URL = 'http://localhost:8081';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
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
      const response = await api.post('/person', userData);
      return response.data; // Успешный ответ
    } catch (error: any) {
      throw error.response?.data || error.message; // Обработка ошибок
    }
  };
  
  export default api;
