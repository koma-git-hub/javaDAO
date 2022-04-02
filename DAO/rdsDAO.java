package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import settingInterface.SqlSetting;

public class rdsDAO implements SqlSetting{

	
	public List<Map<String, String>> viewAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String,String>> dataList = new ArrayList<>();
		try {
			con = getConnection();
			pstmt = con.prepareStatement("select * from " + TABLE_NAME);
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				Map<String,String> obj = new LinkedHashMap<>();
				for(int i=0; i<COLUMNS_NAMES.length;i++) {
					
					String key = COLUMNS_NAMES[i];
					String value = rs.getString(key);
					obj.put(key,value);
				}
				dataList.add(obj);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeWork(con, pstmt, rs);
			
		}
		return dataList;
	}
	
	
	public void insertData(int id, String name, String birthday) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement("insert into person (id, name, birthday) values (?, ?, ?)");
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setDate(3, getDateFormat(birthday));
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			closeWork(con, pstmt);
		}
	}
	
	
	public void updateData(int id, String name, String birthday) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement("update person set name=?, birthday=? where id=?");
			pstmt.setString(1, name);
			pstmt.setDate(2, getDateFormat(birthday));
			pstmt.setInt(3, id);
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			closeWork(con, pstmt);
			
		}
	}
	
	
	public void deleteData() {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement("delete from person");
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeWork(con, pstmt);
		}
	}
	
	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		
		Class.forName(JDBC_DRIVER);
		return DriverManager.getConnection(URL + DB_NAME, USER_NAME, PASSWORD);
	}
	
	public java.sql.Date getDateFormat(String date) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(SDF_FORMAT);
		java.util.Date utilDate = sf.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(utilDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cal.getTimeInMillis());
	}
	
	private void closeWork(Connection con,PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private void closeWork(Connection con,PreparedStatement pstmt,ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}