package com.auth_service.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "Message", propOrder = {
        "id",
        "content",
        "from",
        "to",
        "date",
        "deleted"
}, namespace = "nekiUri/message")
@Table(name="messages_table")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="message_id", nullable=false, unique=true)
    @XmlElement(required=true)
    private Integer id;

    @Column(name="content", nullable=false)
    @XmlElement(required=true)
    private String content;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="user_from_id", referencedColumnName = "user_id", nullable=false)
    @XmlElement(required=true)
    private User from;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="user_to_id", referencedColumnName = "user_id", nullable=false)
    @XmlElement(required=true)
    private User to;

    @Column(name="date", nullable=false)
    @XmlElement(required=true)
    private Date date;

    @Column(name="deleted", nullable=false)
    @XmlElement(required=true)
    private boolean deleted = false;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}
