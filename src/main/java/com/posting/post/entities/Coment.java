package com.posting.post.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_coment")
@EntityListeners(AuditingEntityListener.class)
public class Coment implements Serializable{
    private static final long  serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String coment;

    @CreatedDate
    private LocalDateTime date;

    public Coment() {
    }

    public Coment(Long id, Post post, User user, String coment, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.coment = coment;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
        int dateActually = getPost().getDate().getDayOfMonth();
        int dateComent = this.date.getDayOfMonth();
        int diffDays = dateActually - dateComent;
        return diffDays;
    }

    public String getDiffDate() {
        return "ago - "
            + DiffDate()
            + " days";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Coment coment = (Coment) object;
        return Objects.equals(id, coment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
