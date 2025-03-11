import React from "react";
import { Route,Routes } from "react-router";
import App from "../App";
import ChatPage from "../pages/ChatPage";
import ChatPageComponent from "../components/ChatPageComponent";
const AppRoutes=()=>{
    return(
        <Routes>
        <Route path='/' element={<App/>} />
        <Route path='/chat' element={<ChatPageComponent/>} />
        <Route path='/about' element={<h1 className="dark:text-white"> about Page</h1>} />
        <Route path="*" element={
            <div className="flex w-full h-screen justify-center items-center">
            <h1 className="text-red-500 text-6xl">404 Page Not Found</h1>
          </div>
          
            } />
       </Routes>    
    )
}
export default AppRoutes