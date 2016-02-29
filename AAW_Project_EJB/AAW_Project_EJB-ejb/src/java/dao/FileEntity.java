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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Nath
 */
@Entity
@Table(name = "files")
public class FileEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private byte[] content;
    @Column
    private boolean isProfilePicture;
    @ManyToOne
    @JoinColumn(name = "ownerId")
    private UserEntity owner;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file")
    private List<PostEntity> linkedPosts = new ArrayList<>();
    
    public FileEntity() {}

    public FileEntity(String name, String type, byte[] content, UserEntity owner, boolean isProfilePicture) {
        this.name = name;
        this.type = type;
        this.content = content;
        this.owner = owner;
        this.isProfilePicture = isProfilePicture;
    }
    
    public void addPost(PostEntity post) {
        this.linkedPosts.add(post);
    }
    
    public void removePost(PostEntity post) {
        this.linkedPosts.remove(post);
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
        if (!(object instanceof FileEntity)) {
            return false;
        }
        FileEntity other = (FileEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.FileEntity[ id=" + id + " ]";
    }
    
    // Getters ands setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public byte[] getContent() { return content; }
    public void setContent(byte[] content) { this.content = content; }
    public UserEntity getOwner() { return owner; }
    public void setOwner(UserEntity owner) { this.owner = owner; }
    public boolean isIsProfilePicture() { return isProfilePicture; }
    public void setIsProfilePicture(boolean isProfilePicture) { this.isProfilePicture = isProfilePicture; }
    public List<PostEntity> getLinkedPosts() { return linkedPosts; }
    public void setLinkedPosts(List<PostEntity> linkedPosts) { this.linkedPosts = linkedPosts; }
}
