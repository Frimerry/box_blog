package com.cos.blog.service;
// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Boards;
import com.cos.blog.model.Reply;
import com.cos.blog.model.Users;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void 글쓰기(Boards board, Users user) {
		board.setCount(0);
		board.setUsers(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly=true)
	public Page<Boards> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
		// 모든 게시물 리턴
	}
	
//	@Transactional(readOnly=true)
//	public Page<Boards> toJava(Pageable pageable){
//		List<Boards> board = boardRepository.findByCategory("Java");
//		final int start = (int)pageable.getOffset();
//		final int end = Math.min((start + pageable.getPageSize()), board.size());
//		final Page<Boards> page = new PageImpl<Boards>(board.subList(start, end), pageable, board.size());
//		return page;
//		// List<Boards>를 Page<Boards로 형변환하여 자바 게시판에 자바카테고리 게시물 리턴
//	}
	
	@Transactional(readOnly=true)
	public Page<Boards> toJava(Pageable pageable){
		Page<Boards> board = boardRepository.findByCategory("Java", pageable);
		return board;
		// 자바 게시판에 자바 카테고리 게시물 리턴
	}
	
	@Transactional(readOnly=true)
	public Page<Boards> toSql(Pageable pageable){
		Page<Boards> board = boardRepository.findByCategory("SQL",pageable);
		return board;
		// SQL 게시판에 SQL 카테고리 게시물 리턴
	}


	@Transactional(readOnly=true)
	public Boards 글상세보기(int id) {	
		boardRepository.updateCount(id);
		return boardRepository.findById(id).orElseThrow(()->{
		return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Boards requestBoard) {
		Boards board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
			});
		board.setTitle(requestBoard.getTitle());
		board.setCategory(requestBoard.getCategory());
		board.setContent(requestBoard.getContent());
		// 함수 종료시 트랜잭션 종료, 자동 commit
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		Users user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 작성 실패 : 사용자 정보를 찾을 수 없습니다.");
			});
		
		Boards board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 작성 실패 : 게시글을 찾을 수 없습니다.");
			});
		
		Reply reply = Reply.builder().users(user).boards(board)
				.content(replySaveRequestDto.getContent()).build();

		replyRepository.save(reply);
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
}
