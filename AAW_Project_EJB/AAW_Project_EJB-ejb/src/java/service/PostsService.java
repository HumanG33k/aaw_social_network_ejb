/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PostsDaoLocal;
import dao.PostsEntity;
import dao.UsersEntity;
import java.util.List;
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

    @Override
    public void add(String content, UsersEntity sender, UsersEntity target) {
        PostsEntity post = new PostsEntity(content, sender, target);
        this.postsDao.save(post);
    }
    
    @Override
    public void remove(PostsEntity post) {
        this.postsDao.delete(post);
    }
    
    @Override
    public PostsEntity find(Long id) {
        return this.postsDao.find(id);
    }
    
    @Override
    public List<PostsEntity> searchByTarget(UsersEntity target) {
        return this.postsDao.searchByTarget(target);
    }
    
    @Override
    public List<PostsEntity> searchBySender(UsersEntity sender) {
        return this.postsDao.searchBySender(sender);
    }
}
