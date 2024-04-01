package com.encore.thecatch.post.service;

import com.encore.thecatch.common.CatchException;
import com.encore.thecatch.common.ResponseCode;
import com.encore.thecatch.common.s3.S3Service;
import com.encore.thecatch.post.entity.Image;
import com.encore.thecatch.post.entity.Post;
import com.encore.thecatch.post.dto.request.AddImageReq;
import com.encore.thecatch.post.dto.request.CreatePostReq;
import com.encore.thecatch.post.dto.request.UpdatePostReq;
import com.encore.thecatch.post.dto.response.AddImageRes;
import com.encore.thecatch.post.dto.response.DetailPostRes;
import com.encore.thecatch.post.dto.response.UpdatePostRes;
import com.encore.thecatch.post.repository.ImageRepository;
import com.encore.thecatch.post.repository.PostRepository;
import com.encore.thecatch.user.domain.User;
import com.encore.thecatch.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    @PreAuthorize("hasAuthority('USER')")
    public Post createPost(CreatePostReq createPostReq) {
        List<String> imgPaths = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new CatchException(ResponseCode.USER_NOT_FOUND));

        if (createPostReq.getImages() != null) {
            imgPaths = s3Service.uploadList("Post", createPostReq.getImages());
        }

        Post newPost = createPostReq.toEntity(imgPaths, user);
        postRepository.save(newPost);

        if (imgPaths != null) {
            List<String> imgList = new ArrayList<>();

            for (String imgUrl : imgPaths) {
                Image img = new Image(imgUrl, newPost);
                imageRepository.save(img);
                imgList.add(img.getImgUrl());
            }
        }

        return newPost;
    }


    public Post deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CatchException(ResponseCode.POST_NOT_FOUND));
        post.isDelete();
        return post;
    }

    @PreAuthorize("hasAuthority('USER')")
    public DetailPostRes detailPost(Long id) {
        activePost(id);
        Post post = postRepository.findById(id).orElseThrow(() -> new CatchException(ResponseCode.POST_NOT_FOUND));
        List<Image> imgList = imageRepository.findAllByPostId(post.getId());
        List<String> imgUrl = new ArrayList<>();
        for (Image image : imgList) {
            imgUrl.add(image.getImgUrl());
        }

        return DetailPostRes.from(post, imgUrl);
    }

    @PreAuthorize("hasAuthority('USER')")
    public UpdatePostRes updatePost(Long id, UpdatePostReq updatePostReq) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CatchException(ResponseCode.POST_NOT_FOUND));

        post.updatePost(updatePostReq.getTitle(), updatePostReq.getCategory(), updatePostReq.getContents());

        return UpdatePostRes.from(post);
    }


    @PreAuthorize("hasAuthority('USER')")
    public Page<Post> myPostList(Long id, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new CatchException(ResponseCode.USER_NOT_FOUND));
        return postRepository.findAllByUserIdAndActive(user.getId(), pageable, 0);
    }


    // 삭제된 게시글 확인
    public void activePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CatchException(ResponseCode.POST_NOT_FOUND));
        if (post.getActive() == 1) {
            throw new CatchException(ResponseCode.POST_NOT_ACTIVE);
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    public AddImageRes addImage(Long id, AddImageReq addImageReq) {
        String imgPath = null;

        Post post = postRepository.findById(id).orElseThrow(() -> new CatchException(ResponseCode.POST_NOT_FOUND));

        if (addImageReq.getImage() != null) {
            imgPath = s3Service.upload("Post", addImageReq.getImage());
            Image img = new Image(imgPath, post);
            imageRepository.save(img);
        }

        return AddImageRes.from(imgPath);
    }

    @PreAuthorize("hasAuthority('USER')")
    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(()->new CatchException(ResponseCode.IMAGE_NOT_FOUND));
        String imageUrl = image.getImgUrl();

        imageRepository.delete(image);
        s3Service.deleteFile(imageUrl);
    }
}
