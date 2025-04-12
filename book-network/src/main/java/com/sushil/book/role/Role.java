package com.sushil.book.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sushil.book.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    @CreatedDate
    @Column(nullable = false,insertable = false)
    private LocalDate createdDate;

    @LastModifiedBy
    @Column(insertable = false)
    private LocalDate updatedDate;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

}
