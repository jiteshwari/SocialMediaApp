import Info from './ProfileComponents/InfoProfile/Info'
import UserHome from '../UserHome/UserHome'

 
import { useEffect, useState } from 'react'
import "../Profile/ProfileMiddle.css"

import moment from 'moment'
import ProfileInputPost from './ProfileComponents/ProfileInputPost'
import { getPostsByUserId } from '../../Configs/ApiService'
import ImageUploadComponent from '../Post/ImageUploadComponent'

const ProfileMiddle = ({following,
                        search,
                        images,
                        setImages,
                        profileImg,
                        setProfileImg,
                        name,
                        setName,
                        userName,
                        setUserName,
                        modelDetails,
                        setModelDetails}) => {

  const [userPostData ,setUserPostData] =useState(
    []
  )
  const [body,setBody] =useState("")
  const [importFile,setImportFile] =useState("")
  const [loading, setLoading] = useState(true); 
 

  const handleSubmit =(e)=>{
    e.preventDefault()

  
    const id =userPostData.length ? userPostData[userPostData.length -1].id +1 :1
    const username="Jiteshwari"
    const profilepicture=profileImg
    const datetime=moment.utc(new Date(), 'yyyy/MM/dd kk:mm:ss').local().startOf('seconds').fromNow()
    const img= images ? {img:URL.createObjectURL(images)} : null

   
    const obj ={id:id,
               profilepicture:profilepicture,
               username:username,
               datetime:datetime,
               img:img && (img.img),
               body:body,
               like:0,
               comment:0
              }

    const insert =[...userPostData,obj]
    setUserPostData(insert)
    setBody("")
    setImages(null)
  }


  

  const [searchResults,setSearchResults] =useState("")
    
    useEffect(()=>{
      const searchData = userPostData.filter((val)=>(
        (val.body.toLowerCase().includes(search.toLowerCase()))
       ||
       (val.username.toLowerCase().includes(search.toLowerCase()))
       ))
       setSearchResults(searchData)
       
    },[userPostData,search])



    useEffect(() => {
      const fetchUserPost = async () => {
        const userId = sessionStorage.getItem('userId');
        if (userId) {
          try {
            const posts = await getPostsByUserId(userId);
        
          const example=posts.map(post => ({
            id: post.postId, // Adjust according to your post structure
            username: "jitu",
            profilepicture: profileImg, // Use your profile image or get from post if available
            img: post.posturl || null, // Assuming posts have an img property
            datetime: moment(post.createdDate).fromNow(), // Adjust based on your datetime structure
            body: post.caption,
            like: 10 || 0, // Default to 0 if not available
            comment: 1 || 0 // Default to 0 if not available
          }));
            //setUserPostData(posts);
            if (Array.isArray(posts)) {
              // Append fetched posts to the existing initial posts
              const updatedPosts = [...userPostData, ...example];
              setUserPostData(updatedPosts);
            }
            console.log(posts);
          } catch (error) {
            console.error('Error fetching user data:', error);
          } finally {
            setLoading(false); 
          }
        } else {
          setLoading(false); 
        }
      };
      fetchUserPost();
    }, []);

   

    

  return (
    <div className='profileMiddle'>
     {loading ? ( // Loading indicator
        <div className='loading'>Loading...</div>
      ) : (<>  <Info 
        modelDetails ={modelDetails}
        setModelDetails={setModelDetails}
        profileImg={profileImg}
        setProfileImg={setProfileImg}
        userPostData={userPostData}
        following={following}
        name={name}
        setName={setName}
        userName={userName}
        setUserName={setUserName}
        />
        
        {/* <ProfileInputPost
        modelDetails={modelDetails}
        profileImg={profileImg}
        handleSubmit={handleSubmit}
        body ={body}
        setBody ={setBody}
        importFile ={importFile}
        setImportFile ={setImportFile}
        images={images}
        setImages={setImages}
        /> */}
    <ImageUploadComponent  profileImg={profileImg} modelDetails={modelDetails}/>

        
        <UserHome 
        modelDetails={modelDetails}
        profileImg={profileImg}
        setUserPostData={setUserPostData}
        userPostData={searchResults}
        images={images}
        /></>)
      }
    </div>
  )
}

export default ProfileMiddle