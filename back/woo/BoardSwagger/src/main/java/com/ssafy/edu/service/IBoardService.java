package com.ssafy.edu.service;

import java.util.List;

import com.ssafy.edu.dto.Apply;
import com.ssafy.edu.dto.Apply_board;
import com.ssafy.edu.dto.Board;
import com.ssafy.edu.dto.Post;
import com.ssafy.edu.dto.Post_board;
import com.ssafy.edu.dto.Board_email;
import com.ssafy.edu.dto.Comment;

public interface IBoardService {

	List<Board> getBoard();

	void addBoardEmail(Board_email dto);

	int getBoardId();

	void addPost(Post post);

	void addPostBoard(Post_board pb);

	Board getBoardByID(int board_id);

	void deleteBoard(int board_id);

	void addComment(Comment dto);

	List<Comment> getComment(int board_id);
	
	Comment getCommentByNum(int cnum);

	void deleteComment(int cnum);

	void updateBoardAll(Board dto);

	void updateBoard(Board dto);

	void deletePostRecord(Post_board dto);

	int getPid(Post_board dto);

	void addApply(Apply apl);

	void addApplyBoard(Apply_board ab);

	List<Board> searchBoard(String keyword);

	void addBoard(Board dto);
}
