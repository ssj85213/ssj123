package com.myezen.myapp.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myezen.myapp.domain.CommentVo;
import com.myezen.myapp.service.CommentService;

@RestController
@RequestMapping(value="/comment")
public class CommentController {
	
	@Autowired
	private CommentService cs;	

	@RequestMapping(value="/commentWrite.do", method=RequestMethod.POST)
	public JSONObject commentWrite(CommentVo cv) throws Exception{		
				
		String ip = InetAddress.getLocalHost().getHostAddress();		
		cv.setIp(ip);
		
		int value = cs.commentInsert(cv);
		
		JSONObject js = new JSONObject();
		js.put("value", value);		
		
		return js;
	}
	
	@RequestMapping(value="/{bidx}/{nextBlock}/commentList.do")
	public JSONObject commentList(
			@PathVariable("bidx") int bidx,
			@PathVariable("nextBlock") int nextBlock			
			) {
		
		JSONObject js = new JSONObject();
		ArrayList<CommentVo> alist = cs.commentSelectAll(bidx,nextBlock);
		int totalCnt = cs.commentTotalCnt(bidx);
		
		String moreView = "N";
		
		if (totalCnt > nextBlock*15) {
			moreView = "Y";
		}
		
		js.put("alist", alist);
		js.put("moreView", moreView);
		
		return js;
	}
	
	@RequestMapping(value="/{bidx}/{nextBlock}/more.do")
	public JSONObject more(
			@PathVariable("bidx") int bidx,
			@PathVariable("nextBlock") int nextBlock) {
		
		JSONObject js = new JSONObject();
		
		ArrayList<CommentVo> alist = cs.commentSelectAll(bidx,nextBlock);
		int totalCnt = cs.commentTotalCnt(bidx);
		int nextBlock2= 0;
		
		if (totalCnt > nextBlock*15 ) {
			nextBlock2 = nextBlock+1;
		}else {
			nextBlock2 = nextBlock;
		}				
		
		js.put("alist", alist);
		js.put("nextBlock", nextBlock2);
		
		return js;
	}
	
	
	
	
	
	

}
