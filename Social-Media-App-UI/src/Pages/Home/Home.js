import { useEffect, useState } from 'react';
import Profile from "../../assets/profile.jpg";
import img1 from "../../assets/Post Images/img1.jpg";
import DPimg1 from "../../assets/DP/img1.jpg";
import Cover1 from "../../assets/Friends-Cover/cover-1.jpg";
import "../Home/Home.css";
import Left from "../../Components/LeftSide/Left";
import Middle from "../../Components/MiddleSide/Middle";
import Right from '../../Components/RightSide/Right';
import Nav from '../../Components/Navigation/Nav';
import moment from 'moment/moment';
import { getAllPosts } from '../../Configs/ApiService';

const Home = ({ setFriendsProfile }) => {
    const [posts, setPosts] = useState([]);
    const [body, setBody] = useState("");
    const [importFile, setImportFile] = useState("");
    const [search, setSearch] = useState("");
    const [following, setFollowing] = useState("");
    const [showMenu, setShowMenu] = useState(false);
    const [images, setImages] = useState(null);

    // Function to fetch posts from the API
    const fetchPosts = async () => {
        try {
            const response = await getAllPosts(); // Fetch posts using Axios instance
            setPosts(response); // Set the response data to the posts state
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
        const username = "Vijay";
        const profilepicture = Profile;
        const datetime = moment.utc(new Date(), 'yyyy/MM/dd kk:mm:ss').local().startOf('seconds').fromNow();
        const img = images ? { img: URL.createObjectURL(images) } : null;

        const obj = {
            id: id,
            profilepicture: profilepicture,
            username: username,
            datetime: datetime,
            img: img && img.img,
            body: body,
            like: 0,
            comment: 0
        };

        const insert = [...posts, obj];
        setPosts(insert);
        setBody("");
        setImages(null);
    };

    return (
        <div className='interface'>
            <Nav 
                search={search}
                setSearch={setSearch}
                showMenu={showMenu}
                setShowMenu={setShowMenu}
            />

            <div className="home">
                <Left />

                <Middle 
                    handleSubmit={handleSubmit}
                    body={body}
                    setBody={setBody}
                    importFile={importFile}
                    setImportFile={setImportFile}
                    posts={posts}
                    setPosts={setPosts}
                    search={search}
                    setFriendsProfile={setFriendsProfile}
                    images={images}
                    setImages={setImages}
                />

                <Right />
            </div>
        </div>
    );
};

export default Home;
