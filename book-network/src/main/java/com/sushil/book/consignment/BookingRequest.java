package com.sushil.book.consignment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookingRequest {

    @NotEmpty(message = "Tracking ID is mandatory")
    @NotBlank(message = "Tracking ID is mandatory")
    private String trackingId;

    @NotEmpty(message = "senderName ID is mandatory")
    @NotBlank(message = "senderName ID is mandatory")
    private String senderName;
    @NotEmpty(message = "senderMobile ID is mandatory")
    @NotBlank(message = "senderMobile ID is mandatory")
    private Integer senderMobile;
    
    @NotEmpty(message = "reciverName ID is mandatory")
    @NotBlank(message = "reciverName ID is mandatory")
    private String reciverName;
    @NotEmpty(message = "reciverMobile ID is mandatory")
    @NotBlank(message = "reciverMobile ID is mandatory")
    private Integer reciverMobile;
    @NotEmpty(message = "pincode ID is mandatory")
    @NotBlank(message = "pincode ID is mandatory")
    private Integer pincode;
    private Integer volumetricWeight;
    @NotEmpty(message = "actualWeight ID is mandatory")
    @NotBlank(message = "actualWeight ID is mandatory")
    private Integer actualWeight;
    private Integer finalWeight;
    @NotEmpty(message = "chargableAmount ID is mandatory")
    @NotBlank(message = "chargableAmount ID is mandatory")
    private Integer chargableAmount;

}
