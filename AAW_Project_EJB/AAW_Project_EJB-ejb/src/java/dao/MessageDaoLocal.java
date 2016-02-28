/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nath
 */
@Local
public interface MessageDaoLocal {
    public Long save(MessageEntity message);
    public void update(MessageEntity message);
    public void delete(MessageEntity message);
    public MessageEntity findById(Long id);
    public List<MessageEntity> searchBySenderTarget(UserEntity sender, UserEntity target);
}
