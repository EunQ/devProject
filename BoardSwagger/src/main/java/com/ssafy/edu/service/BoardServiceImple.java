package com.ssafy.edu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.BoardDaoImpl;
import com.ssafy.edu.dto.Apply;
import com.ssafy.edu.dto.Apply_board;
import com.ssafy.edu.dto.Board;
import com.ssafy.edu.dto.Post;
import com.ssafy.edu.dto.Post_board;
import com.ssafy.edu.dto.Board_email;
import com.ssafy.edu.dto.Comment;

@Service
public class BoardServiceImple implements IBoardService{

	@Autowired
	private BoardDaoImpl dao;
	
	@Override
	public List<Board> getBoard() {
		return dao.getBoard();
	}

	@Override
	public void addBoardEmail(Board_email dto) {
		dao.addBoardEmail(dto);
	}

	@Override
	public int getBoardId() {
		return dao.getBoardId();
	}

	@Override
	public void addPost(Post post) {
		dao.addPost(post);
	}

	@Override
	public void addPostBoard(Post_board pb) {
		dao.addPostBoard(pb);
	}

	@Override
	public Board getBoardByID(int board_id) {
		return dao.getBoardByID(board_id);
	}

	@Override
	public void deleteBoard(int board_id) {
		dao.deleteBoard(board_id);
	}

	@Override
	public void addComment(Comment dto) {
		dao.addComment(dto);
	}

	@Override
	public List<Comment> getComment(int board_id) {
		return dao.getComment(board_id);
	}

	@Override
	public Comment getCommentByNum(int cnum) {
		return dao.getCommentByNum(cnum);
	}

	@Override
	public void deleteComment(int cnum) {
		dao.deleteComment(cnum);
	}

	@Override
	public void updateBoardAll(Board dto) {
		dao.updateBoardAll(dto);
	}

	@Override
	public void updateBoard(Board dto) {
		dao.updateBoard(dto);
	}

	@Override
	public void deletePostRecord(Post_board dto) {
		dao.deletePostRecord(dto);
	}

	@Override
	public int getPid(Post_board dto) {
		return dao.getPid(dto);
	}

	@Override
	public void addApply(Apply apl) {
		dao.addApply(apl);
	}

	@Override
	public void addApplyBoard(Apply_board ab) {
		dao.addApplyBoard(ab);
	}

	@Override
	public List<Board> searchBoardByTitle(String keyword) {
		return dao.searchBoardByTitle(keyword);
	}

	@Override
	public void addBoard(Board dto) {
		dao.addBoard(dto);
	}

	@Override
	public List<Board> searchBoardByTag(String hashtag) {
		return dao.searchBoardByTag(hashtag);
	}

}
