/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.UsersDaoLocal;
import dao.UsersEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nath
 */
@Component
@Stateless
public class UsersService implements UsersServiceLocal {
    @EJB
    UsersDaoLocal usersDao;
    
    @Override
    public boolean add(String name, String email, String password) {
        if(this.usersDao.findByName(name) == null) {
            UsersEntity user = new UsersEntity(name, email, password);
            this.usersDao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String name) {
        UsersEntity user = this.usersDao.findByName(name);
        if(user != null) {
            this.usersDao.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public UsersEntity findById(Long id) {
        return this.usersDao.findById(id);
    }
    
    @Override
    public UsersEntity findByName(String name) {
        return this.usersDao.findByName(name);
    }
    
    @Override
    public List<UsersEntity> searchByName(String name) {
        return this.usersDao.searchByName(name);
    }
}
