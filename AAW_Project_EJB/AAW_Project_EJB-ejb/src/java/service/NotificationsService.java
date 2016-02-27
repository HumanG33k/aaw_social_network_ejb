/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.NotificationsDaoLocal;
import dao.NotificationsEntity;
import dao.UsersDaoLocal;
import dao.UsersEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nath
 */
@Component
@Stateless
public class NotificationsService implements NotificationsServiceLocal {
    @EJB
    NotificationsDaoLocal notifsDao;
    @EJB
    UsersDaoLocal usersDao;

    @Override
    public void add(UsersEntity sender, UsersEntity target) {
        if(this.notifsDao.searchBySenderTarget(sender, target) == null) {
            NotificationsEntity notif = new NotificationsEntity(sender, target);
            notif.setId(this.notifsDao.save(notif));
            sender.addSenderNotif(notif);
            this.usersDao.update(sender);
            target.addTargetNotif(notif);
            this.usersDao.update(target);
        }
    }
    
    @Override
    public boolean remove(NotificationsEntity notif) {
        if(notif != null) {
            UsersEntity sender = notif.getSender();
            sender.removeSenderNotif(notif);
            this.usersDao.update(sender);
            UsersEntity target = notif.getTarget();
            target.removeTargetNotif(notif);
            this.usersDao.update(target);
            this.notifsDao.delete(notif);
            return true;
        }
        return false;
    }
    
    @Override
    public NotificationsEntity findById(Long id) {
        return this.notifsDao.findById(id);
    }
    
    @Override
    public NotificationsEntity searchBySenderTarget(UsersEntity sender, UsersEntity target) {
        return this.notifsDao.searchBySenderTarget(sender, target);
    }
}
