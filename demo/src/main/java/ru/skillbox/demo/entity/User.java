package ru.skillbox.demo.entity;

import liquibase.repackaged.org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Date;

@Entity
@EnableAutoConfiguration
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean sex;
    private Date birthDate;
    private Integer city;
    private String avatar;
    private String info;
    private String firstName;
    private String lastName;
    private String middleName;
    private String nickname;
    private String email;
    private String phone;
    private boolean deleted = Boolean.FALSE;

    public User(
            Long id,
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
        this.id = id;
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

    public String getMiddleName() {
        return middleName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getSex() {
        return sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Integer getCity() {
        return city;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getInfo() {
        return info;
    }

    public String getNickname() {
        return nickname;
    }

    public User() {
    }

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
        return "{" +
                "id:" + this.id + "," +
                "sex:" + this.sex + "," +
                "birthDate:" + DateFormatUtils.format(this.birthDate, "dd-MM-yyyy") + "," +
                "city:" + this.city + ","+
                "avatar:" + this.avatar + "," +
                "info:" + this.info + "," +
                "firstName:" + this.firstName + "," +
                "lastName:" + this.lastName + "," +
                "middleName:" + this.middleName + ","+
                "nickname:" + this.nickname + ","+
                "email:" + this.email + "," +
                "phone:" + this.phone +
                "}";
    }
}