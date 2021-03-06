/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import javax.persistence.EntityManager;

/**
 *
 * @author Nathanael Villemin
 */
public interface NotificationsDao {
    public void save(NotificationsEntity notif);
    public void update(NotificationsEntity notif);
    public void delete(NotificationsEntity notif);
    public NotificationsEntity find(Long id);
    public EntityManager getEm();
    public void setEm(EntityManager em);
}
