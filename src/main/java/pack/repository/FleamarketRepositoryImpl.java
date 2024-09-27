package pack.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pack.entity.FleamarketEntity;
import pack.entity.QFleamarketEntity;

import jakarta.persistence.EntityManager;
import java.util.List;

@Repository
public class FleamarketRepositoryImpl implements FleamarketRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public FleamarketRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<FleamarketEntity> searchCategory(Integer category, String input, Pageable pageable) {
        QFleamarketEntity fleamarket = QFleamarketEntity.fleamarketEntity;

        BooleanBuilder builder = new BooleanBuilder();

        // 카테고리 조건 추가
        if (category != null && category != 0) {
            builder.and(fleamarket.categoryEntity.no.eq(category));
        }

        // 검색 조건 추가
        if (input != null && !input.isEmpty()) {
            builder.and(fleamarket.title.containsIgnoreCase(input)
                    .or(fleamarket.contents.containsIgnoreCase(input)));
        }

        // QueryDSL로 쿼리 실행
        List<FleamarketEntity> results = queryFactory.selectFrom(fleamarket)
                .where(builder)
                .orderBy(fleamarket.no.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 계산
        long total = queryFactory.selectFrom(fleamarket)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }
}
