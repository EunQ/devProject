package com.ssafy.edu.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {
	Optional<Member> findByEmail(String email);
	List<Member> findAll();
	/*
	 @Query(value = "select * from table_name where case_1=:case_1 and date='2017-04-04' ", nativeQuery=true)
	List<TableName> findSomeCase(@Param("case_1") String case_1);
	 */
}
