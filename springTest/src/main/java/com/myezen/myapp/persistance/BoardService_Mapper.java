package com.myezen.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;

public interface BoardService_Mapper {
	
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri);
	
	public int boardTotal(SearchCriteria scri);
	
	public BoardVo boardSelectOne(int bidx);
	public int BoardViewCnt(int bidx);
	
	public int boardInsert(BoardVo bv);
	
	public int boardModify(BoardVo bv);
	
	public int boardDelete(int bidx);
	
//	public int boardReplyUpdate(int originbidx, int depth);
	// 매개변수가 같은 타입이면서 여러개인경우 번호로 지정가능
	
	//매개변수가 2개이고 타입이 다르면 해쉬맵에 담아서 넘긴다.
	
	public int boardReplyUpdate(HashMap<String,Integer> hm);
	
	public int boardReplyInsert(BoardVo bv);

}
