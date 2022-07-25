package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.response.CustomerServicePostRetrieveResponse;
import com.nhnacademy.marketgg.server.service.CustomerServicePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/shop/v1/admin/customer-services")
@RequiredArgsConstructor
public class AdminCustomerServiceController {

    private final CustomerServicePostService customerServicePostService;

    private static final String DEFAULT_ADMIN_CUSTOMER_SERVICE = "/shop/v1/admin/customer-services";

    @GetMapping("/oto-inquiries/{inquiryId}")
    public ResponseEntity<CustomerServicePostRetrieveResponse> retrieveOtoInquiry(@PathVariable final Long inquiryId) {
        CustomerServicePostRetrieveResponse inquiryResponse = customerServicePostService.retrieveOtoInquiry(inquiryId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries" + inquiryId))
                             .body(inquiryResponse);
    }

    @GetMapping("/oto-inquiries")
    public ResponseEntity<List<CustomerServicePostRetrieveResponse>> retrieveOtoInquiries(final Pageable pageable) {
        List<CustomerServicePostRetrieveResponse> inquiryResponses = customerServicePostService.retrieveOtoInquiries(
                pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_ADMIN_CUSTOMER_SERVICE + "oto-inquiries"))
                             .body(inquiryResponses);
    }

    @DeleteMapping("oto-inquiries/{inquiryId}")
    public ResponseEntity<Void> deleteOtoInquiries(@PathVariable final Long inquiryId) {
        customerServicePostService.deleteOtoInquiry(inquiryId);

        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create(DEFAULT_ADMIN_CUSTOMER_SERVICE + "/oto-inquiries" + inquiryId))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

}
