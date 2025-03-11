import { useState, useRef, useEffect } from 'react';
import { IoMdSend } from 'react-icons/io';
import { MdAttachFile } from 'react-icons/md';
import { useChatContext } from '../context/ChatContext';
import { useNavigate } from 'react-router';
import SockJS from 'sockjs-client';
import { BASEURL } from '../config/axios.helper';
import { Stomp } from '@stomp/stompjs';
import toast from 'react-hot-toast';
import { getMessages } from '../services/room.service';
import { timeAgo } from '../config/helper';

const ChatPageComponent = () => {
  const { roomId, userName, connected, setRoomId, setUserName, setConnected } = useChatContext();
  const navigate = useNavigate();
  useEffect(() => {
    if (!connected) {
      navigate('/');
    }
  }, [connected, userName, roomId]);

  const [messages, setMessages] = useState([]);
  const [inputValue, setInputValue] = useState('');
  const inputRef = useRef(null);
  const chatBoxRef = useRef(null);
  const [stompClient, setStompClient] = useState(null);

  useEffect(() => {
    const loadMessages = async () => {
      try {
        const response = await getMessages(roomId);
        setMessages(response.data);
        toast.success('Messages loaded successfully!');
      } catch (error) {
        console.error(error);
        toast.error('Failed to load messages.');
      }
    };

    if (connected && roomId) {
      loadMessages();
    }
  }, [connected, roomId]);

  useEffect(() => {
    const connectWebSocket = () => {
      const socket = new SockJS(`${BASEURL}/chat-app`);
      const client = Stomp.over(socket);

      client.connect({}, () => {
        setStompClient(client);
        toast.success('Connected to the chat room.');
        
        client.subscribe(`/topic/room/${roomId}`, (message) => {
          const newMessage = JSON.parse(message.body);
          setMessages((prevMessages) => {
            const isDuplicate = prevMessages.some(msg => msg.timestamp === newMessage.timestamp);
            if (!isDuplicate) {
              return [...prevMessages, newMessage];
            }
            return prevMessages;
          });
        });
      }, (error) => {
        console.error('WebSocket connection failed', error);
        toast.error('WebSocket connection failed. Please try again later.');
        setConnected(false);
      });
    };

    if (connected && roomId) {
      connectWebSocket();
    }

    return () => {
      if (!connected) {
        stompClient.disconnect();
      }
    };
  }, [connected, roomId]);

  useEffect(() => {
    if (chatBoxRef.current) {
      chatBoxRef.current.scroll({
        top: chatBoxRef.current.scrollHeight,
        behavior: 'smooth',
      });
    }
  }, [messages]);

  // Send message function
  const sendMessage = () => {
    if (stompClient && connected && inputValue.trim()) {
      const message = {
        sender: userName,
        content: inputValue,
        roomId: roomId,
      };
      stompClient.send(`/app/sendMessage/${roomId}`, {}, JSON.stringify(message));
      setInputValue('');
    }
  };

  return (
    <div className="min-h-screen dark:text-white w-full relative">
      {/* Header Section */}
      <header className="fixed top-0 left-0 right-0 flex flex-wrap justify-between items-center border-b dark:border-gray-700 px-10 py-5 shadow-md bg-white dark:bg-gray-900 z-10">
        <div>
          <h1 className="text-xl font-semibold">
            Room: <span className="font-normal">{roomId}</span>
          </h1>
        </div>
        <div>
          <h1 className="text-xl font-semibold">
            User: <span className="font-normal">{userName}</span>
          </h1>
        </div>
        <div>
          <button
            onClick={() => {
              stompClient.disconnect();
              setConnected(false);
              setUserName('');
              setRoomId('');
            }}
            className="dark:bg-red-500 dark:hover:bg-red-600 text-white px-4 py-2 rounded-md transition duration-200"
          >
            Leave Room
          </button>
        </div>
      </header>

      {/* Chat Messages Area */}
      <main ref={chatBoxRef} className="pt-20 h-screen py-10 overflow-auto w-full md:w-2/3 dark:bg-slate-600 mx-auto">
        <div className="massage-container flex flex-col p-2 gap-2">
          {messages.map((message, index) => (
            <div key={index} className={`flex ${message.sender === userName ? 'justify-end' : 'justify-start'}`}>
              <div className={`${message.sender === userName ? 'bg-blue-600' : 'bg-gray-800'} my-2 rounded-2xl p-2 max-w-max w-1/2`}>
                <div className="flex flex-row gap-3 items-center">
                  <img
                    className="h-10 w-10 rounded-full m-2"
                    src={`https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairDreads01&accessoriesType=Prescription01&hairColor=Brown&facialHairType=Blank&clotheType=Hoodie&clotheColor=PastelYellow&eyeType=Default&eyebrowType=Default&mouthType=Default&skinColor=Light`}
                    alt="avatar"
                  />
                  <div className="flex flex-col text-start m-1">
                    <p className="font-bold text-lg md:text-xl">{message.sender}</p>
                    <p>{message.content}</p>
                    <p className="text-xs text-gray-400">{timeAgo(message.timestamp)}</p>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </main>

      {/* Footer Section */}
      <footer className="fixed bottom-4 md:left-96 md:right-96 left-10 right-10 w-auto border-t dark:border-gray-700 p-4 bg-white dark:bg-gray-900 z-10 rounded-full items-center justify-around">
        <div className="flex items-center justify-between gap-3">
          <input
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            onKeyDown={(event) => {
              if (event.key === 'Enter') {
                sendMessage();
              }
            }}
            type="text"
            placeholder="Type your message..."
            className="w-full h-full px-4 py-2 border dark:border-gray-600 rounded-md dark:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500 dark:text-white"
          />
          {/* <button className="px-2 py-2 bg-purple-500 hover:bg-purple-600 text-white rounded-full transition duration-200">
            <MdAttachFile size={25} />
          </button> */}
          <button onClick={sendMessage} className="px-2 py-2 bg-blue-500 hover:bg-blue-600 text-white rounded-full transition duration-200">
            <IoMdSend size={25} />
          </button>
        </div>
      </footer>
    </div>
  );
};

export default ChatPageComponent;
