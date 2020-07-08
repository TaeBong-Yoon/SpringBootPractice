package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void write(Board board,User user) {
		board.setCount(0);
		board.setUser(user); // 로그인 되어있는 유저 정보
		boardRepository.save(board);
	}
	
	public List<Board> boardList(){
		return boardRepository.findAll();
	}
}
