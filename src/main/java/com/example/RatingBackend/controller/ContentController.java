package com.example.RatingBackend.controller;

import com.example.RatingBackend.entity.Content;
import com.example.RatingBackend.exception.ResourceNotFoundException;
import com.example.RatingBackend.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/content")
public class ContentController {

    @Autowired
    private ContentRepository contentRepository;

    @GetMapping
    public List<Content> getAllContent(){
        return this.contentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Content getContentById(@PathVariable(value = "id") int contentId){
        return this.contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));
    }

    @PostMapping
    public Content createContent(@RequestBody Content content){
        return this.contentRepository.save(content);
    }

    @PutMapping("/{id}")
    public Content updateContent(@RequestBody Content content, @PathVariable(value = "id") int contentId){
        Content existContent = this.contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        existContent.setTitle(content.getTitle());
        existContent.setContent(content.getContent());
        existContent.setImage(content.getImage());
        existContent.setCreateDate(content.getCreateDate());

        return this.contentRepository.save(existContent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Content> deleteContent(@PathVariable(value = "id") int contentId){
        Content existContent = this.contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found with id: " + contentId));

        this.contentRepository.delete(existContent);

        return ResponseEntity.ok(existContent);
    }
}
