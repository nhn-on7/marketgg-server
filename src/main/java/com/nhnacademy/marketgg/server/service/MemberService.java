package com.nhnacademy.marketgg.server.service;

import java.time.LocalDateTime;

/**
 * 회원 서비스입니다.
 *
 * @version 1.0.0
 */
public interface MemberService {

    /**
     * 지정한 회원의 GG 패스 갱신일시를 일자로 변환하여 반환합니다..
     *
     * @param id - GG 패스 갱신일자를 확인 할 회원의 식별번호입니다.
     * @return 선택한 회원의 GG 패스 갱신일시를 일자로 반환합니다.
     * @since 1.0.0
     */
    LocalDateTime retrievePassUpdatedAt(final Long id);

    /**
     * 지정한 회원을 GG 패스에 구독 처리합니다.
     *
     * @param id - GG 패스를 구독할 회원의 식별번호입니다.
     * @since 1.0.0
     */
    void subscribePass(final Long id);

    /**
     * 지정한 회원을 GG 패스에 구독해지 처리합니다.
     *
     * @param id - GG 패스를 구독해지할 회원의 식별번호입니다.
     * @since 1.0.0
     */
    void withdrawPass(final Long id);

}
