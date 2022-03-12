package in.co.raystech.model;

/**
 * Hibernate Implements Model
 * @author Vikas Singh
 */
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.raystech.dto.CollegeDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.utility.HibDataSource;

public class CollegeModelHibImp implements CollegeModelInt {
	private static Logger log = Logger.getLogger(CollegeModelHibImp.class);

	public long add(CollegeDTO dto) throws Exception {
		log.debug("CollegeModel.Add started");
		Session session = null;
		Transaction tx = null;
		CollegeDTO duplicateCollegeName = findByName(dto.getName());
		if (duplicateCollegeName != null) {
			throw new DuplicateRecordException("college name already exist");
		}
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
			log.debug("CollegeModel.add Success");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("CollegeModel.add Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in add of CollegeModelHibernate");
		} finally {
			session.close();
		}
		log.debug("CollegeModel.add Closed");
		return dto.getId();
	}

	public void delete(CollegeDTO dto) throws Exception {
		log.debug("CollegeModel.Delete started");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			log.debug("CollegeModel.Delete Success");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("CollegeModel.Delete Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in delete of CollegeModelHibernate");
		} finally {
			session.close();
		}
		log.debug("CollegeModel.Delete Closed");
	}

	public CollegeDTO findByName(String name) throws Exception {
		log.debug("CollegeModel.findByName Started!!!");
		if (name == null) {
			throw new ApplicationException("Please Enter Name!!!");
		}
		Session session = null;
		CollegeDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (CollegeDTO) list.get(0);
			}
			log.debug("CollegeModel.findByName Success");
		} catch (Exception e) {
			log.error("CollegeModel.findByName Exception");
			throw new ApplicationException("Exception in CollegeModel.findByName " + e.getMessage());
		} finally {
			session.close();
		}
		return dto;
	}

	public CollegeDTO findByPk(long id) throws Exception {
		log.debug("CollegeModel.findByPk Started!!!");
		if (id == 0) {
			throw new ApplicationException("Please Enter ID!!!");
		}
		Session session = null;
		CollegeDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (CollegeDTO) session.get(CollegeDTO.class, id);
			System.out.println(dto);
			log.debug("CollegeModel.findById Success");
		} catch (Exception e) {
			log.error("CollegeModel.findById Exception");
			throw new ApplicationException("Exception in CollegeModel.findById " + e.getMessage());
		} finally {
			session.close();
		}
		return dto;
	}

	public void update(CollegeDTO dto) throws Exception {
		log.debug("CollegeModel.Update Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			log.debug("CollegeModel.update Success");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("CollegeModel.update Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in update of CollegeModelHibernate");
		} finally {
			session.close();
		}
		log.debug("CollegeModel.update Closed");
	}

	public CollegeDTO findByCity(String city) throws Exception {
		log.debug("CollegeModel.findByCity Started!!!");
		if (city == null) {
			throw new ApplicationException("Please Enter City!!!");
		}
		Session session = null;
		CollegeDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			criteria.add(Restrictions.eq("city", city));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (CollegeDTO) list.get(0);
			}
			log.debug("CollegeModel.findByCity Success");
		} catch (Exception e) {
			log.error("CollegeModel.findByCity Exception");
			throw new ApplicationException("Exception in CollegeModel.findByCity " + e.getMessage());
		} finally {
			session.close();
		}
		return dto;
	}

	/**
	 *
	 */
	public List<CollegeDTO> search(CollegeDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("CollegeModel.search Started!!!");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			if (dto != null) {
				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.eq("name", dto.getName()));
				}
				if (dto.getAddress() != null && dto.getAddress().length() > 0) {
					criteria.add(Restrictions.eq("address", dto.getAddress()));
				}
				if (dto.getState() != null && dto.getState().length() > 0) {
					criteria.add(Restrictions.eq("state", dto.getState()));
				}
				if (dto.getCity() != null && dto.getCity().length() > 0) {
					criteria.add(Restrictions.eq("city", dto.getCity()));
				}
				if (dto.getPhoneNo() != null && dto.getPhoneNo().length() > 0) {
					criteria.add(Restrictions.eq("phoneNo", dto.getPhoneNo()));
				}
			}
			if (pageSize>0) {
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			
			log.debug("CollegeModel.Search Success");
		} catch (Exception e) {
			log.error("CollegeModel.list Exception");
			throw new ApplicationException("Exception in CollegeModel.search" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Exception  in CollegeModel.search Closed!!!");
		return list;
	}

	public List<CollegeDTO> list(int pageNo, int pageSize) throws Exception {
		log.debug("CollegeModel.list Started!!!");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			if (pageSize>0) {
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("CollegeModel.list Success");
		} catch (Exception e) {
			log.error("CollegeModel.list Exception");
			throw new ApplicationException("Exception in CollegeModel.list" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Exception  in CollegeModel.list Closed!!!");
		return list;
	}
	public List search(CollegeDTO dto) throws Exception {
		return search(dto, 1, 100);
	}

	public List list() throws Exception {
		return list(1,10);
	}

}
