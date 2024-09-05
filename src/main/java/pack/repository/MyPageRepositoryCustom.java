package pack.repository;

import org.springframework.data.domain.Pageable;
import pack.entity.PlaceEntity;

import java.util.List;

public interface MyPageRepositoryCustom {
    List<PlaceEntity> findLikedPlacesByUserId(String userId, Pageable pageable);
    long countLikedPlacesByUserId(String userId);
}
