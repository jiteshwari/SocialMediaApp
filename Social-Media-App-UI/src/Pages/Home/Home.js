import { useEffect, useState } from 'react';
import profileImg from "../../assets/DP/img3.jpg";
import "../Home/Home.css";
import Left from "../../Components/LeftSide/Left";
import Middle from "../../Components/MiddleSide/Middle";
import Right from '../../Components/RightSide/Right';
import Nav from '../../Components/Navigation/Nav';
import moment from 'moment/moment';
import axios from 'axios'; // Import axios

const Home = ({ setFriendsProfile }) => {
    const [posts, setPosts] = useState([]);
    const [showMenu, setShowMenu] = useState(false);

    // Function to fetch posts from the API
    const fetchPosts = async () => {
        try {
            // Retrieve the token from sessionStorage
            const token = sessionStorage.getItem('jwtToken'); // Adjust the key as needed

            // Make the API call with the token included in headers
            const response = await axios.get('http://localhost:8083/api/home/posts', {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                }
            });

            // Set the response data to the posts state
            setPosts(response.data); 
        } catch (error) {
            console.error("Error fetching posts:", error);
        }
    };

    // Call fetchPosts when the component mounts
    useEffect(() => {
        fetchPosts();
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        const id = posts.length ? posts[posts.length - 1].id + 1 : 1;
        const username = "jiteshwari";
        const profilepicture = profileImg;
        const datetime = moment.utc(new Date(), 'yyyy/MM/dd kk:mm:ss').local().startOf('seconds').fromNow();

        const obj = {
            id: id,
            profilepicture: profilepicture,
            username: username,
            datetime: datetime,
            like: 0,
            comment: 0
        };

        const insert = [...posts, obj];
        setPosts(insert);
    };

    return (
        <div className='interface'>
            <Nav profileImg={profileImg} />
            <div className="home">
                <Left profileImg={profileImg} />
                <Middle 
                    handleSubmit={handleSubmit}
                    posts={posts}
                    setPosts={setPosts}
                    profileImg={profileImg}
                />
                <Right />
            </div>
        </div>
    );
};

export default Home;
