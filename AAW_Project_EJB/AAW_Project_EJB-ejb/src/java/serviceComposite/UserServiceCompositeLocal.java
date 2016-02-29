/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceComposite;

import common.Enums.SignInResult;
import dao.UserEntity;
import javax.ejb.Local;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nath
 */
@Local
public interface UserServiceCompositeLocal {
    public boolean checkFriendship(UserEntity user, UserEntity friend);
    public boolean addFriendship(UserEntity user, UserEntity friend);
    public boolean removeFriendship(UserEntity user, UserEntity friend);
    public SignInResult checkSignIn(String name, String password);
    public void updateInfo(UserEntity user, String newInfo);
    public void updateProfilePicture(UserEntity user, MultipartFile file);
}
