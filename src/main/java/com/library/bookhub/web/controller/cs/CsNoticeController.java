package com.library.bookhub.web.controller.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.bookhub.entity.cs.CsNoticeEntity;
import com.library.bookhub.security.UserDetailsServiceImpl;
import com.library.bookhub.service.CsFileService;
import com.library.bookhub.service.CsNoticeService;
import com.library.bookhub.web.dto.cs.CsNoticeDto;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class CsNoticeController {

	@Autowired
	CsNoticeService csNoticeService;

	@Autowired
	CsFileService csFileService;
	
	@Autowired
	private UserDetailsServiceImpl serviceImpl;

	// Notice 리스트 화면
	@GetMapping("/notice/list")
	public String noticePage() {
		System.out.println("a");
		return "pages/cs/notice/list";
	}

	// 추가 Notice 검색 리스트
	@GetMapping("/api/notice/search-list")
	@ResponseBody
	public List<CsNoticeEntity> noticeSearchPage(String searchType, String searchInput) {
		System.out.println("b");
		System.out.println(searchType);
		System.out.println(searchInput);
		return csNoticeService.noticeSearchPage(searchType, searchInput);
	}

	// 추가 Notice 리스트
	@GetMapping("/api/notice/list")
	@ResponseBody
	public List<CsNoticeEntity> noticeList() {
		System.out.println("c");
		return csNoticeService.noticeList();
	}

	// Notice 상세보기 화면 띄우기
	@GetMapping("/notice/view/{id}")
	public String detailNotice() {
		System.out.println("d");
		return "pages/cs/notice/view";
	}

	// Notice 상세보기
	@PostMapping("/notice/view")
	@ResponseBody
	public CsNoticeEntity noticeView(int id) {
		System.out.println("e");
		System.out.println(id);

		CsNoticeEntity csNoticeEntity = csNoticeService.noticeView(id);

		return csNoticeEntity;
	}

	// Notice 작성하기 화면
	@GetMapping("/notice/insert")
	public String noticeInsertPage() {

		return "pages/cs/notice/insert";
	}

	// Notice 작성하기
	@PostMapping("/notice/insert")
	public String noticeInsert(CsNoticeDto dto) {
		String userId = serviceImpl.getUserId();
		
		
		System.out.println("유저 정보" +userId);
		
		// 파일 저장
		String filepath = csFileService.saveFiles(dto.getFilepath());

		System.out.println("filepath 확인" + filepath.toString());

		boolean result = csNoticeService.noticeInsert(dto, filepath, userId);

		System.out.println(result);

		if (result == true) {
			return "pages/cs/notice/list";
		}

		return "pages/cs/notice/list";
	}

	// Notice 삭제하기
	@PostMapping("/notice/delete/{id}")
	@ResponseBody
	public boolean noticeDelete(@PathVariable int id) {
		boolean result = csNoticeService.noticeDelete(id);

		return result;
	}

}