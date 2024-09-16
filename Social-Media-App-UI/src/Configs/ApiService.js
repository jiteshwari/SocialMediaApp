import createAxiosInstance from './AxiosConfig'; // Import the function that creates Axios instance

// Create an Axios instance without a base URL
const axiosInstance = createAxiosInstance();

// Define URLs for the API endpoints
const API_URLS = {
    REGISTER: 'http://localhost:8084/api/auth/register',
    LOGIN: 'http://localhost:8084/api/auth/login',
    LOGOUT: 'http://localhost:8084/api/auth/logout',
    FETCH_POSTS: 'http://localhost:8083/api/home/posts',
    LIKE_POST: 'http://localhost:8083/api/home/like'
};

// Example API calls

// Register User
export const registerUser = async (userData) => {
    try {
        console.log(userData);
        const response = await axiosInstance.post(API_URLS.REGISTER, userData, {
            headers: {
                'Content-Type': 'application/json', // Specify headers for JSON data
            },
        });
        console.log(response);
        return response.data;
    } catch (error) {
        throw error;
    }
};

// Login User
export const loginUser = async (loginData) => {
    try {
        const response = await axiosInstance.post(API_URLS.LOGIN, loginData);
        console.log(response.data);
        // Store the token in sessionStorage
        sessionStorage.setItem('jwtToken', response.data.jwtToken);
        return response.data;
    } catch (error) {
        throw error;
    }
};

// Logout User
export const logoutUser = async () => {
    try {
        const response = await axiosInstance.post(API_URLS.LOGOUT);
        // Clear the token from sessionStorage
        sessionStorage.removeItem('jwtToken');
        return response.data;
    } catch (error) {
        throw error;
    }
};

// Fetch all posts
export const getAllPosts = async () => {
    try {
        const response = await axiosInstance.get(API_URLS.FETCH_POSTS);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Error fetching posts:', error);
        throw error;
    }
};

// Like a post
export const likePost = async (userId, postId) => {
    try {
        const response = await axiosInstance.post(API_URLS.LIKE_POST, null, {
            params: { userId, postId }
        });
        return response.data;
    } catch (error) {
        console.error('Error liking post:', error);
        throw error;
    }
};
