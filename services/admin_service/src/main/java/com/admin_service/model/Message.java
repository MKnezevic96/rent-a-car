package com.admin_service.model;


import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name="messages_table")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="message_id", nullable=false, unique=true)
    private Integer id;

    @Column(name="content", nullable=false)
    private String content;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="user_from_id", referencedColumnName = "user_id", nullable=false)
    private User from;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="user_to_id", referencedColumnName = "user_id", nullable=false)
    private User to;

    @Column(name="date", nullable=false)
    private Calendar date;

    @Column(name="deleted", nullable=false)
    private boolean deleted;

    public Message() {
        deleted = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}
