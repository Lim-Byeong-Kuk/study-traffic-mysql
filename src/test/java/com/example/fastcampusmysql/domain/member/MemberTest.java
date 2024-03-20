package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    @DisplayName("EasyRandom 이용 랜덤 객체 생성 테스트")
    @Test
    public void testEasyRandom() {
        LongStream.range(0, 10)
                .mapToObj(i -> MemberFixtureFactory.create(i))
                .forEach(member -> {
                    System.out.println(member.getNickname());
                });
    }

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    void testChangeName() {
        Member member = MemberFixtureFactory.create();
        String expected = "bk";

        member.changeNickname(expected);

        assertThat(expected).isEqualTo(member.getNickname());
    }

    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
    @Test
    void testNicknameMaxLength() {
        Member member = MemberFixtureFactory.create();
        String overMaxLengthName = "abcdefghijklmnop";

        Assertions.assertThatThrownBy(() -> member.changeNickname(overMaxLengthName))
                .isInstanceOf(IllegalArgumentException.class);

    }


}
