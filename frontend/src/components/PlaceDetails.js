import React, { useEffect } from 'react'
import { Navigate, useLocation, useNavigate } from 'react-router-dom';
import'./PlaceDetails.css';
import { useState } from 'react';
import Heart from 'react-animated-heart';
import axios from 'axios';
import { BackendUrl } from '../ip';
import { useSnackbar } from 'notistack';
import Rating from 'react-rating';
import { FaStar } from 'react-icons/fa';
import logger from 'react-logger';





const PlaceDetails = () => {
    const navigate=useNavigate();
    const location=useLocation();
    const [user,setUser]=useState();
    const [review,setReview]=useState({userReview:'',otherReview:''})
    const {enqueueSnackbar}=useSnackbar();
    
    useEffect(()=>{
        let tempuser=JSON.parse(localStorage.getItem('user'));
        // logger.info('tempuser',tempuser);
        setUser(tempuser);
        // logger.info('element' ,location.state)
        if(location.state.likedList.includes(location.state.element.id)){
            setClick(true)
        }
        reviewFunc(tempuser)

    },[])

    // useEffect(()=>{reviewFunc(user)},[review])
    let reviewFunc=(tempuser)=>{
        axios.post(`${BackendUrl}/api/customer/fetch_review_by_location`,{locationId:location.state.element.id},{headers: {"Authorization" : `Bearer ${tempuser.message.token}`}}).then((res)=>{
            setReview(prevState=>({...prevState,otherReview:res.data.message}))
            
    }).catch((err)=>{
        logger.info(err)
    })
    axios.post(`${BackendUrl}/api/customer/fetch_review_of_user`,{locationId:location.state.element.id},{headers: {"Authorization" : `Bearer ${tempuser.message.token}`}}).then((res)=>{
        setReview(prevState=>({...prevState,userReview:res.data.message}))
        logger.info("userReview",res.data);
    }).catch((err)=>{
        logger.info(err)
    })

    
    }
    let onClickFunc=()=>{
        navigate("/home")
    }
    const [isClick, setClick] = useState(false);
    const [text, setText] = useState("");
    const [rating, setRating] = useState(0);
    let refresh=1;
    useEffect(()=>{},[refresh])


    const handleRatingChange = (value) => {
        logger.info(`Rated: ${value}`);
        setRating(value);
      };
    const changeLiked = (clickVal)=>{
        setClick(clickVal)
        logger.info(user.message.token);
        axios.post(`${BackendUrl}/api/customer/add_liked`,{"isLiked":clickVal,"locationId":location.state.element.id},{headers: {"Authorization" : `Bearer ${user.message.token}`}}).then((res)=>{
            enqueueSnackbar(res.data.message,{variant:'info'})
        })
    }
  return (
    <div className='MainContainer'>
        <div className='TitleContainer'>        
            <div className='MainTitleContainer'>
                <div className='LocationTitle'>
                    {location.state.element.name}
                </div>
                <div className='MainRatingContainer'>
                    <div>
                        {"    "}
                    </div>
                    <Rating
                    emptySymbol={<FaStar color="grey" />}
                    fullSymbol={<FaStar color="gold" />} 
                    fractions={2} 
                    initialRating={review.otherReview==''?0:(review.otherReview.map(obj => obj.rating)
                    .reduce((accumulator, current) => accumulator + current, 0))/review.otherReview.length}
                    readonly
                    />
                </div>
            </div>
            <div className='HeartContainer'>
                <Heart isClick={isClick} onClick={() => changeLiked(!isClick)} />
            </div>
        </div>

        <hr className='HorizontalLine'></hr>
        <div className='TopContainer'>
            <div className='ImageContainer'>
                <img src={location.state.element.imgUrl} alt="" className='MainImg'/>
            </div>
            <div className='SideContainer'>
            <div className='SideTextContainer'>
                <div className='insideSideTextContainer'>
                    <div className='SideTextTitle'>Features</div>
                    <hr/>
                    <div className='Sub-text-container'><span className='Sub-title'>Opens at : </span>{location.state.element.opensAt}</div>
                    <div className='Sub-text-container'><span className='Sub-title'>Closes at : </span>{location.state.element.closesAt}</div>
                    <div className='Sub-text-container'><span className='Sub-title'>Approx Amount : </span>${location.state.element.approxPrice}</div>
                    <div className='Sub-text-container'><span className='Sub-title'>Suitable For : </span>{location.state.element.suitableFor}</div>
                    <div className='Sub-text-container'><span className='Sub-title'>Description : </span>{location.state.element.details}</div>
                </div>
            </div> 
            <div className='RatingContainer'>
                <div className='insideRatingContainer'>
                    <div className='SideTextTitle'>Reviews</div>
                        <hr/>
                        {
                            review.otherReview==''?"No reviews yet!": 
                            review.otherReview.map((obj)=>{
                                return <div className='Rating'>
                                <div className='Rating-Username'>User : {obj.username}</div>
                                <div className='Rating-Review'>Comments : {obj.review}</div>
                                <div className='Rating-Stars'>
                                    <Rating
                                        emptySymbol={<FaStar color="grey" />}
                                        fullSymbol={<FaStar color="gold" />} 
                                        fractions={1} 
                                        initialRating={obj.rating}
                                        onChange={handleRatingChange}
                                        readonly
                                    />
                            </div>
                        </div>
                            })
                        }
                        <hr/>
                        
                    </div></div> 
                {review.userReview==''?
                <div>
                <div className='addRatingContainer'>
                    <textarea className='textArea' value={text} onChange={e=>setText(e.target.value)} placeholder='Add a review'/>
                </div>    
                <div>
                <Rating
                    emptySymbol={<FaStar color="grey" />}
                    fullSymbol={<FaStar color="gold" />} 
                    fractions={1} 
                    initialRating={rating}
                    onChange={handleRatingChange}
                />
                </div>
                <div className='ButtonContainer'>
                    <button onClick={()=>{
                        axios.post(`${BackendUrl}/api/customer/add_review`,{"review":text,"locationId":location.state.element.id,"rating":rating},{headers: {"Authorization" : `Bearer ${user.message.token}`}}).then((res)=>{
                            enqueueSnackbar(res.data.message,{variant:'info'})
                            reviewFunc(user);
                        })
                        
                    }}>Add review</button>
                </div>
                </div>:"Review Added!"
                }
                
            </div>
        </div>

    </div>
  )
}

export default PlaceDetails;