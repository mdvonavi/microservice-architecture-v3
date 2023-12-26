package ru.skillbox.demo.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class UserDto {
    private Boolean sex;
    private Date birthDate;
    private Integer city;
    private String avatar;
    private String info;
    private String nickname;

    public UserDto(
            Boolean sex,
            Date birthDate,
            Integer city,
            String avatar,
            String info,
            String nickname
    ) {
        this.sex = sex;
        this.birthDate = birthDate;
        this.city = city;
        this.avatar = avatar;
        this.info = info;
        this.nickname = nickname;
    }

    public UserDto() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    @Override
    public String toString() {
        Map<String,Object> user = new HashMap<>();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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

        ObjectMapper objectMapper = new ObjectMapper();

        String out = "{}";

        try {
            out = objectMapper.writeValueAsString(user);

        } catch (JsonProcessingException ignored) {
        }

        return out;
    }
}