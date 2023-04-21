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
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
		
	@RequestMapping(value="/memberJoin.do")
	public String memberJoin() {		
		
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
			) {		
		
			String memberPwd2 = bcryptPasswordEncoder.encode(memberPwd);
			int value = ms.memberInsert(memberId, memberPwd2, memberName, memberPhone, memberEmail, memberGender, memberAddr, memberBirth);
					
		return "redirect:/";
	}
	
	@RequestMapping(value="/memberList.do")
	public String memberList(Model model) {
		
		ArrayList<MemberVo> alist =  ms.memberList();
				
		model.addAttribute("alist", alist);
		
		return "member/memberList";
	}
	
	@ResponseBody
	@RequestMapping(value="/memberIdCheck.do")
	public String memberIdCheck(@RequestParam("memberId") String memberId) {
		String str = null;
		int value=0;
		
		value = ms.memberIdCheck(memberId);
		
		str = "{\"value\": \""+value+"\"}";
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
			RedirectAttributes rttr
			) {		
			
		MemberVo mv  = ms.memberLogin(memberId);
		String path ="";
		
		if (mv!=null && bcryptPasswordEncoder.matches(memberPwd, mv.getMemberpwd())) {
			
			rttr.addAttribute("midx", mv.getMidx());
			rttr.addAttribute("memberName", mv.getMembername());
			
			if (session.getAttribute("dest") == null) {
				path ="redirect:/";
			}else {
				String dest = (String)session.getAttribute("dest");				
				path = "redirect:"+dest;				
			}			
		}else {
			rttr.addFlashAttribute("msg", "아이디와 비밀번호를 확인해주세요");
			path = "redirect:/member/memberLogin.do";			
		}		
		
		return path;
	}
	
	@RequestMapping(value="/memberLogOut.do")
	public String memberLogOut(HttpSession session) {
		
		session.removeAttribute("midx");
		session.removeAttribute("memberName");
		session.invalidate();		
		
		return "redirect:/";
				
	}
	
	
}
