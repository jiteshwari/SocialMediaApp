import axios from 'axios';

// Create a function that returns an axios instance without a baseURL
const createAxiosInstance = () => {
    const axiosInstance = axios.create({
        timeout: 10000,
        headers: { 'Content-Type': 'application/json' }
    });

    // Request interceptor to attach JWT token to each request
    axiosInstance.interceptors.request.use(
        (config) => {
            const token = sessionStorage.getItem('jwtToken');
            if (token) {
                config.headers['Authorization'] = `Bearer ${token}`;
            }
            return config;
        },
        (error) => {
            console.error('Request error:', error);
            return Promise.reject(error);
        }
    );

    // Response interceptor to handle errors and log them
    axiosInstance.interceptors.response.use(
        (response) => {
            return response;
        },
        (error) => {
            if (error.response) {
                // The request was made and the server responded with a status code
                // that falls out of the range of 2xx
                console.error('Response error:', error.response.data);
                console.error('Response status:', error.response.status);
                console.error('Response headers:', error.response.headers);

                if (error.response.status === 401) {
                    // Handle unauthorized errors, e.g., redirect to login
                    console.error('Unauthorized request - possibly due to invalid or expired token');
                }
            } else if (error.request) {
                // The request was made but no response was received
                console.error('No response received:', error.request);
            } else {
                // Something happened in setting up the request that triggered an Error
                console.error('Error', error.message);
            }
            return Promise.reject(error);
        }
    );

    return axiosInstance;
};

export default createAxiosInstance;
