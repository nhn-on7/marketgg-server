package com.nhnacademy.marketgg.server.repository.coupon;

import com.nhnacademy.marketgg.server.entity.Coupon;
import com.nhnacademy.marketgg.server.entity.QCoupon;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    QCoupon coupon = QCoupon.coupon;

    @Override
    public Coupon findByCouponId(Long couponId) {

        return from(coupon)
                .select(selectAllCouponColumns())
                .where(coupon.id.eq(couponId))
                .fetchOne();
    }

    @Override
    public Page<Coupon> findAllCoupons(Pageable pageable) {

        List<Coupon> result = from(coupon)
                .select(selectAllCouponColumns())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    private ConstructorExpression<Coupon> selectAllCouponColumns() {

        return Projections.constructor(Coupon.class,
                coupon.id,
                coupon.name,
                coupon.type,
                coupon.expiredDate,
                coupon.minimumMoney,
                coupon.discountAmount);
    }

}
