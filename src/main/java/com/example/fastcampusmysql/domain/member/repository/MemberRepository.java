package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TABLE = "member";

    static final RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member
            .builder()
            .id(resultSet.getLong("id"))
            .email(resultSet.getString("email"))
            .nickname(resultSet.getString("nickname"))
            .birthday(resultSet.getObject("birthday", LocalDate.class))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public Optional<Member> findById(Long id) {
        /*
            select *
            from Member
            where id = : id
         */
        String sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
        var param = new MapSqlParameterSource()
                .addValue("id", id);   // id 값이 위 쪽 sql 의 :id 로 바인딩 될 것

        Member member = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper);

        return Optional.ofNullable(member);
    }

    public List<Member> findAllByIdIn(List<Long> ids) {
        // TODO : format 중복 제거?
        // ids 가 비어있는 list 로 넘어오면 문제 발생
        // BadGrammerException -> 쿼리가 SELECT * FROM member WHERE id in () 이런식으로 나가게 됨
        if (ids.isEmpty()) {
            return List.of();   // 비어있는 리스트를 반환하도록 하면 됨
        }
        String sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);
        var params = new MapSqlParameterSource().addValue("ids", ids);  // list(ids) 도 넘어감
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    public Member save(Member member) {
        /*
            member id 를 보고 갱신 또는 삽입을 정함
            반환값은 id를 담아서 반환한다.
         */
        if (member.getId() == null) {
            return insert(member);
        }

        return update(member);
    }

    // id 가 없을 경우 insert
    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withCatalogName("fast_SNS")    // DB 명 지정
                .withTableName(TABLE)        // TABLE
                .usingGeneratedKeyColumns("id");    // AUTO_INCREMENT

        // member 를 넣으면 인터페이스에 맞는 파라미터를 추출해서 만들어준다.
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);

        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private Member update(Member member) {
        String sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, params);
        return member;
    }
}
