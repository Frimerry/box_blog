package com.cos.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cos.blog.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {
	@Autowired
	private final BoardService boardService;
	
	@GetMapping({"","/"})
	public String index(Model model, @PageableDefault(size=5, sort="id", direction= Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index";	// boards로 index.jsp 리턴 -> boards의 내용을 담은 index페이지를 보여준다
	}
	
	@GetMapping({"/java"})
	public String toJava(Model model, @PageableDefault(size=5, sort="id", direction= Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("boards", boardService.toJava(pageable));
		return "java";
	}
	
	@GetMapping({"/sql"})
	public String toSql(Model model, @PageableDefault(size=5, sort="id", direction= Sort.Direction.DESC) Pageable pageable){
		model.addAttribute("boards", boardService.toSql(pageable));
		return "sql";
	}

	
	@GetMapping({"/board/Form"})
	public String saveForm() {
		return "board/saveForm";
	}
	
	
	
	// 글 상세보기
	@GetMapping("/board/{id}")	// 게시글의 id
	public String findbyId(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/detail";	// board로 board/detail.jsp 리턴
	}
	
	// 글 수정하기(update)
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
}
