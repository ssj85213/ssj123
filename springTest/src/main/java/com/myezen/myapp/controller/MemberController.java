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
	//memberService에 같은 타입들을 주입하는것(memberservice에 있는 메소드 사용)
	
	//암호화 하는 모듈 주입
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
	
		//받은 pwd를 암호화
	String memberPwd2 = bcryptPasswordEncoder.encode(memberPwd);

	
	int value = ms.memberInsert(memberId, memberPwd2, memberName, memberPhone, memberEmail, memberGender, memberAddr, memberBirth);	
				
		//redirect = java 의 sendredirect와 같음	
		return "redirect:/";
	}
	
	@RequestMapping(value="/memberList.do")
	//전송방식을 value 뒤에 써서 정의할수 있다 but 안쓰면 두 방식모두 실행됨
	public String memberList(Model model) {
		
		ArrayList<MemberVo> alist = ms.memberList();
		
		//Model 클레스를 사용하여 list를 담는다. = setattribute와 같음
		model.addAttribute("alist",alist);
		
		return "member/memberList";
	}
	
	@ResponseBody
	//문자(String)형으로 안보내고 객체의 형을 그대로 보낼때 사용
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
			RedirectAttributes rttr // 1회 경고창
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
			rttr.addFlashAttribute("msg", "아이디와 비밀번호를 확인해주세요");
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
