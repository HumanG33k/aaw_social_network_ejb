/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Nath
 */
@Entity
@Table(name = "notifications")
public class NotificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "senderId")
    private UserEntity sender;
    @ManyToOne
    @JoinColumn(name = "targetId")
    private UserEntity target;

    public NotificationEntity() {}
    
    public NotificationEntity(UserEntity sender, UserEntity target) {
        this.sender = sender;
        this.target = target;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationEntity)) {
            return false;
        }
        NotificationEntity other = (NotificationEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.NotificationEntity[ id=" + id + " ]";
    }
    
    // Getters ands setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserEntity getSender() { return sender; }
    public void setSender(UserEntity sender) { this.sender = sender; }
    public UserEntity getTarget() { return target; }
    public void setTarget(UserEntity target) { this.target = target; }
}
