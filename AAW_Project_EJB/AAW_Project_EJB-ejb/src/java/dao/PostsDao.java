/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nath
 */
@Stateless
public class PostsDao implements PostsDaoLocal {
    @PersistenceContext(unitName="AAW_Project_EJB-ejbPU")
    private EntityManager em;
    
    @Override
    public void save(PostsEntity post) {
        post = this.em.merge(post);
        this.em.persist(post);
    }

    @Override
    public void update(PostsEntity post) {
        this.em.merge(post);
    }

    @Override
    public void delete(PostsEntity post) {
        post = this.em.merge(post);
        this.em.remove(post);
    }

    @Override
    public PostsEntity find(Long id) {
        return (PostsEntity) this.em.find(PostsEntity.class, id);
    }
    
    @Override
    public ArrayList<PostsEntity> searchByTarget(UsersEntity target) {
        try {
            return (ArrayList<PostsEntity>) this.em.createQuery(
                "SELECT post "
                + "FROM PostsEntity post "
                + "WHERE post.target = :target")
                .setParameter("target", target).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public ArrayList<PostsEntity> searchBySender(UsersEntity sender) {
        try {
            return (ArrayList<PostsEntity>) this.em.createQuery(
                "SELECT post "
                + "FROM PostsEntity post "
                + "WHERE post.sender = :sender")
                .setParameter("sender", sender).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public EntityManager getEm() { return em; }
    public void setEm(EntityManager em) { this.em = em; }
}
