/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceComposite;

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
public class MessageServiceComposite implements MessageServiceCompositeLocal {
    @EJB
    UserDaoLocal userDao;
    @EJB
    MessageDaoLocal messageDao;
    
    @Override
    public int getNumberUnreadMessages(UserEntity user) {
        return this.numberUnreadMessages(user.getTargetMessages());
    }
    
    @Override
    public int getNumberUnreadMessagesFromSender(UserEntity sender, UserEntity target) {
        return this.numberUnreadMessages(this.messageDao.searchBySenderTarget(sender, target));
    }
    
    private int numberUnreadMessages(List<MessageEntity> messages) {
        int nbUnreadMessages = 0;
        for(MessageEntity message : messages) {
            if(!message.getSeen()) {
                nbUnreadMessages++;
            }
        }
        return nbUnreadMessages;
    }
    
    @Override
    public void readMessage(MessageEntity message, UserEntity user) {
        if(!message.getSeen() && !user.equals(message.getSender())) {
            message.setSeen(true);
            this.messageDao.update(message);
        }
    }
}
