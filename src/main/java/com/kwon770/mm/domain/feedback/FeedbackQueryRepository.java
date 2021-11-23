package com.kwon770.mm.domain.feedback;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FeedbackQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public FeedbackQueryRepository(JPAQueryFactory queryFactory) {
        super(Feedback.class);
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<Feedback> findAllOrderByLikeCountDesc() {
        return queryFactory
                .selectFrom(QFeedback.feedback)
                .orderBy(QFeedback.feedback.likingMembers.size().desc())
                .fetch();
    }
}
