/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.NotificationEntity;
import dao.UserEntity;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface NotificationServiceLocal {
    public void add(UserEntity sender, UserEntity target);
    public boolean remove(NotificationEntity notif);
    public NotificationEntity findById(Long id);
    public NotificationEntity searchBySenderTarget(UserEntity sender, UserEntity target);
}
