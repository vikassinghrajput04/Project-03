package in.co.raystech.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.raystech.dto.RoleDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.utility.HibDataSource;

/**
 * Hibernate implements of Role model
 * 
 * @author Harsh
 *
 */
public class RoleModelHibImp implements RoleModelInt {
	private Logger log = Logger.getLogger(RoleModelHibImp.class);

	public long add(RoleDTO dto) throws Exception {
		log.debug("RoleModel.add Started!!!");
		Session session = null;
		Transaction tx = null;
		long pk = 0;
		RoleDTO existDto = findByName(dto.getName());
		if (existDto != null) {
			throw new Exception("Role already exist");
		}
		session = HibDataSource.getSession();
		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
			log.debug("RoleModel.add Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
				log.error("RoleModel.add Exception");
			}
			throw new ApplicationException("Exception in Role Add " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("RoleModel.add Closed");
		return pk;
	}

	public void delete(RoleDTO dto) throws Exception {
		log.debug("RoleModel.delete Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			log.debug("RoleModel.delete Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			log.error("RoleModel.delete Exception");
			throw new ApplicationException("Exception in Role delete " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("RoleModel.delete Closed");
	}

	public void update(RoleDTO dto) throws Exception {
		log.debug("RoleModel.update Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			log.debug("RoleModel.update Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
				log.error("RoleModel.update Exception");
			}
			throw new ApplicationException("Exception in Role update " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("RoleModel.update Closed");
	}

	public List list() throws Exception {
		log.debug("RoleModel.list Started & Closed");
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws Exception {
		log.debug("RoleModel.list Started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("RoleModel.list Success");
		} catch (HibernateException e) {
			log.error("RoleModel.list Exception");
			throw new ApplicationException("Exception : Exception in  role list");
		} finally {
			session.close();
		}
		log.debug("RoleModel.list Closed");
		return list;
	}

	public List search(RoleDTO dto) throws Exception {
		log.debug("RoleModel.search Started & Closed");
		return search(dto, 0, 0);
	}

	public List search(RoleDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("RoleModel.search Started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
			}
			if (dto.getDescription() != null && dto.getDescription().length() > 0) {
				criteria.add(Restrictions.like("description", dto.getDescription() + "%"));
			}
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("RoleModel.search Success");
		} catch (HibernateException e) {
			log.error("RoleModel.search Exception");
			throw new ApplicationException("Exception in course search");
		} finally {
			session.close();
		}
		log.debug("RoleModel.search Closed");
		return list;
	}

	public RoleDTO findByPK(long pk) throws Exception {
		log.debug("RoleModel.findByPk Started");
		RoleDTO dto = null;
		Session session = HibDataSource.getSession();
		try {
			dto = (RoleDTO) session.get(RoleDTO.class, pk);
			log.debug("RoleModel.findByPk Success");
		} catch (HibernateException e) {
			log.error("RoleModel.findByPk Exception");
			throw new ApplicationException("Exception : Exception in getting Role by pk");
		} finally {
			session.close();
		}
		log.debug("RoleModel.findByPk Closed");
		return dto;
	}

	public RoleDTO findByName(String name) throws Exception {
		log.debug("RoleModel.findByName Started");
		Session session = null;
		RoleDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (RoleDTO) list.get(0);
			}
			log.debug("RoleModel.findByName Success");
		} catch (HibernateException e) {
			log.error("RoleModel.findByName Exception");
			throw new ApplicationException("Exception in getting Role by Name " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("RoleModel.findByName Closed");
		return dto;
	}

}
