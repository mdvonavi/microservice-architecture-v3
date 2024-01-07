package ru.skillbox.demo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.GenerationType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@EnableAutoConfiguration
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Post {
    private static final Logger log = LoggerFactory.getLogger("application");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long userId;
    private String descriptions;
    @CreationTimestamp
    private Date timestamp;
    @Transient
    private List<String> images;
    private boolean deleted = Boolean.FALSE;

    public Post(String title, Long userId, String descriptions, Date timestamp, List<String> images) {
        this.title = title;
        this.userId = userId;
        this.descriptions = descriptions;
        this.timestamp = timestamp;
        this.images = images;
    }

    public Post(String title, String descriptions) {
        this.title = title;
        this.descriptions = descriptions;
    }

    public Post(Long userId, String title, String descriptions) {
        this.userId = userId;
        this.title = title;
        this.descriptions = descriptions;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        Map<String, Object> post = new HashMap<>();

        if (this.id != null) {
            post.put("id", this.id);
        }
        if (this.title != null) {
            post.put("title", this.title);
        }
        if (this.descriptions != null) {
            post.put("descriptions", this.descriptions);
        }
        if (this.userId != null) {
            post.put("userId", this.userId);
        }
        if (this.images != null) {
            post.put("images", this.images);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String out = "{}";

        try {
            out = objectMapper.writeValueAsString(post);
            log.info("out string: " + out);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return out;
    }


}
