package com.myezen.myapp.service;

import java.util.ArrayList;

import com.myezen.myapp.domain.MemberVo;

//불완전한 메소드 정의
public interface MemberService {
	
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberPhone, String memberEmail, String memberGender, String memberAddr,String memberBirth);
	
	public ArrayList<MemberVo> memberList();
	
	public int memberIdCheck(String memberId);
	
	public MemberVo memberLogin(String memberId);
}
