package com.myezen.myapp.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.PageMaker;
import com.myezen.myapp.domain.SearchCriteria;
import com.myezen.myapp.service.BoardService;
import com.myezen.myapp.util.MediaUtils;
import com.myezen.myapp.util.UploadFileUtiles;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	
	@Autowired  //같은 타입을 찾아서 주입
	BoardService bs;
	
	@Autowired(required=false)//(required=false) null값을 받아도 화면이 출력됨
	PageMaker pm;
	
	@Resource(name="uploadPath") //같은 name을 찾아서 주입
	String uploadPath;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
//-----리스트-------------------------------------------------------------------
	@RequestMapping(value="/boardList.do")
	public String boardList(SearchCriteria scri,
			Model model) {
		
	//페이징
		int totalCount = bs.boardTotal(scri);
		pm.setScri(scri);
		pm.setTotalCount(totalCount);
		
	//게시판리스트
		ArrayList<BoardVo> blist = bs.boardSelectAll(scri);
		
		model.addAttribute("blist",blist);
		model.addAttribute("pm", pm);
		
		return "board/boardList";
	}

//-----컨텐츠-------------------------------------------------------------------
	
	@RequestMapping(value="/boardContents.do")
	public String boardSelectOne(Model model,
			@RequestParam("bidx") int bidx) {
		
		bs.BoardViewCnt(bidx);
		
		BoardVo bv = bs.boardSelectOne(bidx);

		model.addAttribute("bv",bv);
		return "board/boardContents";
	}

//---글쓰기---------------------------------------------------------------------	
	
	@RequestMapping(value="/boardWrite.do")
	public String boardWrite() {
		
		return "board/boardWrite";
	}
	
	@RequestMapping(value="/boardWriteAction.do")
	public String boardWriteAction(
			@RequestParam("subject") String subject,
			@RequestParam("contents") String contents,
			@RequestParam("writer") String writer,
			@RequestParam("pwd") String pwd,
			@RequestParam("filename") MultipartFile filename,
			HttpSession session
			) throws Exception{
		MultipartFile file = filename;
		
//		System.out.println("원본파일이름"+file.getOriginalFilename());
		
		String uploadedFileName ="";
		
		
		if(!file.getOriginalFilename().equals("")){
			uploadedFileName = UploadFileUtiles.uploadFile(
					uploadPath, 
					file.getOriginalFilename(), 
					file.getBytes());
		}
		
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		int midx =0;
		if(session.getAttribute("midx") !=null) {
			midx = Integer.parseInt(session.getAttribute("midx").toString());
		}
		
//		String pwd2 = bcryptPasswordEncoder.encode(pwd);
		
		BoardVo bv = new BoardVo();
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setIp(ip);
			bv.setMidx(midx);
			bv.setPwd(pwd);
			bv.setFilename(uploadedFileName);
		
		int value = bs.boardInsert(bv);
		
		return "redirect:/board/boardList.do";
	}

//----글수정--------------------------------------------------------------------	
	
	@RequestMapping(value="/boardModify.do")
	public String boardModify(Model model,
			@RequestParam("bidx") int bidx)
		{

		BoardVo bv = bs.boardSelectOne(bidx);
		model.addAttribute("bv",bv);

		return  "/board/boardModify";
	}
	
	@RequestMapping(value="/boardModifyAction.do")
	public String boardModifyAction(
			@RequestParam("bidx") int bidx,
			@RequestParam("subject") String subject,
			@RequestParam("contents") String contents,
			@RequestParam("writer") String writer,
			@RequestParam("filename") MultipartFile filename,
			@RequestParam("pwd") String pwd,
			HttpSession session
			) throws Exception {			
		MultipartFile file = filename;
//		System.out.println("bidx"+bidx+"sub"+subject+"con"+contents+"wri"+writer+"file"+filename+"pwd"+pwd);
	
//		System.out.println("원본파일이름"+file.getOriginalFilename());
		
		String uploadedFileName ="";
		
		
		if(!file.getOriginalFilename().equals("")){
			uploadedFileName = UploadFileUtiles.uploadFile(
					uploadPath, 
					file.getOriginalFilename(), 
					file.getBytes());
		}
		
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		int midx =0;
		if(session.getAttribute("midx") !=null) {
			midx = Integer.parseInt(session.getAttribute("midx").toString());
		}
		System.out.println("midx"+midx);
		
//		String pwd2 = bcryptPasswordEncoder.encode(pwd);
		
		BoardVo bv = new BoardVo();
			bv.setBidx(bidx);
			bv.setSubject(subject);
			bv.setContents(contents);
			bv.setWriter(writer);
			bv.setIp(ip);
			bv.setMidx(midx);
			bv.setPwd(pwd);
			bv.setFilename(uploadedFileName);

		
		
		int value = bs.boardModify(bv);
		
		System.out.println("수정 value"+value);

		
		String path = "";
		
		if(value ==1) {
			path = "redirect:/board/boardContents.do?bidx="+bidx;
		}else {
		path = "redirect:/board//boardModify.do?bidx="+bidx;
		}
		
		return path;
	}
		
	@RequestMapping(value="/boardDelete.do")
	public String boardDelete(Model model,
			@RequestParam("bidx") int bidx ) {
		
		BoardVo bv = bs.boardSelectOne(bidx);
			model.addAttribute("bv", bv);
		
		return"/board/boardDelete";
	}
	
	@RequestMapping(value="/boardDeleteAction.do")
	public String boardDeleteAction(
			@RequestParam("pwd") String pwd,
			@RequestParam("bidx") int bidx,
			RedirectAttributes rttr
			) {
		String path;
		
		System.out.println(bidx);
		BoardVo bv = bs.boardSelectOne(bidx);
		String pwd1 = bv.getPwd();
		if(pwd != null && bcryptPasswordEncoder.matches(pwd,pwd1)) {
			bs.boardDelete(bidx);
			path ="redirect:/board/boardList.do" ;
		}else {
			rttr.addFlashAttribute("msg", "비밀번호를 확인해주세요");
//			String referer = request.getHeader("REferer");
//			path = "redirect" + referer;
			path = "redirect:/board/boardDelete.do";

		}	
		
		return path;
	}
	
	//다른 위치의 파일도 가져와서 다운받을수 있는 메소드
	@ResponseBody
	@RequestMapping(value="/displayFile.do", method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String fileName,@RequestParam(value="down",defaultValue="0" ) int down ) throws Exception{
		
//		System.out.println("fileName:"+fileName);
		
		InputStream in = null;		
		ResponseEntity<byte[]> entity = null;
		
	//	logger.info("FILE NAME :"+fileName);
		
		try{
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();		
			 
			in = new FileInputStream(uploadPath+fileName);
			
			if(mType != null){
				
				if (down==1) {
					fileName = fileName.substring(fileName.indexOf("_")+1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.add("Content-Disposition", "attachment; filename=\""+
							new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");	
					
				}else {
					headers.setContentType(mType);	
				}
				
			}else{
				
				fileName = fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\""+
						new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");				
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
					headers,
					HttpStatus.CREATED);
			
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally{
			in.close();
		}
		return entity;
	} 
	
	
	@RequestMapping(value="/boardReply.do",method=RequestMethod.GET)
	public String boardReply(
			@RequestParam("bidx") int bidx,
			@RequestParam("originbidx") int originbidx,
			@RequestParam("depth") int depth,
			@RequestParam("level_") int level_,
			Model model			
			) {
		
		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		bv.setOriginbidx(originbidx);
		bv.setDepth(depth);
		bv.setLevel_(level_);
		
		model.addAttribute("bv", bv);

		
		return "/board/boardReply";
	}
	
	@RequestMapping(value="/boardReplyAction.do",method=RequestMethod.POST)
	public String boardReplyA(
			@RequestParam("bidx") int bidx,
			@RequestParam("originbidx") int originbidx,
			@RequestParam("depth") int depth,
			@RequestParam("level_") int level_,
			@RequestParam("subject") String subject,
			@RequestParam("contents") String contents,
			@RequestParam("writer") String writer,
			@RequestParam("pwd") String pwd,
			@RequestParam("filename") MultipartFile filename,
			HttpSession session,
			Model model			
			) throws Exception {
		
		MultipartFile file = filename;
			
		String uploadedFileName ="";
				
		if(!file.getOriginalFilename().equals("")){
			uploadedFileName = UploadFileUtiles.uploadFile(
					uploadPath, 
					file.getOriginalFilename(), 
					file.getBytes());
			}

		String ip = InetAddress.getLocalHost().getHostAddress();
		
		int midx =0;
		if(session.getAttribute("midx") !=null) {
			midx = Integer.parseInt(session.getAttribute("midx").toString());
		}
		
		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		bv.setOriginbidx(originbidx);
		bv.setDepth(depth);
		bv.setLevel_(level_);
		bv.setSubject(subject);
		bv.setContents(contents);
		bv.setWriter(writer);
		bv.setIp(ip);
		bv.setMidx(midx);
		bv.setPwd(pwd);
		bv.setFilename(uploadedFileName);
		
		int value = bs.boardReply(bv);

		
		return "redirect:/board/boardList.do";
	
}
}
