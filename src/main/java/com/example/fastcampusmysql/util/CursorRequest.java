package com.example.fastcampusmysql.util;

/**
 *  key 는 중복이 있으면 안된다. ( unique 함이 보장되어야 함 )
 *  그래서 pk 를 사용하기로 함
 *  사용자가 처음 데이터를 요청할 때는 key 가 없기 때문에
 *  default key 를 설정 (여기서는 null , 정책마다 달라질 수 있음)
 *  클라이언트에서도 마지막 페이지 인지는 알 필요 없지만
 *  마지막 데이터인지는 알아야 함, 그래야 스크롤 했을 때 더 이상 요청 안함
 */
public record CursorRequest(Long key, int size) {
    public static final Long NONE_KEY = -1L;   // 데이터 더 이상 없을 때

    public boolean hasKey() {
        return key !=null;
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
