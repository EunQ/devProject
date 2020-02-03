package com.ssafy.edu.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Apply;
import com.ssafy.edu.dto.Apply_board;
import com.ssafy.edu.dto.Board;
import com.ssafy.edu.dto.Post;
import com.ssafy.edu.dto.Post_board;
import com.ssafy.edu.dto.Board_email;
import com.ssafy.edu.dto.Comment;

@Repository
public class BoardDaoImpl {

String ns="ssafy.board.";
    
	@Autowired
	private SqlSession sqlSession;

	public List<Board> getBoard() {
		return sqlSession.selectList(ns + "getBoard");
	}

	public void addBoardEmail(Board_email dto) {
		sqlSession.insert(ns + "addBoard", dto);
	}

	public int getBoardId() {
		return sqlSession.selectOne(ns + "getBoardId");
	}

	public void addPost(Post post) {
		sqlSession.insert(ns + "addPost", post);
	}

	public void addPostBoard(Post_board pb) {
		sqlSession.insert(ns + "addPostBoard", pb);
	}

	public Board getBoardByID(int board_id) {
		return sqlSession.selectOne(ns + "getBoardByID", board_id);
	}

	public void deleteBoard(int board_id) {
		sqlSession.delete(ns + "deleteBoard", board_id);
	}

	public void addComment(Comment dto) {
		sqlSession.insert(ns + "addComment", dto);
	}

	public List<Comment> getComment(int board_id) {
		return sqlSession.selectList(ns + "getComment", board_id);
	}

	public Comment getCommentByNum(int cnum) {
		return sqlSession.selectOne(ns + "getCommentByNum", cnum);
	}

	public void deleteComment(int cnum) {
		sqlSession.delete(ns + "deleteComment", cnum);
	}

	public void updateBoardAll(Board dto) {
		sqlSession.update(ns + "updateBoardAll", dto);
	}

	public void updateBoard(Board dto) {
		sqlSession.update(ns + "updateBoard", dto);
	}

	public void deletePostRecord(Post_board dto) {
		sqlSession.delete(ns + "deletePostRecord", dto);
	}

	public int getPid(Post_board dto) {
		return sqlSession.selectOne(ns + "getPid", dto);
	}

	public void addApply(Apply apl) {
		sqlSession.insert(ns + "addApply", apl);
	}

	public void addApplyBoard(Apply_board ab) {
		sqlSession.insert(ns + "addApplyBoard", ab);
	}

	public List<Board> searchBoard(String keyword) {
		return sqlSession.selectList(ns + "searchBoard", keyword);
	}

	public void addBoard(Board dto) {
		sqlSession.insert(ns + "addBoard", dto);
	}
	
//	public Member getMemberByID(String email) { 
//		return sqlSession.selectOne(ns + "getMemberByID", email);
//	}
//
//	public void addMember(Member dto) {
//		sqlSession.insert(ns + "addMember", dto);
//	}
//
//	public void deleteMember(String email) {
//		sqlSession.delete(ns + "deleteMember", email);
//	}
//
//	public void changeMemberInfo(Member dto) {
//		sqlSession.update(ns + "changeMemberInfo", dto);
//	}
//
//	public void changePwd(Member dto) {
//		sqlSession.update(ns + "changePwd", dto);
//	}

}
