package com.myezen.myapp.controller;

import java.util.ArrayList;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myezen.myapp.domain.MemberVo;
import com.myezen.myapp.service.MemberService;

@Controller
@RequestMapping(value="/member")

public class MemberController {
	
	@Autowired
	MemberService ms;
	//memberService�� ���� Ÿ�Ե��� �����ϴ°�(memberservice�� �ִ� �޼ҵ� ���)
	
	//��ȣȭ �ϴ� ��� ����
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@RequestMapping(value="/memberJoin.do")
	public String memberJoin(){
			
		return "member/memberJoin";
	}

	@RequestMapping(value="/memberJoinAction.do")
	public String memberJoinAction(
			@RequestParam("memberId") String memberId,
			@RequestParam("memberPwd") String memberPwd,
			@RequestParam("memberName") String memberName,
			@RequestParam("memberPhone") String memberPhone,
			@RequestParam("memberEmail") String memberEmail,
			@RequestParam("memberGender") String memberGender,
			@RequestParam("memberAddr") String memberAddr,
			@RequestParam("memberBirth") String memberBirth
			){
	
		//���� pwd�� ��ȣȭ
	String memberPwd2 = bcryptPasswordEncoder.encode(memberPwd);

	
	int value = ms.memberInsert(memberId, memberPwd2, memberName, memberPhone, memberEmail, memberGender, memberAddr, memberBirth);	
				
		//redirect = java �� sendredirect�� ����	
		return "redirect:/";
	}
	
	@RequestMapping(value="/memberList.do")
	//���۹���� value �ڿ� �Ἥ �����Ҽ� �ִ� but �Ⱦ��� �� ��ĸ�� �����
	public String memberList(Model model) {
		
		ArrayList<MemberVo> alist = ms.memberList();
		
		//Model Ŭ������ ����Ͽ� list�� ��´�. = setattribute�� ����
		model.addAttribute("alist",alist);
		
		return "member/memberList";
	}
	
	@ResponseBody
	//����(String)������ �Ⱥ����� ��ü�� ���� �״�� ������ ���
	@RequestMapping(value="/memberIdCheck.do")
	public String memberIdCheck(@RequestParam("memberId") String memberId) {
		
		String str = null;
		int value = 0;
		
		
		value = ms.memberIdCheck(memberId);
		
		str = "{\"value\":\""+value+"\"}";
		return str;
	}
	
	@RequestMapping(value="/memberLogin.do")
	public String memberLogin() {
		return "member/memberLogin";
	}
	
	@RequestMapping(value="/memberLoginAction.do")
	public String memberLoginAction(
			@RequestParam("memberId") String memberId,
			@RequestParam("memberPwd") String memberPwd,
			HttpSession session,
			RedirectAttributes rttr // 1ȸ ���â
			) {
		MemberVo mv =ms.memberLogin(memberId);
		String path;
		if(mv!=null && bcryptPasswordEncoder.matches(memberPwd, mv.getMemberpwd())) {

			
//			session.setAttribute("midx", mv.getMidx());
//			session.setAttribute("memberName", mv.getMembername());
			rttr.addAttribute("midx", mv.getMidx());
			rttr.addAttribute("memberName", mv.getMembername());
			
			if(session.getAttribute("dest")==null) {;
				path = "redirect:/";
			}else {
				String dest = (String)session.getAttribute("dest");
				path="redirect:"+dest;
			}
			
		}else {
			rttr.addFlashAttribute("msg", "���̵�� ��й�ȣ�� Ȯ�����ּ���");
			path = "redirect:/member/memberLogin.do";
		}
			
		return path;
	}
	
	@RequestMapping(value="/memberLogOut.do")
	public String memberLogout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	
}
