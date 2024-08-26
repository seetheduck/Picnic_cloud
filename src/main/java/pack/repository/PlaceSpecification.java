package pack.repository;

import org.springframework.data.jpa.domain.Specification;

import pack.entity.PlaceEntity;

public class PlaceSpecification {
	
	public static Specification<PlaceEntity> hasKeyword(String placeType, String keyword) {
        return (root, query, criteriaBuilder) -> {
            // placeType이 null이거나 비어있지 않은지 확인
            if (placeType == null || placeType.isEmpty()) {
                throw new IllegalArgumentException("placeType must not be null or empty");
            }
            
            // 검색어가 없는 경우에는 placeType만으로 필터링
            if (keyword == null || keyword.isEmpty()) {
                return criteriaBuilder.equal(root.get("placeType"), placeType);
            }

            // 검색어가 있는 경우에는 placeType과 keyword로 필터링
            String likePattern = "%" + keyword + "%";
            return criteriaBuilder.and(
                criteriaBuilder.equal(root.get("placeType"), placeType),
                criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), likePattern),
                    criteriaBuilder.like(root.get("address"), likePattern)
                )
            );
        };
    }
	
	public static Specification<PlaceEntity> orderByPointAndLikeCnt() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(
                    criteriaBuilder.desc(root.get("point")),
                    criteriaBuilder.desc(root.get("likeCnt"))
            );
            return criteriaBuilder.conjunction(); // 정렬을 위한 스텁 조건
        };
    }
}
