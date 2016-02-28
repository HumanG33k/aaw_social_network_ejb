/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.MessageEntity;
import dao.UserEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface MessageServiceLocal {
    public void add(String content, UserEntity sender, UserEntity target);
    public void remove(MessageEntity message);
    public MessageEntity findById(Long id);
    public List<MessageEntity> searchBySenderTarget(UserEntity sender, UserEntity target);
}
