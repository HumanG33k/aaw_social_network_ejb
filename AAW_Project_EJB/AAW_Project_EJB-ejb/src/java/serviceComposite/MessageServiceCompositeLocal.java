/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceComposite;

import dao.MessageEntity;
import dao.UserEntity;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface MessageServiceCompositeLocal {
    public int getNumberUnreadMessages(UserEntity user);
    public int getNumberUnreadMessagesFromSender(UserEntity sender, UserEntity target);
    public void readMessage(MessageEntity message, UserEntity user);
}
