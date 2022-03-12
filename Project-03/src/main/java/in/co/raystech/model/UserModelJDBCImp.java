package in.co.raystech.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.raystech.dto.UserDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DatabaseException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.exception.RecordNotFoundException;
import in.co.raystech.utility.EmailBuilder;
import in.co.raystech.utility.EmailMessage;
import in.co.raystech.utility.EmailUtility;
import in.co.raystech.utility.JDBCDataSource;


/**
 * 
 * @author Vikas Singh
 *
 */
public class UserModelJDBCImp implements UserModelInt{

	private static Logger log = Logger.getLogger(UserModelJDBCImp.class);
	public static UserModelJDBCImp model = new UserModelJDBCImp();

	private int roleId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int nextPK() throws Exception {
		log.debug("UserModel.nextPk Started!!!");
		StringBuffer sql = new StringBuffer("SELECT MAX(ID) FROM ST_USER");
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
			log.debug("UserModel.nextPk Success!!");
		} catch (Exception e) {
			log.error("UserModel.nextPk Exception");
			e.printStackTrace();
		} finally {
			log.debug("UserModel.nextPk Closed!!!");
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public Long add(UserDTO dto) throws Exception {
		log.debug("UserModel.add Started!!");
		Connection conn = null;
		StringBuffer sql = new StringBuffer("INSERT INTO ST_USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		long pk = nextPK();
		UserDTO existdto = findByLogin(dto.getLogin());

		if (dto.getRoleId() == 0) {
			dto.setRoleId(3);
		}

		if (existdto.getFirstName() != null) {
			throw new DuplicateRecordException("Login Id already exists");
		} else {
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ps.setLong(1, pk);
				ps.setString(2, dto.getFirstName());
				ps.setString(3, dto.getLastName());
				ps.setString(4, dto.getLogin());
				ps.setString(5, dto.getPassword());
				ps.setString(6, dto.getConfirmPassword());
				ps.setString(7, dto.getMobileNo());
				ps.setLong(8, dto.getRoleId());
				ps.setDate(9, new Date(dto.getDob().getTime()));
				ps.setString(10, dto.getGender());
				ps.setTimestamp(11, dto.getLastLogin());
				ps.setString(12, dto.getLock());
				ps.setString(13, dto.getRegisteredIP());
				ps.setString(14, dto.getLastLoginIP());
				ps.setString(15, dto.getCreatedBy());
				ps.setString(16, dto.getModifiedBy());
				ps.setTimestamp(17, dto.getCreatedDatetime());
				ps.setTimestamp(18, dto.getModifiedDatetime());
				ps.setInt(19, dto.getUnSuccessfulLogin());

				ps.executeUpdate();
				conn.commit();
				ps.close();
				log.debug("UserModel.add Success!!!");
			} catch (Exception e) {
				log.error("UserModel.add Exception");
				e.printStackTrace();
			}
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());

			} finally {
				log.debug("UserModel.add Closed!!");
				JDBCDataSource.closeConnection(conn);
			}
		}
		return pk;
	}

	public UserDTO authenticate(String login, String pwd) throws Exception {
		log.debug("UserModel.authenticate Started!!!");
		Connection conn = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN = ? AND PASSWORD = ?");
		UserDTO dto = new UserDTO();

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, login);
			ps.setString(2, pwd);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setConfirmPassword(rs.getString(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setDob(rs.getDate(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				dto.setUnSuccessfulLogin(rs.getInt(19));
			}
			log.debug("UserModel.authenticate Success!!");
			if (dto.getFirstName() == null) {
				System.out.println("Authenticate Failed!!!!");
			}
		} catch (Exception e) {
			log.error("UserModel.authenticate Exception");
			throw new ApplicationException("Error Login Data  " + e.getMessage());
		} finally {
			log.debug("UserModel.authenticate Closed!!!");
			JDBCDataSource.closeConnection(conn);
		}

		return dto;
	}

	public void delete(UserDTO dto) throws Exception {
		log.debug("UserModel.delete Started!!!");
		StringBuffer sql = new StringBuffer("DELETE FROM ST_USER WHERE ID = ?");
		Connection conn = null;
		long l = dto.getId();
		dto=new UserDTO();
		dto = findByPK(l);
		if (dto.getId() != 0) {
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);

				PreparedStatement ps = conn.prepareStatement(sql.toString());
				ps.setLong(1, dto.getId());

				ps.executeUpdate();
				conn.commit();
				ps.close();
				log.debug("UserModel.delete Success!!");
			} catch (Exception e) {
				log.error("UserModel.delete Exception!!!!");
				throw new DatabaseException("Error in deleting from database");
			} finally {
				log.debug("UserModel.delete Closed!!!!");
				JDBCDataSource.closeConnection(conn);
			}
		} else {
			throw new RecordNotFoundException("Record Not Found");
		}

	}

	public UserDTO findByPK(Long l) throws Exception {
		log.debug("UserModel.findByPk Started!!!");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ID = ?");
		Connection conn = null;
		UserDTO dto = new UserDTO();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, l);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setConfirmPassword(rs.getString(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setDob(rs.getDate(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				dto.setUnSuccessfulLogin(rs.getInt(19));
			}
			ps.close();
			rs.close();

			if (dto.getId() != 0) {
				log.debug("UserModel.findByPk Success!!!");
			}

		} catch (Exception e) {
			log.error("UserModel.findByPk Exception!!!");
			throw new RecordNotFoundException("Record Not Found");
		} finally {
			log.debug("UserModel.findByPk Closed!!!");
			JDBCDataSource.closeConnection(conn);
		}

		return dto;
	}

	public UserDTO findByLogin(String login) throws Exception {
		log.debug("UserModel.findByLogin Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN=?");
		UserDTO dto = new UserDTO();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setConfirmPassword(rs.getString(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setDob(rs.getDate(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				dto.setUnSuccessfulLogin(rs.getInt(19));

			}
			ps.close();
			rs.close();
			log.debug("UserModel.findByLogin Success!!!");
		} catch (Exception e) {
			log.error("UserModel.findByLogin Exception!!!");
			e.printStackTrace();
		} finally {
			log.debug("UserModel.findByLogin Closed!!");
			JDBCDataSource.closeConnection(conn);

		}
		return dto;
	}

	public boolean forgetPassword(String login) throws Exception {
		UserDTO userData = findByLogin(login);
		boolean flag = false;

		if (userData == null) {
			throw new RecordNotFoundException("Email ID does not exists !");

		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastName", userData.getLastName());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNRAYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		boolean check = EmailUtility.sendMail(msg);
		flag = check;

		return flag;

	}

	public boolean changePassword(long id, String oldPassword, String newPassword) throws Exception {
		log.debug("UserModel.changePassword Started!!!");
		boolean flag = false;
		UserDTO dtoExist = null;

		dtoExist = findByPK(id);
		if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {
			dtoExist.setPassword(newPassword);
			update(dtoExist);
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", dtoExist.getLogin());
		map.put("password", dtoExist.getPassword());
		map.put("firstName", dtoExist.getFirstName());
		map.put("lastName", dtoExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dtoExist.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		log.debug("UserModel.changePassword Success!!!");
		log.debug("UserModel.changePassword Closed!!!");
		return flag;

	}

	public void update(UserDTO dto) throws Exception {
		log.debug("UserModel.update Started!!!");
		StringBuffer sql = new StringBuffer(
				"UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,CONFIRM_PASSWORD=?,MOBILE_NO=?,ROLE_ID=?,DOB=?,GENDER=?,LAST_LOGIN=?,USER_LOCK=?,REGISTERED_IP=?,LAST_LOGIN_IP=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=?,UNSUCCESSFUL_LOGIN=? WHERE ID=?");
		Connection conn = null;

		UserDTO dtoExist = findByLogin(dto.getLogin());

		if (dtoExist != null && !(dtoExist.getId() == dto.getId())) {
			throw new DuplicateRecordException("LoginId is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ps.setString(1, dto.getFirstName());
			ps.setString(2, dto.getLastName());
			ps.setString(3, dto.getLogin());
			ps.setString(4, dto.getPassword());
			ps.setString(5, dto.getConfirmPassword());
			ps.setString(6, dto.getMobileNo());
			ps.setLong(7, dto.getRoleId());
			ps.setDate(8, new Date(dto.getDob().getTime()));
			ps.setString(9, dto.getGender());
			ps.setTimestamp(10, dto.getLastLogin());
			ps.setString(11, dto.getLock());
			ps.setString(12, dto.getRegisteredIP());
			ps.setString(13, dto.getLastLoginIP());
			ps.setString(14, dto.getCreatedBy());
			ps.setString(15, dto.getModifiedBy());
			ps.setTimestamp(16, dto.getCreatedDatetime());
			ps.setTimestamp(17, dto.getModifiedDatetime());
			ps.setInt(18, dto.getUnSuccessfulLogin());
			ps.setLong(19, dto.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			log.debug("UserModel.update Success!!!");
		} catch (Exception e) {
			log.error("UserModel.update Exception!!!");
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			log.debug("UserModel.update Closed!!!");
			JDBCDataSource.closeConnection(conn);
		}

	}

	public UserDTO updateAccess(UserDTO dto) throws ApplicationException {
		log.debug("UserModel.updateAccess Started");
		log.debug("UserModel.updateAccess Closed");
		return null;
	}

	public List<UserDTO> search(UserDTO dto) throws Exception {
		log.debug("UserModel.search(only dto argument) Started");
		log.debug("UserModel.search(only dto argument) Closed");
		return search(dto, 1, 100);
	}

	public List<UserDTO> search(UserDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("UserModel.search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

		if (dto != null) {

			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}

			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + dto.getFirstName() + "%'");
			}

			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + dto.getLastName() + "%'");
			}

			if (dto.getLogin() != null && dto.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + dto.getLogin() + "%'");
			}

			if (dto.getPassword() != null && dto.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + dto.getPassword() + "%'");
			}
			if (dto.getConfirmPassword() != null && dto.getConfirmPassword().length() > 0) {
				sql.append(" AND  CONFIRMPASSWORD like '" + dto.getConfirmPassword() + "%'");
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + dto.getDob());
			}
			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + dto.getMobileNo());
			}
			if (dto.getRoleId() > 0) {
				sql.append(" AND ROLE_ID = " + dto.getRoleId());
			}

			if (dto.getGender() != null && dto.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + dto.getGender() + "%'");
			}

		}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}
		log.debug("UserModel.search Query Success ");
		log.debug("QUERY-- " + sql);
		ArrayList<UserDTO> list = new ArrayList<UserDTO>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setConfirmPassword(rs.getString(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setDob(rs.getDate(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				dto.setUnSuccessfulLogin(rs.getInt(19));

				list.add(dto);
			}
			rs.close();
			log.debug("UserModel.search Success!!");
		} catch (Exception e) {
			log.error("UserModel.search Exception");
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			log.debug("UserModel.search Closed!!");
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	public List<UserDTO> list() throws Exception {
		return list(0, 0);
	}

	public List<UserDTO> list(int pageNo, int pageSize) throws Exception {
		log.debug("UserModel.list Started");
		ArrayList<UserDTO> list = new ArrayList<UserDTO>();
		StringBuffer sql = new StringBuffer("select * from ST_USER");

		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}
		log.debug("UserModel.list Query Success!!");
		log.debug("QUERY-- " + sql);
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				UserDTO dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setConfirmPassword(rs.getString(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setDob(rs.getDate(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				dto.setUnSuccessfulLogin(rs.getInt(19));
				list.add(dto);
			}
			rs.close();
			log.debug("UserModel.list Success!!!");
		} catch (Exception e) {
			log.error("UserModel.list Exception ");
			throw new ApplicationException("Exception : Exception in getting list of user");
		} finally {
			log.debug("UserModel.list Closed!!!");
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}

	public boolean resetPassword(UserDTO dto) throws Exception {
		log.debug("UserModel.resetPassword Started!!!");
		String newPassword = String.valueOf(new java.util.Date().getTime()).substring(0, 4);
		UserDTO userData = findByPK(dto.getId());
		userData.setPassword(newPassword);

		try {
			update(userData);
		} catch (DuplicateRecordException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());
		map.put("firstName", dto.getFirstName());
		map.put("lastName", dto.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		log.debug("UserModel.resetPassword Success!!!");
		log.debug("UserModel.resetPassword Closed!!!");
		return true;
	}

	public Long registerUser(UserDTO dto) throws Exception {
		log.debug("UserModel.register Started!!!");
		Long pk = model.add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Registration is successful for ORS Project SunilOS");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		log.debug("UserModel.register Success!!!");
		log.debug("UserModel.register Closed!!!");
		return pk;
	}

	public List<UserDTO> getRoles(UserDTO dto) throws Exception {
		log.debug("UserModel.getRoles Started!!!");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ROLE_ID=?");
		Connection conn = null;
		List<UserDTO> list = new ArrayList<UserDTO>();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, dto.getRoleId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setConfirmPassword(rs.getString(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setDob(rs.getDate(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
				dto.setUnSuccessfulLogin(rs.getInt(19));
				list.add(dto);
			}
			rs.close();
			log.debug("UserModel.getRoles Success!!!");
		} catch (Exception e) {
			log.error("UserModel.getRoles Exception!!!");
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			log.debug("UserModel.getRoles Closed!!!");
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}
