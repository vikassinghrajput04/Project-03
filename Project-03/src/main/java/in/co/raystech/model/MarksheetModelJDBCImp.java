package in.co.raystech.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import in.co.raystech.dto.MarksheetDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DatabaseException;
import in.co.raystech.exception.RecordNotFoundException;
import in.co.raystech.utility.JDBCDataSource;
/**
 * 
 * @author Vikas Singh
 *
 */
public class MarksheetModelJDBCImp implements MarksheetModelInt{
	private static Logger log = Logger.getLogger(MarksheetModelJDBCImp.class);

	public int nextPK() throws Exception {
		log.debug("MarksheetModel.nextPk Started!!!");
		StringBuffer sql = new StringBuffer("SELECT MAX(ID) FROM ST_MARKSHEET");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			log.debug("MarksheetModel.nextPk Success!!!");
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("MarksheetModel.nextPk Error");
			throw new DatabaseException("Exception in nextPk of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("MarksheetModel.nextPk close");
		}
		return pk + 1;
	}

	public Long add(MarksheetDTO dto) throws Exception {
		log.debug("MarksheetModel.add Started!!!");
		StringBuffer sql = new StringBuffer("INSERT INTO ST_MARKSHEET VALUES(?,?,?,?,?,?,?,?,?,?,?)");
		Connection conn = null;
		Long pk = (long) nextPK();
		/*
		 * if (dto.getName() == null) { throw new ApplicationException("Enter Name"); }
		 * if (dto.getStudentId()==0) { StudentModel m = new StudentModel();
		 * Studentdto stdto = new Studentdto(); stdto =
		 * m.findByName(dto.getName()); if (stdto.getId()==0) { throw new
		 * ApplicationException("Student id not found Please Add Student"); }
		 * dto.setStudentId((int) stdto.getId());
		 * 
		 * }
		 */
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ps.setString(2, dto.getRollNo());
			ps.setLong(3, dto.getStudentId());
			ps.setString(4, dto.getName());
			ps.setInt(5, dto.getPhysics());
			ps.setInt(6, dto.getChemistry());
			ps.setInt(7, dto.getMaths());
			ps.setString(8, dto.getCreatedBy());
			ps.setString(9, dto.getModifiedBy());
			ps.setTimestamp(10, dto.getCreatedDatetime());
			ps.setTimestamp(11, dto.getModifiedDatetime());

			ps.executeUpdate();
			conn.commit();
			log.debug("MarksheetModel.add Success!!!");

		} catch (Exception e) {
			log.error("MarksheetModel.add Exception!!!");
			throw new DatabaseException("Exception in add of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("MarksheetModel.add Closed!!!");
		}
		return pk;
	}

	public void delete(MarksheetDTO dto) throws Exception {
		StringBuffer sql = new StringBuffer("DELETE FROM ST_MARKSHEET WHERE ID=?");
		log.debug("MarksheetModel.Delete Started!!!");
		Connection conn = null;
		long id = dto.getId();
		if (id == 0) {
			log.error("Enter Id");
			throw new ApplicationException("Enter ID!!!!");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, id);
			ps.executeUpdate();
			conn.commit();
			log.debug("MarksheetModel.delete Success!!!");
		} catch (Exception e) {
			log.error("MarksheetModel.delete Exception");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public MarksheetDTO findByPK(long pk) throws Exception {
		log.debug("MarksheetModel.findByPk Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE ID=?");
		Connection conn = null;
		MarksheetDTO dto = new MarksheetDTO();
		if (pk == 0) {
			throw new ApplicationException("Enter Id");
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getInt(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setName(rs.getString(4));
				dto.setPhysics(rs.getInt(5));
				dto.setChemistry(rs.getInt(6));
				dto.setMaths(rs.getInt(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setCreatedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));

			}
			if (dto.getName() == null) {
				throw new RecordNotFoundException("Record Not found!!!!");
			}
			log.debug("MarksheetModel.findByPk Success!!!");
		} catch (Exception e) {
			log.error("MarksheetModel.findByPk Exception");
			throw new DatabaseException("Exception in FindByPK of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.error("MarksheetModel.findByPk Closed!!!");
		}
		return dto;
	}

	public MarksheetDTO findByRollNo(String rollNo) throws Exception {
		log.debug("MarksheetModel.findByRollNo Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE ROLL_NO=?");
		Connection conn = null;
		MarksheetDTO dto = new MarksheetDTO();
		if (rollNo == null) {
			throw new ApplicationException("Enter RollNo");
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, rollNo);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getInt(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setName(rs.getString(4));
				dto.setPhysics(rs.getInt(5));
				dto.setChemistry(rs.getInt(6));
				dto.setMaths(rs.getInt(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setCreatedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));

			}
			if (dto.getName() == null) {
				throw new RecordNotFoundException("Record Not found!!!!");
			}
			log.debug("MarksheetModel.findByRollNo Success!!!");
		} catch (Exception e) {
			log.error("MarksheetModel.findByRollNo Exception");
			throw new DatabaseException("Exception in FindByRollNo of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.error("MarksheetModel.findByRollNo Closed!!!");
		}
		return dto;
	}

	public List<MarksheetDTO> getMeritList(int pageNo, int pageSize) throws Exception {
		log.debug("MarksheetModel.getMeritList Started!!!");
		StringBuffer sql = new StringBuffer(
				"SELECT *,(PHYSICS+CHEMISTRY+MATHS) AS TOTAL_MARKS FROM ST_MARKSHEET WHERE PHYSICS>40 AND CHEMISTRY>40 AND MATHS>40 ORDER BY TOTAL_MARKS DESC");
		Connection conn = null;

		List<MarksheetDTO> list = new ArrayList<MarksheetDTO>();
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		log.debug("MarksheetModel.getMeritList Query Success!!!");
		System.out.println(sql);
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MarksheetDTO dto = new MarksheetDTO();
				dto.setId(rs.getInt(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setName(rs.getString(4));
				dto.setPhysics(rs.getInt(5));
				dto.setChemistry(rs.getInt(6));
				dto.setMaths(rs.getInt(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setCreatedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));
				list.add(dto);
			}
			rs.close();
			log.debug("MarksheetModel.getMeritList Success!!!");
		} catch (Exception e) {
			log.error("MarksheetModel.getMeritList Exception");
			throw new ApplicationException("Exception : Exception in getting merit list of student");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("MarksheetModel.getMeritList Closed!!!");
		}
		return list;
	}

	public void update(MarksheetDTO dto) throws Exception {
		log.debug("MarksheetModel.update Started!!!!");
		StringBuffer sql = new StringBuffer(
				"UPDATE ST_MARKSHEET SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, dto.getRollNo());
			ps.setLong(2, dto.getStudentId());
			ps.setString(3, dto.getName());
			ps.setInt(4, dto.getPhysics());
			ps.setInt(5, dto.getChemistry());
			ps.setInt(6, dto.getMaths());
			ps.setString(7, dto.getCreatedBy());
			ps.setString(8, dto.getModifiedBy());
			ps.setTimestamp(9, dto.getCreatedDatetime());
			ps.setTimestamp(10, dto.getModifiedDatetime());
			ps.setLong(11, dto.getId());

			ps.executeUpdate();
			conn.commit();
			log.debug("MarksheetModel.update Success!!!!");
		} catch (Exception e) {
			log.error("MarksheetModel.update Exception");
			throw new DatabaseException("Exception in MarksheetModel.update");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("MarksheetModel.update Closed!!!!");
		}

	}

	public List<MarksheetDTO> search(MarksheetDTO dto) throws Exception {
		return search(dto, 0, 0);
	}

	public List<MarksheetDTO> search(MarksheetDTO dto, int pageNo, int pageSize) throws Exception {

		log.debug("MarksheetModel.search Started!!!");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if (dto.getRollNo() != null && dto.getRollNo().length() > 0) {
				sql.append(" AND ROLL_NO LIKE '" + dto.getRollNo() + "%'");
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				sql.append(" AND NAME LIKE '" + dto.getName() + " % '");
			}
			if (dto.getPhysics() > 0) {
				sql.append(" AND PHYSICS = " + dto.getPhysics());
			}
			if (dto.getChemistry() > 0) {
				sql.append(" AND CHEMISTRY = " + dto.getChemistry());
			}
			if (dto.getMaths() > 0) {
				sql.append(" AND MATHS = '" + dto.getMaths());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		log.debug("MarksheetModel.search Query Success!!!");
		log.debug("Query--" + sql);
		System.out.println(sql);
		ArrayList<MarksheetDTO> list = new ArrayList<MarksheetDTO>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new MarksheetDTO();
				dto.setId(rs.getInt(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setName(rs.getString(4));
				dto.setPhysics(rs.getInt(5));
				dto.setChemistry(rs.getInt(6));
				dto.setMaths(rs.getInt(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setCreatedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));
				list.add(dto);
			}
			rs.close();
			log.debug("MarksheetModel.search Success!!!!");
		} catch (Exception e) {
			log.error("MarksheetModel.search Exception!!!");
			throw new ApplicationException("MarksheetModel search exception " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("MarksheetModel.search Closed!!!");
		return list;
	}

	public List<MarksheetDTO> list() throws Exception {
		return list(0,0);
	}

	public List<MarksheetDTO> list(int pageNo, int pageSize) throws Exception {
		log.debug("MarksheetModel.list Started!!!");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		log.debug("MarksheetModel.list Query Success!!!");
		log.debug("Query-- " + sql);
		System.out.println(sql);
		ArrayList<MarksheetDTO> list = new ArrayList<MarksheetDTO>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MarksheetDTO dto = new MarksheetDTO();
				dto = new MarksheetDTO();
				dto.setId(rs.getInt(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setName(rs.getString(4));
				dto.setPhysics(rs.getInt(5));
				dto.setChemistry(rs.getInt(6));
				dto.setMaths(rs.getInt(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setCreatedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));
				list.add(dto);
			}
			rs.close();
			log.debug("MarksheetModel.list Success!!!!");
		} catch (Exception e) {
			log.error("MarksheetModel.list Exception!!!");
			throw new ApplicationException("MarksheetModel list exception " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("MarksheetModel.list Closed!!!");
		return list;

	}

}