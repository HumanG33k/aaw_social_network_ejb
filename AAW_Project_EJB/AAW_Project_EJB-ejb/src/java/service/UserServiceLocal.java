/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.UserEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface UserServiceLocal {
    public boolean add(String name, String email, String password);
    public boolean remove(String name);
    public UserEntity findById(Long id);
    public UserEntity findByName(String name);
    public List<UserEntity> searchByName(String name);
}
