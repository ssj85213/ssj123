package com.myezen.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myezen.myapp.domain.MemberVo;



public interface MemberService_Mapper {
	
	//���̹�Ƽ���� ����� �޼ҵ带 �����Ѵ�
	
	//public MemberVo memberLogin(String id, String pwd);
		
	public int memberInsert(MemberVo mv);

	public ArrayList<MemberVo> memberList();
	
	public int memberIdCheck(String memberId);

	public MemberVo memberLogin(String memberId);
}
