package com.nhnacademy.marketgg.server.controller;

import com.nhnacademy.marketgg.server.dto.request.CouponDto;
import com.nhnacademy.marketgg.server.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * 쿠폰 관련 Rest Controller 입니다.
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    private static final String DEFAULT_COUPON = "/admin/coupons";

    /**
     * 입력한 정보로 쿠폰을 등록하는 PostMapping 을 지원합니다.
     *
     * @param couponDto - 쿠폰을 등록하기 위한 정보를 담고 있는 DTO 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PostMapping
    public ResponseEntity<Void> createCoupon(@RequestBody final CouponDto couponDto) {
        couponService.createCoupon(couponDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .location(URI.create(DEFAULT_COUPON))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * couponId 에 해당하는 쿠폰을 조회하는 GetMapping 을 지원합니다.
     *
     * @param couponId - 조회할 쿠폰의 식별번호 입니다.
     * @return 조회한 쿠폰의 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDto> retrieveCoupon(@PathVariable final Long couponId) {
        CouponDto couponResponse = couponService.retrieveCoupon(couponId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_COUPON + "/" + couponId))
                             .body(couponResponse);
    }

    /**
     * 전체 쿠폰 목록을 조회하는 GetMapping 을 지원합니다.
     *
     * @return 전체 쿠폰 목록 DTO 를 List 로 반환합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<List<CouponDto>> retrieveCoupons(Pageable pageable) {
        List<CouponDto> couponResponses = couponService.retrieveCoupons(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_COUPON))
                             .body(couponResponses);
    }

    /**
     * 선택한 쿠폰을 수정하는 PutMapping 을 지원합니다.
     *
     * @param couponId  - 수정할 쿠폰의 식별번호입니다.
     * @param couponDto - 수정할 정보를 담은 DTO 입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @PutMapping("/{couponId}")
    public ResponseEntity<Void> updateCoupon(@PathVariable final Long couponId,
                                             @RequestBody final CouponDto couponDto) {

        couponService.updateCoupon(couponId, couponDto);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_COUPON))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

    /**
     * 선택한 쿠폰을 삭제하는 DeleteMapping 을 지원합니다.
     *
     * @param couponId - 삭제할 쿠폰의 식별번호입니다.
     * @return Mapping URI 를 담은 응답 객체를 반환합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable final Long couponId) {
        couponService.deleteCoupon(couponId);

        return ResponseEntity.status(HttpStatus.OK)
                             .location(URI.create(DEFAULT_COUPON))
                             .contentType(MediaType.APPLICATION_JSON)
                             .build();
    }

}
