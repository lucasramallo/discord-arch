import React, {useRef, useState, useEffect} from 'react'
import {Button, Input} from '@mui/material'
import io from 'socket.io-client'

export default function Chat() {
  const bottomRef = useRef()
  const messageRef = useRef()
  const [messageList, setMessageList] = useState([])


  const socket = io.connect('http://localhost:3001')

  useEffect(()=>{
    socket.on('receive_message', data => {
      setMessageList((current) => [...current, data])
    })

    return () => socket.off('receive_message')
  }, [socket])

  useEffect(()=>{
    scrollDown()
  }, [messageList])

  const handleSubmit = () => {
    const message = messageRef.current.value
    if(!message.trim()) return
    console.log("kkkk")
    socket.emit('message', message)
    clearInput()
    focusInput()
  }

  const clearInput = () => {
    messageRef.current.value = ''
  }

  const focusInput = () => {
    messageRef.current.focus()
  }

  const getEnterKey = (e) => {
    if(e.key === 'Enter')
      handleSubmit()
  }

  const scrollDown = () => {
    bottomRef.current.scrollIntoView({behavior: 'smooth'})
  }

  return (
    <div>
      <div>
        {
          messageList.map((message,index) => (
            <div key={index}>
              <div className="message-author"><strong>{message.author}</strong></div>
              <div className="message-text">{message.text}</div>
            </div>
          ))
        }
        <div ref={bottomRef} />
        <div>
          <Input inputRef={messageRef} placeholder='Mensagem' onKeyDown={(e)=>getEnterKey(e)} fullWidth />
          <Button onClick={()=>handleSubmit()} variant="contained" size="large">Enviar</Button>
        </div>
      </div>
    </div>
  )
}