package com.example.fastcampusmysql.application.usacase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.TimelineReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetTimelinePostsUsacase {

    private final FollowReadService followReadService;
    private final PostReadService postReadService;
    private final TimelineReadService timelineReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        /**
         *  1. memberId -> follow 조회
         *  2. 1번 결과로 게시물 조회
         */
        List<Follow> followings = followReadService.getFollowings(memberId);
        List<Long> followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return  postReadService.getPosts(followingMemberIds, cursorRequest);
    }

    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        /**
         *  1. Timeline 테이블 조회
         *  2. 1번에 해당하는 게시물을 조회한다.
         */
        PageCursor<Timeline> pagedTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        List<Long> postIds = pagedTimelines.body().stream().map(Timeline::getPostId).toList();
        List<Post> posts = postReadService.getPosts(postIds);

        return new PageCursor<>(pagedTimelines.nextCursorRequest(), posts);
    }

}
