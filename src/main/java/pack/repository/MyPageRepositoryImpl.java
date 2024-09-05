package pack.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pack.entity.PlaceEntity;
import pack.entity.QLikesEntity;
import pack.entity.QPlaceEntity;

import java.util.List;

@Repository
public class MyPageRepositoryImpl implements MyPageRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<PlaceEntity> findLikedPlacesByUserId(String userId, Pageable pageable) {
        QPlaceEntity place = QPlaceEntity.placeEntity;
        QLikesEntity likes = QLikesEntity.likesEntity;

        return queryFactory.selectFrom(place)
                .join(likes).on(likes.placeNo.eq(place.no))  // PlaceEntity의 no와 LikesEntity의 placeNo로 조인
                .where(likes.userId.eq(userId))              // userId로 필터링
                .offset(pageable.getOffset())                // 페이징 오프셋
                .limit(pageable.getPageSize())               // 페이징 크기
                .fetch();
    }

    @Override
    public long countLikedPlacesByUserId(String userId) {
        QLikesEntity likes = QLikesEntity.likesEntity;
        return queryFactory.selectFrom(likes)
                .where(likes.userId.eq(userId))              // userId로 좋아요 개수 카운트
                .fetchCount();
    }
}
