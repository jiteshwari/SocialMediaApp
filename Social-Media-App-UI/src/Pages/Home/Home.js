import { useEffect, useState } from 'react'



import "../Home/Home.css"

import Left from "../../Components/LeftSide/Left"
import Middle from "../../Components/MiddleSide/Middle"
import Right from '../../Components/RightSide/Right'
import Nav from '../../Components/Navigation/Nav'
import moment from 'moment/moment'
import { getAllPosts, getUserById } from '../../Configs/ApiService'

const Home = ({ setFriendsProfile }) => {

    const [user, setUser] = useState({
        id: 5,
        email: "m6@gmail.com",
        password: "$2a$10$cpfdIGFQP269i.B51rZ2BedtRbli0s5Gmh5bxyb21ciRoJ2CS7ORa",
        firstName: "a",
        lastName: "b",
        bio: "cc",
        profilepic: "https://storage.googleapis.com/myimages-jitu/image_1726605567725_Image.jpg"
    });
    const [data,setData]=useState(
        {
            "postId": 1,
            "userId": 1,
            "contentType": "image",
            "posturl": "https://storage.googleapis.com/myimages-jitu/image_1726607915249_user.jpg",
            "caption": "First post",
            "createdDate": "2024-09-18T02:48:35.988658",
            "updatedDate": null
        }
    );
    const [posts, setPosts] = useState([
        {
            id: 1,
            username: user.firstName +" "+ user.lastName, // Using user's name
            profilepicture: user.profilepic, // Using user's profile picture
            img: data.posturl,
            datetime: data.createdDate,
            body: data.caption,
            like: 44,
            comment: 3,
            unFilledLike: true,
            coverpicture: data.posturl,
            ModelCountryName: "USA",
            ModelJobName: "Java Developer",
            ModelJoinedDate: "Joined in 2019-02-28",
            followers: 1478
        }
    ]);
    

    const [body, setBody] = useState("")
    useEffect(() => {
        const fetchUserData = async () => {
            const userId = sessionStorage.getItem('userId');
            if (userId) {
                try {
                    const postdata=await getAllPosts();
                    console.log(postdata);
                    const userData = await getUserById(userId);
                    console.log(userData);
                    setUser(userData);
                } catch (error) {
                    console.error('Error fetching user data:', error);
                }
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


    const [following, setFollowing] = useState("")

    const [showMenu, setShowMenu] = useState(false)
    const [images, setImages] = useState(null)
  const [profileImg,setProfileImg] =useState("https://storage.googleapis.com/myimages-jitu/image_1726605567725_Image.jpg")


    return (
        <div className='interface'>
            <Nav
            profileImg={profileImg}
            />

            <div className="home">

                <Left profileImg={profileImg}/>

                <Middle
                    handleSubmit={handleSubmit}
                    posts={posts}
                    setPosts={setPosts}
                    search={search}
                    images={images}
                    setImages={setImages}
                />

                <Right
                 
                />
            </div>

        </div>
    )
}

export default Home