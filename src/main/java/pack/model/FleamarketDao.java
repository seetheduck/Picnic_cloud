package pack.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pack.dto.FleamarketDto;
import pack.entity.FleamarketEntity;
import pack.repository.FleamarketInterface;

@Repository
public class FleamarketDao {
	
	@Autowired
	private FleamarketInterface repository;
	
	public List<FleamarketDto> findAll() {
		return repository.findAll().stream().map(FleamarketEntity::toDto).collect(Collectors.toList());
	}
}
