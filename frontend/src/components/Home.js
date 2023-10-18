import React, { useEffect, useState } from 'react'
import'./Home.css'
import { Link } from 'react-router-dom';
import { useContext } from 'react';
import {DetailsContext} from './Navbar';
import { useNavigate } from 'react-router-dom';
import { useSnackbar } from 'notistack';
import axios from 'axios';
import { BackendUrl } from '../ip';
import logger from 'react-logger';

const Home = () => {
  useEffect(()=>{
    var temp= window.localStorage.getItem(
      "user"
    );
    if(temp==null){
      navigate("/login",{replace:true})
    }
    else{
      var user=JSON.parse(window.localStorage.getItem("user"));
      axios.get(`${BackendUrl}/api/customer/fetch_liked_list`,{ headers: {"Authorization" : `Bearer ${user.message.token}`} }).then((res)=>{
        if(res.data==null){
          navigate("/login",{replace:true})
        }
        setLikedList(res.data.message)
      }).catch((err)=>{logger.info(err);})
    }
  },[])

  const value=useContext(DetailsContext);
 let navigate=useNavigate();
 const[likedList,setLikedList]=useState([])
  const[locationName,setLocationName]=useState("")
  // logger.info(value.details)


  useEffect(()=>{
    if(value.details.location===''){

    }
    else{
      var t= JSON.parse(window.localStorage.getItem("locations"));
      setLocationName(t[value.details.location-1].location)
    }
  },[value])
  return (
    <div>
      <div className='container'>
        <div className="hero-image d-flex" >
          <p className="hero-heading">
            {value.details.location===''?
            `Welcome ${value.details.firstname.replace(/^./, value.details.firstname[0].toUpperCase())}!`
            :`Explore the city of ${locationName}!`}
          </p>
          <p className="hero-subheading">
            {value.details.location===''?`Select a location to proceed!`:`Scroll down to know more!`}
          </p>
        </div> 
      </div>
    <div>
      {
      value.details.info==null?'':
      <div className='grid-container'>
        {
      value.details.info.map(element => (
        <div >
        <div className="tile">
          <Link to="/details" state={{element:element,likedList:likedList}}>
            <img src={element.imgUrl} alt=""/>
              <div className="tile-text">
                <h5>{element.name}</h5>
              </div>
          </Link>
        </div> 
      </div>
      ))}
      </div>
}
    </div>
    </div>
  )
}

export default Home