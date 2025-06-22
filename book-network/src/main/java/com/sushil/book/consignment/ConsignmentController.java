package com.sushil.book.consignment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("booking")
@RequiredArgsConstructor
public class ConsignmentController {

    private ConsignmentService consignmentService;

    @PostMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> saveBooking(
        @RequestBody @Valid BookingRequest request) {
        
            consignmentService.save(request);
        
        return ResponseEntity.accepted().build();
    }
    

}
