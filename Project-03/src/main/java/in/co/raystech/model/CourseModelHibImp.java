package in.co.raystech.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.raystech.dto.CourseDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.utility.HibDataSource;


/**
 * Hibernate implements of course model
 * @author Vikas Singh
 *
 */
public class CourseModelHibImp implements CourseModelInt {
private static Logger log = Logger.getLogger(CourseModelHibImp.class);
	public Long add(CourseDTO dto) throws Exception {
		log.debug("CourseModel.add Started");
		Session session = null;
		Transaction tx = null;
		long pk = 0;
		CourseDTO existDto = findByName(dto.getCourseName());
		if (existDto != null) {
			throw new DuplicateRecordException("Course already exist");
		}
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
			log.debug("CourseModel.add Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("CourseModel.add Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in course Add " + e.getMessage());
		} finally {
			session.close();
			log.debug("CourseModel.add Closed");
		}
		return pk;
	}

	public void delete(CourseDTO dto) throws Exception {
		log.debug("CourseModel.delete started");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			log.debug("CourseModel.delete Closed");
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("CourseModel.delete Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in course delete " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("CourseModel.delete Closed!!");
	}

	public void update(CourseDTO dto) throws Exception {
		log.debug("CourseModel.update started!!!");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			log.debug("CourseModel.update Success!!");
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("CourseModel.update Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in course update " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("CourseModel.update Closed!!!");
	}

	public CourseDTO findByName(String name) throws Exception {
		log.debug("CourseModel.findByName Started");
		Session session = null;
		CourseDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CourseDTO.class);
			criteria.add(Restrictions.eq("courseName", name));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (CourseDTO) list.get(0);
			}
			log.debug("CourseModel.findByName Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("CourseModel.findByName Exception");
			throw new ApplicationException("Exception in getting User by Login " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("CourseModel.findByName Closed");
		return dto;
	}

	public List list() throws Exception {
		log.debug("CourseModel.list null args Started & Closed");
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws Exception {
		log.debug("CourseModel.list Started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CourseDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("CourseModel.list Success!!!");
		} catch (HibernateException e) {
			log.error("CourseModel.list Exception");
			throw new ApplicationException("Exception : Exception in  course list");
		} finally {
			session.close();
		}
		log.debug("CourseModel.list Closed!!!");
		return list;
	}

	public List search(CourseDTO dto) throws Exception {
		log.debug("CourseModel.search null args Started & Closed!!!");
		return search(dto, 0, 0);
	}

	public List search(CourseDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("CourseModel.search Started!!!");
		  Session session = null;
	        List list = null;
	        try {
	            session = HibDataSource.getSession();
	            Criteria criteria = session.createCriteria(CourseDTO.class);

	            if (dto.getId() > 0) {
	                criteria.add(Restrictions.eq("id", dto.getId()));
	            }
	            if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
	                criteria.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
	            }
	            if (dto.getDuration() != null && dto.getDuration().length() > 0) {
	                criteria.add(Restrictions.like("duration", dto.getDuration()
	                        + "%"));
	            }
	            if (dto.getDescription() != null && dto.getDescription().length() > 0) {
	                criteria.add(Restrictions.like("description", dto.getDescription() + "%"));
	            }
	            // if page size is greater than zero the apply pagination
	            if (pageSize > 0) {
	                criteria.setFirstResult(((pageNo - 1) * pageSize));
	                criteria.setMaxResults(pageSize);
	            }
	            list = criteria.list();
	            log.debug("CourseModel.search Success!!!");
	        } catch (HibernateException e) {
	            log.error("CourseModel.search Exception");
	            throw new ApplicationException("Exception in course search");
	        } finally {
	            session.close();
	        }
	        log.debug("CourseModel.Search Closed!!!!");
	        return list;
	}

	public CourseDTO findByPk(long i) throws Exception {
		log.debug("CourseModel.findByPk Started!!!");
		Long pk = i;
		System.out.println("======"+pk);
		Session session = null;
		CourseDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (CourseDTO) session.get(CourseDTO.class, pk);
			log.debug("CourseModel.findByPk Success!!!");
		} catch (HibernateException e) {
			log.error("CourseModel.findByPk Exception");
			throw new ApplicationException("Exception : Exception in getting course by pk");
		} finally {
			session.close();
		}
		log.debug("CourseModel.findByPk Closed!!!!");
		return dto;
	}

}