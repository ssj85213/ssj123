package com.myezen.myapp.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

@Component 
//bean Configuration 파일에 bean을 따로 등록하지 않아도 객체를 사용할 수 있게 함.
// bean에 등록하는 것은 개발자 직접 제어가 불가능한 외부 라이브러리 등을 bean으로 만들경우
// component는 개발자가 직접 작성한 class를 bean으로 등록하기위해 사용
public class PageMaker {
	
	private int displayPageNum = 5;	
	private int startPage;						
	private int endPage;				
	private int totalCount;					
	
	private boolean prev;					
	private boolean next;					
	
	private SearchCriteria scri;						

	public SearchCriteria getScri() {
		return scri;
	}

	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();							
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}
	
	
	public void calcData() {

		
			endPage = (int)(Math.ceil(scri.getPage() / (double)displayPageNum)*displayPageNum) ;	
		
			startPage = (endPage - displayPageNum)+1;
		
		
			int tempEndPage = (int)(Math.ceil(totalCount /(double)scri.getPerPageNum()));
			
			if(endPage > tempEndPage) {
				endPage = tempEndPage;
			}
		
			prev = (startPage == 1 ? false : true);		
			
			next = (endPage*scri.getPerPageNum() >= totalCount ? false : true);
	}
	
	public String encoding (String keyword) {
		String str = "";
		
		try {
			str = URLEncoder.encode(keyword,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	
}
