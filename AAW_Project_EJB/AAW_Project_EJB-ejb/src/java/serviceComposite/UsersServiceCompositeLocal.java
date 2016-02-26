/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceComposite;

import common.Enums.SignInResult;
import dao.UsersEntity;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface UsersServiceCompositeLocal {
    public boolean checkFriendship(UsersEntity user, UsersEntity friend);
    public boolean addFriendship(UsersEntity user, UsersEntity friend);
    public boolean removeFriendship(UsersEntity user, UsersEntity friend);
    public SignInResult checkSignIn(String name, String password);
    public void updateInfo(UsersEntity user, String newInfo);
}
