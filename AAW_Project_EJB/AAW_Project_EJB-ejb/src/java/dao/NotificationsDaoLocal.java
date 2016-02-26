/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface NotificationsDaoLocal {
    public void save(NotificationsEntity notif);
    public void update(NotificationsEntity notif);
    public void delete(NotificationsEntity notif);
    public NotificationsEntity findById(Long id);
    public List<NotificationsEntity> searchByTarget(UsersEntity target);
    public NotificationsEntity searchBySenderTarget(UsersEntity sender, UsersEntity target);
}
