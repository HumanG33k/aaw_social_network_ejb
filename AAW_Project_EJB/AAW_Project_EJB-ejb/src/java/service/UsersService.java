/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import common.Enums.SignInResult;
import dao.UsersDaoLocal;
import dao.UsersEntity;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Nath
 */
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
    public UsersEntity find(Long id) {
        return this.usersDao.find(id);
    }
    
    @Override
    public UsersEntity findByName(String name) {
        return this.usersDao.findByName(name);
    }
    
    @Override
    public ArrayList<UsersEntity> searchByName(String name) {
        return this.usersDao.searchByName(name);
    }
    
    @Override
    public SignInResult checkSignIn(String name, String password) {
        UsersEntity user = this.findByName(name);
        if(user == null) {
            return SignInResult.WRONG_USER;
        }
        if(!password.equals(user.getPassword())) {
            return SignInResult.WRONG_PASSWORD;
        }
        return SignInResult.SUCCESS;
    }
    
    @Override
    public boolean checkFriendship(UsersEntity user, UsersEntity friend) {
        return this.usersDao.checkFriendship(user, friend);
    }
    
    @Override
    public boolean addFriendship(UsersEntity user, UsersEntity friend) {
        return this.usersDao.addFriendship(user, friend);
    }
    
    @Override
    public boolean removeFriendship(UsersEntity user, UsersEntity friend) {
        return this.usersDao.removeFriendship(user, friend);
    }
    
    @Override
    public void updateInfo(UsersEntity user, String newInfo) {
        user.setInformation(newInfo);
        this.usersDao.update(user);
    }
}
