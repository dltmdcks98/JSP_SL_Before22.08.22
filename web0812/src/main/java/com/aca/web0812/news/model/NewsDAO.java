package com.aca.web0812.news.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aca.web0812.domain.News;
import com.aca.web0812.pool.ConnectionManager;
import com.aca.web0812.pool.PoolManager;


/*
 * 기존 코드방식에서랑은 다르게, Connection 객체는 커넥션 풀을 이용한다
 * 우리가 사용하는 커넥션풀은 Tomcat 서버가 제공하는 풀을 이용하되, JNDI로 자원에 접근할 예정
 */
public class NewsDAO {
	ConnectionManager manager;//웹이건 응용이건 둘다 포함 할수 있는 객체
	
	public NewsDAO(){
		//manager = new PoolManager();//웹용으로 생성 
		manager = PoolManager.getInstance();//위의 코드를 싱글톤으로 받아옴
		
	}
	//Create
	public int insert(News news) {
		Connection con=null;
		int result=0;
		PreparedStatement pstmt=null;
		try {
			con=manager.getConnection();//다형성: 자료형은 ConnectionManager이지만, 
			//호출되는 메서드 동작은 각각 틀리게 동작할 수 있다. ConnectionManger가 어떤 때는 PoolManager로 동작 DBManager로 동작할 수 있다.
			String sql="insert into news(news_id, title, writer, content) values(seq_news.nextval,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getWriter());
			pstmt.setString(3, news.getContent());
			
			result= pstmt.executeUpdate();//DML 수행
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			manager.freeConnection(con, pstmt);
		}
		return result;
	}
	//Read
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		List<News> list = new ArrayList<News>();
		
		con=manager.getConnection();
		String sql="select * from news order by news_id desc";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				News news = new News();
				news.setNews_id(rs.getInt("news_id"));
				news.setTitle(rs.getString("title"));
				news.setWriter(rs.getString("writer"));
				news.setContent(rs.getString("content"));
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));
				
				list.add(news);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			manager.freeConnection(con,pstmt,rs);
		}
		return list;
	}
	public void select() {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<News> list= new ArrayList<News>();
		
		con = manager.getConnection();
		String sql="select * from news where news_id=?";
		
	}
	//update
	public void update() {
		String sql="update news set title=?, writer?, content=? where news_id=?";
	}
	//delete
	public void delete() {
		String sql="delete news where news_id=?";
	}
}