package com.example.fastcampusmysql.util;

import com.example.fastcampusmysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

/**
 *  builder 패턴으로 생성하기 보다는 다양한 Object 를 생성할 수 있도록
 *  Fixture 라이브러리를 사용
 *  그 중 EasyRandom 사용
 *  EasyRandom 은 seed 기반으로 동작
 *  같은 seed 기반으로 생성하면 똑같은 객체가 반환 됨
 */
public class MemberFixtureFactory {
    static public Member create() {
        EasyRandomParameters param = new EasyRandomParameters();
        return new EasyRandom(param).nextObject(Member.class);
    }
    static public Member create(Long seed) {
        EasyRandomParameters param = new EasyRandomParameters()
                .seed(seed);

        return new EasyRandom(param).nextObject(Member.class);
    }
}
