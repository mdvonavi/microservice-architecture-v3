package ru.skillbox.demo.service;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
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
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final PostImageRepository postImageRepository;

    public PostService(MinioClient minioClient, PostRepository postRepository, PostImageRepository postImageRepository) {
        this.minioClient = minioClient;
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
    }
    public String createPost(Post post) {
        Post savedPost = postRepository.save(post);
        return String.format("post added with id = %s",  savedPost.getId());
    }

    public Object getPost(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getUserPosts(long user_id) {
        return postRepository.findByUserId(user_id);
    }

    private List<String> saveImagesToMinio(List<MultipartFile> imageFiles) throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {

        List<String> imageIds = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            String imageId = UUID.randomUUID().toString();


            InputStream stream = imageFile.getInputStream();

            ObjectWriteResponse response = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(imageId)
                            .stream(stream, imageFile.getSize(), -1)
                            .build()
            );

            stream.close();

            // Сохранение идентификатора изображения
            imageIds.add(imageId);
        }

        return imageIds;
    }

    public String savePostWithImages(Post post, List<MultipartFile> imageFiles) {
        try {
            Post savedPost = postRepository.save(post);
            // Сохранение изображений в MinIO и получение их идентификаторов
            List<String> imageIds = saveImagesToMinio(imageFiles);

            // Связывание изображений с постом
            List<PostImage> postImages = createPostImageEntities(savedPost, imageIds);



            return "post added";
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | MinioException e) {
            e.printStackTrace();
            // Обработка ошибки сохранения
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
}
