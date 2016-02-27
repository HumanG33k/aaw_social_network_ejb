/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PostEntity;
import dao.UserEntity;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface PostServiceLocal {
    public void add(String content, UserEntity sender, UserEntity target);
    public void remove(PostEntity post);
    public PostEntity findById(Long id);
}
