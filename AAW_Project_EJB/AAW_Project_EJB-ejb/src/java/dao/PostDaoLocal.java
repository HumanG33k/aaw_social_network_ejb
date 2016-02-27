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
public interface PostDaoLocal {
    public Long save(PostEntity post);
    public void update(PostEntity post);
    public void delete(PostEntity post);
    public PostEntity findById(Long id);
}
