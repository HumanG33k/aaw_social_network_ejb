/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.NotificationsEntity;
import dao.UsersEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.NotificationsServiceLocal;
import service.UsersServiceLocal;
import serviceComposite.UsersServiceCompositeLocal;

/**
 *
 * @author Nathanael Villemin
 */
@Controller
public class NotificationsController {
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/NotificationsService")
    NotificationsServiceLocal notifsService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/UsersService")
    UsersServiceLocal usersService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/UsersServiceComposite")
    UsersServiceCompositeLocal usersServiceComposite;
    
    // Method used to show all the posts related to the user
    @RequestMapping(value="notifications", method=RequestMethod.GET)
    public ModelAndView handleNotifs(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }

        session.setAttribute("currentPage", "/notifications.htm");
        
        // Get all the notifications sent to this user
        Long sessionUserId = (Long)session.getAttribute("userId");
        UsersEntity user = this.usersService.findById(sessionUserId);
        List<NotificationsEntity> notifs = user.getTargetNotifs();
        ModelAndView mv = new ModelAndView("notifications");
        mv.addObject("currentUser", user);
        mv.addObject("notifs", notifs);
        mv.addObject("nbNotifs", notifs.size());
        
        return mv;
    }
    
    // Method used to send a friend request
    @RequestMapping(value="{userId}/sendRequest", method=RequestMethod.GET)
    public ModelAndView handleSendRequest(HttpServletRequest request, @PathVariable Long userId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        Long sessionUserId = (Long)session.getAttribute("userId");
        UsersEntity user = this.usersService.findById(sessionUserId);
        UsersEntity targetUser = this.usersService.findById(userId);
        
        this.notifsService.add(user, targetUser);

        return new ModelAndView("redirect:profile.htm");
    }
    
    // Method used to accept a friend request
    @RequestMapping(value="{notifId}/acceptFriend", method=RequestMethod.GET)
    public ModelAndView handleAcceptFriend(HttpServletRequest request, @PathVariable Long notifId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        Long sessionUserId = (Long)session.getAttribute("userId");
        UsersEntity user = this.usersService.findById(sessionUserId);
        NotificationsEntity notif = this.notifsService.findById(notifId);
        if(user.equals(notif.getTarget()) && this.usersServiceComposite.addFriendship(notif.getSender(), notif.getTarget())) {
            this.notifsService.remove(notif);
        }

        ModelAndView mv = new ModelAndView("redirect:" + session.getAttribute("currentPage"));
        mv.addObject("currentUser", user);
        
        return mv;
    }
    
    // Method used to deny a friend request
    @RequestMapping(value="{notifId}/denyFriend", method=RequestMethod.GET)
    public ModelAndView handleDenyFriend(HttpServletRequest request, @PathVariable Long notifId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }

        NotificationsEntity notif = this.notifsService.findById(notifId);
        if(notif != null) {
            this.notifsService.remove(notif);
        }
        
        return new ModelAndView("redirect:/notifications.htm");
    }
}
