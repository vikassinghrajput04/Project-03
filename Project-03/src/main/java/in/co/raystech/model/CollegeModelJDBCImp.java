package in.co.raystech.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import in.co.raystech.dto.CollegeDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DatabaseException;
import in.co.raystech.utility.JDBCDataSource;

/**
 * 
 * @author Vikas Singh
 *
 */
public class CollegeModelJDBCImp implements CollegeModelInt {
	private static Logger log = Logger.getLogger(CollegeModelJDBCImp.class);

	public int nextPK() throws Exception {
		log.debug("CollegeModel.nextPk started");
		StringBuffer sql = new StringBuffer("SELECT MAX(ID) FROM ST_COLLEGE");
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			ps.close();
			rs.close();
			log.debug("CollegeModel,next Pk Fetched");
		} catch (Exception e) {
			log.error("CollegeModel.nextPk Exception");
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CollegeModel.nextPk closed");
		}
		return pk + 1;
	}

	public long add(CollegeDTO dto) throws Exception {

		log.debug("CollegeModel.Add started");
		StringBuffer sql = new StringBuffer("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");
		Connection conn = null;

		int pk = nextPK();
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, pk);
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getAddress());
			ps.setString(4, dto.getState());
			ps.setString(5, dto.getCity());
			ps.setString(6, dto.getPhoneNo());
			ps.setString(7, dto.getCreatedBy());
			ps.setString(8, dto.getModifiedBy());
			ps.setTimestamp(9, dto.getCreatedDatetime());
			ps.setTimestamp(10, dto.getModifiedDatetime());

			ps.executeUpdate();
			conn.commit();
			JDBCDataSource.closeConnection(conn, ps);
			log.debug("CollegeModel.add Success");
		} catch (Exception e) {
			log.error("CollegeModel.add Exception");
			throw new ApplicationException("Error in Inserting Data");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CollegeModel.add Closed");
		}

		return pk;

	}

	public void delete(CollegeDTO dto) throws Exception {
		log.debug("CollegeModel.Delete started");
		StringBuffer sql = new StringBuffer("DELETE FROM ST_COLLEGE WHERE ID = ?");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			System.out.println("Deleted!!!!");
			log.debug("CollegeModel.delete Success");
		} catch (Exception e) {
			log.error("CollegeModel.delete Exception");
			throw new DatabaseException("Exception in Delete");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CollegeModel.delete Closed");
		}

	}

	public CollegeDTO findByName(String name) throws Exception {
		log.debug("CollegeModel.findByName Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE NAME = ?");

		Connection conn = null;
		CollegeDTO dto = new CollegeDTO();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto.setId(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneNo(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
			}
			log.debug("CollegeModel.findByName Success!!!");
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("CollegeModel.findByName Exception");
			throw new ApplicationException("Exception in Record Fetching");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CollegeModel.findByName Closed!!!");
		}

		return dto;
	}

	public CollegeDTO findByPk(long id) throws Exception {
		log.debug("CollegeModel.findByPk Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE ID = ?");
		Connection conn = null;
		CollegeDTO dto = new CollegeDTO();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneNo(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
			}
			log.debug("CollegeModel.findByPk Success!!!");
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("CollegeModel.findByPk Exception");
			throw new ApplicationException("Exception in Record Fetching");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CollegeModel.findByPk Closed!!!");
		}

		return dto;

	}

	public void update(CollegeDTO dto) throws Exception {
		log.debug("CollegeModel.update Started!!!");
		StringBuffer sql = new StringBuffer(
				"UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getAddress());
			ps.setString(3, dto.getState());
			ps.setString(4, dto.getCity());
			ps.setString(5, dto.getPhoneNo());
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());
			ps.setTimestamp(9, dto.getModifiedDatetime());
			ps.setLong(10, dto.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

			log.debug("CollegeModel.update Success!!!");

		} catch (Exception e) {
			log.error("CollegeModel.Update Exception");
			throw new ApplicationException("Exception in Update College");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CollegeModel.update Closed!!!");
		}

	}

	public CollegeDTO findByCity(String city) throws Exception {
		log.debug("CollegeModel.findByCity Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE CITY = ?");
		Connection conn = null;
		CollegeDTO dto = new CollegeDTO();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, city);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto.setId(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneNo(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));

			}
			log.debug("CollegeModel.findByCity Success!!!");
			ps.close();
			rs.close();
		} catch (Exception e) {
			log.error("CollegeModel.findByCity Exception", e);
			throw new ApplicationException("Exception in Record Fetching");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CollegeModel.findByCity Closed!!!");

		}

		return dto;

	}

	public List<CollegeDTO> search(CollegeDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("CollegeModel.Search Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				sql.append(" AND NAME like '" + dto.getName() + "%'");
			}
			if (dto.getAddress() != null && dto.getAddress().length() > 0) {
				sql.append(" AND ADDRESS like '" + dto.getAddress() + "%'");
			}
			if (dto.getState() != null && dto.getState().length() > 0) {
				sql.append(" AND STATE like '" + dto.getState() + "%'");
			}
			if (dto.getCity() != null && dto.getCity().length() > 0) {
				sql.append(" AND CITY like '" + dto.getCity() + "%'");
			}
			if (dto.getPhoneNo() != null && dto.getPhoneNo().length() > 0) {
				sql.append(" AND PHONE_NO = " + dto.getPhoneNo());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		log.debug("CollegeModel.Search Query Success!!!");
		ArrayList<CollegeDTO> list = new ArrayList<CollegeDTO>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new CollegeDTO();
				dto.setId(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneNo(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				list.add(dto);
			}
			log.debug("CollegeModel.Search Success!!!");
			rs.close();
			ps.close();
		} catch (Exception e) {
			log.error("CollegeModel.Search Exception");
			throw new ApplicationException("Exception : Exception in search college");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CollegeModel.Search Closed!!!");
		}

		return list;
	}

	public List<CollegeDTO> list(int pageNo, int pageSize) throws Exception {
		log.debug("CollegeModel.list Started!!!");
		List<CollegeDTO> list = new ArrayList<CollegeDTO>();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}
		log.debug("CollegeModel.list Query Success!!!");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CollegeDTO dto = new CollegeDTO();
				dto.setId(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneNo(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				list.add(dto);
			}
			rs.close();
			log.debug("CollegeModel.list Success!!!");
		} catch (Exception e) {
			log.error("CollegeModel.list Exception");
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CollegeModel.list Closed!!!");
		}

		return list;

	}

	public List list() throws Exception {
		return list(1, 10);
	}

}
