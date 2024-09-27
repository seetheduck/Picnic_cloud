package pack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pack.entity.FleamarketEntity;

public interface FleamarketRepositoryCustom {
    Page<FleamarketEntity> searchCategory(Integer category, String input, Pageable pageable);
}
