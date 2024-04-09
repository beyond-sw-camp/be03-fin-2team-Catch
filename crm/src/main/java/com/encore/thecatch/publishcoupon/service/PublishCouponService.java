package com.encore.thecatch.publishcoupon.service;

import com.encore.thecatch.common.CatchException;
import com.encore.thecatch.common.ResponseCode;
import com.encore.thecatch.coupon.domain.Coupon;
import com.encore.thecatch.coupon.domain.CouponStatus;
import com.encore.thecatch.coupon.repository.CouponRepository;
import com.encore.thecatch.publishcoupon.domain.PublishCoupon;
import com.encore.thecatch.publishcoupon.dto.KafkaLimitedCoupon;
import com.encore.thecatch.publishcoupon.repository.PublishCouponRepository;
import com.encore.thecatch.user.domain.User;
import com.encore.thecatch.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PublishCouponService {

    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final PublishCouponRepository publishCouponRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public Coupon limitedCouponReceive(String data) throws JsonProcessingException {

        KafkaLimitedCoupon limitedCoupon = objectMapper.readValue(data, KafkaLimitedCoupon.class);

        User user = userRepository.findById(limitedCoupon.getUserId()).orElseThrow(()-> new CatchException(ResponseCode.USER_NOT_FOUND));
        Coupon coupon = couponRepository.findById(limitedCoupon.getCouponId()).orElseThrow(()->new CatchException(ResponseCode.COUPON_NOT_FOUND));

        List<PublishCoupon> publishCouponList = publishCouponRepository.findByCouponIdAndUserId(coupon.getId(),user.getId());

        if(publishCouponList.isEmpty()) {
            PublishCoupon publishCoupon = PublishCoupon.builder().coupon(coupon).user(user).couponStatus(CouponStatus.ISSUANCE).build();
            publishCouponRepository.save(publishCoupon);
        }

        return coupon;
    }


}