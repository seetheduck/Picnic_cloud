package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pack.entity.FilesEntity;


public interface FilesRepository extends JpaRepository<FilesEntity,Integer> {

}
