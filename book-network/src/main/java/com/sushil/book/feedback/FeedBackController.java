package com.sushil.book.feedback;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("feedbacks")
@Tag(name = "Feedback")
@RequiredArgsConstructor
public class FeedBackController {

    private final FeedBackService feedbackService;


    @PostMapping("/{book-id}")
    public ResponseEntity<Integer> postFeedback(
        @PathVariable("book-id") Integer bookId,
        @RequestBody @Valid FeedbackRequest feedback,
        Authentication connectedUser){
        
        return ResponseEntity.ok(feedbackService.saveFeedback(bookId,feedback,connectedUser));
    }
    
}
