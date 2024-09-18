import { useState } from 'react'
import Left from '../../Components/LeftSide/Left'
import ProfileMiddle from '../../Components/Profile/ProfileMiddle'
import Right from '../../Components/RightSide/Right'
import Nav from '../../Components/Navigation/Nav'
import "../Profile/Profile.css"


const Profile = () => {

  const [following,setFollowing] =useState(3)
  const [search,setSearch] =useState("")

  const [showMenu,setShowMenu] =useState(false)

  const [images,setImages] =  useState(null)

  const [name,setName]= useState("")
  const [userName,setUserName]= useState("")
  const [profileImg,setProfileImg] =useState("https://storage.googleapis.com/myimages-jitu/image_1726605567725_Image.jpg")

  const [user, setUser] = useState({
    id: 5,
    email: "m6@gmail.com",
    password: "$2a$10$cpfdIGFQP269i.B51rZ2BedtRbli0s5Gmh5bxyb21ciRoJ2CS7ORa",
    firstName: "a",
    lastName: "b",
    bio: "cc",
    profilepic: "https://storage.googleapis.com/myimages-jitu/image_1726605567725_Image.jpg"
});

  const [modelDetails,setModelDetails] = useState(
    {
      ModelName:user.firstName,
      ModelUserName:user.firstName+user.lastName,
      ModelCountryName:"India",
      ModelJobName:"Full Stack Developer in IBM"
    }
  )

  return (
    <div className='interface'>
        <Nav
        search={search}
        setSearch={setSearch}
        showMenu={showMenu}
        setShowMenu={setShowMenu}
        profileImg={profileImg}
        />
      <div className="home">
        <Left 
        profileImg={profileImg}
        />

        <ProfileMiddle 
        following={following}
        search={search}
        images={images}
        setImages={setImages}
        name={name}
        setName={setName}
        userName={userName}
        setUserName={setUserName}
        profileImg={profileImg}
        setProfileImg={setProfileImg}
        modelDetails={modelDetails}
        setModelDetails={setModelDetails}
        />
        
        <Right 
        />
      </div>
    </div>
  )
}

export default Profile