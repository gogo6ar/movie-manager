package com.example.myspringproject.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

}
