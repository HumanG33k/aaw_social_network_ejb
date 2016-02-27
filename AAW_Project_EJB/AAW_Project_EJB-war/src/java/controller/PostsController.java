/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PostsEntity;
import dao.UsersEntity;
import java.util.Collections;
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
import service.PostsServiceLocal;
import service.UsersServiceLocal;

/**
 *
 * @author Nathanael Villemin
 */
@Controller
public class PostsController {
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/PostsService")
    PostsServiceLocal postsService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/UsersService")
    UsersServiceLocal usersService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/NotificationsService")
    NotificationsServiceLocal notifsService;

    // Method used to show all the posts related to the user
    @RequestMapping(value="home", method=RequestMethod.GET)
    public ModelAndView handleHome(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }

        session.setAttribute("currentPage", "/home.htm");
        
        // Get all the posts sent to this user
        Long sessionUserId = (Long)session.getAttribute("userId");
        UsersEntity user = this.usersService.findById(sessionUserId);
        List<PostsEntity> posts = user.getTargetPosts();
        
        // Add the posts sent by this user
        for(PostsEntity post : user.getSenderPosts()) {
            if(!posts.contains(post)) {
                posts.add(post);
            }
        }
        
        // Add the posts sent by friends
        for(UsersEntity friend : user.getFriends()) {
            for(PostsEntity post : friend.getSenderPosts()) {
                if(!posts.contains(post)) {
                    posts.add(post);
                }
            }
        }
        
        Collections.sort(posts, Collections.reverseOrder());
        
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("currentUser", user);
        mv.addObject("posts", posts);
        mv.addObject("nbNotifs", user.getTargetNotifs().size());
        
        return mv;
    }
    
    // Method used to handle the creation of a new post
    @RequestMapping(value="{userId}/createPost", method=RequestMethod.POST, params="postContent")
    public ModelAndView handleAddPost(HttpServletRequest request, @PathVariable Long userId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        String content = request.getParameter("postContent");
        
        if(!content.isEmpty()) {
            Long sessionUserId = (Long)session.getAttribute("userId");
            UsersEntity sender = this.usersService.findById(sessionUserId);
            UsersEntity target = this.usersService.findById(userId);
            this.postsService.add(content, sender, target);
        }
        
        return new ModelAndView("redirect:" + session.getAttribute("currentPage"));
    }
    
    // Method used to handle the removal of new post
    @RequestMapping(value="{postId}/removePost", method=RequestMethod.GET)
    public ModelAndView handleRemovePost(HttpServletRequest request, @PathVariable Long postId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        PostsEntity post = this.postsService.findById(postId);
        if(post != null) {
            this.postsService.remove(post);
        }

        return new ModelAndView("redirect:" + session.getAttribute("currentPage"));
    }
}
