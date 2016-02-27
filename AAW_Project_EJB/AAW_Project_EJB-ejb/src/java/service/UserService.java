/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.UserDaoLocal;
import dao.UserEntity;
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
public class UserService implements UserServiceLocal {
    @EJB
    UserDaoLocal userDao;
    
    @Override
    public boolean add(String name, String email, String password) {
        if(this.userDao.findByName(name) == null) {
            UserEntity user = new UserEntity(name, email, password);
            this.userDao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String name) {
        UserEntity user = this.userDao.findByName(name);
        if(user != null) {
            this.userDao.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public UserEntity findById(Long id) {
        return this.userDao.findById(id);
    }
    
    @Override
    public UserEntity findByName(String name) {
        return this.userDao.findByName(name);
    }
    
    @Override
    public List<UserEntity> searchByName(String name) {
        return this.userDao.searchByName(name);
    }
}
