package controller;

import dao.FileEntity;
import dao.UserEntity;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.FileServiceLocal;
import service.UserServiceLocal;
import serviceComposite.UserServiceCompositeLocal;
import serviceComposite.MessageServiceCompositeLocal;

/**
 *
 * @author Nathanael Villemin
 */
@Controller
public class FileController {
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/FileService")
    FileServiceLocal fileService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/UserService")
    UserServiceLocal userService;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/UserServiceComposite")
    UserServiceCompositeLocal userServiceComposite;
    @EJB(mappedName="java:global/AAW_Project_EJB/AAW_Project_EJB-ejb/MessageServiceComposite")
    MessageServiceCompositeLocal messageServiceComposite;

    // Method used to display the files page
    @RequestMapping(value = "{userId}/files", method = RequestMethod.GET)
    public ModelAndView handleFiles(HttpServletRequest request, @PathVariable Long userId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        Long sessionUserId = (Long)session.getAttribute("userId");
        UserEntity user = this.userService.findById(sessionUserId);
        ModelAndView mv = new ModelAndView("files");
        mv.addObject("currentUser", user);
        mv.addObject("nbNotifs", user.getTargetNotifs().size());
        mv.addObject("nbMessages", this.messageServiceComposite.getNumberUnreadMessages(user));
        mv.addObject("files", user.getFiles());
        mv.addObject("uploadMessage", "Please select a file to upload.");
        
        return mv;
    }

    @RequestMapping(value = "{userId}/uploadFile", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(HttpServletRequest request, @RequestParam("fileToUpload") MultipartFile file, @PathVariable Long userId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }

        Long sessionUserId = (Long)session.getAttribute("userId");
        UserEntity user = this.userService.findById(sessionUserId);
        ModelAndView mv = new ModelAndView("files");
        mv.addObject("currentUser", user);
        mv.addObject("nbNotifs", user.getTargetNotifs().size());
        mv.addObject("nbMessages", this.messageServiceComposite.getNumberUnreadMessages(user));
        
        if (!file.isEmpty()) {
            String name = file.getOriginalFilename();
            String type = file.getContentType();
            try {
                byte[] content = file.getBytes();
                this.fileService.add(name, type, content, user);
                mv.addObject("uploadMessage", "Your file " + name + " has been successfully uploaded.");
            } catch (Exception e) {
                mv.addObject("uploadMessage", e.getMessage());
            }
        } else {
            mv.addObject("uploadMessage", "Error: Your file is empty.");
        }
        
        mv.addObject("files", user.getFiles());
        
        return mv;
    }
    
    @RequestMapping(value = "{fileId}/removeFile", method = RequestMethod.GET)
    public ModelAndView handleFileRemove(HttpServletRequest request, @PathVariable Long fileId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }

        Long sessionUserId = (Long)session.getAttribute("userId");
        UserEntity user = this.userService.findById(sessionUserId);
        ModelAndView mv = new ModelAndView("files");
        mv.addObject("currentUser", user);
        mv.addObject("nbNotifs", user.getTargetNotifs().size());
        mv.addObject("nbMessages", this.messageServiceComposite.getNumberUnreadMessages(user));
        
        FileEntity file = this.fileService.findById(fileId);
        String fileName = file.getName();
        this.fileService.remove(file);
        user = this.userService.findById(sessionUserId);
        mv.addObject("files", user.getFiles());
        mv.addObject("uploadMessage", fileName + " has been successfully deleted.");
        
        return mv;
    }
    
    @RequestMapping(value = "{fileId}/downloadFile", method = RequestMethod.GET)
    public ModelAndView handleFileDownload(HttpServletRequest request, HttpServletResponse response, @PathVariable Long fileId) {
        HttpSession session = request.getSession();
        if(session == null || !request.isRequestedSessionIdValid()) {
            return new ModelAndView("index");
        }
        
        FileEntity file = this.fileService.findById(fileId);
        response.setContentType(file.getType());
        response.setContentLength(file.getContent().length);
        response.setHeader("Content-Disposition","attachment; filename=\"" + file.getName() +"\"");
 
        try {
            FileCopyUtils.copy(file.getContent(), response.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}