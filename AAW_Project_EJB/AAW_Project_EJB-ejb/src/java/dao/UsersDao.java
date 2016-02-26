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
public class UsersDao implements UsersDaoLocal {
    @PersistenceContext(unitName="AAW_Project_EJB-ejbPU")
    private EntityManager em;
    
    @Override
    public void save(UsersEntity user) {
        user = this.em.merge(user);
        this.em.persist(user);
    }

    @Override
    public void update(UsersEntity user) {
        this.em.merge(user);
    }

    @Override
    public void delete(UsersEntity user) {
        user = this.em.merge(user);
        this.em.remove(user);
    }

    @Override
    public UsersEntity find(Long id) {
        return (UsersEntity) this.em.find(UsersEntity.class, id);
    }
    
    @Override
    public UsersEntity findByName(String name) {
        try {
            return (UsersEntity) this.em.createQuery(
                "SELECT user "
                + "FROM UsersEntity user "
                + "WHERE user.name = :name")
                .setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<UsersEntity> searchByName(String name) {
        try {
            String sql = "WHERE user.name LIKE '%"+name+"%'" ;
            
            return (List<UsersEntity>) this.em.createQuery(
                "SELECT user "
                + "FROM UsersEntity user "
                + sql)
                .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public boolean checkFriendship(UsersEntity user, UsersEntity friend) {
        return user.getFriends().contains(friend);
    }
    
    @Override
    public boolean addFriendship(UsersEntity user, UsersEntity friend) {
        if(user.addFriend(friend) && friend.addFriend(user)) {
            this.update(user);
            this.update(friend);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean removeFriendship(UsersEntity user, UsersEntity friend) {
        if(user.removeFriend(friend) && friend.removeFriend(user)) {
            this.update(user);
            this.update(friend);
            return true;
        }
        return false;
    }
    
    public EntityManager getEm() { return em; }
    public void setEm(EntityManager em) { this.em = em; }
}
