package com.posting.post.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.posting.post.pk.ComentPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_coment")
@EntityListeners(AuditingEntityListener.class)
public class Coment implements Serializable{
    private static final long  serialVersionUID = 1L;

    @EmbeddedId
    private ComentPK id = new ComentPK();

    private String coment;

    @CreatedDate
    private LocalDateTime date;

    public Coment() {
    }

    public Coment(Post post, User user, String coment, LocalDateTime date) {
        id.setPost(post);
        id.setUser(user);
        this.coment = coment;
        this.date = date;
    }

    @JsonIgnore
    public Post getPost() {
        return id.getPost();
    }

    public void setPost(Post post) {
        id.setPost(post);
    }

    public User getUser() {
        return id.getUser();
    }

    public void setUser(User user) {
        id.setUser(user);
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int DiffDate() {
        int dateActually = id.getPost().getDate().getDayOfMonth();
        int dateComent = this.date.getDayOfMonth();
        int diffDays = dateActually - dateComent;
        return diffDays;
    }

    public String getDiffDate() {
        return "ago - "
            + DiffDate()
            + " days";
    }
}
