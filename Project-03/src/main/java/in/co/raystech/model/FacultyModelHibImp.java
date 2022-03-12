package in.co.raystech.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.raystech.dto.CollegeDTO;
import in.co.raystech.dto.CourseDTO;
import in.co.raystech.dto.FacultyDTO;
import in.co.raystech.dto.SubjectDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.utility.HibDataSource;

/**
 * Hibernate implements of Faculty model
 * 
 * @author Vikas Singh
 *
 */

public class FacultyModelHibImp implements FacultyModelInt {
	private static Logger log = Logger.getLogger(FacultyModelHibImp.class);

	public Long add(FacultyDTO dto) throws Exception {
		log.debug("FacultyModel.add Started");
		Session session = null;
		Transaction tx = null;
		long pk = 0;
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO dto1 = model.findByPk(dto.getCollegeId());

		String CollegeName = dto1.getName();
		dto.setCollegeName(CollegeName);

		CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
		CourseDTO dto2 = model1.findByPk(dto.getCourseId());

		String CourseName = dto2.getCourseName();
		dto.setCourseName(CourseName);

		SubjectModelInt model2 = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO dto3 = model2.findByPk(dto.getSubjectId());

		String SubjectName = dto3.getSubjectName();
		dto.setSubjectName(SubjectName);

		/*
		 * FacultyDTO duplicataRole = findByEmail(dto.getEmailId());
		 * 
		 * // Check if create Faculty already exist
		 * 
		 * if (duplicataRole != null) { throw new
		 * DuplicateRecordException("Faculty already exists"); }
		 */

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
			log.debug("FacultyModel.add Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("FacultyModel.add Exception");
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in faculty Add " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("FacultyModel.add Closed");
		return pk;
	}

	public void delete(FacultyDTO dto) throws Exception {
		log.debug("FacultyModel.delete Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			log.debug("FacultyModel.delete Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("FacultyModel.delete Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in faculty delete " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("FacultyModel.delete Closed");
	}

	public void update(FacultyDTO dto) throws ApplicationException, Exception {
		log.debug("FacultyModel.update Started");
		Session session = null;
		Transaction tx = null;
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO dto1 = model.findByPk(dto.getCollegeId());
		log.debug("colleges Name--- " + dto1.getName());
		String CollegeName = dto1.getName();
		dto.setCollegeName(CollegeName);

		CourseModelInt model1 = ModelFactory.getInstance().getCourseModel();
		CourseDTO dto2 = model1.findByPk(dto.getCourseId());
		String CourseName = dto2.getCourseName();
		dto.setCourseName(CourseName);

		SubjectModelInt model2 = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO dto3 = model2.findByPk(dto.getSubjectId());
		String SubjectName = dto3.getSubjectName();
		dto.setSubjectName(SubjectName);

//			FacultyDTO duplicataRole = findByEmailId(dto.getEmailId());
//			System.out.println("fkkkkkkkkkkkk"+duplicataRole+"...."+dto.getEmailId());
//			// Check if create Faculty already exist
//			if (duplicataRole != null) {
//				throw new DuplicateRecordException("Faculty already exists");
//			}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			log.debug("FacultyModel.update Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("FacultyModel.update Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in faculty update " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("FacultyModel.update Closed");
	}

	public List list() throws ApplicationException {
		log.debug("FacultyModel.list null args Started & Closed");
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("FacultyModel.list Started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("FacultyModel.list Success");
		} catch (Exception e) {
			log.error("FacultyModel.list Exception");
			throw new ApplicationException("Exception : Exception in  faculty list");
		} finally {
			session.close();
		}
		log.debug("FacultyModel.list Closed!!!");
		return list;
	}

	public List search(FacultyDTO dto) throws Exception {
		log.debug("FacultyModel.search null args Started & Closed");
		return search(dto, 0, 0);
	}

	public List search(FacultyDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("FacultyModel.search Started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			if (dto != null) {
				if (dto.getId() != 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
					criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
				}
				if (dto.getEmailId() != null && dto.getEmailId().length() > 0) {
					criteria.add(Restrictions.like("emailId", dto.getEmailId() + "%"));
				}
				if (dto.getLastName() != null && dto.getLastName().length() > 0) {
					criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
				}
				if (dto.getCollegeId() > 0) {
					criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
				}
				if (dto.getCourseId() > 0) {
					criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
				}
				if (dto.getSubjectId() > 0) {
					criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
				}
			}

			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("FacultyModel.search Success");
		} catch (HibernateException e) {
			log.error("FacultyModel.search Exception");
			throw new ApplicationException("Exception in course search");
		} finally {
			session.close();
		}
		log.debug("FacultyModel.search Closed");
		return list;
	}

	public FacultyDTO findByEmail(String email) throws Exception {
		log.debug("FacultyModel.findByEmail Started!!!");
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			criteria.add(Restrictions.eq("emailId", email));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (FacultyDTO) list.get(0);
			}
			log.debug("FacultyModel.findByEmail Success");
		} catch (HibernateException e) {
			log.error("FacultyModel.findByEmail Exception");
			throw new ApplicationException("Exception in getting faculty by login " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("FacultyModel.findByEmail Closed");
		return dto;
	}

	public List<FacultyDTO> Search(FacultyDTO dto) throws Exception {
		log.debug("FacultyModel.search null args Started & Closed");
		return search(dto, 0, 100);
	}

	public FacultyDTO findByPk(long id) throws Exception {
		log.debug("FacultyModel.findByPk Started");
		Long pk = id;
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (FacultyDTO) session.get(FacultyDTO.class, pk);
			log.debug("FacultyModel.findByPk Success");
		} catch (HibernateException e) {
			log.error("FacultyModel.findByPk Exception");
			throw new ApplicationException("Exception : Exception in getting faculty by pk");
		} finally {
			session.close();
		}
		log.debug("FacultyModel.findByPk Closed");
		return dto;
	}
}
