package com.chatbot.chatbot;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/qna")

//@CrossOrigin(origins = "http://localhost:5173")
public class AIcontroller {
    @Autowired
    private QnaService qnaService;
    @PostMapping("/ask")
public ResponseEntity<String> askQuestion(@RequestBody Map<String,String> payload){
        String question=payload.get("question");
        String answer =qnaService.getAnswer(question);
        return ResponseEntity.ok(answer);

    }
}
