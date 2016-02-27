/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface NotificationDaoLocal {
    public Long save(NotificationEntity notif);
    public void update(NotificationEntity notif);
    public void delete(NotificationEntity notif);
    public NotificationEntity findById(Long id);
    public NotificationEntity searchBySenderTarget(UserEntity sender, UserEntity target);
}
