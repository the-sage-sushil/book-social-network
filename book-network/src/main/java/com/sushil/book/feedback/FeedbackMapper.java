package com.sushil.book.feedback;

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
            .id(feedback.id())
            .archived(false)
            .shareable(false).
        build()
        )
        .build();
    }


}
