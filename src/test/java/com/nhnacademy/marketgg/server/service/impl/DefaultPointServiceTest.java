package com.nhnacademy.marketgg.server.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.marketgg.server.dto.request.MemberCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.MemberGradeCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.OrderCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.PointHistoryRequest;
import com.nhnacademy.marketgg.server.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.server.entity.Cart;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.entity.Order;
import com.nhnacademy.marketgg.server.entity.PointHistory;
import com.nhnacademy.marketgg.server.repository.cart.CartRepository;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.order.OrderRepository;
import com.nhnacademy.marketgg.server.repository.pointhistory.PointHistoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class DefaultPointServiceTest {

    @InjectMocks
    DefaultPointService pointService;

    @Mock
    PointHistoryRepository pointHistoryRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    CartRepository cartRepository;

    private PointRetrieveResponse pointRetrieveResponse;
    private PointHistoryRequest pointHistoryRequest;
    private MemberCreateRequest memberCreateRequest;
    private MemberGradeCreateRequest memberGradeCreateRequest;
    private OrderCreateRequest orderCreateRequest;
    private Cart cart;

    @BeforeEach
    void setUp() {
        pointRetrieveResponse =
            new PointRetrieveResponse(1L, 1L, 1000, 1000, "??????", LocalDateTime.now());
        pointHistoryRequest = new PointHistoryRequest();
        memberCreateRequest = new MemberCreateRequest();
        memberGradeCreateRequest = new MemberGradeCreateRequest();
        orderCreateRequest = new OrderCreateRequest();
        cart = cartRepository.save(new Cart());
    }

    @Test
    @DisplayName("????????? ????????? ?????? ?????? ??????")
    void testRetrievePointHistories() {
        given(pointHistoryRepository.findAllByMemberId(anyLong())).willReturn(List.of(pointRetrieveResponse));
        List<PointRetrieveResponse> responses = pointService.retrievePointHistories(1L);

        assertThat(responses.get(0).getPoint()).isEqualTo(1000);
    }

    @Test
    @DisplayName("???????????? ????????? ????????? ?????? ?????? ??????")
    void testAdminRetrievePointHistories() {
        given(pointHistoryRepository.findAllForAdmin()).willReturn(List.of(pointRetrieveResponse));
        List<PointRetrieveResponse> responses = pointService.adminRetrievePointHistories();

        assertThat(responses.get(0).getPoint()).isEqualTo(1000);
    }

    @Test
    @DisplayName("?????? ?????? X ????????? ?????? ??????")
    void testCreatePointHistory() {
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(new Member(memberCreateRequest, cart)));
        ReflectionTestUtils.setField(pointHistoryRequest, "point", 1000);

        pointService.createPointHistory(1L, pointHistoryRequest);

        verify(pointHistoryRepository, times(1)).save(any(PointHistory.class));
    }

    @Test
    @DisplayName("????????? ?????? ??????, ?????? ?????? ????????? ?????? ??????")
    void testCreatePointHistoryForOrderIsMember() {
        ReflectionTestUtils.setField(memberGradeCreateRequest, "grade", "Member");
        MemberGrade memberGrade = new MemberGrade(memberGradeCreateRequest);

        Member member = new Member(memberCreateRequest, cart);
        Order order = new Order(member, orderCreateRequest);

        ReflectionTestUtils.setField(pointHistoryRequest, "point", 1000);
        ReflectionTestUtils.setField(member, "memberGrade", memberGrade);
        ReflectionTestUtils.setField(member.getMemberGrade(), "grade", "Member");

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        pointService.createPointHistoryForOrder(1L, 1L, pointHistoryRequest);

        verify(pointHistoryRepository, times(1)).save(any(PointHistory.class));
    }

    @Test
    @DisplayName("????????? ?????? VIP, ?????? ?????? ????????? ?????? ??????")
    void testCreatePointHistoryForOrderIsVip() {
        ReflectionTestUtils.setField(memberGradeCreateRequest, "grade", "VIP");
        MemberGrade memberGrade = new MemberGrade(memberGradeCreateRequest);

        Member member = new Member(memberCreateRequest, cart);
        Order order = new Order(member, orderCreateRequest);

        ReflectionTestUtils.setField(pointHistoryRequest, "point", 1000);
        ReflectionTestUtils.setField(member, "memberGrade", memberGrade);
        ReflectionTestUtils.setField(member.getMemberGrade(), "grade", "VIP");

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        pointService.createPointHistoryForOrder(1L, 1L, pointHistoryRequest);

        verify(pointHistoryRepository, times(1)).save(any(PointHistory.class));
    }

    @Test
    @DisplayName("????????? ?????? G-VIP, ?????? ?????? ????????? ?????? ??????")
    void testCreatePointHistoryForOrderIsGVip() {
        ReflectionTestUtils.setField(memberGradeCreateRequest, "grade", "G-VIP");
        MemberGrade memberGrade = new MemberGrade(memberGradeCreateRequest);

        Member member = new Member(memberCreateRequest, cart);
        Order order = new Order(member, orderCreateRequest);

        ReflectionTestUtils.setField(pointHistoryRequest, "point", 1000);
        ReflectionTestUtils.setField(member, "memberGrade", memberGrade);
        ReflectionTestUtils.setField(member.getMemberGrade(), "grade", "G-VIP");

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        pointService.createPointHistoryForOrder(1L, 1L, pointHistoryRequest);

        verify(pointHistoryRepository, times(1)).save(any(PointHistory.class));
    }

    @Test
    @DisplayName("????????? ?????? G-VIP, ?????? ?????? ????????? ???????????? ?????? ??????")
    void testCreatePointHistoryForOrderIsMinus() {
        ReflectionTestUtils.setField(memberGradeCreateRequest, "grade", "G-VIP");
        MemberGrade memberGrade = new MemberGrade(memberGradeCreateRequest);

        Member member = new Member(memberCreateRequest, cart);
        Order order = new Order(member, orderCreateRequest);

        ReflectionTestUtils.setField(pointHistoryRequest, "point", -1000);
        ReflectionTestUtils.setField(member, "memberGrade", memberGrade);
        ReflectionTestUtils.setField(member.getMemberGrade(), "grade", "G-VIP");

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        pointService.createPointHistoryForOrder(1L, 1L, pointHistoryRequest);

        verify(pointHistoryRepository, times(1)).save(any(PointHistory.class));
    }

}
