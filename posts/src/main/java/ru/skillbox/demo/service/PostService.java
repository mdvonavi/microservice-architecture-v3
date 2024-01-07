package ru.skillbox.demo.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.demo.UserHandler;
import ru.skillbox.demo.entity.Post;
import ru.skillbox.demo.entity.PostImage;
import ru.skillbox.demo.repository.PostImageRepository;
import ru.skillbox.demo.repository.PostRepository;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private final MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final PostImageRepository postImageRepository;

    @Autowired
    private final UserHandler userHandler;


    public PostService(
            MinioClient minioClient,
            PostRepository postRepository,
            PostImageRepository postImageRepository, UserHandler userHandler) {
        this.minioClient = minioClient;
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.userHandler = userHandler;
    }

    public String createPost(Post post) {
        Post savedPost = postRepository.save(post);
        return String.format("post added with id = %s",  savedPost.getId());
    }

    public Object getPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        post.setImages(getImageListByPostId(id));
        return post;
    }

    private List<String> getImageListByPostId(long id) {
        List<PostImage> imageList = postImageRepository.findByPostId(id);
        List<String> stringList = new ArrayList<>();

        for (PostImage image : imageList) {
            stringList.add(image.getEtag());
        }
        return stringList;
    }

    public String deletePost(long id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        postRepository.deleteById(id);
        return String.format("Post deleted with id = %s", id);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getUserPosts(long userId) {
        return postRepository.findByUserId(userId);
    }

    private List<String> saveImagesToMinio(List<MultipartFile> imageFiles)
            throws IOException,
            MinioException,
            NoSuchAlgorithmException,
            InvalidKeyException {

        List<String> imageIds = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            String imageId = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();

            try (InputStream stream = imageFile.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(imageId)
                                .stream(stream, imageFile.getSize(), -1)
                                .build()
                );
            }

            // Сохранение идентификатора изображения
            imageIds.add(imageId);
        }

        return imageIds;
    }

    public String savePost(Post post, List<MultipartFile> imageFiles) {
        if (post.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!userHandler.existsById(post.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        try {
            Post savedPost = postRepository.save(post);

            if (imageFiles != null) {
                List<String> imageIds = saveImagesToMinio(imageFiles);
                createPostImageEntities(savedPost, imageIds);
            }

            return "post saved with id = " + savedPost.getId().toString();
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | MinioException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<PostImage> createPostImageEntities(Post post, List<String> imageIds) {
        List<PostImage> postImages = new ArrayList<>();

        for (String imageId : imageIds) {
            PostImage postImage = new PostImage();
            postImage.setPostId(post.getId());
            postImage.setEtag(imageId);

            postImages.add(postImageRepository.save(postImage));
        }

        return postImages;
    }

    public String updatePost(String postString, List<MultipartFile> imageFiles) {
        return "updated";
    }
}
