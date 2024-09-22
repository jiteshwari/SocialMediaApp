import { useEffect, useState } from 'react'



import "../Home/Home.css"

import Left from "../../Components/LeftSide/Left"
import Middle from "../../Components/MiddleSide/Middle"
import Right from '../../Components/RightSide/Right'
import Nav from '../../Components/Navigation/Nav'
import moment from 'moment/moment'
import { getAllPosts, getUserById } from '../../Configs/ApiService'

const Home = ({ setFriendsProfile }) => {

    const [user, setUser] = useState();

    const [data,setData]=useState(
        {
            "postId": 1,
            "userId": 7,
            "contentType": "image",
            "posturl": "https://storage.googleapis.com/myimages-jitu/image_1726607915249_user.jpg",
            "caption": "First post",
            "createdDate": "2024-09-18T02:48:35.988658",
            "updatedDate": null
        }
    );
    const [posts, setPosts] = useState([]);
    

    const [body, setBody] = useState("")
    const [loading, setLoading] = useState(true);



    useEffect(() => {
        const fetchUserData = async () => {
            const userId = sessionStorage.getItem('userId');
            if (userId) {
                try {
                    setLoading(true); // Start loading
                    const postdata = await getAllPosts(); // Fetch all posts
                    const currentUser=await getUserById(userId);
                    setUser(currentUser);
                    // Map over posts and fetch each user's data
                    const formattedPosts = await Promise.all(postdata.map(async (post) => {
                        const userData = await getUserById(post.userId); // Fetch user by post's userId
                        return {
                            id: post.postId, // Assuming postId is the identifier
                            username: userData.firstName + " " + userData.lastName, // Using fetched user data
                            profilepicture: userData.profilepic, // Using user's profile picture
                            img: post.posturl, // Image URL from post
                            datetime: moment(post.createdDate).fromNow(), // Format the date using moment
                            body: post.caption, // Caption from post
                            like: post.likes || 0, // Assuming likes field exists
                            comment: post.comments || 0, // Assuming comments field exists
                            unFilledLike: true, // You can set this based on your logic
                            coverpicture: post.posturl, // Using post image as cover picture
                            ModelCountryName: "USA", // You can modify this based on your needs
                            ModelJobName: "Java Developer", // Modify as needed
                            ModelJoinedDate: "Joined in 2019-02-28", // Modify as needed
                            followers: userData.followers || 0 // Use fetched user data for followers
                        };
                    }));
    
                    setPosts(formattedPosts); // Set the mapped posts to state
                } catch (error) {
                    console.error('Error fetching user data:', error);
                } finally {
                    setLoading(false); // End loading
                }
            } else {
                setLoading(false); // End loading if no userId
            }
        };
    
        fetchUserData();
    }, []);
    
    

    const handleSubmit = (e) => {
        e.preventDefault()
        const id = posts.length ? posts[posts.length - 1].id + 1 : 1
        const username = "Jiteshwari"
        const profilepicture = user.profilepic
        const datetime = moment.utc(data.createdDate, 'yyyy/MM/dd kk:mm:ss').local().startOf('seconds').fromNow()
        const img = user.profilepic

        const obj = {
            id: id,
            profilepicture: profilepicture,
            username: username,
            datetime: datetime,
            img: img && (img.img),
            body: body,
            like: 0,
            comment: 0
        }
        const insert = [...posts, obj]
        setPosts(insert)
        setBody("")
        setImages(null)

    }

    const [search, setSearch] = useState("")

    const [showMenu, setShowMenu] = useState(false)
    const [images, setImages] = useState(null)
  const [profileImg,setProfileImg] =useState("")


    return (
        <div className='interface'>
            {loading ? ( // Step 2: Render loading indicator
                    <div className='loading'>Loading...</div>
                ) : (<><Nav
            profileImg={user.profilepic}
            />

            <div className="home">

                <Left profileImg={user.profilepic}/>

               
                <Middle
                    handleSubmit={handleSubmit}
                    posts={posts}
                    setPosts={setPosts}
                    search={search}
                    set={setBody}
                    setSearch={setSearch}
                    images={images}
                    setImages={setImages}
                    profileImg={user.profilepic}
                />

                <Right
                 
                />
            </div>
            </> )}
        </div>
    )
}

export default Home