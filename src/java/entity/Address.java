//package entity;
//
//import java.io.Serializable;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "model")
//public class Address implements Serializable {
//
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name = "first_name", length = 45, nullable = false)
//    private String first_name;
//
//    @Column(name = "last_name", length = 45, nullable = false)
//    private String last_name;
//
//    @Column(name = "line1")
//    private String line1;
//
//    @Column(name = "line2")
//    private String line2;
//
//    @Column(name = "postal_code", length = 10, nullable = false)
//    private String postal_code;
//
//    @ManyToOne
//    @Column(name = "city_id")
//    private City city;
//
//    @Column(name = "mobile", length = 10, nullable = false)
//    private String mobile;
//
//    @ManyToOne
//    @Column(name = "user_id")
//    private User user_id;
//
//    public Address() {
//    }
//
//    public void setFirst_name(String first_name) {
//        this.first_name = first_name;
//    }
//
//    public void setLast_name(String last_name) {
//        this.last_name = last_name;
//    }
//
//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public String getLast_name() {
//        return last_name;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getLine1() {
//        return line1;
//    }
//
//    public String getLine2() {
//        return line2;
//    }
//
//    public String getPostal_code() {
//        return postal_code;
//    }
//
//    public City getCity() {
//        return city;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public User getUser_id() {
//        return user_id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setLine1(String line1) {
//        this.line1 = line1;
//    }
//
//    public void setLine2(String line2) {
//        this.line2 = line2;
//    }
//
//    public void setPostal_code(String postal_code) {
//        this.postal_code = postal_code;
//    }
//
//    public void setCity(City city) {
//        this.city = city;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public void setUser_id(User user_id) {
//        this.user_id = user_id;
//    }
//
//    public void setUser(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//}
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Jude Ravindu
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    public Address() {
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false, length = 45)
    private String first_name;

    @Column(name = "last_name", nullable = false, length = 45)
    private String last_name;

    @Column(name = "line1", nullable = false)
    private String line1;

    @Column(name = "line2", nullable = false)
    private String line2;

    @Column(name = "mobile", length = 45, nullable = false)
    private String mobile;

    @Column(name = "postal_code", length = 10, nullable = false)
    private String postal_code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
