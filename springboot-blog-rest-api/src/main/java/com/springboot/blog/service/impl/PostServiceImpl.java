package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    private PostRepository postRepository;


    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //convert DTO to entity
        System.out.println("Start of dto to entity");
        Post post = mapToEntity(postDto);
        System.out.println("end of dto to entity");
        System.out.println("value of id " + post.getId());
        System.out.println("value of title " + post.getTitle());
        System.out.println("value of description " + post.getDescription());
        System.out.println("value of content " + post.getContent());
        Post newPost = postRepository.save(post); // I belive it is failing here

        //convert entity to DTO
        System.out.println("Start of entity to dto");
        PostDto postResponse = mapToDto(newPost);
        System.out.println("end of entity to dto");
        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        System.out.println("start in getAllPost");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        //get content from pageobjetc
        List<Post> listOfPage = posts.getContent();
        System.out.println("medium in getAllPost");
        List<PostDto> content = listOfPage.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setPageSize(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        System.out.println("End in getAllPost");
        return postResponse;

    }

    @Override
    public PostDto getPostById(Long id) {
        System.out.println("Start of getPostById ");
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        System.out.println("End of getPostById ");
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        System.out.println("Start of updatePost ");
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);
        System.out.println("End of getPostById ");
        return mapToDto(updatePost);
    }

    @Override
    public void deletePostById(Long id) {
        System.out.println("Start of deletePostById ");
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);
        System.out.println("End of deletPostById ");
    }

    //Dto to Entity
    private Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }

    //Entity to Dto
    private PostDto mapToDto(Post post) {
        PostDto postResponse = modelMapper.map(post, PostDto.class);
//        PostDto postResponse = new PostDto();
//        postResponse.setId(post.getId());
//        postResponse.setTitle(post.getTitle());
//        postResponse.setDescription(post.getDescription());
   //     postResponse.setContent(post.getContent());
        return postResponse;
    }
}
