package com.sushil.book.user;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Token {

    @GeneratedValue
    @Id
    private Integer id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime validatedAt;


    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;


}
