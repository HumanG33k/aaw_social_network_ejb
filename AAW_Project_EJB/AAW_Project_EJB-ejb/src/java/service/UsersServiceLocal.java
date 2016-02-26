/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import common.Enums.SignInResult;
import dao.UsersEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface UsersServiceLocal {
    public boolean add(String name, String email, String password);
    public boolean remove(String name);
    public UsersEntity find(Long id);
    public UsersEntity findByName(String name);
    public List<UsersEntity> searchByName(String name);
    public SignInResult checkSignIn(String name, String password);
    public boolean checkFriendship(UsersEntity user, UsersEntity friend);
    public boolean addFriendship(UsersEntity user, UsersEntity friend);
    public boolean removeFriendship(UsersEntity user, UsersEntity friend);
    public void updateInfo(UsersEntity user, String newInfo);
}
