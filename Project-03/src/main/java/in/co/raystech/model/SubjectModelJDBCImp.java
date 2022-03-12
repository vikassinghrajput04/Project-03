package in.co.raystech.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.raystech.dto.CourseDTO;
import in.co.raystech.dto.SubjectDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.utility.JDBCDataSource;

/**
 * 
 * @author Vikas Singh
 *
 */
public class SubjectModelJDBCImp implements SubjectModelInt{

	private Logger log = Logger.getLogger(SubjectModelJDBCImp.class);

	public int nextPk() throws Exception {
		log.debug("SubjectModel.nextPk started!!!");
		StringBuffer sql = new StringBuffer("SELECT MAX(id) FROM ST_SUBJECT");
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
			ps.close();
			log.debug("SubjectModel.nextPk Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SubjectModel.nextPk Exception ...", e);
			throw new ApplicationException("Exception in NextPk of subject Model");
		} finally {
			log.debug("SubjectModel.nextPk Closed!!!!");
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public Long add(SubjectDTO dto) throws Exception {
		long pk = nextPk();
		log.debug("SubjectModel.add Started!!!");
		Connection conn = null;
		StringBuffer sql = new StringBuffer("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");
		// get Course Name
		CourseModelJDBCImp cModel = new CourseModelJDBCImp();
		CourseDTO Coursedto = cModel.findByPk(dto.getCourseId());
		dto.setCourseName(Coursedto.getCourseName());


		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();

			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ps.setLong(1, pk);
			ps.setString(2, dto.getSubjectName());
			ps.setString(3, dto.getCourseName());
			ps.setInt(4, dto.getCourseId());
			ps.setString(5, dto.getDescription());
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());
			ps.setTimestamp(9, dto.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			log.debug("SubjectModel.add Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SubjectModel.add Exception.." + e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Subject");
		} finally {
			log.debug("SubjectModel.add Closed!!!");
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void delete(SubjectDTO dto) throws Exception {
		log.debug("SubjectModel.Delete Started!!!!");
		StringBuffer sql = new StringBuffer("DELETE FROM ST_SUBJECT WHERE ID=?");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			conn.commit();
			log.debug("SubjectModel.Delete Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SubjectModel.Delete Exception ...", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception in Rollback of Delte Method of Subject Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delte Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("SubjectModel.Delete Closed!!!");
	}

	public void update(SubjectDTO dto) throws Exception {
		log.debug("SubjectModel.update Started");
		StringBuffer sql = new StringBuffer(	"UPDATE ST_SUBJECT SET SUBJECT_NAME=?,COURSE_NAME=?,COURSE_ID=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		Connection conn = null;

		SubjectDTO dtoExist = findByPk(dto.getId());

		// Check if updated id already exist
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Subject Name is already exist");
		}

	
		 //get Course Name CourseModel cModel = new CourseModel(); Coursedto
		CourseModelJDBCImp courseModel = new CourseModelJDBCImp();
		  CourseDTO coursedto= courseModel.findByPk(dto.getCourseId());
		  dto.setCourseName(coursedto.getCourseName());
		 

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, dto.getSubjectName());
			ps.setString(2, dto.getCourseName());
			ps.setInt(3, dto.getCourseId());
			ps.setString(4, dto.getDescription());
			ps.setString(5, dto.getCreatedBy());
			ps.setString(6, dto.getModifiedBy());
			ps.setTimestamp(7, dto.getCreatedDatetime());
			ps.setTimestamp(8, dto.getModifiedDatetime());
			ps.setLong(9, dto.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SubjectModel.update Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Subject ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("SubjectModel.update Closed!!");
	}

	public SubjectDTO findByName(String name) throws Exception {
		log.debug("SubjectModel.findByName Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE SUBJECT_NAME=?");
		Connection conn = null;
		SubjectDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new SubjectDTO();
				dto.setId(rs.getInt(1));
				dto.setSubjectName(rs.getString(2));
				dto.setCourseName(rs.getString(3));
				dto.setCourseId(rs.getInt(4));
				dto.setDescription(rs.getString(5));
				dto.setCreatedBy(rs.getString(6));
				dto.setModifiedBy(rs.getString(7));
				dto.setCreatedDatetime(rs.getTimestamp(8));
				dto.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			ps.close();
			log.debug("SubjectModel.findByName Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SubjectModel.findByName Exception...."+ e);
			e.printStackTrace();
			throw new ApplicationException("Exception in findByName Method of SubjectModel");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		 log.debug("SubjectModel.findByName Closed!!!");
		return dto;
	}

	
	public SubjectDTO findByPk(long pk) throws Exception {
		log.debug("SubjectModel.findBypk Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
		Connection conn = null;
		SubjectDTO dto = new SubjectDTO();

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new SubjectDTO();

				dto.setId(rs.getInt(1));
				dto.setSubjectName(rs.getString(2));
				dto.setCourseName(rs.getString(3));
				dto.setCourseId(rs.getInt(4));
				dto.setDescription(rs.getString(5));
				dto.setCreatedBy(rs.getString(6));
				dto.setModifiedBy(rs.getString(7));
				dto.setCreatedDatetime(rs.getTimestamp(8));
				dto.setModifiedDatetime(rs.getTimestamp(9));
			}
			log.debug("SubjectModel.findBypk Success!!!");
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByPk Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("SubjectModel.findByPk Closed!!!");
		return dto;
	}

	public List<SubjectDTO> search(SubjectDTO dto) throws Exception {
		return search(dto, 0, 0);
	}

	public List<SubjectDTO> search(SubjectDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("SubjectModel.search Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}
			if (dto.getCourseId() > 0) {
				sql.append(" AND COURSE_ID = " + dto.getCourseId());
			}

			if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
				sql.append(" AND SUBJECT_NAME LIKE '" + dto.getSubjectName() + "%'");
			}
			if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
				sql.append(" AND COURSE_NAME LIKE '" + dto.getCourseName() + "%'");
			}
			if (dto.getDescription() != null && dto.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION LIKE '" + dto.getDescription() + " % '");
			}

		}

		// Page Size is greater then Zero then aplly pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		System.out.println("Query -- " + sql);
		log.debug("Query Success -- "+sql);
		Connection conn = null;
		ArrayList<SubjectDTO> list = new ArrayList<SubjectDTO>();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new SubjectDTO();

				dto.setId(rs.getInt(1));
				dto.setSubjectName(rs.getString(2));
				dto.setCourseName(rs.getString(3));
				dto.setCourseId(rs.getInt(4));
				dto.setDescription(rs.getString(5));
				dto.setCreatedBy(rs.getString(6));
				dto.setModifiedBy(rs.getString(7));
				dto.setCreatedDatetime(rs.getTimestamp(8));
				dto.setModifiedDatetime(rs.getTimestamp(9));
				list.add(dto);
			}
			rs.close();
			ps.close();
			log.debug("SubjectModel.search Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SubjectModel.search Exception....", e);
			throw new ApplicationException("Exception in search Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("SubjectModel.search Closed!!!!");
		return list;
	}

	public List<SubjectDTO> list() throws Exception {
		return list(0, 0);
	}

	public List<SubjectDTO> list(int pageNo, int pageSize) throws Exception {
		log.debug("SubjectModel.list Started!!!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT ");

		// Page Size is greater then Zero then aplly pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		ArrayList<SubjectDTO> list = new ArrayList<SubjectDTO>();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SubjectDTO dto = new SubjectDTO();

				dto.setId(rs.getInt(1));
				dto.setSubjectName(rs.getString(2));
				dto.setCourseName(rs.getString(3));
				dto.setCourseId(rs.getInt(4));
				dto.setDescription(rs.getString(5));
				dto.setCreatedBy(rs.getString(6));
				dto.setModifiedBy(rs.getString(7));
				dto.setCreatedDatetime(rs.getTimestamp(8));
				dto.setModifiedDatetime(rs.getTimestamp(9));
				list.add(dto);
			}
			rs.close();
			ps.close();
			log.debug("SubjectModel.list Success!!!!");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SubjectModel.list Exception....", e);
			throw new ApplicationException("Exception in list Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("SubjectModel.list Closed!!!");
		return list;
	}
}
