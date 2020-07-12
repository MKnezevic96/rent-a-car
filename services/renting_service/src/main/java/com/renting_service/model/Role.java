package com.renting_service.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "Role", propOrder = {
        "id",
        "name",
        "user",
        "privileges",
        "deleted"
}, namespace = "nekiUri/role")
@Table(name = "role_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @XmlElement(required=true)
    private Integer id;

    @Column(name = "name")
    @XmlElement
    private String name;

    @ManyToMany(mappedBy = "role")
    @XmlElement
    private Collection<User> user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    @XmlElement
    private Collection<Privilege> privileges;

    @Column(name="deleted", nullable=false)
    @XmlElement(required=true)
    private boolean deleted;

    public Role() {
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUser() {
        return user;
    }

    public void setUser(Collection<User> user) {
        this.user = user;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

}
