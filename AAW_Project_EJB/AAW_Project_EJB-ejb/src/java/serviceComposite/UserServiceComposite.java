/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceComposite;

import common.Enums.SignInResult;
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
public class UserServiceComposite implements UserServiceCompositeLocal {
    @EJB
    UserDaoLocal userDao;
    
    @Override
    public boolean checkFriendship(UserEntity user, UserEntity friend) {
        return user.getFriends().contains(friend);
    }
    
    @Override
    public boolean addFriendship(UserEntity user, UserEntity friend) {
        if(user.addFriend(friend) && friend.addFriend(user)) {
            this.userDao.update(user);
            this.userDao.update(friend);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean removeFriendship(UserEntity user, UserEntity friend) {
        if(user.removeFriend(friend) && friend.removeFriend(user)) {
            this.userDao.update(user);
            this.userDao.update(friend);
            return true;
        }
        return false;
    }
    
    @Override
    public SignInResult checkSignIn(String name, String password) {
        UserEntity user = this.userDao.findByName(name);
        if(user == null) {
            return SignInResult.WRONG_USER;
        }
        if(!password.equals(user.getPassword())) {
            return SignInResult.WRONG_PASSWORD;
        }
        return SignInResult.SUCCESS;
    }
    
    @Override
    public void updateInfo(UserEntity user, String newInfo) {
        user.setInformation(newInfo);
        this.userDao.update(user);
    }
}
