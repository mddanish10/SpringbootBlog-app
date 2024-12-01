package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;


    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //convert DTO to entity
        System.out.println("Start of dto to entity");
        Post post =mapToEntity(postDto);
        System.out.println("end of dto to entity");
        Post newPost= postRepository.save(post);

        //convert entity to DTO
        System.out.println("Start of entity to dto");
        PostDto postResponse = mapToDto(newPost);
        System.out.println("end of entity to dto");
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPost() {
        System.out.println("start in getAllPost");
        List<Post> posts= postRepository.findAll();
        System.out.println("medium in getAllPost");
       return  posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    //Dto to Entity

    private Post mapToEntity(PostDto postDto){

        Post post= new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    //Entity to Dto
    private PostDto mapToDto(Post post){
        PostDto postResponse = new PostDto();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setDescription(post.getDescription());
        postResponse.setContent(post.getContent());
        return postResponse;
    }
}
