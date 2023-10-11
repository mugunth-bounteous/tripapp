
import './App.css';
// import Login from './components/Login';
import Login from './components/Login'
import Register from './components/Register';
import { useRoutes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './components/Home.js';
import PlaceDetails from './components/PlaceDetails';
import LikedPage from './components/LikedPage';


function App() {
  let ret=useRoutes([
    {path:"/",element:<Login/>},
    {path:"login",element:<Login/>},
    {path:"register",element:<Register/>},
    {element:<Navbar/>,
    children:[
     {path:"home",element:<Home/>},
     {path:"details",element:<PlaceDetails/>},
     {path:"favorites",element:<LikedPage/>}
    ]
  }
  ])
  return ret;
}

export default App;
