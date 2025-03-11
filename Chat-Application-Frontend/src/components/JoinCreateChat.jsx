import React, { useState } from "react";
import toast from "react-hot-toast";
import { IoIosChatboxes } from "react-icons/io";
import { createRoomService, joinChatApi } from "../services/room.service";
import { useChatContext } from "../context/ChatContext";
import { useNavigate } from "react-router";

const JoinCreateChat = () => {
    const {roomId, userName, connected,setRoomId, setUserName,setConnected}=useChatContext();
    const navigate= useNavigate();
    const [detail,setDetail]=useState({
        roomId:"",
        userName:"",
    });
    function handelFormInputChange(event){
        setDetail({
            ...detail,
            [event.target.name]:event.target.value,
        });
    }
    function valideteForm(){
        if(detail.roomId==='' || detail.userName===''){
            toast.error("Invalid Input !! ");
            return false;
        }

        return true;
    }
    function joinChat(){
        if (valideteForm()) {
            console.log(detail)
            joinChatApi(detail.roomId)
            .then((response)=>{
                toast.success("Joining...")
                setUserName(detail.userName);
                setRoomId(response.data.roomId);
                setConnected(true);
                navigate("/chat")
            })
            .catch((error)=>{
                toast.error(error.response.data)
            })
        }
    }
    function createRoom(){
        if (valideteForm()) {
            console.log(detail)
            
            createRoomService(detail.roomId)
            .then((response)=>{
                // console.log(response)
                toast.success("Room is Created Sucessfully !!")
                setUserName(detail.userName);
                setRoomId(response.data.roomId);
                setConnected(true);
                navigate("/chat")

            })
            .catch((error)=>{
                    toast.error(error.response.data)
            })


           
        }
    }
    return ( 
       <div className="min-h-screen flex items-center justify-center">

            <div className="p-10 border dark:border-gray-400 w-full flex flex-col gap-5 max-w-md rounded   dark:bg-gray-900 shadow">
             <div className="justify-center items-center w-full flex">
             <IoIosChatboxes 
             size={100} />
             </div>
                <h1 className="text-2xl font-semibold text-center">Join Room | Create Room ...</h1>
                <div>

                    <label htmlFor="name" className="block font-medium mb-2">
                        Your Name
                    </label>
                    <input 
                    type="text"
                    id="name"
                    onChange={handelFormInputChange}
                    value={detail.userName}
                    name="userName"
                    placeholder="Enter Your name"
                    className="w-full dark:bg-gray-600 px-4 py-2 border dark:border-gray-400 rounded-lg focus:outline-none  focus:ring-2 focus:ring-blue-500 seou"
                    />
                    <label htmlFor="room-id" className="block font-medium mb-2 mt-2">
                        Room Id / New Room Id
                    </label>
                    <input 
                    type="text"
                    id="room-id"
                    onChange={handelFormInputChange}
                    value={detail.roomId}
                    name="roomId"
                    placeholder="Enter Room Id"
                    className="w-full dark:bg-gray-600 px-4 py-2 border dark:border-gray-400 rounded-lg focus:outline-none  focus:ring-2 focus:ring-blue-500 seou"
                    />
                    <div className="mt-2 py-2  justify-center items-center gap-5 flex " > 
                        <button className="px-2 py-2 dark:bg-blue-500 hover:dark:bg-blue-700 rounded-full" onClick={joinChat}>Join Room </button>
                   
                        <button className="px-2 py-2 dark:bg-orange-500 hover:dark:bg-orange-700 rounded-full" onClick={createRoom}>Create Room </button>
                    </div>
                </div>
            </div>

       </div> 
     );
}
 
export default JoinCreateChat;