package com.telusko.springmvcboot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.telusko.springmvcboot.model.gymplan;


public interface GymRepo extends JpaRepository<gymplan, Integer>, CrudRepository<gymplan, Integer> {

	@Query("select id,body_part,exercise1,exercise2,exercise3,exercise4,exercise5 from gymplan where id= :id ")
	String find (@Param("id") int id);
	
	@Query("select count(1) from gymplan")
	int totalWorkOutAvailable ();

	@Modifying
	@Transactional
	@Query("delete from gymplan  where id not in (1,2,3)")
	void deleteOldRecord ();
	
	
	@Modifying
	@Transactional
	@Query(value="alter table gymplan auto_increment = 3",nativeQuery = true)
	void resetIdSequence();

	
}
