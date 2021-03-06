package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CouponRepositoryCustom {

    /**
     * couponId 에 해당하는 쿠폰을 반환합니다.
     *
     * @param couponId - 조회할 쿠폰의 식별번호 입니다.
     * @return 조회한 쿠폰을 DTO 로 반환합니다.
     * @since 1.0.0
     */
    Coupon findByCouponId(Long couponId);

    /**
     * 전체 쿠폰 목록을 반환합니다.
     *
     * @return 전체 쿠폰 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    Page<Coupon> findAllCoupons(Pageable pageable);

}
