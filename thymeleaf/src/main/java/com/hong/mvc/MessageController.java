package com.hong.mvc;

import com.hong.Message;
import com.hong.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Hongwei on 2016/1/10.
 */
@Controller
@RequestMapping("/")
public class MessageController {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RequestMapping
    public ModelAndView findList() {
        Iterable<Message> messages = this.messageRepository.findAll();
        return new ModelAndView("message/list", "messages", messages);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute Message message) {
        return "message/form";
    }
}
