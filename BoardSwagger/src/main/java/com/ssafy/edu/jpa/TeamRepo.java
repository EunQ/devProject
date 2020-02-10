package com.ssafy.edu.jpa;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Team;

@Repository
public interface TeamRepo extends JpaRepository<Team, Integer>{
	Optional<Team> findByTeamId(int team_id);
//	List<Team> findAllByBoardId(int board_id);
	
//	@Query("select p from Post p where p.id > :id")
//	Post findPostByPk(@Param("id") Long id);
//	@Query("select t from Team t")
//	public final static String FIND_BY_ID_STATE = "SELECT a FROM Table1 a RIGHT JOIN a.table2Obj b " +
//			"WHERE b.column = :id" +
//			"AND a.id NOT IN (SELECT c.columnFromA from a.table3Obj c where state = :state)";
//	
//	
//	@Query(FIND_BY_ID_STATE)

//	@NamedNativeQuery(name = "test1", query = "select tm from TeamMember")
	
	@Query(nativeQuery = true, value = "select * from Team t where t.team_id in (select team_id from team_member where email = :email)")
	List<Team> findAllTeamByEmail(@Param("email") String email);
}
