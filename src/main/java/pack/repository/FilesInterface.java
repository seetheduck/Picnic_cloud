package pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pack.entity.FilesEntity;


public interface FilesInterface extends JpaRepository<FilesEntity,Integer> {

}
