/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
public class NotificationDao implements NotificationDaoLocal {
    @PersistenceContext(unitName="AAW_Project_EJB-ejbPU")
    private EntityManager em;
    
    @Override
    public Long save(NotificationEntity notif) {
        notif = this.em.merge(notif);
        this.em.persist(notif);
        return notif.getId();
    }

    @Override
    public void update(NotificationEntity notif) {
        this.em.merge(notif);
    }

    @Override
    public void delete(NotificationEntity notif) {
        notif = this.em.merge(notif);
        this.em.remove(notif);
    }

    @Override
    public NotificationEntity findById(Long id) {
        return (NotificationEntity) this.em.find(NotificationEntity.class, id);
    }

    @Override
    public NotificationEntity searchBySenderTarget(UserEntity sender, UserEntity target) {
        try {
            return (NotificationEntity) this.em.createQuery(
                "SELECT notif "
                + "FROM NotificationEntity notif "
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
