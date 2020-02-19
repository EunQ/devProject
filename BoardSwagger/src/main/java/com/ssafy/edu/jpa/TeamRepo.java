package com.ssafy.edu.jpa;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Team;
import com.ssafy.edu.dto.TeamWithApply;

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
	List<Team> findAllByEmail(@Param("email") String email);
	

	@Query(nativeQuery = true, value = "select * from Team t where t.team_id in (select apply.team_id from apply where apply.board_id = :board_id)")
	List<Team> findAllByBoardId(@Param("board_id") Integer board_id);
	
//	@Query(nativeQuery = true ,value = "select new com.ssafy.edu.dto.TeamWithApply(t.team_id ,t.team_date ,t.team_name, a.idea) "
//			+ " from Team t, Apply a where t.team_id = a.team_id and a.board_id = :board_id")
//	List<TeamWithApply> findAllTeamWithApplyByBoardId(@Param("board_id") Integer board_id);
	
	@Modifying
	@Query(nativeQuery = true, value="delete from Team where Team.team_state = 'READY' and Team.team_id in (select apply.team_id from apply where apply.board_id = :board_id)")
	void deleteAllByBoardIdAndReady(@Param("board_id") int boardId);
	
//	@Modifying
//	@Query(nativeQuery = true, value = "update Team t set t.state = :state where t.state = :pre_state and t.team_id in (select apply.team_id from apply where apply.board_id = :board_id)")
//	void updateTeamState(@Param("board_id")int boardId,@Param("pre_state") String preState,@Param("state") String state);
//	
//	@Modifying
//	@Query(nativeQuery = true, value = "update Team t set t.state = :state where t.state like 'END%' and t.team_id in (select apply.team_id from apply where apply.board_id = :board_id)")
//	void updateTeamState(@Param("board_id")int boardId, @Param("state") String state);
}
