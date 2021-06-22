package com.example.myspringproject.web.controller;

import com.example.myspringproject.service.ChatMessageService;
import com.example.myspringproject.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
     private final SimpMessagingTemplate messagingTemplate;
     private final ChatMessageService chatMessageService;
     private final ChatRoomService chatRoomService;

//     @MessageMapping("/chat")
//     public void processMessage(@Payload ChatMessage chatMessage) {
//          var chatId = chatRoomService
//                  .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
//          chatMessage.setChatId(chatId.get());
//
//          ChatMessage saved = chatMessageService.save(chatMessage);
//
//          messagingTemplate.convertAndSendToUser(
//                  chatMessage.getRecipientId(),"/queue/messages",
//                  new ChatNotification(
//                          saved.getId().toString(),
//                          saved.getSenderId(),
//                          saved.getSenderName()));
//     }
}
