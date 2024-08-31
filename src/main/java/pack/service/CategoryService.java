package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pack.dto.CategoryDto;
import pack.entity.CategoryEntity;
import pack.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	// 카테고리 목록 조회
	public List<CategoryDto> getCategories() {

		List<CategoryEntity> categoryEntities = repository.findByMarketNoIsNotNull();
		// Entity를 DTO로 변환
		return categoryEntities.stream()
				.map(CategoryEntity::toDto)
				.collect(Collectors.toList());
	}
}
