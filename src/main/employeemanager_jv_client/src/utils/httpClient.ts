// src/utils/httpClient.ts
import axios from 'axios';
import { useLoading } from 'vue3-loading-overlay';

const httpClient = axios.create({
  baseURL: 'http://localhost:8080/api',
});

let loadingCount = 0;
const loader= useLoading();

httpClient.interceptors.request.use((config) => {
  loadingCount++;
  if (loadingCount === 1) {
    loader.show({ canCancel: false, isFullScreen: true, active: true });
  }
  return config;
});

httpClient.interceptors.response.use(
  (response) => {
    loadingCount--;
    if (loadingCount === 0) {
      loader.hide();
    }
    return response;
  },
  (error) => {
    loadingCount--;
    if (loadingCount === 0) {
      loader.hide();
    }
    return Promise.reject(error);
  }
);

export default httpClient;
