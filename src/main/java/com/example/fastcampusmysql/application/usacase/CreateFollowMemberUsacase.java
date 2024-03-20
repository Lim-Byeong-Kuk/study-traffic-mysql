package com.example.fastcampusmysql.application.usacase;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateFollowMemberUsacase {
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;

    /*
        usarcase layer 는 가능한한 로직이 없어야 한다.
        각 도메인의 비즈니스 로직은 각 도메인 서비스에 들어가 있어야한다.
        usacase 는 각 도메인의 흐름을 제어하는 역할
     */
    public void execute(Long fromMemberId, Long toMemberId) {
        /*
            1. 입력받은 memberId로 회원조회
            2. FollowWriteService.create() 호출
         */
        MemberDto fromMember = memberReadService.getMember(fromMemberId);
        MemberDto toMember = memberReadService.getMember(toMemberId);
        followWriteService.create(fromMember, toMember);
    }
}
