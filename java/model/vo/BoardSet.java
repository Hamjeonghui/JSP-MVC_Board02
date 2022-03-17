package model.vo;

import java.util.ArrayList;

//두 테이블을 연관지어 들고다닐 자료형 가방
public class BoardSet {
	private BoardVO boardVO; //게시글자료형
	private ArrayList<ReplyVO> replyList=new ArrayList<ReplyVO>(); //댓글리스트
	
	public BoardVO getBoardVO() {
		return boardVO;
	}
	public ArrayList<ReplyVO> getReplyList() {
		return replyList;
	}
	public void setBoardVO(BoardVO boardVO) {
		this.boardVO = boardVO;
	}
	public void setReplyList(ArrayList<ReplyVO> replyList) {
		this.replyList = replyList;
	}
	
	@Override
	public String toString() {
		return "BoardSet [boardVO=" + boardVO + ", replyList=" + replyList + "]";
	}
}