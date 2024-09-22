import React from 'react'
import "../Navigation/Nav.css"
import SearchIcon from '@mui/icons-material/Search';
import { Link } from 'react-router-dom';

import {AiOutlineHome} from "react-icons/ai"
import {LiaUserFriendsSolid} from "react-icons/lia"
import {IoNotificationsOutline} from "react-icons/io5"
import {TbMessage} from "react-icons/tb"

 
const Nav = ({search,setSearch,setShowMenu,profileImg}) => {


  
  return (
    <nav>
        <div className="n-logo">
            <Link to="/home" className='logo' style={{color:"black",textDecoration:"none"}}>
              <h1>Social <span>Media</span></h1>
            </Link>
        </div>

      <div className="n-form-button" >

        <form className='n-form' onSubmit={(e)=>e.preventDefault()} >
          <SearchIcon className='search-icon'/>
          <input type="text" 
          placeholder='Search post'
          id='n-search'
          value={search}
          onChange={(e)=>setSearch(e.target.value)}
          />
        </form>
      </div>

      <div className="social-icons">
      <Link to="/home" style={{textDecoration:"none",display:"flex",alignItems:"center",color:"white"}}>
        <AiOutlineHome className='nav-icons'/>
      </Link>

       
           
        <TbMessage className='nav-icons'/>
        <LiaUserFriendsSolid 
        className='nav-icons'
        onClick={()=>setShowMenu(true)}/>
      </div>


       <div className="n-profile" >
          <Link to="/profile"> 
            <img src={profileImg ? (profileImg) : "https://www.defineinternational.com/wp-content/uploads/2014/06/dummy-profile.png"} className='n-img' style={{marginBottom:"-7px"}}/>
          </Link>
      </div>
  
    </nav>
  )
}

export default Nav