/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PostsEntity;
import dao.UsersEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface PostsServiceLocal {
    public void add(String content, UsersEntity sender, UsersEntity target);
    public void remove(PostsEntity post);
    public PostsEntity find(Long id);
    public List<PostsEntity> searchByTarget(UsersEntity target);
    public List<PostsEntity> searchBySender(UsersEntity sender);
}
