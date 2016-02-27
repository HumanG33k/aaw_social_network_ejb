/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface UserDaoLocal {
    public void save(UserEntity user);
    public void update(UserEntity user);
    public void delete(UserEntity user);
    public UserEntity findById(Long id);
    public UserEntity findByName(String name);
    public List<UserEntity> searchByName(String name);
}
