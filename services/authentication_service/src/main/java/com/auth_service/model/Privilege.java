package com.auth_service.model;

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
        name = "Privilege", propOrder = {
        "id",
        "name",
        "roles",
        "deleted"
}, namespace = "nekiUri/privilege")
@Table(name = "privilege_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Privilege implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @XmlElement(required=true)
    private Integer id;

    @Column(name = "name")
    @XmlElement
    private String name;

    @ManyToMany(mappedBy = "privileges")
    @XmlElement
    private Collection<Role> roles;

    @Column (name="deleted", nullable =false)
    @XmlElement
    boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Privilege() {
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
