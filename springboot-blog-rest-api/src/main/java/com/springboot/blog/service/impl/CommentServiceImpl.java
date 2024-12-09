package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.PostService;
import org.hibernate.annotations.Comments;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        //retrive posi by id
        Post post =postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));

        //set comment to post entity
        comment.setPost(post);

        //comment entity to db
        Comment newComment=commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrive the comments by posid
          List<Comment> comments= commentRepository.findByPostId(postId);
          return  comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        //retrive post by id
        Post post =postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));

        //retrive comment by id
         Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));

       if(!(comment.getPost().getId()).equals(post.getId())){
           throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not bleong to a post");
       }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

        //retrive post by id
        Post post =postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));

        //retrive comment by id
        Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));

        if(!(comment.getPost().getId()).equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not bleong to a post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedcomment = commentRepository.save(comment);
        return mapToDto(updatedcomment);
        }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        //retrive post by id
        Post post =postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));

        //retrive comment by id
        Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));

        if(!(comment.getPost().getId()).equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not bleong to a post");
        }

        commentRepository.delete(comment);
    }

    //entity to DTO
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
    //Dto to entity

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
