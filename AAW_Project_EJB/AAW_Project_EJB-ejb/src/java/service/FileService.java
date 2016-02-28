/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.FileDaoLocal;
import dao.FileEntity;
import dao.UserDaoLocal;
import dao.UserEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nath
 */
@Component
@Stateless
public class FileService implements FileServiceLocal {
    @EJB
    FileDaoLocal fileDao;
    @EJB
    UserDaoLocal userDao;

    @Override
    public void add(String name, String type, byte[] content, UserEntity owner) {
        FileEntity file = new FileEntity(name, type, content, owner);
        file.setId(this.fileDao.save(file));
        owner.addFile(file);
        this.userDao.update(owner);
    }
    
    @Override
    public void remove(FileEntity file) {
        if(this.fileDao.findById(file.getId()) != null) {
            this.fileDao.delete(file);
            UserEntity owner = file.getOwner();
            owner.removeFile(file);
            this.userDao.update(owner);
        }
    }
    
    @Override
    public FileEntity findById(Long id) {
        return this.fileDao.findById(id);
    }
}
