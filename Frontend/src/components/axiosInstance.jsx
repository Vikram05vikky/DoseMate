import axios from "axios";

const axiosInstance = axios.create({
  // baseURL: "http://localhost:8080", // 👈 backend base URL
  baseURL : 'https://dimktqsi2kki8.cloudfront.net/',

  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosInstance;
