/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nath
 */
@Component
@Stateless
public class FileDao implements FileDaoLocal {
    @PersistenceContext(unitName="AAW_Project_EJB-ejbPU")
    private EntityManager em;
    
    @Override
    public Long save(FileEntity file) {
        file = this.em.merge(file);
        this.em.persist(file);
        return file.getId();
    }

    @Override
    public void update(FileEntity file) {
        this.em.merge(file);
    }

    @Override
    public void delete(FileEntity file) {
        file = this.em.merge(file);
        this.em.remove(file);
    }

    @Override
    public FileEntity findById(Long id) {
        return (FileEntity) this.em.find(FileEntity.class, id);
    }
    
    @Override
    public FileEntity findProfilePicture(UserEntity owner) {
        try {
            return (FileEntity) this.em.createQuery(
                "SELECT file "
                + "FROM FileEntity file, UserEntity owner "
                + "WHERE owner.id = :ownerId "
                + "AND file.isProfilePicture = true "
                + "AND file.owner = :fileOwner")
                .setParameter("ownerId", owner.getId())
                .setParameter("fileOwner", owner).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
