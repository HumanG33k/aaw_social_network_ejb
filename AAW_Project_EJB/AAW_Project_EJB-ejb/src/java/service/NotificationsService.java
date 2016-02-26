/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.NotificationsDaoLocal;
import dao.NotificationsEntity;
import dao.UsersEntity;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Nath
 */
@Stateless
public class NotificationsService implements NotificationsServiceLocal {
    @EJB
    NotificationsDaoLocal notifsDao;

    @Override
    public void add(UsersEntity sender, UsersEntity target) {
        NotificationsEntity notif = new NotificationsEntity(sender, target);
        this.notifsDao.save(notif);
    }
    
    @Override
    public boolean remove(NotificationsEntity notif) {
        if(notif != null) {
            this.notifsDao.delete(notif);
            return true;
        }
        return false;
    }
    
    @Override
    public NotificationsEntity find(Long id) {
        return this.notifsDao.find(id);
    }
    
    @Override
    public ArrayList<NotificationsEntity> searchByTarget(UsersEntity target) {
        return this.notifsDao.searchByTarget(target);
    }
    
    @Override
    public NotificationsEntity searchBySenderTarget(UsersEntity sender, UsersEntity target) {
        return this.notifsDao.searchBySenderTarget(sender, target);
    }
}
