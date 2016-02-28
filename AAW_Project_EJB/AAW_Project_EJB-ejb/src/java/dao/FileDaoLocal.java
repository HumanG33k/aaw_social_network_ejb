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
public interface FileDaoLocal {
    public Long save(FileEntity file);
    public void update(FileEntity file);
    public void delete(FileEntity file);
    public FileEntity findById(Long id);
}
