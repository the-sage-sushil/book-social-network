package com.sushil.book.feedback;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sushil.book.common.PageResponse;

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


    @PostMapping()
    public ResponseEntity<Integer> postFeedback(
        @RequestBody @Valid FeedbackRequest feedback,
        Authentication connectedUser){
        
        return ResponseEntity.ok(feedbackService.saveFeedback(feedback,connectedUser));
    }

    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> findAllBookFeedback(
        @PathVariable("book-id") Integer bookId,
        @RequestParam(name = "page", defaultValue = "0",required = false) int page,
        @RequestParam(name = "size", defaultValue = "10",required = false) int size,
        Authentication connectedUser
        
        ) {
        return ResponseEntity.ok(feedbackService.findAllBookFeedback(bookId, page, size, connectedUser));
    }
    
    
}
