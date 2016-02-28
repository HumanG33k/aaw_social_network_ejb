/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Nath
 */
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String information;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<PostEntity> senderPosts = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target")
    private List<PostEntity> targetPosts = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<MessageEntity> senderMessages = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target")
    private List<MessageEntity> targetMessages = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<NotificationEntity> senderNotifs = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target")
    private List<NotificationEntity> targetNotifs = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<FileEntity> files = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserEntity> friends = new ArrayList<>();

    public UserEntity() {}
    
    public UserEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.information = "Nothing yet!";
    }

    public void addSenderPost(PostEntity post) {
        this.senderPosts.add(post);
    }
    
    public void removeSenderPost(PostEntity post) {
        this.senderPosts.remove(post);
    }
    
    public void addTargetPost(PostEntity post) {
        this.targetPosts.add(post);
    }
    
    public void removeTargetPost(PostEntity post) {
        this.targetPosts.remove(post);
    }
    
    public void addSenderMessage(MessageEntity message) {
        this.senderMessages.add(message);
    }
    
    public void removeSenderMessage(MessageEntity message) {
        this.senderMessages.remove(message);
    }
    
    public void addTargetMessage(MessageEntity message) {
        this.targetMessages.add(message);
    }
    
    public void removeTargetMessage(MessageEntity message) {
        this.targetMessages.remove(message);
    }
    
    public void addSenderNotif(NotificationEntity notif) {
        this.senderNotifs.add(notif);
    }
    
    public void removeSenderNotif(NotificationEntity notif) {
        this.senderNotifs.remove(notif);
    }
    
    public void addTargetNotif(NotificationEntity notif) {
        this.targetNotifs.add(notif);
    }
    
    public void removeTargetNotif(NotificationEntity notif) {
        this.targetNotifs.remove(notif);
    }
    
    public void addFile(FileEntity file) {
        this.files.add(file);
    }
    
    public void removeFile(FileEntity file) {
        this.files.remove(file);
    }
    
    public boolean addFriend(UserEntity friend) {
        if(!this.friends.contains(friend)) {
            this.friends.add(friend);
            return true;
        }
        return false;
    }
    
    public boolean removeFriend(UserEntity friend) {
        if(this.friends.contains(friend)) {
            this.friends.remove(friend);
            return true;
        }
        return false;
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
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.UserEntity[ id=" + id + " ]";
    }
    
    // Getters ands setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getInformation() { return information; }
    public void setInformation(String information) { this.information = information; }
    public List<PostEntity> getSenderPosts() { return senderPosts; }
    public void setSenderPosts(List<PostEntity> senderPosts) { this.senderPosts = senderPosts; }
    public List<PostEntity> getTargetPosts() { return targetPosts; }
    public void setTargetPosts(List<PostEntity> targetPosts) { this.targetPosts = targetPosts; }
    public List<MessageEntity> getSenderMessages() { return senderMessages; }
    public void setSenderMessages(List<MessageEntity> senderMessages) { this.senderMessages = senderMessages; }
    public List<MessageEntity> getTargetMessages() { return targetMessages; }
    public void setTargetMessages(List<MessageEntity> targetMessages) { this.targetMessages = targetMessages; }
    public List<NotificationEntity> getSenderNotifs() { return senderNotifs; }
    public void setSenderNotifs(List<NotificationEntity> senderNotifs) { this.senderNotifs = senderNotifs; }
    public List<NotificationEntity> getTargetNotifs() { return targetNotifs; }
    public void setTargetNotifs(List<NotificationEntity> targetNotifs) { this.targetNotifs = targetNotifs; }
    public List<FileEntity> getFiles() { return files; }
    public void setFiles(List<FileEntity> files) { this.files = files; }
    public List<UserEntity> getFriends() { return friends; }
    public void setFriends(List<UserEntity> friends) { this.friends = friends; }
}
