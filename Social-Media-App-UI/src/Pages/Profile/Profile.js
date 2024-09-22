import { useEffect, useState } from 'react';
import Left from '../../Components/LeftSide/Left';
import ProfileMiddle from '../../Components/Profile/ProfileMiddle';
import Right from '../../Components/RightSide/Right';
import Nav from '../../Components/Navigation/Nav';
import "../Profile/Profile.css";
import { getUserById } from '../../Configs/ApiService';

const Profile = () => {
  const [following, setFollowing] = useState(3);
  const [search, setSearch] = useState("");
  const [showMenu, setShowMenu] = useState(false);
  const [images, setImages] = useState(null);
  const [name, setName] = useState("");
  const [userName, setUserName] = useState("");
  const [profileImg, setProfileImg] = useState("");
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true); // New loading state
  const [modelDetails, setModelDetails] = useState();

  useEffect(() => {
    const fetchUserData = async () => {
      const userId = sessionStorage.getItem('userId');
      if (userId) {
        try {
          const userData = await getUserById(userId);
          console.log(userData);
          setUser(userData);
          setModelDetails({
            ModelName: userData.firstName,
            ModelUserName: userData.firstName + userData.lastName,
            ModelCountryName: "India",
            ModelJobName: "Full Stack Developer in IBM"
          });
          setProfileImg(userData.profilepic);
        } catch (error) {
          console.error('Error fetching user data:', error);
        } finally {
          setLoading(false); 
        }
      } else {
        setLoading(false); 
      }
    };
    fetchUserData();
  }, []);

  // Show loading indicator if data is still being fetched
  if (loading) {
    return <div className="loading">Loading...</div>; // Customize your loading indicator
  }

  return (
    <div className='interface'>
      <Nav profileImg={profileImg} />
      <div className="home">
        <Left profileImg={profileImg} />
        <ProfileMiddle 
        setImages={setImages}
          search={search}
          name={name}
          profileImg={profileImg}
          modelDetails={modelDetails}
        />
        <Right />
      </div>
    </div>
  );
}

export default Profile;
