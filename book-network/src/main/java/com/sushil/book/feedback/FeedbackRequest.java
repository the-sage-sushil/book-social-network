package com.sushil.book.feedback;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record FeedbackRequest(
    
    @Positive(message = "200")
    @Min(value = 0, message = "200")
    @Max(value = 5, message = "201")
    @NotNull(message = "202")
    double rating,
    
    
    @NotNull(message = "203")
    @NotEmpty(message = "203")
    @NotBlank(message = "203")
    String commnet,


    @NotNull(message = "205")
    @NotEmpty(message = "205")
    @NotBlank(message = "205")
    Integer bookId
) {
 
}
