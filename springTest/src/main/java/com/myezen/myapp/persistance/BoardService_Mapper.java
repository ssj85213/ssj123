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
	// �Ű������� ���� Ÿ���̸鼭 �������ΰ�� ��ȣ�� ��������
	
	//�Ű������� 2���̰� Ÿ���� �ٸ��� �ؽ��ʿ� ��Ƽ� �ѱ��.
	
	public int boardReplyUpdate(HashMap<String,Integer> hm);
	
	public int boardReplyInsert(BoardVo bv);

}
