/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nath
 */
@Component
@Stateless
public class UserDao implements UserDaoLocal {
    @PersistenceContext(unitName="AAW_Project_EJB-ejbPU")
    private EntityManager em;
    
    @Override
    public void save(UserEntity user) {
        user = this.em.merge(user);
        this.em.persist(user);
    }

    @Override
    public void update(UserEntity user) {
        this.em.merge(user);
    }

    @Override
    public void delete(UserEntity user) {
        user = this.em.merge(user);
        this.em.remove(user);
    }

    @Override
    public UserEntity findById(Long id) {
        return (UserEntity) this.em.find(UserEntity.class, id);
    }
    
    @Override
    public UserEntity findByName(String name) {
        try {
            return (UserEntity) this.em.createQuery(
                "SELECT user "
                + "FROM UserEntity user "
                + "WHERE user.name = :name")
                .setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<UserEntity> searchByName(String name) {
        try {
            return (List<UserEntity>) this.em.createQuery(
                "SELECT user "
                + "FROM UserEntity user "
                + "WHERE user.name LIKE '%" + name + "%'")
                .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public EntityManager getEm() { return em; }
    public void setEm(EntityManager em) { this.em = em; }
}
