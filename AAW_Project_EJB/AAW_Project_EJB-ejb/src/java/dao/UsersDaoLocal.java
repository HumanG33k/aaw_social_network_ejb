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
public interface UsersDaoLocal {
    public void save(UsersEntity user);
    public void update(UsersEntity user);
    public void delete(UsersEntity user);
    public UsersEntity findById(Long id);
    public UsersEntity findByName(String name);
    public List<UsersEntity> searchByName(String name);
}
