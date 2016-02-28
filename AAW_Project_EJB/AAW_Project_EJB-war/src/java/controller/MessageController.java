/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.MessageEntity;
import dao.UserEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.MessageServiceLocal;
import service.UserServiceLocal;
import serviceComposite.MessageServiceCompositeLocal;

/**
 *
 * @author Nathanael Villemin
 */
@Controller
public class MessageController {
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/MessageService")
    MessageServiceLocal messageService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/UserService")
    UserServiceLocal userService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/MessageServiceComposite")
    MessageServiceCompositeLocal messageServiceComposite;

    // Method used to show all the messages related to the user
    @RequestMapping(value="{userId}/messages", method=RequestMethod.GET)
    public ModelAndView handleMessages(HttpServletRequest request, @PathVariable Long userId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }

        session.setAttribute("currentPage", "/" + userId.toString() + "/messages.htm");
        
        Long sessionUserId = (Long)session.getAttribute("userId");
        UserEntity user = this.userService.findById(sessionUserId);
        ModelAndView mv = new ModelAndView("messages");
        mv.addObject("currentUser", user);
        mv.addObject("nbNotifs", user.getTargetNotifs().size());
        
        if(userId == 0) {
            mv.addObject("showFriends", true);
            List<UserEntity> friends = user.getFriends();
            mv.addObject("friends", friends);
            HashMap<UserEntity, Integer> nbUnreadMessages = new HashMap<>();
            for(UserEntity friend : friends) {
                nbUnreadMessages.put(friend, this.messageServiceComposite.getNumberUnreadMessagesFromSender(friend, user));
            }
            mv.addObject("nbUnreadMessages", nbUnreadMessages);
        } else {
            UserEntity friend = this.userService.findById(userId);
            List<MessageEntity> messages = this.messageService.searchBySenderTarget(user, friend);
            messages.addAll(this.messageService.searchBySenderTarget(friend, user));
            
            for(MessageEntity message : messages) {
                this.messageServiceComposite.readMessage(message, user);
            }
            
            // TODO: Fix this, doesn't sort
            Collections.sort(messages, new Comparator<MessageEntity>() {
                @Override
                public int compare(MessageEntity m1, MessageEntity m2) {
                    return -(m1.getDate().compareTo(m2.getDate()));
                }
            });
            
            mv.addObject("showFriends", false);
            mv.addObject("friend", friend);
            mv.addObject("messages", messages);
        }
        
        mv.addObject("nbMessages", this.messageServiceComposite.getNumberUnreadMessages(user));
        
        return mv;
    }
    
    // Method used to handle the creation of a new message
    @RequestMapping(value="{userId}/createMessage", method=RequestMethod.POST, params="messageContent")
    public ModelAndView handleAddMessage(HttpServletRequest request, @PathVariable Long userId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        String content = request.getParameter("messageContent");
        
        if(!content.isEmpty()) {
            Long sessionUserId = (Long)session.getAttribute("userId");
            UserEntity sender = this.userService.findById(sessionUserId);
            UserEntity target = this.userService.findById(userId);
            this.messageService.add(content, sender, target);
        }
        
        return new ModelAndView("redirect:" + session.getAttribute("currentPage"));
    }
    
    // Method used to handle the removal of new message
    @RequestMapping(value="{messageId}/removeMessage", method=RequestMethod.GET)
    public ModelAndView handleRemoveMessage(HttpServletRequest request, @PathVariable Long messageId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        MessageEntity message = this.messageService.findById(messageId);
        if(message != null) {
            this.messageService.remove(message);
        }

        return new ModelAndView("redirect:" + session.getAttribute("currentPage"));
    }
}
