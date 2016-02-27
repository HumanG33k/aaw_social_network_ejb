/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nath
 */
@Component
@Stateless
public class PostsDao implements PostsDaoLocal {
    @PersistenceContext(unitName="AAW_Project_EJB-ejbPU")
    private EntityManager em;
    
    @Override
    public Long save(PostsEntity post) {
        post = this.em.merge(post);
        this.em.persist(post);
        return post.getId();
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
    public PostsEntity findById(Long id) {
        return (PostsEntity) this.em.find(PostsEntity.class, id);
    }

    public EntityManager getEm() { return em; }
    public void setEm(EntityManager em) { this.em = em; }
}
