import React from 'react'
import { useState } from 'react';
import { Link } from 'react-router-dom';
import './Login.css'
import {  useSnackbar } from 'notistack';
import { BackendUrl } from '../ip';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import logger from 'react-logger';


const Register = () => {
  let navigate=useNavigate()
  useEffect(()=>{
    if(localStorage.getItem('user')!==null){
      navigate("/home",{replace:true})
    }
  },[])

    const [object, setObject] = useState({username:'',password:'',firstname:'',lastname:''});
    const [confirmPassword, setConfirmPassword] = useState('');
    const {enqueueSnackbar}=useSnackbar()
  
    const handleUsernameChange = (event) => {
        setObject(prevState=>({...prevState,username:event.target.value}));
    };
    const handlePasswordChange = (event) => {
        setObject(prevState=>({...prevState,password:event.target.value}));
      };
    const handleFirstNameChange = (event) => {
        setObject(prevState=>({...prevState,firstname:event.target.value}));
    };
    const handleConfirmPasswordChange = (event) => {
      setConfirmPassword(event.target.value);
    };
    const handleLastNameChange = (event) => {
        setObject(prevState=>({...prevState,lastname:event.target.value}));
    };
  
    const checkPassword=(password,confirmPassword)=>{
      if(password==''){
        return {status:false,message:'Password is empty!'}
      }
      if(confirmPassword==''){
        return {status:false,message:'Confirm password is empty!'}
      }
      if(password.length<7){
        return {status:false,message:'Password length is less than 7'}
      }
      if(password!==confirmPassword){
        return {status:false,message:'Passwords are not matching!'}
      }
      return {status:true,message:'All good!'}
    }
    const handleSubmit = (event) => {
      event.preventDefault();
      let res=checkPassword(object.password,confirmPassword)
      if (res.status){
        axios.post(`${BackendUrl}/api/customer/register`,{...object}).then((res)=>{
          logger.info(res.data.message);
          enqueueSnackbar(res.data.message,{variant:"success"})
          navigate("/login")
        }).catch((err)=>{
          enqueueSnackbar(err.response.data.message,{variant:"error"})
        })
      }
      else{
        enqueueSnackbar(res.message,{variant:"error"})
      }
    };
  return (
    <div className='parent'>
        <div className="login-container">
    <form className="login-form" onSubmit={handleSubmit}>
      <h2>Register</h2>
      <label>
        
        <input type="username" value={object.username} onChange={handleUsernameChange} placeholder='Username'/>
      </label>
      <label>
        
        <input type="text" value={object.firstname} onChange={handleFirstNameChange} placeholder='First Name' />
      </label>
      <label>
 
        <input type="text" value={object.lastname} onChange={handleLastNameChange} placeholder='Last Name'/>
      </label>
      <label>
        <input type="password" value={object.password} onChange={handlePasswordChange} placeholder='Password' />
      </label>
      <label>
        <input type="password" value={confirmPassword} onChange={handleConfirmPasswordChange} placeholder='Confirm Password' />
      </label>
      <button type="submit">Register</button>
      <div className='MessageTextClass'>Already have an account? <Link to="/login" className='LinkTextClass'>Login!</Link> </div>
      
    </form>
  </div>
  </div>
  )
}

export default Register