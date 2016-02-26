/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceComposite;

import common.Enums.SignInResult;
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
public class UsersServiceComposite implements UsersServiceCompositeLocal {
    @EJB
    UsersDaoLocal usersDao;
    
    @Override
    public boolean checkFriendship(UsersEntity user, UsersEntity friend) {
        return user.getFriends().contains(friend);
    }
    
    @Override
    public boolean addFriendship(UsersEntity user, UsersEntity friend) {
        if(user.addFriend(friend) && friend.addFriend(user)) {
            this.usersDao.update(user);
            this.usersDao.update(friend);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean removeFriendship(UsersEntity user, UsersEntity friend) {
        if(user.removeFriend(friend) && friend.removeFriend(user)) {
            this.usersDao.update(user);
            this.usersDao.update(friend);
            return true;
        }
        return false;
    }
    
    @Override
    public SignInResult checkSignIn(String name, String password) {
        UsersEntity user = this.usersDao.findByName(name);
        if(user == null) {
            return SignInResult.WRONG_USER;
        }
        if(!password.equals(user.getPassword())) {
            return SignInResult.WRONG_PASSWORD;
        }
        return SignInResult.SUCCESS;
    }
    
    @Override
    public void updateInfo(UsersEntity user, String newInfo) {
        user.setInformation(newInfo);
        this.usersDao.update(user);
    }
}
