package in.co.raystech.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import in.co.raystech.dto.CollegeDTO;
import in.co.raystech.dto.StudentDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DatabaseException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.utility.JDBCDataSource;


/**
 * 
 * @author Vikas Singh
 *
 */
public class StudentModelJDBCImp implements StudentModelInt {

	private static Logger log = Logger.getLogger(StudentModelJDBCImp.class);

	public Integer nextPK() throws Exception {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_STUDENT");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			log.debug("StudentModel.nextPK Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("StudentModel.nextPK Exception..", e);
			throw new DatabaseException("Exception : Exception StudentModel.nextPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("StudentModel.nextPK End");
		return pk + 1;
	}

	public long add(StudentDTO dto) throws Exception {
		log.debug("StudentModel.add Started!!!");
		Connection conn = null;

		// get College Name
		CollegeModelJDBCImp cModel = new CollegeModelJDBCImp();
		CollegeDTO collegedto = cModel.findByPk(dto.getCollegeId());
		dto.setCollegeName(collegedto.getName());
		long pk =0;
		/*
		 * Studentdto duplicateName = findByEmailId(dto.getEmail()); int pk = 0;
		 * 
		 * if (duplicateName != null) { throw new
		 * DuplicateRecordException("Email already exists"); }
		 */

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_STUDENT VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setLong(2, dto.getCollegeId());
			pstmt.setString(3, dto.getCollegeName());
			pstmt.setString(4, dto.getFirstName());
			pstmt.setString(5, dto.getLastName());
			pstmt.setDate(6, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(7, dto.getMobileNo());
			pstmt.setString(8, dto.getEmail());
			pstmt.setString(9, dto.getCreatedBy());
			pstmt.setString(10, dto.getModifiedBy());
			pstmt.setTimestamp(11, dto.getCreatedDatetime());
			pstmt.setTimestamp(12, dto.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			log.debug("StudentModel.add Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("StudentModel.add Exception.." + e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("StudentModel.add Closed!!!");
		return pk;
	}

	public void delete(StudentDTO dto) throws Exception {
		log.debug("StudentModel.delete Started!!!");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_STUDENT WHERE ID=?");
			pstmt.setLong(1, dto.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
			log.debug("StudentModel.delete Success!!!");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("StudentModel.delete Exception.." + e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	public StudentDTO findByEmailId(String Email) throws Exception {
		log.debug("StudentModel.findBy Email Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE EMAIL=?");
		StudentDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, Email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new StudentDTO();
				dto.setId(rs.getInt(1));
				dto.setCollegeId(rs.getInt(2));
				dto.setCollegeName(rs.getString(3));
				dto.setFirstName(rs.getString(4));
				dto.setLastName(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setEmail(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();
			log.debug("StudentModel.findBy Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("StudentModel.findBy Exception.." + e);
			throw new ApplicationException("Exception : Exception in getting User by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("StudentModel.findBy Closed!!!");
		return dto;
	}

	public StudentDTO findByPK(long pk) throws Exception {
		log.debug("StudentModel.findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE ID=?");
		StudentDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new StudentDTO();
				dto.setId(rs.getInt(1));
				dto.setCollegeId(rs.getInt(2));
				dto.setCollegeName(rs.getString(3));
				dto.setFirstName(rs.getString(4));
				dto.setLastName(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setEmail(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
			log.debug("StudentModel.findByPK Success!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("StudentModel.findByPK Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("StudentModel.findByPK Closed!!!");
		return dto;
	}

	public void update(StudentDTO dto) throws Exception {
		log.debug("StudentModel.update Started!!!");
		Connection conn = null;

		StudentDTO dtoExist = findByEmailId(dto.getEmail());

		// Check if updated Roll no already exist
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Email Id is already exist");
		}

		// get College Name
		CollegeModelJDBCImp cModel = new CollegeModelJDBCImp();
		CollegeDTO collegedto = cModel.findByPk(dto.getCollegeId());
		dto.setCollegeName(collegedto.getName());

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_STUDENT SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DATE_OF_BIRTH=?,MOBILE_NO=?,EMAIL=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setLong(1, dto.getCollegeId());
			ps.setString(2, dto.getCollegeName());
			ps.setString(3, dto.getFirstName());
			ps.setString(4, dto.getLastName());
			ps.setDate(5, new java.sql.Date(dto.getDob().getTime()));
			ps.setString(6, dto.getMobileNo());
			ps.setString(7, dto.getEmail());
			ps.setString(8, dto.getCreatedBy());
			ps.setString(9, dto.getModifiedBy());
			ps.setTimestamp(10, dto.getCreatedDatetime());
			ps.setTimestamp(11, dto.getModifiedDatetime());
			ps.setLong(12, dto.getId());
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
			log.debug("StudentModel.update Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("StudentModel.update Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Student ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("StudentModel.update Closed!!!");
	}

	public List<StudentDTO> search(StudentDTO dto) throws Exception {
		return search(dto, 0, 0);
	}

	public List<StudentDTO> search(StudentDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("StudentModel.search Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME LIKE '" + dto.getFirstName() + "%'");
			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME LIKE '" + dto.getLastName() + "%'");
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + dto.getDob());
			}
			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO LIKE '" + dto.getMobileNo() + "%'");
			}
			if (dto.getEmail() != null && dto.getEmail().length() > 0) {
				sql.append(" AND EMAIL LIKE '" + dto.getEmail() + "%'");
			}
			if (dto.getCollegeId() != 0 && dto.getCollegeId() > 0) {
				sql.append(" AND COLLEGE_ID = " + dto.getCollegeId());
			}
			if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
				sql.append(" AND COLLEGE_NAME = '" + dto.getCollegeName() + "'");
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		System.out.println(sql);
		log.debug("StudentModel.search Query Success!!");
		ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new StudentDTO();
				dto.setId(rs.getInt(1));
				dto.setCollegeId(rs.getInt(2));
				dto.setCollegeName(rs.getString(3));
				dto.setFirstName(rs.getString(4));
				dto.setLastName(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setEmail(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
				list.add(dto);
			}
			rs.close();
			log.debug("StudentModel.search Success!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("StudentModel.search Exception.." + e);
			throw new ApplicationException("Exception : Exception in search Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("StudentModel.search Success!!!");
		return list;
	}

	public List<StudentDTO> list() throws Exception {
		return list(1, 100);
	}

	/**
	 * Get List of Student with pagination
	 * 
	 * 
	 * 
	 * @param pageNo   reads
	 * @param pageSize reads
	 * @return values in List
	 * @throws Exception exception
	 */
	public List<StudentDTO> list(int pageNo, int pageSize) throws Exception {
		log.debug("StudentModel.list Started!!!");
		ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		log.debug("Query Success!!! " + sql);
		System.out.println(sql);
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StudentDTO dto = new StudentDTO();
				dto.setId(rs.getInt(1));
				dto.setCollegeId(rs.getInt(2));
				dto.setCollegeName(rs.getString(3));
				dto.setFirstName(rs.getString(4));
				dto.setLastName(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setEmail(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
				list.add(dto);
			}
			rs.close();
			log.debug("StudentModel.list Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("StudentModel.list" + e);
			throw new ApplicationException("Exception : Exception in getting list of Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("StudentModel.list Closed!!!");
		return list;

	}

	public StudentDTO findByName(String firstname) throws Exception {
		log.debug("StudentModel.findBy Name Started!!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE FIRST_NAME=?");
		StudentDTO dto = null;
		Connection conn = null;
		if (firstname == null) {
			throw new ApplicationException("Enter Name");
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, firstname);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new StudentDTO();
				dto.setId(rs.getInt(1));
				dto.setCollegeId(rs.getInt(2));
				dto.setCollegeName(rs.getString(3));
				dto.setFirstName(rs.getString(4));
				dto.setLastName(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setEmail(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();
			log.debug("StudentModel.findByName Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("StudentModel.findByName Exception.." + e);
			throw new ApplicationException("Exception : Exception in getting User by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("StudentModel.findByName Closed!!!");
		return dto;
	}

}
