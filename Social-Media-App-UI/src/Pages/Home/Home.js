// 
import React, { useState } from 'react';
import axios from 'axios';

const Home = () => {
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState(null);

  // Fetch Posts Function with Logging
  const fetchPosts = async () => {
    const API_URL = 'http://localhost:8082/api/posts/all';  // Your backend URL

    try {
      console.log('Initiating API call to fetch posts...');  // Log start of API call

      // Simulating the presence of a JWT token (if necessary)
      const token = sessionStorage.getItem('jwtToken');
      console.log('JWT token used:', token);

      // Make API call
      const response = await axios.get(API_URL, {
        headers: {
          'Authorization': `Bearer ${token}`,  // Add Authorization header if required
          'Content-Type': 'application/json,multipart/form-data',
        },  // For cross-origin credentials
      });

      console.log('API call successful. Response data:', response.data);  // Log successful response
      setPosts(response.data);
      setError(null);
    } catch (err) {
      console.error('Error occurred during API call:', err);  // Log error
      setError('Failed to fetch posts. Please try again later.');
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Home Page</h1>

      {/* Button to Fetch Posts */}
      <button
        onClick={() => {
          console.log('Fetch Posts button clicked.');  // Log button click
          fetchPosts();
        }}
        style={{ padding: '10px 20px', fontSize: '16px' }}
      >
        Fetch Posts
      </button>

      {/* Error Display */}
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {/* Posts Display */}
      {posts.length > 0 ? (
        <div>
          <h2>Posts</h2>
          <ul>
            {posts.map(post => (
              <li key={post.postId}>
                <strong>{post.caption || post.contentText}</strong>
                <p>Content Type: {post.contentType}</p>
              </li>
            ))}
          </ul>
          {console.log('Posts displayed:', posts)} {/* Log post data */}
        </div>
      ) : (
        <p>No posts to display.</p>
      )}
    </div>
  );
};

export default Home;
