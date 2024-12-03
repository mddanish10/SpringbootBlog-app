package com.springboot.blog.controller;


import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import com.springboot.blog.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    //for loose coupling we inject interface not class as controller depends on services
    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Creating the post request
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        System.out.println("Inside CreatePost controller");
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //Retriving all the post
    @GetMapping
    public List<PostDto> getAllPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize
    ){
        System.out.println("Inside getallPost controller");
        return  postService.getAllPost(pageNo,pageSize);
    }

    //Retriving the post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") Long id){
        System.out.println("Inside getPostById controller");
        return  ResponseEntity.ok(postService.getPostById(id));
    }

    //Updating the post by id
    @PutMapping("/{id}")
     public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable(name="id") Long id ){
        System.out.println("Inside updatePost controller");
       PostDto updatePost= postService.updatePost(postDto,id);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
     }

     //Delete Post by id
     @DeleteMapping("/{id}")
     public ResponseEntity<String> deletePostById(@PathVariable(name="id") Long id){
         System.out.println("Inside deletePostById controller");
        postService.deletePostById(id);
        return new ResponseEntity<>("Post is deleted Successfully", HttpStatus.OK);
     }
}
