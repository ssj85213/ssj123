package com.myezen.myapp.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myezen.myapp.domain.CommentVo;
import com.myezen.myapp.service.CommentService;



@RequestMapping(value="/comment")
@RestController  //ResponseBody가 모든 컨트롤러에 적용됨
public class CommentController {
	
	@Autowired
	CommentService cs;
	
	
	@RequestMapping(value="/commentWrite.do", method=RequestMethod.POST)
	public JSONObject commentWrite(
			CommentVo cv
//			@RequestParam("bidx") int bidx,
//			@RequestParam("midx") int midx,
//			@RequestParam("cwriter") String cwriter,
//			@RequestParam("ccontents") String ccontents
//			@RequestParam("nextBlock") int nextBlock
			) throws Exception{		
		String ip = InetAddress.getLocalHost().getHostAddress();
		cv.setIp(ip);
//		CommentVo cv =new CommentVo();
//			cv.setBidx(bidx);
//			cv.setMidx(midx);
//			cv.setCwriter(cwriter);
//			cv.setCcontents(ccontents);
		
		int value = cs.commentInsert(cv);
		JSONObject js = new JSONObject();
		
		js.put("value", value);
//		System.out.println("bidx :"+cv.getBidx());
//		System.out.println("con :"+cv.getCcontents());
//		System.out.println("midx :"+cv.getMidx());
//		System.out.println("cwriter :"+cv.getMidx());
//	
		
		return js;
	}
	
	@RequestMapping(value="/{bidx}/{nextBlock}/commentList.do")
	public JSONObject commentList(
			@PathVariable("bidx") int bidx,
			@PathVariable("nextBlock") int nextBlock			
			) {
		
		JSONObject js = new JSONObject();
		
		ArrayList<CommentVo> alist =  cs.commentSelectAll(bidx,nextBlock);
		int totalCnt = cs.commentToalCnt(bidx);
		
		String moreView = "N";
		if(totalCnt > nextBlock*15) {
			moreView = "Y";			
		}
		
		js.put("alist", alist);
		js.putIfAbsent("moreView", moreView);
		
		return js;
	}	
	
	@RequestMapping(value="/{bidx}/{nextBlock}/more.do")
	public JSONObject more(
			@PathVariable("bidx") int bidx,
			@PathVariable("nextBlock") int nextBlock) {
		
		JSONObject js = new JSONObject();
		
		ArrayList<CommentVo> alist =  cs.commentSelectAll(bidx,nextBlock);
		int totalCnt = cs.commentToalCnt(bidx);
		int nextBlock2 = 0;
		
		if (totalCnt > nextBlock*15 ) {
			nextBlock2 = nextBlock+1;
		}else {
			nextBlock2 = nextBlock;
		}
		
		js.put("alist", alist);
		js.put("nextBlock", nextBlock2);
		
		
		return js;
	}
	
	@RequestMapping(value="/commentDelete.do", method=RequestMethod.POST)
	public JSONObject commentDelete(
			CommentVo cv) {
		JSONObject js = new JSONObject();
		
		int value = cs.commentDelete(cv);
		js.put("value", value);
		
		return js;
	}

}
