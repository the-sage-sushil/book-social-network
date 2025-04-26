package com.sushil.book.user;

import com.sushil.book.book.Book;
import com.sushil.book.history.BookTransactionHistory;
import com.sushil.book.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@SuperBuilder
@Entity
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column
    private String password;
    private LocalDate dateOfBirth;
    private boolean isEnabled;
    private boolean accountLocked;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDate createdDate;

    @LastModifiedBy
    @Column(insertable = false)
    private LocalDate updatedDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    @OneToMany(mappedBy = "user")
    private List<BookTransactionHistory> histories;


    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().
                map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }




    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public String fullNam() {
        return firstName +" "+lastName;
    }
}
