package com.myezen.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;
import com.myezen.myapp.persistance.BoardService_Mapper;

@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService {
	
	
	private BoardService_Mapper bsm;
	@Autowired
	public BoardServiceImpl(SqlSession sqlSession) {
		
		this.bsm = sqlSession.getMapper(BoardService_Mapper.class);
	}
	
	
	@Override
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri) {
		
		ArrayList<BoardVo> blist = bsm.boardSelectAll(scri);
		
		return blist;
	}

	@Override
	public int boardTotal(SearchCriteria scri) {
		
		int value = bsm.boardTotal(scri);
		
		return value;
	}

	@Override
	public int BoardViewCnt(int bidx) {
		
		int value = bsm.BoardViewCnt(bidx);
		
		return value;
	}
	
	@Override
	public BoardVo boardSelectOne(int bidx) {
		
		BoardVo bv = bsm.boardSelectOne(bidx);
		
		return bv;
	}


	
	@Override
	public int boardInsert(BoardVo bv) {
		
		int value = bsm.boardInsert(bv);
		
		return value;
	}

	@Override
	public int boardModify(BoardVo bv){
		
		int value = bsm.boardModify(bv);
		
		return value;
	}


	@Override
	public int boardDelete(int bidx) {

		int value = bsm.boardDelete(bidx);
		
		return value;
	}

	@Transactional
	@Override
	public int boardReply(BoardVo bv) {
		
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		
		hm.put("originbidx",bv.getOriginbidx());
		hm.put("depth",bv.getDepth());
		
		
//		bsm.boardReplyUpdate(bv.getOriginbidx(), bv.getDepth());
		
		int value = bsm.boardReplyInsert(bv);

		return 0;
	}








}
