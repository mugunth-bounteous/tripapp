import React from 'react'
import { Link } from 'react-router-dom';
import { useState } from 'react';
import './Login.css'
import axios from 'axios';
import {BackendUrl} from '../ip';
import { useNavigate } from 'react-router-dom';
import { useSnackbar } from 'notistack';
import { useEffect } from 'react';

const Login = () => {
  const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();
  useEffect(()=>{
    if(localStorage.getItem('user')!==null){
      navigate("/home",{replace:true})
    }
  },[])

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
  
    const handleUsernameChange = (event) => {
      setUsername(event.target.value);
    };
  
    const handlePasswordChange = (event) => {
      setPassword(event.target.value);
    };
  
    const handleSubmit = (event) => {
      event.preventDefault();
      axios.post(`${BackendUrl}/api/customer/login`,{username,password}).then((res)=>{
        window.localStorage.setItem("user",JSON.stringify(res.data))
        navigate("/home",{replace:true})
        console.log(res.data)
       }).catch((err)=>{
        console.log(err);
        enqueueSnackbar(err.response.data.message,{variant:"error"});
       })
    };
  return (
    <div className='parent'>
    <div className="login-container">
    <form className="login-form" onSubmit={handleSubmit}>
        <>
      <h2>Login</h2>
      <label>
        <input type="username" value={username} onChange={handleUsernameChange} placeholder='Username'/>
      </label>
      <label>
        <input type="password" value={password} onChange={handlePasswordChange} placeholder='Password'/>
      </label>
      <button type="submit">Login</button>
      <div className='MessageTextClass'>Don't have an account? <Link to="/register" className='LinkTextClass'>Register!</Link> </div>
      </>
    </form>
  </div>
  </div>
  )
}

export default Login