package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *  과거의 데이터를 가지고 있는 히스토리 테이블은
 *  정규화의 대상이 아니다.
 */
@Getter
public class MemberNicknameHistory {
    private final Long id;
    private final Long memberId;
    private final String nickname;
    private final LocalDateTime createdAt;

    @Builder
    public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
