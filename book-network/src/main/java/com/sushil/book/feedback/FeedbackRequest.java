package com.sushil.book.feedback;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record FeedbackRequest(
    
    @Positive(message = "200")
    @Min(value = 0, message = "200")
    @Max(value = 5, message = "201")
    @NotNull(message = "202")
    Double rating,
    
    
    @NotNull(message = "2203")
    @NotEmpty(message = "2203")
    String comment,


    @NotNull(message = "2305")
    Integer bookId
) {
 
}
