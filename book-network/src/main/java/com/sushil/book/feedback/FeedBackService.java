package com.sushil.book.feedback;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.sushil.book.book.Book;
import com.sushil.book.book.BookRepository;
import com.sushil.book.common.PageResponse;
import com.sushil.book.exception.OperationNotPermittedException;
import com.sushil.book.user.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class FeedBackService {

    private final BookRepository bookRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public Integer saveFeedback(FeedbackRequest feedback, Authentication connectedUser) {

        Book book = bookRepository.findById(feedback.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found by the given id::" + feedback.bookId()));

        if (book.isArchived() && !book.isShareable()) {
            throw new OperationNotPermittedException("This book is not eligible for borrowing");
        }
        User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(user.getId(), book.getId())) {
            throw new OperationNotPermittedException("You don't need to borrow your book!");
        }

        Feedback feedback2 = feedbackMapper.toFeedback(feedback);
        feedbackRepository.save(feedback2);
        return feedbackRepository.save(feedback2).getId();
    }

    public PageResponse<FeedbackResponse> findAllBookFeedback(Integer bookId, int page, int size, Authentication connectedUser) {
        
        Pageable pageable = PageRequest.of(page, size);

        User user = ((User) connectedUser.getPrincipal());

        Page<Feedback> feedbacks = feedbackRepository.findAllBookFeedbackById(bookId,pageable);
        
        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
            .map(f -> feedbackMapper.toFeedbackResponse(f,user.getId()))
            .toList();
        return new PageResponse<>(
            feedbackResponses,
            feedbacks.getNumber(),
            feedbacks.getSize(),
            feedbacks.getTotalElements(),
            feedbacks.getTotalPages(),
            feedbacks.isFirst(),
            feedbacks.isLast()
        );

    }

}
