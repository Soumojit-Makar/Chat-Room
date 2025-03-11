import { HttpClient } from "../config/axios.helper"

export const createRoomService=async(roomDetail)=>{
    return await HttpClient.post(`/api/chat-app`,roomDetail,{
        headers:{
            'Content-Type': 'text/plain'
        }
    });
}
export const joinChatApi=async(roomId)=>{
    return await  HttpClient.get(`/api/chat-app/${roomId}`)
}
export const getMessages=async(roomId,size=50,page=0)=>{
    return await  HttpClient.get(`/api/chat-app/${roomId}/messages?size=${size}&page=${page}`)
}