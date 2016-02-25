/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PostsDao;
import dao.PostsEntity;
import dao.UsersEntity;
import java.util.ArrayList;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nathanael Villemin
 */
@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    PostsDao postsDao;

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

    @Transactional
    @Override
    public ArrayList<PostsEntity> searchByTarget(UsersEntity target) {
        try {
            return (ArrayList<PostsEntity>) this.postsDao.getEm().createQuery(
                "SELECT post "
                + "FROM PostsEntity post "
                + "WHERE post.target = :target")
                .setParameter("target", target).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    @Override
    public ArrayList<PostsEntity> searchBySender(UsersEntity sender) {
        try {
            return (ArrayList<PostsEntity>) this.postsDao.getEm().createQuery(
                "SELECT post "
                + "FROM PostsEntity post "
                + "WHERE post.sender = :sender")
                .setParameter("sender", sender).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
