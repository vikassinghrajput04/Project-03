package in.co.raystech.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.raystech.dto.UserDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.exception.RecordNotFoundException;
import in.co.raystech.utility.EmailBuilder;
import in.co.raystech.utility.EmailMessage;
import in.co.raystech.utility.EmailUtility;
import in.co.raystech.utility.HibDataSource;

/**
 * Hibernate implements of User model
 * 
 * @author Vikas Singh
 *
 */

public class UserModelHibImp implements UserModelInt {
	private static Logger log = Logger.getLogger(UserModelHibImp.class);

	/**
	 * Add a User
	 *
	 */
	public Long add(UserDTO dto) throws Exception {
		log.debug("UserModel.add Started!!");
		long pk = 0;
		UserDTO existDto = null;
		existDto = findByLogin(dto.getLogin());
		if (existDto != null) {
			throw new DuplicateRecordException("login id already exist");
		}
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
			log.debug("UserModel.add Success!!!");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			log.error("UserModel.add Exception");
			throw new ApplicationException("Exception in User Add " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("UserModel.add Closed!!");
		return pk;

	}

	/**
	 * Delete a User
	 *
	 */

	public void delete(UserDTO dto) throws Exception {
		log.debug("UserModel.delete Started!!!");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			log.debug("UserModel.delete Success!!");
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.error("UserModel.delete Exception!!!!");
			throw new ApplicationException("Exception in User Delete" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("UserModel.delete Closed!!!!");
	}

	/**
	 * update a User
	 *
	 */
	public void update(UserDTO dto) throws Exception {
		log.debug("UserModel.update Started!!!");
		Session session = null;
		Transaction tx = null;
		UserDTO existDto = findByLogin(dto.getLogin());
		// Check if updated LoginId already exist
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("LoginId is already exist");
		}
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			log.debug("UserModel.update Success!!!");
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.error("UserModel.update Exception!!!");
			throw new ApplicationException("Exception in User update" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("UserModel.update Closed!!!");
	}

	/**
	 * find a User by login
	 *
	 */
	public UserDTO findByLogin(String login) throws Exception {
		log.debug("UserModel.findByLogin Started!!!");
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (UserDTO) list.get(0);
			}
			log.debug("UserModel.findByLogin Success!!!");
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("UserModel.findByLogin Exception!!!");
			throw new ApplicationException("Exception in getting User by Login " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("UserModel.findByLogin Closed!!");
		return dto;
	}

	/**
	 * list of User
	 *
	 */

	public List list() throws Exception {
		return list(0, 0);
	}

	/**
	 * 
	 * list of User with pagination parameters
	 *
	 */
	public List list(int pageNo, int pageSize) throws Exception {
		log.debug("UserModel.list Started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("UserModel.list Success!!");
		} catch (HibernateException e) {
			log.error("UserModel.list Exception ");
			throw new ApplicationException("Exception : Exception in  Users list");
		} finally {
			session.close();
		}
		log.debug("UserModel.list Closed!!!");
		return list;
	}

	/**
	 * search a User
	 *
	 */
	public List search(UserDTO dto) throws Exception {
		return search(dto, 0, 0);
	}

	/**
	 * search a User with pagination
	 *
	 */
	public List search(UserDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("UserModel.search Started");
		Session session = null;
		ArrayList<UserDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (dto != null) {
				if (dto.getId() != 0) {
					criteria.add(Restrictions.like("id", dto.getId()));
				}
				if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
					criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
				}

				if (dto.getLastName() != null && dto.getLastName().length() > 0) {
					criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
				}
				if (dto.getLogin() != null && dto.getLogin().length() > 0) {
					criteria.add(Restrictions.like("login", dto.getLogin() + "%"));
				}
				if (dto.getPassword() != null && dto.getPassword().length() > 0) {
					criteria.add(Restrictions.like("password", dto.getPassword() + "%"));
				}
				if (dto.getGender() != null && dto.getGender().length() > 0) {
					criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
				}
				if (dto.getDob() != null && dto.getDob().getDate() > 0) {
					criteria.add(Restrictions.eq("dob", dto.getDob()));
				}
				if (dto.getLastLogin() != null && dto.getLastLogin().getTime() > 0) {
					criteria.add(Restrictions.eq("lastLogin", dto.getLastLogin()));
				}
				if (dto.getRoleId() > 0) {
					criteria.add(Restrictions.eq("roleId", dto.getRoleId()));
				}
				if (dto.getUnSuccessfulLogin() > 0) {
					criteria.add(Restrictions.eq("unSuccessfulLogin", dto.getUnSuccessfulLogin()));
				}
			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<UserDTO>) criteria.list();
			log.debug("UserModel.search Success!!");
		} catch (HibernateException e) {
			log.error("UserModel.search Exception");
			throw new ApplicationException("Exception in user search");
		} finally {
			session.close();
		}
		log.debug("UserModel.search Closed!!");
		return list;
	}

	/**
	 * authenticate a User
	 *
	 */
	public UserDTO authenticate(String login, String password) throws Exception {
		log.debug("UserModel.authenticate Started!!!");
		Session session = null;
		UserDTO dto = null;
		try {
		session = HibDataSource.getSession();
		Query q = session.createQuery("from UserDTO where login=? and password=?");
		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		log.debug("UserModel.authenticate Success!!");
		if (list.size() > 0) {
			dto = (UserDTO) list.get(0);
		} else {
			dto = null;
		}}catch (Exception e) {
			log.error("UserModel.authenticate Exception");
			e.printStackTrace();
			throw new ApplicationException("Exception ");
		}finally {
			session.close();
		}
		log.debug("UserModel.authenticate Closed");
		return dto;
	}

	public List getRoles(UserDTO dto) throws Exception {
		return null;
	}

	/**
	 * change password of a User
	 * 
	 * @throws Exception
	 *
	 */
	public boolean changePassword(long id, String newPassword, String oldPassword) throws Exception {
		boolean flag = false;
		UserDTO dtoExist = null;

		dtoExist = findByPK(id);
		if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {
			dtoExist.setPassword(newPassword);
			try {
				update(dtoExist);
			} catch (DuplicateRecordException e) {

				throw new ApplicationException("LoginId is already exist");
			}
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
		msg.setSubject("Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return flag;

	}

	/**
	 * forget password of User
	 *
	 */
	public boolean forgetPassword(String login) throws Exception {

		UserDTO userData = findByLogin(login);
		boolean flag = false;
		System.out.println("i am forget password method" + userData);
		if (userData == null) {
			throw new RecordNotFoundException("Email Id Does not matched.");
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastName", userData.getLastName());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		flag = true;

		return flag;
	}

	/**
	 * reset password of User
	 *
	 */
	public boolean resetPassword(UserDTO dto) throws Exception {
		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
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

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return true;
	}

	/**
	 * register a User
	 * 
	 * @throws Exception
	 *
	 */
	public Long registerUser(UserDTO dto) throws Exception {
		long pk = add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Registration is successful for ORS Project SUNRAYS Technologies");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}

	/**
	 * find a User by pk
	 *
	 */
	public UserDTO findByPK(Long l) throws Exception {
		Session session = null;
		UserDTO dto = new UserDTO();
		try {
			session = HibDataSource.getSession();
			dto = (UserDTO) session.get(UserDTO.class, l);
		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			session.close();
		}
		return dto;
	}

}
