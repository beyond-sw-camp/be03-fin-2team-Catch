package com.encore.thecatch.coupon.service;

import com.encore.thecatch.admin.domain.Admin;
import com.encore.thecatch.admin.repository.AdminRepository;
import com.encore.thecatch.common.CatchException;
import com.encore.thecatch.common.ResponseCode;
import com.encore.thecatch.common.dto.Role;
import com.encore.thecatch.common.redis.RedisService;
import com.encore.thecatch.common.util.AesUtil;
import com.encore.thecatch.company.domain.Company;
import com.encore.thecatch.company.repository.CompanyRepository;
import com.encore.thecatch.coupon.domain.Coupon;
import com.encore.thecatch.coupon.domain.CouponStatus;
import com.encore.thecatch.coupon.dto.*;
import com.encore.thecatch.coupon.repository.CouponQueryRepository;
import com.encore.thecatch.coupon.repository.CouponRepository;
import com.encore.thecatch.mail.service.EmailSendService;
import com.encore.thecatch.notification.service.NotificationService;
import com.encore.thecatch.receivecoupon.domain.ReceiveCoupon;
import com.encore.thecatch.receivecoupon.repository.ReceiveCouponRepository;
import com.encore.thecatch.user.domain.User;
import com.encore.thecatch.user.dto.request.PublishUserDto;
import com.encore.thecatch.user.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final ReceiveCouponRepository receiveCouponRepository;
    private final CouponQueryRepository couponQueryRepository;
    private final NotificationService notificationService;
    private final RedisService redisService;
    private final AdminRepository adminRepository;
    public final FirebaseMessaging firebaseMessaging;
    public final EmailSendService emailSendService;
    private final AesUtil aesUtil;


    public CouponService(CompanyRepository companyRepository, UserRepository userRepository, CouponRepository couponRepository, ReceiveCouponRepository receiveCouponRepository, CouponQueryRepository couponQueryRepository, NotificationService notificationService, RedisService redisService, AdminRepository adminRepository, FirebaseMessaging firebaseMessaging, EmailSendService emailSendService, AesUtil aesUtil) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
        this.receiveCouponRepository = receiveCouponRepository;
        this.couponQueryRepository = couponQueryRepository;
        this.notificationService = notificationService;
        this.redisService = redisService;
        this.adminRepository = adminRepository;
        this.firebaseMessaging = firebaseMessaging;
        this.emailSendService = emailSendService;
        this.aesUtil = aesUtil;
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Coupon createCoupon(CouponReqDto couponReqDto){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Admin admin = adminRepository.findByEmployeeNumber(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.ADMIN_NOT_FOUND));
            Long companyId = admin.getCompany().getId();
            // UUID(Universally Unique Identifier)란?
            //범용 고유 식별자를 의미하며 중복이 되지 않는 유일한 값을 구성하고자 할때 주로 사용됨(ex)세션 식별자, 쿠키 값, 무작위 데이터베이스값 )
            String code = UUID.randomUUID().toString();
            Coupon new_coupon = Coupon.builder()
                    .name(couponReqDto.getName())
                    .code(code)
                    .quantity(couponReqDto.getQuantity())
                    .couponStatus(CouponStatus.ISSUANCE)
                    .startDate(LocalDate.parse(couponReqDto.getStartDate()))
                    .endDate(LocalDate.parse(couponReqDto.getEndDate()))
                    .companyId(admin.getCompany())
                    .build();
            Coupon coupon = couponRepository.save(new_coupon);
            return coupon;
        }catch(CatchException e){
            throw new CatchException(ResponseCode.EXISTING_COUPON_NAME);
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<CouponResDto> findAll(Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findByEmployeeNumber(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.ADMIN_NOT_FOUND));
        Company company = admin.getCompany();
        Page<Coupon> coupons = couponRepository.findByCompanyId(company, pageable);
        return coupons.map(CouponResDto::toCouponResDto);
    }

<<<<<<< Updated upstream
    public List<CouponResDto> findMyAll(){
=======
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<CouponFindResDto> searchCoupon(SearchCouponCondition searchCouponCondition, Pageable pageable) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findByEmployeeNumber(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.ADMIN_NOT_FOUND));
        Company company = admin.getCompany();
        return couponQueryRepository.findCouponList(searchCouponCondition, company, pageable);
    }

    public List<CouponResDto> findReceivable() {
>>>>>>> Stashed changes
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.USER_NOT_FOUND));
        Company company =  user.getCompany();
        List<Coupon> coupons = couponRepository.findByCompanyIdAndCouponStatus(company, CouponStatus.PUBLISH);
        List<CouponResDto> couponResDtos = new ArrayList<>();
        for(Coupon coupon : coupons){
            couponResDtos.add(CouponResDto.toCouponResDto(coupon));
        }
        return couponResDtos;
    }
    public Page<CouponResDto> findMyAll(Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.USER_NOT_FOUND));
        Page<ReceiveCoupon> coupons = receiveCouponRepository.findByUserId(user.getId(), pageable);
        List<CouponResDto> couponResDtos = new ArrayList<>();
        return coupons.map(CouponResDto::publishToCouponDto);
    }

    public CouponResDto findById(Long id) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
        return CouponResDto.toCouponResDto(coupon);
    }

    public Page<CouponFindResDto> searchCoupon(SearchCouponCondition searchCouponCondition, Pageable pageable) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findByEmployeeNumber(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.USER_NOT_FOUND));
        return couponQueryRepository.findCouponList(searchCouponCondition, admin.getCompany(), pageable);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Coupon publish(Long id, PublishUserDto publishUserDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findByEmployeeNumber(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.ADMIN_NOT_FOUND));
        Long companyId = admin.getCompany().getId();
        Coupon coupon = couponRepository.findById(id).orElseThrow(() -> new CatchException(ResponseCode.COUPON_NOT_FOUND));
        if(!coupon.getCompanyId().getId().equals(companyId)){
            throw new CatchException(ResponseCode.ACCESS_DENIED);
        }
        if(coupon.getCouponStatus() == CouponStatus.PUBLISH){
            throw new CatchException(ResponseCode.ALREADY_PUBLISH_COUPON);
        }
        List<Long> userIds = publishUserDto.getUserIds();
        List<User> users = new ArrayList<>();
        for(Long userId : userIds){
            users.add(userRepository.findById(userId).orElseThrow(() -> new CatchException(ResponseCode.USER_NOT_FOUND)));
        }
        for(User user : users){
            String fcmToken = redisService.getValues(String.format("%s:%s", "PushToken", user.getEmail()));
            if(fcmToken.equals("false")){
                boolean confirm = false;
                notificationService.saveNotification(user, confirm, coupon);
            }else {
                boolean confirm = true;
                notificationService.saveNotification(user, confirm, coupon);
                Message message = Message.builder()
                        .setToken(fcmToken)
                        .setNotification(Notification.builder()
                                .setTitle(coupon.getName())
                                .setBody(coupon.getCode())
                                .build())
                        .build();
                try {
                    String response = firebaseMessaging.send(message);
                    System.out.println("Successfully sent message: " + response);
                } catch (FirebaseMessagingException e) {
                    System.err.println("Error sending message: " + e.getMessage());
                }
            }
            emailSendService.createCouponEmail(coupon, aesUtil.aesCBCDecode(admin.getEmail()));
        }
        coupon.publishCoupon();
        return coupon;
    }


    @Transactional
    public Coupon receive(CouponReceiveDto couponReceiveDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.USER_NOT_FOUND));
        Coupon coupon = couponRepository.findByCode(couponReceiveDto.getCode()).orElseThrow(()->new CatchException(ResponseCode.COUPON_NOT_FOUND));
        if(!coupon.getCompanyId().equals(user.getCompany())){
            throw new CatchException(ResponseCode.NON_RECEIVABLE_COUPON);
        }
        if(receiveCouponRepository.findByCouponIdAndUserId(coupon.getId(), user.getId()) != null){
            throw new CatchException(ResponseCode.ALREADY_RECEIVED_COUPON);
        }
        if(coupon.getCouponStatus().equals(CouponStatus.PUBLISH)){
            ReceiveCoupon receiveCoupon = ReceiveCoupon.builder()
                    .user(user)
                    .coupon(coupon)
                    .couponStatus(CouponStatus.RECEIVE)
                    .build();
            receiveCouponRepository.save(receiveCoupon);
        }
        return coupon;
    }

    public Coupon couponUpdate(Long id, CouponReqDto couponReqDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.USER_NOT_FOUND));
        Long companyId = user.getCompany().getId();
        Coupon coupon = couponRepository.findById(id).orElseThrow(()->new CatchException(ResponseCode.COUPON_NOT_FOUND));
        if(coupon.getCouponStatus().equals(CouponStatus.ISSUANCE) && user.getRole().equals(Role.ADMIN) && coupon.getCompanyId() == user.getCompany()){
            coupon.updateCoupon(couponReqDto);
            couponRepository.save(coupon);
        }else{
            throw new CatchException(ResponseCode.COUPON_CAN_NOT_UPDATE);
        }
        return coupon;
    }
    @Transactional
    public Coupon couponDelete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.USER_NOT_FOUND));
        Coupon coupon = couponRepository.findById(id).orElseThrow(()->new CatchException(ResponseCode.COUPON_NOT_FOUND));
        if(coupon.getCouponStatus().equals(CouponStatus.ISSUANCE) && user.getRole().equals(Role.ADMIN) && coupon.getCompanyId() == user.getCompany()){
            coupon.deleteCoupon();
        }else{
            throw new IllegalArgumentException("삭제 불가한 쿠폰입니다.");
        }
        return coupon;
    }

    public Long couponPublishCount() {
        return couponQueryRepository.couponPublishCount();
    }

    public int findMyCouponCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()-> new CatchException(ResponseCode.USER_NOT_FOUND));
        List<ReceiveCoupon> coupons = receiveCouponRepository.findByUserId(user.getId());
        return coupons.size();
    }
}
