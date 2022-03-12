package in.co.raystech.model;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.raystech.dto.StudentDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.utility.HibDataSource;

/**
 * Hibernate implements of Student model
 * 
 * @author Vikas Singh
 *
 */
public class StudentModelHibImp implements StudentModelInt {
private Logger log = Logger.getLogger(StudentModelHibImp.class);
	public long add(StudentDTO dto) throws Exception {
		log.debug("StudentModel.add Started");
		Session session = HibDataSource.getSession();
		Transaction tx = null;

		
		  StudentDTO dtoExist = findByEmailId(dto.getEmail());
		  
		  // Check if updated Email already exist
		  
		  if (dtoExist != null && dtoExist.getId() != dto.getId()) { throw new
		  Exception("Email is already exist"); }
		  
		 
		long pk = 0;
		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
			log.debug("StudentModel.add Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			log.error("StudentModel.add Exception");
			throw new Exception("Exception in Student Add " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("StudentModel.add Closed");
		return pk;
	}

	public void delete(StudentDTO dto) throws Exception {
		log.debug("StudentModel.delete Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			log.debug("StudentModel.delete Success");
		} catch (HibernateException e) {
			log.error("StudentModel.delete Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new Exception("Exception in Student Delete" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("StudentModel.delete Closed");
	}

	public void update(StudentDTO dto) throws Exception {
		log.debug("StudentModel.update Started");
		Session session = null;
		Transaction tx = null;

		
		  StudentDTO dtoExist = findByEmailId(dto.getEmail());
		  
		  // Check if updated Email already exist 
		  if (dtoExist != null &&dtoExist.getId() != dto.getId()) { throw new
		  Exception("Email is already exist"); }
		 
		
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);

			tx.commit();
			log.debug("StudentModel.update Success");
		} catch (HibernateException e) {
			log.error("StudentModel.update Exception");
			if (tx != null) {
				tx.rollback();
				throw new Exception("Exception in Student Update" + e.getMessage());
			}
		} finally {
			session.close();
		}
		log.debug("StudentModel.update Closed");
	}

	public StudentDTO findByPK(long pk) throws Exception {
		log.debug("StudentModel.findByPk Started");
		Session session = HibDataSource.getSession();
		StudentDTO dto = null;
		try {
			dto = (StudentDTO) session.get(StudentDTO.class, pk);
			log.debug("StudentModel.findByPk Success");
		} catch (HibernateException e) {
			log.error("StudentModel.findByPk Exception");
			throw new ApplicationException("Exception : Exception in getting Student by pk");
		} finally {
			session.close();
		}
		log.debug("StudentModel.findByPk Closed");
		return dto;
	}

	public StudentDTO findByEmailId(String emailId) throws Exception {
		log.debug("StudentModel.findByEmail Started");
		Session session = HibDataSource.getSession();
		StudentDTO dto = null;
		try {
			Criteria criteria = session.createCriteria(StudentDTO.class);
			criteria.add(Restrictions.eq("email", emailId));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (StudentDTO) list.get(0);
			}
			log.debug("StudentModel.findByEmail Success");
		} catch (HibernateException e) {
			log.error("StudentModel.findByEmail Exception");
			throw new ApplicationException("Exception in getting Student by email " + e.getMessage());
		} finally {
			session.close();
		}
		log.error("StudentModel.findByEmail Closed");
		return dto;
	}

	public List list() throws Exception {
		log.debug("StudentModel.list Started & Closed");
		return list(1,100);
	}

	public List list(int pageNo, int pageSize) throws Exception {
		log.debug("StudentModel.list Started");
		Session session = null;
		List list;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(StudentDTO.class);

			// if page size is greater than zero then apply pagination
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) ;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
			log.debug("StudentModel.list Success");
		} catch (HibernateException e) {
			log.error("StudentModel.list Exception");
			throw new ApplicationException("Exception : Exception in  Student list");
		} finally {
			session.close();
		}
		log.debug("StudentModel.list Closed!!!");
		return list;
	}

	public List search(StudentDTO dto) throws Exception {
		log.debug("StudentModel.search Started & Closed");
		return search(dto, 0, 0);
	}

	public List search(StudentDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("StudentModel.search Started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(StudentDTO.class);
			if (dto != null) {
				if (dto.getId() != 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
					criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
				}
				if (dto.getEmail() != null && dto.getEmail().length() > 0) {
					criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
				}
				if (dto.getLastName() != null && dto.getLastName().length() > 0) {
					criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
				}
				if (dto.getDob() != null && dto.getDob().getDate() > 0) {
					criteria.add(Restrictions.eq("dob", dto.getDob()));
				}
				if (dto.getCollegeId() > 0) {
					criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
				}

				if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
					criteria.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
				}
			}
			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("StudentModel.search Success");
		} catch (HibernateException e) {
			log.error("StudentModel.search Exception");
			throw new ApplicationException("Exception in Student search");
		} finally {
			session.close();
		}
		log.debug("StudentModel.search Closed");
		return list;
	}

	

}
