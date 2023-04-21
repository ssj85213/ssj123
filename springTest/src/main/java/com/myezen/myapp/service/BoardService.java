package com.myezen.myapp.service;

import java.util.ArrayList;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;

public interface BoardService {
	
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri);
	
	public int boardTotal(SearchCriteria scri);
	
	public BoardVo boardSelectOne(int bidx);
	public int BoardViewCnt(int bidx);
	
	public int boardInsert(BoardVo bv);

	public int boardModify(BoardVo bv);
	
	public int boardDelete(int bidx);
	
	public int boardReply(BoardVo bv);
	
	
}
