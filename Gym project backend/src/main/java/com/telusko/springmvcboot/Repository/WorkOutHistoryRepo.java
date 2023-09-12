package com.telusko.springmvcboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telusko.springmvcboot.model.WorkOutHistory;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface WorkOutHistoryRepo extends JpaRepository<WorkOutHistory, Integer> {

	@Query("select exerciseId from WorkOutHistory " +
			"where date= (select max(date) from WorkOutHistory where userId= :userId)" +
			"and userId= :userId")
	String findLastExerciseDone (@Param("userId") Long userId);

	@Query(value="select id,body_part,date,exercise1,exercise2,exercise3,exercise4,exercise5,user_id,exercise_id" +
			"  from WorkOutHistory where user_id= :userId order by date DESC", nativeQuery = true)
	List<WorkOutHistory> findAllByUserId(@Param("userId") Long userId);

	@Query(value="select id,body_part,date,exercise1,exercise2,exercise3,exercise4,exercise5,user_id,exercise_id" +
			"  from WorkOutHistory where user_id= :userId order by date DESC limit :offs, :pageSize ",  nativeQuery = true)
	List<WorkOutHistory> findHistWithPagination( @Param("offs") int offs,@Param("pageSize") int pageSize, @Param("userId") Long userId);
}
