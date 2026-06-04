import axios from "axios";

// J'ai mis le port 8080 pour correspondre à ton backend Spring Boot
const API_BASE_URL = "http://localhost:8080/api";

const api = axios.create({
  baseURL: API_BASE_URL,
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("access_token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem("access_token");
      localStorage.removeItem("refresh_token");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

// VOICI LA FONCTION QUI MANQUAIT !
export const login = (email, password) => {
  return axios.post(`${API_BASE_URL}/auth/login`, {
    email,
    password,
  });
};

export const getSystems = () => {
  return api.get("/systems");
};

export const getSystem = (systemId) => {
  return api.get(`/systems/${systemId}`);
};

export const getSystemProduction = (systemId, start, end) => {
  return api.get(`/systems/${systemId}/production`, {
    params: { start, end },
  });
};

export default api;