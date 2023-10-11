import React, { useEffect, useState } from 'react'
import './LikedPage.css'
import { Link, useLocation } from 'react-router-dom'
import { BackendUrl } from '../ip'
import axios from 'axios'
import { useSnackbar } from 'notistack'
import Heart from 'react-animated-heart'

const LikedPage = () => {
    const location=useLocation()
    const {enqueueSnackbar}=useSnackbar()
    const [user,setUser]=useState()
    useEffect(()=>{    
        console.log("location state",location.state)
        let temp=JSON.parse(window.localStorage.getItem("user"))
         setUser(temp)
        axios.get(`${BackendUrl}/api/customer/fetch_liked_data`,{ headers: {"Authorization" : `Bearer ${temp.message.token}`} }).then((res)=>{
        console.log(res.data);

        setLikedData(res.data.message)
        var temp=res.data.message.map((obj)=>{return obj.id})
        setLikedList(temp)
      })},[])
      const [likedData,setLikedData]=useState([])
      const [likedList,setLikedList]=useState([])
    console.log(location.state);

    const changeLiked = (clickVal,id)=>{
        if(clickVal==false){
          let temp=likedData.filter((elem)=>elem.id!==id)
          setLikedData(temp);
        }
        console.log(user.message.token);
        axios.post(`${BackendUrl}/api/customer/add_liked`,{"isLiked":false,"locationId":id},{headers: {"Authorization" : `Bearer ${user.message.token}`}}).then((res)=>{
            enqueueSnackbar(res.data.message,{variant:'info'})
        })
    }
        return (
          <div className="scrollable-list">
            <ul>
              {likedData.map((item, index) => (
                <li key={index} onClick={()=>{}}>  
                    <div className='ItemCard'>
                        <div className='ItemImg'>
                            {console.log(item)}
                            <Link className="linkStyle" to="/details" state={{element:item,likedList:likedList}}>
                            <img src={`${item.imgUrl}`} alt="" /> 
                        </Link>
                        </div>
                        <div >
                        <Link className="linkStyle" to="/details" state={{element:item,likedList:likedList}}>
                          <div className='ItemsDetails'>
                            <div className='ItemTitle'>
                                <h1>{item.name}</h1></div>
                            <div className='HorizontalLine'><hr/></div>
                            <div className='ItemBody'>
                              <p>{item.details}</p>
                            </div>
                            </div>
                            </Link>
                        </div>
                        <div className='HeartContainer'>
                          <Heart isClick={true} onClick={() =>changeLiked(false,item.id)} className='TheHeart'/>
                        </div>
                    </div>
                </li>
              ))}
            </ul>
          </div>
        );
}

export default LikedPage;