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
public class MessageDao implements MessageDaoLocal {
    @PersistenceContext(unitName="AAW_Project_EJB-ejbPU")
    private EntityManager em;
    
    @Override
    public Long save(MessageEntity message) {
        message = this.em.merge(message);
        this.em.persist(message);
        return message.getId();
    }

    @Override
    public void update(MessageEntity message) {
        this.em.merge(message);
    }

    @Override
    public void delete(MessageEntity message) {
        message = this.em.merge(message);
        this.em.remove(message);
    }

    @Override
    public MessageEntity findById(Long id) {
        return (MessageEntity) this.em.find(MessageEntity.class, id);
    }

    @Override
    public List<MessageEntity> searchBySenderTarget(UserEntity sender, UserEntity target) {
        try {
            return (List<MessageEntity>) this.em.createQuery(
                "SELECT message "
                + "FROM MessageEntity message "
                + "WHERE message.sender = :sender "
                + "AND message.target = :target")
                .setParameter("sender", sender)
                .setParameter("target", target).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public EntityManager getEm() { return em; }
    public void setEm(EntityManager em) { this.em = em; }
}
