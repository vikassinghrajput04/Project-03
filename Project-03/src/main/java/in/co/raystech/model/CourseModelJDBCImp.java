package in.co.raystech.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.raystech.dto.CourseDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DatabaseException;
import in.co.raystech.utility.JDBCDataSource;
/**
 * 
 * @author Vikas Singh
 *
 */
public class CourseModelJDBCImp implements CourseModelInt {
	private static Logger log = Logger.getLogger(CourseModelJDBCImp.class);

	public Long add(CourseDTO dto) throws Exception {
		log.debug("CourseModel.add Started!!");
		StringBuffer sql = new StringBuffer("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");
		Connection conn = null;
		Long pk = nextPK();
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ps.setString(2, dto.getCourseName());
			ps.setString(3, dto.getDescription());
			ps.setString(4, dto.getDuration());
			ps.setString(5, dto.getCreatedBy());
			ps.setString(6, dto.getModifiedBy());
			ps.setTimestamp(7, dto.getCreatedDatetime());
			ps.setTimestamp(8, dto.getModifiedDatetime());

			ps.executeUpdate();
			conn.commit();
			log.debug("CourseModel.add Success!!!");
			ps.close();

		} catch (Exception e) {
			log.error("CourseModel.add Exception");
			throw new DatabaseException("Exception in Inserting");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CourseModel.add Closed!!!");
		}
		
		return pk;
	}

	public Long nextPK() throws Exception {
		log.debug("CourseModel.nextPk Started!!!");
		StringBuffer sql = new StringBuffer("SELECT MAX(ID) FROM ST_COURSE");
		Long pk = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			log.debug("CourseModel.nextPk Success!!!");
		} catch (Exception e) {
			log.error("CourseModel.nextPk Exception");
			throw new DatabaseException("Exception in getting PK");

		}finally {
			log.debug("CourseModel.nextPk Closed!!!");
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk + 1;
	}

	public void update(CourseDTO dto) throws Exception {
		log.debug("CourseModel.update Started!!!");
		StringBuffer sql = new StringBuffer(
				"UPDATE ST_COURSE SET COURSE_NAME=?,DESCRIPTION=?,DURATION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		Connection conn = null;
		if (dto.getId() == 0) {
			throw new ApplicationException("User Does not Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, dto.getCourseName());
			ps.setString(2, dto.getDescription());
			ps.setString(3, dto.getDuration());
			ps.setString(4, dto.getCreatedBy());
			ps.setString(5, dto.getModifiedBy());
			ps.setTimestamp(6, dto.getCreatedDatetime());
			ps.setTimestamp(7, dto.getModifiedDatetime());
			ps.setLong(8, dto.getId());

			ps.executeUpdate();
			conn.commit();
			log.debug("CourseModel.update Success!!!");

		} catch (Exception e) {
			log.error("CourseModel.update Exception");
			throw new DatabaseException("Exception in Update");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CourseModel.update Closed!!!");
		}
		
	}

	public void delete(CourseDTO dto) throws Exception {
		log.debug("CourseModel.delete Started");
		StringBuffer sql = new StringBuffer("DELETE FROM ST_COURSE WHERE ID=?");
		Connection conn = null;
		if (dto.getId() == 0) {
			throw new ApplicationException("User Does not Exist");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, dto.getId());

			ps.executeUpdate();
			conn.commit();
			log.debug("CourseModel.delete Success!!!");
		} catch (Exception e) {
			log.error("CourseModel.delete Exception");
			throw new DatabaseException("Error in Deleting");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CourseModel.delete Closed!!");
		}
	
	}

	public CourseDTO findByPk(long i) throws Exception {
		log.debug("CourseModel.findByPk Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
		Connection conn = null;
		CourseDTO dto = new CourseDTO();
		if (i == 0) {
			throw new ApplicationException("User Does not Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, i);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getInt(1));
				dto.setCourseName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setDuration(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));
			}
			ps.close();
			rs.close();
			log.debug("CourseModel.findByPk Success!!!");

		} catch (Exception e) {
			log.error("CourseModel.findByPk Exception");
			throw new DatabaseException("Error in getting Data");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CourseModel.findByPk Closed!!!");
		}
		
		return dto;
	}

	public CourseDTO findByName(String name) throws Exception {
		log.debug("CourseModel.findByName Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE COURSE_NAME=?");
		Connection conn = null;
		CourseDTO dto = new CourseDTO();
		if (name == null) {
			throw new ApplicationException("User Does not Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getInt(1));
				dto.setCourseName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setDuration(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));
			}
			ps.close();
			rs.close();
			log.debug("CourseModel.findByName Success!!!");

		} catch (Exception e) {
			log.error("CourseModel.findByName Exception");
			throw new DatabaseException("Error in getting Data");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CourseModel.findByName Closed!!!");
		}
		
		return dto;
	}

	public List<CourseDTO> search(CourseDTO dto) throws Exception {
		log.debug("CourseModel.search null argument Started!!!");
		dto = new CourseDTO();
		return search(dto, 0, 0);
	}

	public List<CourseDTO> search(CourseDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("CourseModel.search Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");
		
		if (dto.getId() != 0) {
			Long id = dto.getId();
			sql.append(" AND ID = " + id);

		}
		if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
			sql.append(" AND COURSE_NAME LIKE '" + dto.getCourseName() + "%'");

		}
		if (dto.getDescription() != null && dto.getDescription().length() > 0) {
			sql.append(" AND DESCRIPTION LIKE '" + dto.getDescription() + "%'");

		}
		if (dto.getDuration() != null && dto.getDuration().length() > 0) {
			sql.append(" AND DURATION LIKE '" + dto.getDuration() + "%'");

		}
		
		
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}
		log.debug("CourseModel.search Success Query!!!");
		System.out.println("Query--- "+sql);
		ArrayList<CourseDTO> list = new ArrayList<CourseDTO>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			System.out.println(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new CourseDTO();
				dto.setId(rs.getInt(1));
				dto.setCourseName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setDuration(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));
				list.add(dto);
			}
				log.debug("CourseModel.search Success!!!");
			
		} catch (Exception e) {
			log.error("CourseModel.search Exception");
			throw new DatabaseException("Exception in Search Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("CourseModel.search Closed!!!");
		}
		
		return list;
	}
	public List<CourseDTO> list() throws Exception {
		log.debug("CourseModel.list null Argument Started!!!");
		return list(1,10);

	}
	public List<CourseDTO> list(int pageNo,int pageSize) throws Exception{
		log.debug("CourseModel.list Started!!!");
				StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE ");
		
				if(pageSize>0){
					pageNo = (pageNo-1)*pageSize;
					sql.append(" limit "+ pageNo +" , "+pageSize);
				}
				log.debug("CourseModel.list Query Success!!!");
				List<CourseDTO> list = new ArrayList<CourseDTO>();
				Connection conn = null;
				
				try{
					conn = JDBCDataSource.getConnection();
					PreparedStatement ps =conn.prepareStatement(sql.toString());
					ResultSet rs =ps.executeQuery();
					while (rs.next()) {
						CourseDTO dto = new CourseDTO();
						dto.setId(rs.getInt(1));
						dto.setCourseName(rs.getString(2));	
						dto.setDescription(rs.getString(3));
						dto.setDuration(rs.getString(4));
					    dto.setCreatedBy(rs.getString(5));
						dto.setModifiedBy(rs.getString(6));
						dto.setCreatedDatetime(rs.getTimestamp(7));
						dto.setModifiedDatetime(rs.getTimestamp(8));
						
						list.add(dto);
					}
					log.debug("CourseModel.list Success!!!");
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
					log.error("CourseModel.list Exception"+e);
					throw new ApplicationException("Exception : Exception in CourseModel List method " + e.getMessage());		
				}finally{
					JDBCDataSource.closeConnection(conn);
					log.debug("CourseModel.list Closed!!!");
				}
				
				return list;
			}
}
