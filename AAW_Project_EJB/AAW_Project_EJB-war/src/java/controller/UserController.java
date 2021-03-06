package controller;

import common.Enums.SignInResult;
import dao.NotificationEntity;
import dao.PostEntity;
import dao.UserEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.FileServiceLocal;
import service.NotificationServiceLocal;
import service.PostServiceLocal;
import service.UserServiceLocal;
import serviceComposite.MessageServiceCompositeLocal;
import serviceComposite.UserServiceCompositeLocal;

/**
 *
 * @author Nathanael Villemin
 */
@Controller
public class UserController {
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/UserService")
    UserServiceLocal userService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/UserServiceComposite")
    UserServiceCompositeLocal userServiceComposite;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/NotificationService")
    NotificationServiceLocal notifService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/PostService")
    PostServiceLocal postService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/FileService")
    FileServiceLocal fileService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/MessageServiceComposite")
    MessageServiceCompositeLocal messageServiceComposite;

    // Method used to display the index page
    @RequestMapping(value="index", method=RequestMethod.GET)
    public ModelAndView handleIndex(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("indexMessage", "Please sign up or sign in if you already have an account.");
        return mv;
    }
    
    // Method used to handle the sign up of a new user on the index page
    @RequestMapping(value="signUp", method=RequestMethod.POST)
    public ModelAndView handleSignUp(HttpServletRequest request) {
        // Get the values that the user sent
        String name = request.getParameter("nameSignUp");
        String email = request.getParameter("emailSignUp");
        String password = request.getParameter("passwordSignUp");
        
        ModelAndView mv = new ModelAndView("index");
        
        if(name.isEmpty()) {
            mv.addObject("indexMessage", "Error: Your name cannot be empty.");
        } else if(password.length() < 8) {
            mv.addObject("indexMessage", "Error: You password must have at least 8 characters.");
        } else {
            boolean success = this.userService.add(name, email, password);
            if(success) {
                mv.addObject("indexMessage", "You have successfully signed up to the social network. You can now sign in.");
            } else {
                mv.addObject("indexMessage", "Error: This name is already used.");
            }
        }
        
        return mv;
    }
    
    // Method used to handle the sign in of an existing user from the index page
    @RequestMapping(value="signIn", method=RequestMethod.POST, params={"nameSignIn", "passwordSignIn"})
    public ModelAndView handleSignIn(HttpServletRequest request) {
        // Get the values that the user sent
        String name = request.getParameter("nameSignIn");
        String password = request.getParameter("passwordSignIn");
        
        ModelAndView mv;

        SignInResult result = this.userServiceComposite.checkSignIn(name, password);
        if(result != SignInResult.SUCCESS) {
            mv = new ModelAndView("index");
            if(result == SignInResult.WRONG_USER) {
                mv.addObject("indexMessage", "Error: This user doesn't exist.");
            } else if(result == SignInResult.WRONG_PASSWORD) {
                mv.addObject("indexMessage", "Error: Incorrect password.");
            }
        } else {
            mv = new ModelAndView("redirect:home.htm");
            
            // Creating the session of the user
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", this.userService.findByName(name).getId());
            session.setMaxInactiveInterval(600); // Inactive after 10 minutes
        }
        
        return mv;
    }
    
    // Method used to handle the sign out of a user
    @RequestMapping(value="signOut", method=RequestMethod.GET)
    public ModelAndView handleSignOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:index.htm");
    }
    
    // Method used to show the user friends
    @RequestMapping(value="friends", method=RequestMethod.GET)
    public ModelAndView handleFriends(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        session.setAttribute("currentPage", "/friends.htm");
        
        Long sessionUserId = (Long)session.getAttribute("userId");
        UserEntity user = this.userService.findById(sessionUserId);
        ModelAndView mv = new ModelAndView("friends");
        mv.addObject("currentUser", user);
        mv.addObject("friends", user.getFriends());
        mv.addObject("nbNotifs", user.getTargetNotifs().size());
        mv.addObject("nbMessages", this.messageServiceComposite.getNumberUnreadMessages(user));
        
        return mv;
    }
    
    // Method used to handle the search of users
    @RequestMapping(value="search", method=RequestMethod.POST)
    public ModelAndView handleSearch(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        session.setAttribute("currentPage", "/search.htm");
        
        String searchName = request.getParameter("searchName");
        List<UserEntity> users = this.userService.searchByName(searchName);
        ModelAndView mv = new ModelAndView("search");
        Long sessionUserId = (Long)session.getAttribute("userId");
        UserEntity user = this.userService.findById(sessionUserId);
        mv.addObject("currentUser", user);
        mv.addObject("users", users);
        mv.addObject("nbNotifs", user.getTargetNotifs().size());
        mv.addObject("nbMessages", this.messageServiceComposite.getNumberUnreadMessages(user));

        return mv;
    }
    
    // Method used to show the profile of a user
    @RequestMapping(value="{userId}/profile", method=RequestMethod.GET)
    public ModelAndView handleProfile(HttpServletRequest request, @PathVariable Long userId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        session.setAttribute("currentPage", "/" + userId.toString() + "/profile.htm");
        
        Long sessionUserId = (Long)session.getAttribute("userId");
        UserEntity user = this.userService.findById(sessionUserId);
        UserEntity targetUser = this.userService.findById(userId);
        
        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("currentUser", user);
        mv.addObject("user", targetUser);
        mv.addObject("nbNotifs", user.getTargetNotifs().size());
        mv.addObject("nbMessages", this.messageServiceComposite.getNumberUnreadMessages(user));
        mv.addObject("files", user.getFiles());
        boolean isMyProfile = user.equals(targetUser);
        mv.addObject("myProfile", isMyProfile);  
        boolean isMyFriend = this.userServiceComposite.checkFriendship(user, targetUser);
        mv.addObject("myFriend", isMyFriend);
        mv.addObject("messages", false);
        
        if(!isMyProfile && !isMyFriend) {
            NotificationEntity sentRequest = this.notifService.searchBySenderTarget(user, targetUser);
            boolean requestSent = (sentRequest != null);
            mv.addObject("requestSent", requestSent);
            if(!requestSent) {
                NotificationEntity receivedRequest = this.notifService.searchBySenderTarget(targetUser, user);
                boolean requestReceived = (receivedRequest != null);
                mv.addObject("requestReceived", requestReceived);
                if(requestReceived) {
                    mv.addObject("notifId", receivedRequest.getId());
                }
            }
        }

        List<PostEntity> posts = targetUser.getTargetPosts();
        
        // Add the posts sent by this user
        for(PostEntity post : targetUser.getSenderPosts()) {
            if(!posts.contains(post)) {
                posts.add(post);
            }
        }
        
        List<PostEntity> tempPosts = new ArrayList<>();
        tempPosts.addAll(posts);
        Collections.sort(tempPosts, Collections.reverseOrder());
        
        mv.addObject("posts", tempPosts);
        
        return mv;
    }
    
    // Method used to change the user info
    @RequestMapping(value="{userId}/userInfo", method=RequestMethod.POST)
    public ModelAndView handleUserInfo(HttpServletRequest request, @PathVariable Long userId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        Long sessionUserId = (Long)session.getAttribute("userId");
        UserEntity user = this.userService.findById(sessionUserId);
        String newInfo = request.getParameter("infoInput");
        if(!newInfo.isEmpty()) {
            this.userServiceComposite.updateInfo(user, newInfo);
        }

        return new ModelAndView("redirect:profile.htm");
    }
    
    // Method used to change the user info
    @RequestMapping(value="{userId}/removeFriend", method=RequestMethod.GET)
    public ModelAndView handleRemoveFriend(HttpServletRequest request, @PathVariable Long userId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        Long sessionUserId = (Long)session.getAttribute("userId");
        UserEntity user = this.userService.findById(sessionUserId);
        UserEntity targetUser = this.userService.findById(userId);
        
        if(this.userServiceComposite.checkFriendship(user, targetUser)) {
            this.userServiceComposite.removeFriendship(user, targetUser);
        }

        return new ModelAndView("redirect:profile.htm");
    }
    
    @RequestMapping(value = "updateProfilePicture", method = RequestMethod.POST)
    public ModelAndView handleProfilePicture(HttpServletRequest request, @RequestParam("profilePicture") MultipartFile file) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }

        Long sessionUserId = (Long)session.getAttribute("userId");
        if (!file.isEmpty() && file.getContentType().startsWith("image")) {
            UserEntity user = this.userService.findById(sessionUserId);
            this.userServiceComposite.updateProfilePicture(user, file);
        }

        return new ModelAndView("redirect:/" + sessionUserId + "/profile.htm");
    }
}