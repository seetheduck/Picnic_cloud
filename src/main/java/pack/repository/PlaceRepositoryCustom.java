package pack.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import pack.entity.PlaceEntity;

public interface PlaceRepositoryCustom {
	List<PlaceEntity> findPlaces(@Param("placeType") String placeType, @Param("keyword") String keyword);
}
