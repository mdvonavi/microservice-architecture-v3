package ru.skillbox.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@EnableAutoConfiguration
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User {
    private static final Logger log = LoggerFactory.getLogger("UserService");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean sex;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

    //minimum required user fields
    public User(
            String nickname,
            String email
    ) {
        this.nickname = nickname;
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
        Map<String, Object> user = new HashMap<>();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if (this.id != null) {
            user.put("id", this.id);
        }
        if (this.firstName != null) {
            user.put("firstName", this.firstName);
        }
        if (this.lastName != null) {
            user.put("lastName", this.lastName);
        }
        if (this.middleName != null) {
            user.put("middleName", this.middleName);
        }
        if (this.sex != null) {
            user.put("sex", this.sex);
        }
        if (this.birthDate != null) {
            user.put("birthDate ", df.format(this.birthDate));
        }
        if (this.city != null) {
            user.put("city", this.city);
        }
        if (this.avatar != null) {
            user.put("avatar", this.avatar);
        }
        if (this.info != null) {
            user.put("info", this.info);
        }
        user.put("nickname", this.nickname);
        user.put("email", this.email);
        if (this.phone != null) {
            user.put("phone", this.phone);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String out = "{}";

        try {
            out = objectMapper.writeValueAsString(user);
            log.info("out string: " + out);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return out;
    }
}