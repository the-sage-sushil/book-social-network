package com.sushil.book.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class FeedbackResponse {

    private Double note;

    private String comment;

    private boolean ownFeedback ;

}
