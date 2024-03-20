package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.PostFixtureFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

//@Transactional
//@Disabled
@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    @DisplayName("5개 벌크 인서트")
    @Test
    public void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(2022,1,1),
                LocalDate.of(2022,2,1)
                );

        List<Post> posts = IntStream.range(0, 5)
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        postRepository.bulkInsert(posts);
    }

    @Disabled
    @DisplayName("100만개 그냥 인서트")
    @Test
    public void insertTest() {
        var easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(1992,1,1),
                LocalDate.of(2024,1,1)
        );

        var stopWatch = new StopWatch();
        stopWatch.start();

        List<Post> posts = IntStream.range(0, 10000*100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        stopWatch.stop();
        System.out.println("객체 생성 시간 : " + stopWatch.getTotalTimeSeconds());
    }


    @DisplayName("100만개 벌크 인서트")
    @Test
    public void bulkInsertTest() {
        var easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(1900,1,1),
                LocalDate.of(2024,1,1)
        );

        var stopWatch = new StopWatch();
        stopWatch.start();

        int _1만 = 10000;
        List<Post> posts = IntStream.range(0, _1만 * 100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        stopWatch.stop();
        System.out.println("객체 생성 시간 : " + stopWatch.getTotalTimeSeconds());

        var queryStopWatch = new StopWatch();
        queryStopWatch.start();

        postRepository.bulkInsert(posts);

        queryStopWatch.stop();
        System.out.println("DB 인서트 시간: " + queryStopWatch.getTotalTimeSeconds());

    }
}
