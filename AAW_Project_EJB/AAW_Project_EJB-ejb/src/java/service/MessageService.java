/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.FileEntity;
import dao.MessageDaoLocal;
import dao.MessageEntity;
import dao.UserDaoLocal;
import dao.UserEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nath
 */
@Component
@Stateless
public class MessageService implements MessageServiceLocal {
    @EJB
    MessageDaoLocal messageDao;
    @EJB
    UserDaoLocal userDao;

    @Override
    public void add(String content, UserEntity sender, UserEntity target, FileEntity file) {
        MessageEntity message = new MessageEntity(content, sender, target, file);
        message.setId(this.messageDao.save(message));
        sender.addSenderMessage(message);
        this.userDao.update(sender);
        target.addTargetMessage(message);
        this.userDao.update(target);
    }
    
    @Override
    public void remove(MessageEntity message) {
        if(this.messageDao.findById(message.getId()) != null) {
            this.messageDao.delete(message);
            UserEntity sender = message.getSender();
            sender.removeSenderMessage(message);
            this.userDao.update(sender);
            UserEntity target = message.getTarget();
            target.removeTargetMessage(message);
            this.userDao.update(target);
        }
    }
    
    @Override
    public MessageEntity findById(Long id) {
        return this.messageDao.findById(id);
    }
    
    @Override
    public List<MessageEntity> searchBySenderTarget(UserEntity sender, UserEntity target) {
        return this.messageDao.searchBySenderTarget(sender, target);
    }
}
