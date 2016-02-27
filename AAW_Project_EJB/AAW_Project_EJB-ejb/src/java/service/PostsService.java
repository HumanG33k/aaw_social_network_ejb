/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PostsDaoLocal;
import dao.PostsEntity;
import dao.UsersDaoLocal;
import dao.UsersEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nath
 */
@Component
@Stateless
public class PostsService implements PostsServiceLocal {
    @EJB
    PostsDaoLocal postsDao;
    @EJB
    UsersDaoLocal usersDao;

    @Override
    public void add(String content, UsersEntity sender, UsersEntity target) {
        PostsEntity post = new PostsEntity(content, sender, target);
        post.setId(this.postsDao.save(post));
        sender.addSenderPost(post);
        this.usersDao.update(sender);
        target.addTargetPost(post);
        this.usersDao.update(target);
    }
    
    @Override
    public void remove(PostsEntity post) {
        if(this.postsDao.findById(post.getId()) != null) {
            this.postsDao.delete(post);
            UsersEntity sender = post.getSender();
            sender.removeSenderPost(post);
            this.usersDao.update(sender);
            UsersEntity target = post.getTarget();
            target.removeTargetPost(post);
            this.usersDao.update(target);
        }
    }
    
    @Override
    public PostsEntity findById(Long id) {
        return this.postsDao.findById(id);
    }
}
