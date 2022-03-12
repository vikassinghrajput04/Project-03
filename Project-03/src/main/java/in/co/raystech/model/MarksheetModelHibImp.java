package in.co.raystech.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.raystech.dto.MarksheetDTO;
import in.co.raystech.dto.StudentDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.utility.HibDataSource;

/**
 * Hibernate implements of marksheet model
 * 
 * @author Vikas Singh
 *
 */
public class MarksheetModelHibImp implements MarksheetModelInt {
	private static Logger log = Logger.getLogger(MarksheetModelHibImp.class);

	public Long add(MarksheetDTO dto) throws Exception {
		log.debug("MarksheetModel.add Started!!!!");
		Session session =null;
		Transaction tx = null;

		// get Student Name

		  StudentModelInt sModel = ModelFactory.getInstance().getStudentModel();
		  StudentDTO studentDTO = sModel.findByPK(dto.getStudentId());
		  dto.setName(studentDTO.getFirstName() + " " + studentDTO.getLastName());
		 
		/*
		 * MarksheetDTO duplicateMarksheet = findByRollNo(dto.getRollNo());
		 * 
		 * if (duplicateMarksheet != null) { throw new
		 * DuplicateRecordException("Roll Number already exists"); }
		 */
		long pk = 0;
		try {
			log.debug("In try block of add");
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
			log.debug("MarksheetModel.add Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("MarksheetModel.add Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in marksheet Add " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("MarksheetModel.add Closed!!");
		return pk;
	}

	public void delete(MarksheetDTO dto) throws Exception {
		log.debug("MarksheetModel.delete Started");
		Session session = null;
		Transaction tx = null;
		MarksheetDTO dtoExist = findByPK(dto.getId());
		if (dtoExist == null) {
			throw new ApplicationException("Marksheet does not exist");
		}
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			log.debug("MarksheetModel.delete Success");
		} catch (HibernateException e) {
			log.error("MarksheetModel.delete Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Marksheet Delete" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("MarksheetModel.delete Closed!!!");
	}

	public void update(MarksheetDTO dto) throws Exception {
		log.debug("MarksheetModel.update Started");
		Session session = null;
		Transaction tx = null;
		System.out.println("MArksheet Update run");
		MarksheetDTO dtoExist = findByRollNo(dto.getRollNo());

		// get Student Name
		
		  StudentModelInt sModel = ModelFactory.getInstance().getStudentModel();
		  StudentDTO studentDTO = sModel.findByPK(dto.getStudentId());
		  dto.setName(studentDTO.getFirstName() + " " + studentDTO.getLastName());
		 
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			log.debug("MarksheetModel.update Success");
		} catch (HibernateException e) {
			log.debug("MarksheetModel.update Exception");
			if (tx != null) {
				tx.rollback();
				throw new ApplicationException("Exception in Marksheet Update" + e.getMessage());
			}
		} finally {
			session.close();
		}
		log.debug("MarksheetModel.update Closed");
	}

	public List list() throws Exception {
		log.debug("MarksheetModel.list Started & Closed");
		return list(1, 10);
	}

	public List list(int pageNo, int pageSize) throws Exception {
		log.debug("MarksheetModel.list Started");
		Session session = null;
		List list = new ArrayList();

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("MarksheetModel.list Success");
		} catch (Exception e) {
			log.error("MarksheetModel.list Exception");
			throw new ApplicationException("Exception in Marksheet List" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("MarksheetModel.list Closed");
		return list;
	}

	public List search(MarksheetDTO dto) throws Exception {
		log.debug("MarksheetModel.search Started & Closed");
		return search(dto, 1, 10);
	}

	public List search(MarksheetDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("MarksheetModel.search Started");
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getRollNo() != null && dto.getRollNo().length() > 0) {
				criteria.add(Restrictions.like("rollNo", dto.getRollNo() + "%"));
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
			}
			if (dto.getPhysics() != 0 && dto.getPhysics() > 0) {
				criteria.add(Restrictions.eq("physics", dto.getPhysics()));
			}
			if (dto.getChemistry() != 0 && dto.getChemistry() > 0) {
				criteria.add(Restrictions.eq("chemistry", dto.getChemistry()));
			}
			if (dto.getMaths() != 0 && dto.getMaths() > 0) {
				criteria.add(Restrictions.eq("maths", dto.getMaths()));
			}
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("MarksheetModel.search Success");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MarksheetModel.search Exception");
			throw new ApplicationException("Exception in Marksheet Search " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("MarksheetModel.search Closed");
		return list;
	}

	public MarksheetDTO findByRollNo(String rollNo) throws Exception {
		log.debug("MarksheetModel.findByRollNo Started");
		Session session = null;
		MarksheetDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			criteria.add(Restrictions.eq("rollNo", rollNo));
			List list = criteria.list();

			if (list.size() > 0) {
				dto = (MarksheetDTO) list.get(0);
			}
			log.debug("MarksheetModel.findByRollNo Success");
		} catch (Exception e) {
			log.error("MarksheetModel.findByRollNo Exception");
			throw new ApplicationException("Exception in getting Marksheet by pk" + e.getMessage());
		
		} finally {
			session.close();
		}
		log.debug("MarksheetModel.findByRollNo Closed");
		return dto;
	}

	public List getMeritList(int pageNo, int pageSize) throws Exception {
		log.debug("MarksheetModel.getMeritList Started");
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			StringBuffer hql = new StringBuffer(
					"from MarksheetDTO WHERE ( physics >33 and chemistry>33 and maths>33 ) order by (physics+chemistry+maths) desc");

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				hql.append(" limit " + pageNo + "," + pageSize);
			}
			log.debug("Query----"+ hql);
			Query query = session.createQuery(hql.toString());
			list = query.list();
			log.debug("MarksheetModel.getMeritList Success");
		} catch (Exception e) {
			log.error("MarksheetModel.getMeritList Exception");
			throw new ApplicationException("Exception in  marksheet list" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("MarksheetModel.getMeritList Closed");
		return list;
	}

	public MarksheetDTO findByPK(long l) throws Exception {
		log.debug("MarksheetModel.getMeritList Started");
		Session session = null;
		MarksheetDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (MarksheetDTO) session.get(MarksheetDTO.class, l);
			log.debug("MarksheetModel.getMeritList Success");
		} catch (Exception e) {
			log.error("MarksheetModel.getMeritList Exception");
			throw new ApplicationException("Exception in getting Marksheet by pk" + e.getMessage());

		} finally {
			session.close();
		}
		log.debug("MarksheetModel.getMeritList Closed");
		return dto;
	}

}
