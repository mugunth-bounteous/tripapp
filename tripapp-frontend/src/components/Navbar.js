import React, { useState,createContext, useEffect } from 'react'
import './Navbar.css'
import { useLocation } from 'react-router-dom';
import { Link, Outlet } from 'react-router-dom'
import axios from 'axios';
import { BackendUrl } from '../ip';
import { enqueueSnackbar } from 'notistack';
import { useNavigate } from 'react-router-dom';

export const DetailsContext = createContext(null);
  
const Navbar = () => {
const{navigate}=useNavigate();
  const curLocation=useLocation();
  var location=JSON.parse(window.localStorage.getItem(
    "user"
  ))
  const [locations,setLocations]=useState(null)
 
  useEffect(()=>{},[locations])
  useEffect(()=>{
    var temp= window.localStorage.getItem(
      "user"
    );
    if(temp==null){
      navigate("/login",{replace:true})
    }
    else{
    axios.get(`${BackendUrl}/api/trip/fetch-all-location`,{ headers: {"Authorization" : `Bearer ${location.message.token}`} }).then((res)=>{
      if(res.data==null){
        navigate("/login",{replace:true})
      }
      setLocations(res.data.message)
      console.log(res.data.message)
      window.localStorage.setItem("locations",JSON.stringify(res.data.message))
    })
    let data=window.localStorage.getItem(
      "user"
    )

    let loc=window.localStorage.getItem(
      "selectedLocation"
    )
    if(loc!=null){
      setDetails(prevstate=>({...prevstate,location:loc}))
    }
    location=JSON.parse(data)

    if(data==null){
      navigate("/login",{replace:true})
    }
  }
  },[])
  const [details,setDetails] = useState({
    username:location.message.data.username,
    password:location.message.data.password,
    location:'',
    firstname:location.message.data.firstName,
    lastname:location.message.data.lastName,
    info:null
})
  
  var func = (event)=>{
    console.log(event.target.value)
    setDetails(prevstate=>({...prevstate,location:event.target.value}))
    window.localStorage.setItem("selectedLocation",event.target.value)
    axios.post(`${BackendUrl}/api/trip/fetch`,{location:event.target.value},{ headers: {"Authorization" : `Bearer ${location.message.token}`} }).then((res)=>{
      console.log(res.data.message)
      setDetails(prevstate=>({...prevstate,info:res.data.message}))
    })
  }


  return (
    <div>
    <nav className="navbar">
      <div className="navbar-logo">
      {
        curLocation.pathname!=="/home"?"Trip app":
        <select name="selectedCity" onChange={func} value={details.location} >
        <option disabled={true} value="" >
          --Choose a city--
        </option>
        {locations!=null?locations.map((element) => {
          console.log(element);
          return <option value={element.id} >{element.location}</option>
        }):<></>}

        </select>
      }
      </div>
      <ul className="navbar-nav">
        <li className="nav-item">
          <Link to="/home" className="nav-link">Home</Link>
        </li>
        <li className="nav-item">
          <Link to="/favorites" className="nav-link" state={{details:details}}>Favorites</Link>
        </li>
        <li className="nav-item">
          <Link to="/login" className="nav-link" onClick={()=>{window.localStorage.clear()}}>Logout</Link>
        </li>
      </ul>
    </nav>
    <DetailsContext.Provider
      value={{ details,setDetails }}
    >
      <Outlet />
    </DetailsContext.Provider>
    
  </div>
  )
}

export default Navbar