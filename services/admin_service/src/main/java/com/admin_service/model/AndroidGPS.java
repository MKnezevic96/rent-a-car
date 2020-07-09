package com.admin_service.model;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "AndroidGps", propOrder = {
        "id",
        "longitude",
        "latitude",
        "email",
        "password",
        "car",
        "deleted"
}, namespace = "nekiUri/android_gps")
@Table(name="android_gps_table")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class AndroidGPS {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="android_gps_id", nullable=false, unique=true)
    @XmlElement(required=true)
    private Integer id;

    @Column(name="longitude")
    @XmlElement
    private Double longitude;

    @Column(name="latitude")
    @XmlElement
    private Double latitude;

    @Column(name="email", nullable=false)
    @XmlElement(required=true)
    private String email;

    @Column(name="password", nullable=false)
    @XmlElement(required=true)
    private String password;

    @Column(name="deleted", nullable=false)
    @XmlElement(required=true)
    private boolean deleted = false;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="cars_id_", referencedColumnName="cars_id", nullable=false)
    @XmlElement(required=true)
    private Cars car;

    public AndroidGPS () {

    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        this.car = car;
    }

}