package com.sushil.book.feedback;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.sushil.book.book.Book;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FeedbackMapper {

    public Feedback toFeedback(FeedbackRequest feedback) {
    
        return Feedback.builder()
        .note(feedback.rating())
        .comment(feedback.commnet())
        .book(
            Book.builder()
            .id(feedback.bookId())
            .archived(false)
            .shareable(false).
        build()
        )
        .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer userId) {
       
        return FeedbackResponse.builder()
        .note(feedback.getNote())
        .comment(feedback.getComment())
        .ownFeedback(Objects.equals(feedback.getCreatedBy(), userId))
        .build();
    }


}
