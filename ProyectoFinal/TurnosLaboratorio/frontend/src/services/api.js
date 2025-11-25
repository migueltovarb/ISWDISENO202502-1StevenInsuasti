import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para manejar errores
api.interceptors.response.use(
  response => response,
  error => {
    // No redirigir automáticamente en 401, dejar que cada componente maneje el error
    return Promise.reject(error);
  }
);