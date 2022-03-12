package in.co.raystech.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.raystech.dto.CollegeDTO;
import in.co.raystech.dto.CourseDTO;
import in.co.raystech.dto.FacultyDTO;
import in.co.raystech.dto.SubjectDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DatabaseException;
import in.co.raystech.exception.RecordNotFoundException;
import in.co.raystech.utility.JDBCDataSource;
/**
 * 
 * @author Vikas Singh
 *
 */

public class FacultyModelJDBCImp implements FacultyModelInt{
	private Logger log = Logger.getLogger(FacultyModelJDBCImp.class);

	public Long add(FacultyDTO dto) throws Exception {
		log.debug("FacultyModel.add Started!!!");
		StringBuffer sql = new StringBuffer("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		Long pk = nextPK();
		CollegeModelJDBCImp collegeModelJDBCImp = new CollegeModelJDBCImp();
		CollegeDTO collegedto = collegeModelJDBCImp.findByPk(dto.getCollegeId());
		dto.setCollegeName(collegedto.getName());

		CourseModelJDBCImp courseModel = new CourseModelJDBCImp();
		CourseDTO coursedto = courseModel.findByPk(dto.getCollegeId());
		dto.setCourseName(coursedto.getCourseName());

		SubjectModelJDBCImp subjectModel = new SubjectModelJDBCImp();
		SubjectDTO subjectdto = subjectModel.findByPk(dto.getSubjectId());
		dto.setSubjectName(subjectdto.getSubjectName());
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ps.setString(2, dto.getFirstName());
			ps.setString(3, dto.getLastName());
			ps.setString(4, dto.getGender());
			ps.setDate(5, new Date(dto.getdOJ().getTime()));
			ps.setString(6, dto.getQualification());
			ps.setString(7, dto.getEmailId());
			ps.setString(8, dto.getMobileNo());
			ps.setLong(9, dto.getCollegeId());
			ps.setString(10, dto.getCollegeName());
			ps.setLong(11, dto.getCourseId());
			ps.setString(12, dto.getCourseName());
			ps.setLong(13, dto.getSubjectId());
			ps.setString(14, dto.getSubjectName());
			ps.setString(15, dto.getCreatedBy());
			ps.setString(16, dto.getModifiedBy());
			ps.setTimestamp(17, dto.getCreatedDatetime());
			ps.setTimestamp(18, dto.getModifiedDatetime());

			ps.executeUpdate();
			conn.commit();
			log.debug("FacultyModel.add Success!!!");

		} catch (Exception e) {
			log.error("FacultyModel.add Exception!!!");
			throw new DatabaseException("Exception in Inserting Data");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FacultyModel.add Closed!!!");
		return pk;
	}

	public Long nextPK() throws Exception {
		log.debug("FacultyModel.nextPk Started!!!");
		StringBuffer sql = new StringBuffer("SELECT MAX(ID) FROM ST_FACULTY");
		Connection conn = null;
		Long pk = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			log.debug("FacultyModel.nextPk Success!!!");
		} catch (Exception e) {
			log.error("FacultyModel.nextPk Exception!!!");
			throw new DatabaseException("Error in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FacultyModel.nextPk Closed!!!");
		return pk + 1;
	}

	public void update(FacultyDTO dto) throws Exception {
		log.debug("FacultyModel.update Started!!!");
		StringBuffer sql = new StringBuffer(
				"UPDATE ST_FACULTY SET FIRST_NAME=?,LAST_NAME=?,GENDER=?,DOJ=?,QUALIFICATION=?,EMAIL_ID=?,MOBILE_NO=?,COLLEGE_ID=?,COLLEGE_NAME=?,COURSE_ID=?,COURSE_NAME=?,SUBJECT_ID=?,SUBJECT_NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		Connection conn = null;
		CollegeModelJDBCImp collegeModel = new CollegeModelJDBCImp();
		CollegeDTO collegedto = collegeModel.findByPk(dto.getCollegeId());
		dto.setCollegeName(collegedto.getName());

		CourseModelJDBCImp courseModel = new CourseModelJDBCImp();
		CourseDTO coursedto = courseModel.findByPk(dto.getCollegeId());
		dto.setCourseName(coursedto.getCourseName());

		SubjectModelJDBCImp subjectModel = new SubjectModelJDBCImp();
		SubjectDTO subjectdto = subjectModel.findByPk(dto.getSubjectId());
		dto.setSubjectName(subjectdto.getSubjectName());
		if (dto.getFirstName() == null) {
			throw new ApplicationException("User not Found");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, dto.getFirstName());
			ps.setString(2, dto.getLastName());
			ps.setString(3, dto.getGender());
			ps.setDate(4, new Date(dto.getdOJ().getTime()));
			ps.setString(5, dto.getQualification());
			ps.setString(6, dto.getEmailId());
			ps.setString(7, dto.getMobileNo());
			ps.setLong(8, dto.getCollegeId());
			ps.setString(9, dto.getCollegeName());
			ps.setLong(10, dto.getCourseId());
			ps.setString(11, dto.getCourseName());
			ps.setLong(12, dto.getSubjectId());
			ps.setString(13, dto.getSubjectName());
			ps.setString(14, dto.getCreatedBy());
			ps.setString(15, dto.getModifiedBy());
			ps.setTimestamp(16, dto.getCreatedDatetime());
			ps.setTimestamp(17, dto.getModifiedDatetime());
			ps.setLong(18, dto.getId());

			ps.executeUpdate();
			conn.commit();
			log.debug("FacultyModel.Update Success!!!");
			ps.close();
		} catch (Exception e) {
			log.error("FacultyModel.Update Exception");
			throw new DatabaseException("Exception in Update");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FacultyModel.Update Closed!!!");
	}

	public FacultyDTO findByPk(long id) throws Exception {
		log.debug("FacultyModel.findByPk Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID = ?");
		FacultyDTO dto = new FacultyDTO();
		Connection conn = null;
		if (id == 0) {
			System.err.println("Please Enter id");
			System.exit(1);
		} else {
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ps.setLong(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					dto.setId(rs.getLong(1));
					dto.setFirstName(rs.getString(2));
					dto.setLastName(rs.getString(3));
					dto.setGender(rs.getString(4));
					dto.setdOJ(rs.getDate(5));
					dto.setQualification(rs.getString(6));
					dto.setEmailId(rs.getString(7));
					dto.setMobileNo(rs.getString(8));
					dto.setCollegeId(rs.getInt(9));
					dto.setCollegeName(rs.getString(10));
					dto.setCourseId(rs.getInt(11));
					dto.setCourseName(rs.getString(12));
					dto.setSubjectId(rs.getInt(13));
					dto.setSubjectName(rs.getString(14));
					dto.setCreatedBy(rs.getString(15));
					dto.setModifiedBy(rs.getString(16));
					dto.setCreatedDatetime(rs.getTimestamp(17));
					dto.setModifiedDatetime(rs.getTimestamp(18));
				}
				if (dto.getFirstName() == null) {
					System.out.println("User Not Found");
					throw new RecordNotFoundException("Record Not Found!!!");
				}
				log.debug("FacultyModel.findByPk Success!!!");
			} catch (Exception e) {
				throw new DatabaseException("Exception in findByPk");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		}
		log.debug("FacultyModel.findByPk Closed!!!");
		return dto;
	}

	public FacultyDTO findByName(String name) throws Exception {
		log.debug("FacultyModel.findByName Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE FIRST_NAME = ?");
		FacultyDTO dto = new FacultyDTO();
		Connection conn = null;
		if (name == null) {
			System.err.println("Please Enter First_Name");
			System.exit(1);
		} else {
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ps.setString(1, name);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					dto.setId(rs.getLong(1));
					dto.setFirstName(rs.getString(2));
					dto.setLastName(rs.getString(3));
					dto.setGender(rs.getString(4));
					dto.setdOJ(rs.getDate(5));
					dto.setQualification(rs.getString(6));
					dto.setEmailId(rs.getString(7));
					dto.setMobileNo(rs.getString(8));
					dto.setCollegeId(rs.getInt(9));
					dto.setCollegeName(rs.getString(10));
					dto.setCourseId(rs.getInt(11));
					dto.setCourseName(rs.getString(12));
					dto.setSubjectId(rs.getInt(13));
					dto.setSubjectName(rs.getString(14));
					dto.setCreatedBy(rs.getString(15));
					dto.setModifiedBy(rs.getString(16));
					dto.setCreatedDatetime(rs.getTimestamp(17));
					dto.setModifiedDatetime(rs.getTimestamp(18));
				}
				if (dto.getMobileNo() == null) {
					throw new RecordNotFoundException("Record Not Found");
				}
			} catch (Exception e) {
				throw new DatabaseException("Exception in findByName");
			} finally {
				JDBCDataSource.closeConnection(conn);
				log.debug("FacultyModel.findByName Closed!!!");
			}
		}

		return dto;
	}

	public FacultyDTO findByEmail(String email) throws Exception {
		log.debug("FacultyModel.findByEmail Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_ID = ?");
		FacultyDTO dto = new FacultyDTO();
		Connection conn = null;
		if (email == null) {
			throw new ApplicationException("Please Enter Email");
		} else {
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ps.setString(1, email);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					dto.setId(rs.getLong(1));
					dto.setFirstName(rs.getString(2));
					dto.setLastName(rs.getString(3));
					dto.setGender(rs.getString(4));
					dto.setdOJ(rs.getDate(5));
					dto.setQualification(rs.getString(6));
					dto.setEmailId(rs.getString(7));
					dto.setMobileNo(rs.getString(8));
					dto.setCollegeId(rs.getInt(9));
					dto.setCollegeName(rs.getString(10));
					dto.setCourseId(rs.getInt(11));
					dto.setCourseName(rs.getString(12));
					dto.setSubjectId(rs.getInt(13));
					dto.setSubjectName(rs.getString(14));
					dto.setCreatedBy(rs.getString(15));
					dto.setModifiedBy(rs.getString(16));
					dto.setCreatedDatetime(rs.getTimestamp(17));
					dto.setModifiedDatetime(rs.getTimestamp(18));
				}
				if (dto.getFirstName() == null) {
					System.out.println("User Not Found");
					throw new RecordNotFoundException("Record Not Found!!!");
				}
			} catch (Exception e) {
				log.debug("FacultyModel.findByEmail Exception!!!");
				throw new DatabaseException("Exception in findByEmail");
			} finally {
				JDBCDataSource.closeConnection(conn);
				log.debug("FacultyModel.findByEmail Closed!!!");
			}
		}

		return dto;
	}

	public void delete(FacultyDTO dto) throws Exception {
		log.debug("FacultyModel.delete Started!!!");
		StringBuffer sql = new StringBuffer("DELETE FROM ST_FACULTY WHERE ID = ?");
		Connection conn = null;
		if (dto.getId() == 0) {
			throw new ApplicationException("Enter Id");
		} else {
			dto = findByPk(dto.getId());
			if (dto.getFirstName() == null) {
				throw new RecordNotFoundException("Record Not Found");
			}
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, dto.getId());

			ps.executeUpdate();
			conn.commit();
			log.debug("FacultyModel.delete Success!!!");
		} catch (Exception e) {
			log.error("FacultyModel.delete Exception!!!");
			throw new DatabaseException("Exception in delete");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("FacultyModel.delete Closed!!");
		}
	}

	public List<FacultyDTO> list() throws Exception {
		log.debug("FacultyModel.list null argument Started!!!");
		return list(0, 0);
	}

	public List<FacultyDTO> list(int pageNo, int pageSize) throws Exception {
		log.debug("FacultyModel.list Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY");
		ArrayList<FacultyDTO> list = new ArrayList<FacultyDTO>();
		Connection conn = null;

		// if page is greater than zero then apply pagination
		if (pageSize > 0) {
			sql.append(" LIMIT " + pageNo + " , " + pageSize);
		}
		log.debug("FacultyModel.list Query Success!!!");
		try {
			System.out.println(sql);
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				FacultyDTO dto = new FacultyDTO();

				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setdOJ(rs.getDate(5));
				dto.setQualification(rs.getString(6));
				dto.setEmailId(rs.getString(7));
				dto.setMobileNo(rs.getString(8));
				dto.setCollegeId(rs.getInt(9));
				dto.setCollegeName(rs.getString(10));
				dto.setCourseId(rs.getInt(11));
				dto.setCourseName(rs.getString(12));
				dto.setSubjectId(rs.getInt(13));
				dto.setSubjectName(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));

				list.add(dto);
			}
			rs.close();
			ps.close();
			log.debug("FacultyModel.list Success!!!");
		} catch (Exception e) {
			log.error("FacultyModel.list Exception!!!");
			throw new DatabaseException("Error in List of Faculty Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("FacultyModel.list Closed!!!");
		}
		return list;
	}

	public List<FacultyDTO> Search(FacultyDTO dto) throws Exception {
		log.debug("FacultyModel.list having only dto not Page No and Page Size");
		return search(dto, 0, 0);
	}

	public List<FacultyDTO> search(FacultyDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("FacultyModel.search method Started!!!!");

				StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");
				if (dto != null) {
					if (dto.getId() > 0) {
						sql.append(" AND id = " + dto.getId());
					}
					if (dto.getCourseId() > 0) {
						sql.append(" AND college_Id = " + dto.getCourseId());
					}
					if (dto.getFirstName() != null && dto.getFirstName().trim().length() > 0) {
						sql.append(" AND First_Name like '" + dto.getFirstName() + "%' ");
					}
					if (dto.getLastName() != null && dto.getLastName().trim().length() > 0) {
						sql.append(" AND LAST_NAME like '" + dto.getLastName() + "%' ");
					}

					if (dto.getEmailId() != null && dto.getEmailId().length() > 0) {
						sql.append(" AND Email_Id like '" + dto.getEmailId() + "%' ");
					}

					if (dto.getGender() != null && dto.getGender().length() > 0) {
						sql.append(" AND Gender like '" + dto.getGender() + "%' ");
					}

					if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
						sql.append(" AND Mobile_No like '" + dto.getMobileNo() + "%' ");
					}

					if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
						sql.append(" AND college_Name like '" + dto.getCollegeName() + "%' ");
					}
					if (dto.getCourseId() > 0) {
						sql.append(" AND course_Id = " + dto.getCourseId());
					}
					if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
						sql.append(" AND course_Name like '" + dto.getCollegeName() + "%' ");
					}
					if (dto.getSubjectId() > 0) {
						sql.append(" AND Subject_Id = " + dto.getSubjectId());
					}
					if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
						sql.append(" AND subject_Name like '" + dto.getSubjectName() + "%' ");
					}
				}

				// if page no is greater then zero then apply pagination
				System.out.println("model page ........" + pageNo + " " + pageSize);
				if (pageSize > 0) {
					pageNo = (pageNo - 1) * pageSize;
					sql.append(" limit " + pageNo + " , " + pageSize);
				}

				Connection conn = null;
				ArrayList list = new ArrayList();
				try {
					System.out.println(sql);
					conn = JDBCDataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql.toString());
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						dto = new FacultyDTO();

						dto.setId(rs.getLong(1));
						dto.setFirstName(rs.getString(2));
						dto.setLastName(rs.getString(3));
						dto.setGender(rs.getString(4));
						dto.setdOJ(rs.getDate(5));
						dto.setQualification(rs.getString(6));
						dto.setEmailId(rs.getString(7));
						dto.setMobileNo(rs.getString(8));
						dto.setCollegeId(rs.getInt(9));
						dto.setCollegeName(rs.getString(10));
						dto.setCourseId(rs.getInt(11));
						dto.setCourseName(rs.getString(12));
						dto.setSubjectId(rs.getInt(13));
						dto.setSubjectName(rs.getString(14));
						dto.setCreatedBy(rs.getString(15));
						dto.setModifiedBy(rs.getString(16));
						dto.setCreatedDatetime(rs.getTimestamp(17));
						dto.setModifiedDatetime(rs.getTimestamp(18));

						list.add(dto);
					}
					
					rs.close();

				} catch (Exception e) {
					e.printStackTrace();
					// log.error("database Exception .. " , e);
					e.printStackTrace();
					throw new ApplicationException("Exception : Exception in Search method of Faculty Model");
				} finally {
					JDBCDataSource.closeConnection(conn);
				}
				log.debug("Faculty Model search method End");
				return list;

	}
}
