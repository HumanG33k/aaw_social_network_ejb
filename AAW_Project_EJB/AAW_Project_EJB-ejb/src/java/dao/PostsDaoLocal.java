/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface PostsDaoLocal {
    public Long save(PostsEntity post);
    public void update(PostsEntity post);
    public void delete(PostsEntity post);
    public PostsEntity findById(Long id);
}
