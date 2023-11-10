package ru.skillbox.demo.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean sex;
    private Date birthDate;
    private Integer city;
    private String avatar;
    private String info;
    private String nickname;
    private String email;
    private String phone;
    private boolean deleted = Boolean.FALSE;

    public User(
            String firstName,
            String lastName,
            String middleName,
            Boolean sex,
            Date birthDate,
            Integer city,
            String avatar,
            String info,
            String nickname,
            String email,
            String phone
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.city = city;
        this.avatar = avatar;
        this.info = info;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }
}
