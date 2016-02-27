/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.NotificationDaoLocal;
import dao.NotificationEntity;
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
public class NotificationService implements NotificationServiceLocal {
    @EJB
    NotificationDaoLocal notifDao;
    @EJB
    UserDaoLocal userDao;

    @Override
    public void add(UserEntity sender, UserEntity target) {
        if(this.notifDao.searchBySenderTarget(sender, target) == null) {
            NotificationEntity notif = new NotificationEntity(sender, target);
            notif.setId(this.notifDao.save(notif));
            sender.addSenderNotif(notif);
            this.userDao.update(sender);
            target.addTargetNotif(notif);
            this.userDao.update(target);
        }
    }
    
    @Override
    public boolean remove(NotificationEntity notif) {
        if(notif != null) {
            UserEntity sender = notif.getSender();
            sender.removeSenderNotif(notif);
            this.userDao.update(sender);
            UserEntity target = notif.getTarget();
            target.removeTargetNotif(notif);
            this.userDao.update(target);
            this.notifDao.delete(notif);
            return true;
        }
        return false;
    }
    
    @Override
    public NotificationEntity findById(Long id) {
        return this.notifDao.findById(id);
    }
    
    @Override
    public NotificationEntity searchBySenderTarget(UserEntity sender, UserEntity target) {
        return this.notifDao.searchBySenderTarget(sender, target);
    }
}
