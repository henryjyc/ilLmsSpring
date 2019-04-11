package com.st.lmssql.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.st.lmssql.dao.GenericDao;
import com.st.lmssql.models.LibraryBranch;

//has createStatement usage for reference
public class LibBranchDaoImp implements GenericDao<LibraryBranch> {

	private Connection con;
	
	public LibBranchDaoImp(Connection con) {
		this.con = con;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.st.lmssql.dao.GenericDao#add(java.lang.Object)
	 * returns false if an object with the id# already exists
	 */
	@Override
	public boolean add(LibraryBranch obj) throws SQLException {
		if(has(obj.getBranchId()))
				return false;
		String query = "INSERT INTO tbl_library_branch (branchName, branchAddress) " + 
					   "VALUES (" + obj.getBranchName() + "," + obj.getBranchAddress() + ")";
	
		Statement stmt = con.createStatement();
		stmt.executeUpdate(query);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.st.lmssql.dao.GenericDao#get(int)
	 * returns null if object is not found
	 */
	@Override
	public LibraryBranch get(int objId) throws SQLException {
		LibraryBranch libBranch = null;
		String query = "SELECT * " + 
					   "FROM tbl_library_branch " + 
					   "WHERE branchId=" + objId;
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			int bId = rs.getInt("branchId");
			String bName = rs.getString("branchName");
			String bAddr = rs.getString("branchAddress");
			libBranch = new LibraryBranch(bId, bName, bAddr);
		}		

		return libBranch;
	}

	/*
	 * (non-Javadoc)
	 * @see com.st.lmssql.dao.GenericDao#getAll()
	 * returns empty list if table is empty
	 */
	@Override
	public ArrayList<LibraryBranch> getAll() throws SQLException {
		ArrayList<LibraryBranch> libBranches = new ArrayList<>();
		String query = "SELECT * FROM tbl_library_branch";
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			int bId = rs.getInt("branchId");
			String bName = rs.getString("branchName");
			String bAddr = rs.getString("branchAddress");
			LibraryBranch lb = new LibraryBranch(bId, bName, bAddr);
			libBranches.add(lb);
		}	

		return libBranches;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.st.lmssql.dao.GenericDao#update(java.lang.Object)
	 * returns false if the object does not exist
	 */
	@Override
	public boolean update(LibraryBranch obj) throws SQLException {
		if(!has(obj.getBranchId()))
			return false;
		String query = "UPDATE tbl_library_branch " + 
					   "SET branchName=" + obj.getBranchName() + "," + "branchAddress=" + obj.getBranchAddress() + " " +
					   "WHERE branchId=" + obj.getBranchId();
		
		Statement stmt = con.createStatement();
		stmt.executeUpdate(query);  
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.st.lmssql.dao.GenericDao#delete(java.lang.Object)
	 * returns false if the object does not exist
	 */
	@Override
	public boolean delete(LibraryBranch obj) throws SQLException {
		if(!has(obj.getBranchId()))
			return false;
		String query = "DELETE FROM tbl_library_branch WHERE branchId=?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, obj.getBranchId());
		pstmt.executeUpdate();
		return true;
	}

	@Override
	public boolean has(int objId) throws SQLException {
		String query = "SELECT * FROM tbl_library_branch WHERE branchId=?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, objId);
		ResultSet rs = pstmt.executeQuery();
		return (rs.next());
	}
}