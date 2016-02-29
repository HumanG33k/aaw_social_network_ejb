/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.FileDaoLocal;
import dao.FileEntity;
import dao.PostDaoLocal;
import dao.PostEntity;
import dao.UserDaoLocal;
import dao.UserEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nath
 */
@Component
@Stateless
public class PostService implements PostServiceLocal {
    @EJB
    PostDaoLocal postDao;
    @EJB
    UserDaoLocal userDao;
    @EJB
    FileDaoLocal fileDao;

    @Override
    public void add(String content, UserEntity sender, UserEntity target, FileEntity file) {
        PostEntity post = new PostEntity(content, sender, target, file);
        post.setId(this.postDao.save(post));
        sender.addSenderPost(post);
        this.userDao.update(sender);
        target.addTargetPost(post);
        this.userDao.update(target);
        if(file != null) {
            file.addPost(post);
            this.fileDao.update(file);
        }
    }
    
    @Override
    public void remove(PostEntity post) {
        if(this.postDao.findById(post.getId()) != null) {
            UserEntity sender = post.getSender();
            sender.removeSenderPost(post);
            this.userDao.update(sender);
            UserEntity target = post.getTarget();
            target.removeTargetPost(post);
            this.userDao.update(target);
            FileEntity file = post.getFile();
            if(file != null) {
                file.removePost(post);
                this.fileDao.update(file);
            }
            this.postDao.delete(post);
        }
    }
    
    @Override
    public PostEntity findById(Long id) {
        return this.postDao.findById(id);
    }
}
