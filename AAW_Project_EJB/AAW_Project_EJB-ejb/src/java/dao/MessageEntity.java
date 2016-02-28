/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Nath
 */
@Entity
@Table(name = "messages")
public class MessageEntity extends PostEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column
    private boolean isSeen;
    
    public MessageEntity() {}
    
    public MessageEntity(String content, UserEntity sender, UserEntity target) {
        super(content, sender, target);
        this.isSeen = false;
    }
    
    // Getters ands setters
    public boolean getSeen() { return isSeen; }
    public void setSeen(boolean isSeen) { this.isSeen = isSeen; }
}
