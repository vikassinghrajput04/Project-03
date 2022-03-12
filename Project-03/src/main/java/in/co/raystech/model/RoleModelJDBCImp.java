package in.co.raystech.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import in.co.raystech.dto.RoleDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DatabaseException;
import in.co.raystech.exception.RecordNotFoundException;
import in.co.raystech.utility.JDBCDataSource;
/**
 * 
 * @author Vikas Singh
 *
 */
public class RoleModelJDBCImp implements RoleModelInt{
	private static Logger log = Logger.getLogger(RoleModelJDBCImp.class);

	public Integer nextPK() throws Exception {
		log.debug("RoleModel.nextPK Started");
		StringBuffer sql = new StringBuffer("SELECT MAX(ID) FROM ST_ROLE");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			log.debug("RoleModel.nextPK Success!!!");
		} catch (Exception e) {
			log.error("RoleModel.nextPK Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			log.debug("RoleModel.nextPK Closed!!!");
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public long add(RoleDTO dto) throws Exception {
		log.debug("RoleModel.add Started");
		StringBuffer sql = new StringBuffer("INSERT INTO ST_ROLE VALUES(?,?,?,?,?,?,?)");
		Connection conn = null;
		long pk = 0;
		/*
		 * Roledto Duplicatedto = findByName(dto.getName()); // create role if
		 * already exists if (Duplicatedto != null) { throw new
		 * DuplicateRecordException("Role already exists"); }
		 */
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getDescription());
			ps.setString(4, dto.getCreatedBy());
			ps.setString(5, dto.getModifiedBy());
			ps.setTimestamp(6, dto.getCreatedDatetime());
			ps.setTimestamp(7, dto.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			log.error("RoleModel.add Exception", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception: add rollback exception" + ex.getMessage());
			}
			throw new DatabaseException("Exception in RoleModel.add");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("RoleModel.add Closed!!!!");
		return pk;
	}

	public void delete(RoleDTO dto) throws Exception {
		log.debug("RoleModel.deleted started");
		StringBuffer sql = new StringBuffer("DELETE FROM ST_ROLE WHERE ID=?");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error("RoleModel.delete Exception" + e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:Delete rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception: RoleModel.delete");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("RoleModel.delete Closed!!!");

	}

	public void update(RoleDTO dto) throws Exception {
		log.debug("RoleModel.update Started!!!");
		StringBuffer sql = new StringBuffer("UPDATE ST_ROLE SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		Connection conn = null;
		/*
		 * Roledto duplicateRole = findByName(dto.getName()); // Check if updated Role
		 * already exists if (duplicateRole != null && duplicateRole.getId() !=
		 * dto.getId()) { throw new DuplicateRecordException("Role already exists"); }
		 */
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getDescription());
			ps.setString(3, dto.getCreatedBy());
			ps.setString(4, dto.getModifiedBy());
			ps.setTimestamp(5, dto.getCreatedDatetime());
			ps.setTimestamp(6, dto.getModifiedDatetime());
			ps.setLong(7, dto.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			log.debug("RoleModel.update Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("RoleModel.update Exception.."+ e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Updating Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("RoleModel.update Closed!!!");
	}

	
	public RoleDTO findByPK(long pk) throws Exception {
		 log.debug("RoleModel.findByPK Started");
		 StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE ID=?");
		RoleDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new RoleDTO();
				dto.setId(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
			log.debug("RoleModel.findByPK Success!!!!");
		} catch (Exception e) {
			 e.printStackTrace();
			 log.error("RoleModel.findByPk Exception.."+e);
			throw new ApplicationException("Exception :Exception in getting by user pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("RoleModel.findByPk Closed!!!");
		return dto;

	}

	
	public RoleDTO findByName(String Name) throws Exception {
		log.debug("RoleModel.findByName Started!!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE NAME=?");
		RoleDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, Name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new RoleDTO();
				dto.setId(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
			log.debug("RoleModel.findByName Success!!!");
			if (dto.getName() == null) {
				throw new RecordNotFoundException("Record Not Found!!!");
			}
		} catch (Exception e) {
			log.error("RoleModel.findByName Exception!!!!");
			throw new DatabaseException("RoleModel.findByName Exception");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("RoleModel.findByEmail Closed!!!!");
		return dto;

	}

	
	public List<RoleDTO> search(RoleDTO dto) throws Exception {
		return search(dto, 0, 0);
	}



	public List<RoleDTO> search(RoleDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("RoleModel.search Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE 1=1");

		ArrayList<RoleDTO> list = new ArrayList<RoleDTO>();

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				sql.append(" AND NAME LIKE '" + dto.getName() + "%'");
			}
			if (dto.getDescription() != null && dto.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION LIKE '" + dto.getDescription() + "%'");
				System.out.println(sql);
			}
		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new RoleDTO();
				dto.setId(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
				list.add(dto);
			}
			log.debug("RoleModel.search Success!!!");
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		 log.debug("RoleModel.search Closed!!!");
		return list;
	}

	
	public List<RoleDTO> list() throws Exception {
		return list(0, 0);

	}

	public List<RoleDTO> list(int pageNo, int pageSize) throws Exception {
		log.debug("RoleModel.list Started");
		ArrayList<RoleDTO> list = new ArrayList<RoleDTO>();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			System.out.println("list p");
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RoleDTO dto = new RoleDTO();
				dto.setId(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
				list.add(dto);
			}
			rs.close();
			log.debug("RoleModel.list Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("RoleModel.list Exception", e);
			throw new ApplicationException("RoleModel.Exception : Exception in getting list of Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("RoleModel.list Closed!!!");
		return list;

	}
}
