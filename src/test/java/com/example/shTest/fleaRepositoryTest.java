//package com.example.shTest;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ContextConfiguration;
//
//import lombok.extern.slf4j.Slf4j;
//import pack.repository.FleamarketEntityInterface;
//
//
//@ContextConfiguration(classes = FleamarketEntityInterface.class)
//@DataJpaTest
//@Slf4j
//public class fleaRepositoryTest {
//
//	@Autowired
//	private FleamarketEntityInterface repo;
//	
//	@Test
//	public void testjpql() {
//		log.info(repo.findBymTitleormContaining("Bear").toString());
//	}
//}
