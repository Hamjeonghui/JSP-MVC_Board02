package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.vo.BoardSet;
import model.vo.BoardVO;
import model.vo.ReplyVO;

public class BoardDAO { // BoardSet을 다루는 DAO클래스
	Connection conn;
	PreparedStatement pstmt;

	static final String insert_board="insert into board (mid,content) values(?,?)";
	static final String insert_reply="insert into reply (bid,mid,msg) values(?,?,?)";
	static final String delete_board="delete from board where bid=?";
	static final String delete_reply="delete from reply where rid=?";
	static final String update="update board set favcnt=favcnt+1 where bid=?";
	//전체목록
	static final String selectAll_board="select * from board order by bid desc limit 0,?"; // 페이징처리 pagination
	//내가 쓴 글 확인하려고!
	static final String selectAll_board2="select * from board where mid=? order by bid desc limit 0,?";
	//내가 쓴 글 전부 말고! 해당하는 글 하나!
	static final String selecOne_board="select * from board where bid=?";
	// 오라클에서는 rownum으로 대체하여 sql작성★
	static final String selectAll_reply="select * from reply where bid=? order by rid desc";
	
	
	
	
	// 글 작성
	public boolean insertBoard(BoardVO vo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(insert_board);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getContent());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		JDBCUtil.disconnect(pstmt, conn);
		return true;
	}
	
	// 댓글 작성
	public boolean insertReply(ReplyVO vo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(insert_reply);
			pstmt.setInt(1, vo.getBid());
			pstmt.setString(2, vo.getMid());
			pstmt.setString(3, vo.getMsg());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		JDBCUtil.disconnect(pstmt, conn);
		return true;
	}
	
	// 글 삭제
	public boolean deleteBoard(BoardVO vo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(delete_board);
			pstmt.setInt(1, vo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		JDBCUtil.disconnect(pstmt, conn);
		return true;
	}
	
	// 댓글 삭제
	public boolean deleteReply(ReplyVO vo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(delete_reply);
			pstmt.setInt(1, vo.getRid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		JDBCUtil.disconnect(pstmt, conn);
		return true;
	}
	
	// 좋아요 ☆
	public boolean update(BoardVO vo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(update);
			pstmt.setInt(1, vo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}
	
	// 전체 글+댓글 출력 ☆
	public ArrayList<BoardSet> selectAll(BoardVO vo, int cnt){
		ArrayList<BoardSet> datas=new ArrayList<BoardSet>();
		conn=JDBCUtil.connect();
		try {
			//그게 아니라면 더 하던거 해(특정인 검색을 안했다면!)
			// ★ 이때 왼쪽부터 읽기 때문에 equals가 먼저 온다면 "야! 이거 null인데 어떻게 equals해!"
			//하면서 Exception뜬다! 그러니까 무조건 null부터 써야함!!! ★
			if(vo.getMid()==null || vo.getMid().equals("")) {
				System.out.println("dao로그 : 전체목록출력");
				pstmt=conn.prepareStatement(selectAll_board);
				pstmt.setInt(1, cnt);
			}else {//특정인 게시글 보기였다면,
				System.out.println("dao로그 : 내가 쓴 글 목록 보기");
				pstmt=conn.prepareStatement(selectAll_board2);
				pstmt.setString(1, vo.getMid());
				pstmt.setInt(2, cnt);
			}

			//이 아래로는 동일합니다
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardSet bs=new BoardSet();
				
				BoardVO boardVO=new BoardVO();
				boardVO.setBdate(rs.getString("bdate"));
				boardVO.setBid(rs.getInt("bid"));
				boardVO.setContent(rs.getString("content"));
				boardVO.setFavcnt(rs.getInt("favcnt"));
				boardVO.setMid(rs.getString("mid"));
				boardVO.setRpcnt(rs.getInt("rpcnt"));
				bs.setBoardVO(boardVO);
				
				ArrayList<ReplyVO> replyList=new ArrayList<ReplyVO>();
				pstmt=conn.prepareStatement(selectAll_reply);
				pstmt.setInt(1, rs.getInt("bid"));
				ResultSet rs2=pstmt.executeQuery();
				while(rs2.next()) {
					ReplyVO replyVO=new ReplyVO();
					replyVO.setBid(rs2.getInt("bid"));
					replyVO.setMid(rs2.getString("mid"));
					replyVO.setMsg(rs2.getString("msg"));
					replyVO.setRdate(rs2.getString("rdate"));
					replyVO.setRid(rs2.getInt("rid"));
					replyList.add(replyVO);
				}
				rs2.close();
				bs.setReplyList(replyList);
				
				datas.add(bs);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.disconnect(pstmt, conn);
		return datas;
	}
	
	// 글 1개랑 +댓글 출력 ☆
	public ArrayList<BoardSet> selectOne(BoardVO vo){
		ArrayList<BoardSet> datas=new ArrayList<BoardSet>();
		conn=JDBCUtil.connect();
		try {
				System.out.println("dao로그 : 글 상세보기");
				pstmt=conn.prepareStatement(selecOne_board);
				pstmt.setInt(1, vo.getBid());
			
				ResultSet rs=pstmt.executeQuery();
			//검색 결과가 있으면
			if(rs.next()) {
				BoardSet bs=new BoardSet();
				
				BoardVO boardVO=new BoardVO();
				boardVO.setBdate(rs.getString("bdate"));
				boardVO.setBid(rs.getInt("bid"));
				boardVO.setContent(rs.getString("content"));
				boardVO.setFavcnt(rs.getInt("favcnt"));
				boardVO.setMid(rs.getString("mid"));
				boardVO.setRpcnt(rs.getInt("rpcnt"));
				bs.setBoardVO(boardVO);
				
				ArrayList<ReplyVO> replyList=new ArrayList<ReplyVO>();
				pstmt=conn.prepareStatement(selectAll_reply);
				pstmt.setInt(1, rs.getInt("bid"));
				ResultSet rs2=pstmt.executeQuery();
				while(rs2.next()) {
					ReplyVO replyVO=new ReplyVO();
					replyVO.setBid(rs2.getInt("bid"));
					replyVO.setMid(rs2.getString("mid"));
					replyVO.setMsg(rs2.getString("msg"));
					replyVO.setRdate(rs2.getString("rdate"));
					replyVO.setRid(rs2.getInt("rid"));
					replyList.add(replyVO);
				}
				rs2.close();
				bs.setReplyList(replyList);
				
				datas.add(bs);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.disconnect(pstmt, conn);
		return datas;
	}
	
	
	
}
