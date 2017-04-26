package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CartDBBean {

	private static CartDBBean instance = new CartDBBean();

	public static CartDBBean getInstance() {
		return instance;
	}

	private CartDBBean() {

	}

	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/Oracle11g");
		return ds.getConnection();

	}

	public void insertCart(CartDataBean cart)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "";
	
		try{
			conn = getConnection();
			sql = "insert into cart(book_id,buyer,book_title,buy_price,buy_count,book_image) "
					+ "values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart.getBook_id());
			pstmt.setString(2, cart.getBuyer());
			pstmt.setString(3, cart.getBook_title());
			pstmt.setInt(4, cart.getBuy_price());
			pstmt.setByte(5, cart.getBuy_count());
			pstmt.setString(6, cart.getBook_image());
			pstmt.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}finally {
			try{
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	
	public int getListCount(String id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int x = 0;
		
		try{
			conn= getConnection();
			pstmt = conn.prepareStatement("select count(*) from cart where buyer=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				x =rs.getInt(1);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
