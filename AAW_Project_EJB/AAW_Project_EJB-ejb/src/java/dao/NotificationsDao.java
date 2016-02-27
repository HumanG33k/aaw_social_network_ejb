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
public class NotificationsDao implements NotificationsDaoLocal {
    @PersistenceContext(unitName="AAW_Project_EJB-ejbPU")
    private EntityManager em;
    
    @Override
    public Long save(NotificationsEntity notif) {
        notif = this.em.merge(notif);
        this.em.persist(notif);
        return notif.getId();
    }

    @Override
    public void update(NotificationsEntity notif) {
        this.em.merge(notif);
    }

    @Override
    public void delete(NotificationsEntity notif) {
        notif = this.em.merge(notif);
        this.em.remove(notif);
    }

    @Override
    public NotificationsEntity findById(Long id) {
        return (NotificationsEntity) this.em.find(NotificationsEntity.class, id);
    }

    @Override
    public NotificationsEntity searchBySenderTarget(UsersEntity sender, UsersEntity target) {
        try {
            return (NotificationsEntity) this.em.createQuery(
                "SELECT notif "
                + "FROM NotificationsEntity notif "
                + "WHERE notif.sender = :sender "
                + "AND notif.target = :target")
                .setParameter("sender", sender)
                .setParameter("target", target).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public EntityManager getEm() { return em; }
    public void setEm(EntityManager em) { this.em = em; }
}
