package com.sushil.book.consignment;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Consignment {

    @Id
    @GeneratedValue
    private Integer id;

    private String trackingId;

    @CreatedDate
    @Column(nullable = false, updatable = true)
    private LocalDateTime createdDate;

    private String type;
    private String senderName;
    private Integer senderMobile;
    private String reciverName;
    private Integer reciverMobile;
    private Integer pincode;
    private Integer volumetricWeight;
    private Integer actualWeight;
    private Integer finalWeight;
    private Integer chargableAmount;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

}
