/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.FileEntity;
import dao.UserEntity;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface FileServiceLocal {
    public void add(String name, String type, byte[] content, UserEntity owner, boolean isProfilePicture);
    public void remove(FileEntity file);
    public FileEntity findById(Long id);
    public FileEntity findProfilePicture(UserEntity owner);
}
