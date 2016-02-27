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
public class UsersEntity implements Serializable {
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
    private List<PostsEntity> senderPosts = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target")
    private List<PostsEntity> targetPosts = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<NotificationsEntity> senderNotifs = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target")
    private List<NotificationsEntity> targetNotifs = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UsersEntity> friends = new ArrayList<>();

    public UsersEntity() {}
    
    public UsersEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.information = "Nothing yet!";
    }

    public void addSenderPost(PostsEntity post) {
        this.senderPosts.add(post);
    }
    
    public void removeSenderPost(PostsEntity post) {
        this.senderPosts.remove(post);
    }
    
    public void addTargetPost(PostsEntity post) {
        this.targetPosts.add(post);
    }
    
    public void removeTargetPost(PostsEntity post) {
        this.targetPosts.remove(post);
    }
    
    public void addSenderNotif(NotificationsEntity notif) {
        this.senderNotifs.add(notif);
    }
    
    public void removeSenderNotif(NotificationsEntity notif) {
        this.senderNotifs.remove(notif);
    }
    
    public void addTargetNotif(NotificationsEntity notif) {
        this.targetNotifs.add(notif);
    }
    
    public void removeTargetNotif(NotificationsEntity notif) {
        this.targetNotifs.remove(notif);
    }
    
    public boolean addFriend(UsersEntity friend) {
        if(!this.friends.contains(friend)) {
            this.friends.add(friend);
            return true;
        }
        return false;
    }
    
    public boolean removeFriend(UsersEntity friend) {
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
        if (!(object instanceof UsersEntity)) {
            return false;
        }
        UsersEntity other = (UsersEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.UsersEntity[ id=" + id + " ]";
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
    public List<PostsEntity> getSenderPosts() { return senderPosts; }
    public void setSenderPosts(List<PostsEntity> senderPosts) { this.senderPosts = senderPosts; }
    public List<PostsEntity> getTargetPosts() { return targetPosts; }
    public void setTargetPosts(List<PostsEntity> targetPosts) { this.targetPosts = targetPosts; }
    public List<NotificationsEntity> getSenderNotifs() { return senderNotifs; }
    public void setSenderNotifs(List<NotificationsEntity> senderNotifs) { this.senderNotifs = senderNotifs; }
    public List<NotificationsEntity> getTargetNotifs() { return targetNotifs; }
    public void setTargetNotifs(List<NotificationsEntity> targetNotifs) { this.targetNotifs = targetNotifs; }
    public List<UsersEntity> getFriends() { return friends; }
    public void setFriends(List<UsersEntity> friends) { this.friends = friends; }
}
