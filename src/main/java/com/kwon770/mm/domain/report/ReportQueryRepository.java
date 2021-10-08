package com.kwon770.mm.domain.report;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ReportQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ReportQueryRepository(JPAQueryFactory queryFactory) {
        super(Report.class);
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<Report> findAllOrderByLikeCountDesc() {
        return queryFactory
                .selectFrom(QReport.report)
                .orderBy(QReport.report.likingMembers.size().desc())
                .fetch();
    }
}
