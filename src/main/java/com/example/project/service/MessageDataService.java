package com.example.project.service;

import com.example.project.model.Message;
import com.example.project.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageDataService {

    @Autowired
    MessageRepository messageRepository;

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public List<Message> findByTag(String tag) {
        return messageRepository.findByTag(tag);
    }

    public void save(Message message) {
        messageRepository.save(message);
    }
}
