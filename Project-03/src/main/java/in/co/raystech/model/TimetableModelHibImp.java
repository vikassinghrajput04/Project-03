package in.co.raystech.model;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import in.co.raystech.dto.CourseDTO;
import in.co.raystech.dto.SubjectDTO;
import in.co.raystech.dto.TimetableDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.utility.HibDataSource;

/**
 * Hibernate implements of TimeTable model
 * 
 * @author Vikas Singh
 *
 */
public class TimetableModelHibImp implements TimetableModelInt {

	private static Logger log = Logger.getLogger(TimetableModelHibImp.class);

	/**
	 * add a timetable
	 *
	 */
	public Long add(TimetableDTO dto) throws Exception {
		log.debug("TimetableModel.add Started");
		CourseModelInt Cmodel = ModelFactory.getInstance().getCourseModel();
		CourseDTO Cbean = null;
		Cbean = Cmodel.findByPk(dto.getCourseId());
		dto.setCourseName(Cbean.getCourseName());

		SubjectModelInt Smodel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO Sbean = Smodel.findByPk(dto.getSubjectId());
		dto.setSubjectName(Sbean.getSubjectName());

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
			log.debug("TimetableModel.add Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("TimetableModel.add Exception");
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in timetable Add " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("TimetableModel.add Closed");
		return pk;
	}

	/**
	 * delete a timetable
	 *
	 */
	public void delete(TimetableDTO dto) throws Exception {
		log.debug("TimetableModel.delete Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			log.debug("TimetableModel.delete Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			log.error("TimetableModel.delete Exception");
			throw new ApplicationException("Exception in Timetable delete " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("TimetableModel.delete Closed");
	}

	/**
	 * update a timetable
	 *
	 */
	public void update(TimetableDTO dto) throws Exception {
		log.debug("TimetableModel.update Started");
		CourseModelInt Cmodel = ModelFactory.getInstance().getCourseModel();
		CourseDTO Cbean = null;
		Cbean = Cmodel.findByPk(dto.getCourseId());
		dto.setCourseName(Cbean.getCourseName());

		SubjectModelInt Smodel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO Sbean = Smodel.findByPk(dto.getSubjectId());
		dto.setSubjectName(Sbean.getSubjectName());

		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
			log.debug("TimetableModel.update Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
				log.error("Timetable Model.update Exception");
			}
			throw new ApplicationException("Exception in timetable update " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Timetable Model.update Closed");
	}

	/**
	 * list of timetable
	 *
	 */
	public List list() throws Exception {
		return list(0, 0);
	}

	/**
	 * list of timetable with pagination parameters
	 *
	 */
	public List list(int pageNo, int pageSize) throws Exception {
		log.debug("Timetable Model.list Started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("Timetable Model.list Success");
		} catch (HibernateException e) {
			log.error("Timetable Model.list Exception");
			throw new ApplicationException("Exception : Exception in  TimetableDTO list");
			
		} finally {
			session.close();
			log.debug("Timetable Model.list Closed");
		}
		return list;
	}

	/**
	 * search a timetable
	 * 
	 * @throws Exception
	 *
	 */
	public List search(TimetableDTO dto) throws Exception {
		log.debug("TimeTableModel.search started & closed");
		return search(dto, 1, 10);
	}

	/**
	 * search a timetable with pagination parameters
	 *
	 */
	public List search(TimetableDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("Timetable Model.search Started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			if (dto != null) {
				if (dto.getId() != 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
					criteria.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
				}
				if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
					criteria.add(Restrictions.like("subjectName", dto.getSubjectName() + "%"));
				}
				if (dto.getSemester() != null && dto.getSemester().length() > 0) {
					criteria.add(Restrictions.like("semester", dto.getSemester() + "%"));
				}
				if (dto.getExamDate() != null && dto.getExamDate().getDate() > 0) {
					criteria.add(Restrictions.eq("examDate", dto.getExamDate()));
				}
				if (dto.getSubjectId() > 0) {
					criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
				}
				if (dto.getCourseId() > 0) {
					criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
				}
			}
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			log.debug("Timetable Model.search Success");
		} catch (HibernateException e) {
			log.error("Timetable Model.search Exception");
			throw new ApplicationException("Exception in course search");
		} finally {
			session.close();
			log.debug("Timetable Model.search Closed");
		}
		return list;
	}

	/**
	 * find a timetable by pk
	 *
	 */
	public TimetableDTO findByPK(long pk) throws Exception {
		log.debug("Timetable Model.findByPK Started");
		Session session = null;
		TimetableDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (TimetableDTO) session.get(TimetableDTO.class, pk);
			log.debug("Timetable Model.findByPK Success");
		} catch (HibernateException e) {
			log.error("Timetable Model.findByPK Exception");
			throw new ApplicationException("Exception : Exception in getting TimetableDTO by pk");

		} finally {
			session.close();
			log.debug("Timetable Model.findByPK closed ");
		}
		return dto;
	}

	/**
	 * check by coursename
	 *
	 */
	public TimetableDTO checkByCourseName(long courseId, Date examDate) throws Exception {
		log.debug("Timetable Model.checkByCourseName Started");
		Date Exdate = new Date(examDate.getTime());
		Session session = null;
		TimetableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			criteria.add(Restrictions.and(Restrictions.eq("courseId", courseId), Restrictions.eq("examDate", Exdate)));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimetableDTO) list.get(0);
				log.debug("Timetable Model.checkByCourseName Success");
			}
		} catch (HibernateException e) {
			log.error("Timetable Model.checkByCourseName Exception");
			throw new ApplicationException("Exception : Exception in getting TimetableDTO by pk");
			
		} finally {
			session.close();
			log.debug("Timetable Model.checkByCourseName Closed");
		}
		return dto;
	}

	/**
	 * check by subjectname
	 *
	 */
	public TimetableDTO checkBySubjectName(long courseId, long subjectId, Date examDate) throws Exception {
		log.debug("Timetable Model.checkBySubjectName Started");
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		Session session = null;
		TimetableDTO dto = null;
		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.eq("courseId", courseId));
			dis.add(Restrictions.eq("subjectId", subjectId));
			dis.add(Restrictions.eq("examDate", date));
			criteria.add(dis);
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimetableDTO) list.get(0);
				log.debug("Timetable Model.checkBySubjectName Success");
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			log.error("Timetable Model.checkBySubjectName Exception");
			throw new ApplicationException("Exception : Exception in getting TimetableDTO by pk");
			
		} finally {
			session.close();
			log.debug("Timetable Model.checkBySubjectName Closed");
		}
		return dto;
	}

	/**
	 * check by semester
	 * 
	 */
	public TimetableDTO checkBysemester(long courseId, long subjectId, String semester, Date examDate)
			throws Exception {
		log.debug("Timetable Model.checkBysemester Started");
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		Session session = null;
		TimetableDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimetableDTO.class);
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.eq("courseId", courseId));
			dis.add(Restrictions.eq("subjectId", subjectId));
			dis.add(Restrictions.like("semester", semester));
			dis.add(Restrictions.eq("examDate", date));
			criteria.add(dis);
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimetableDTO) list.get(0);
				log.debug("Timetable Model.checkBysemester Success");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Timetable Model.checkBysemester Exception");
			throw new ApplicationException("Exception in checkBysemester");
			
		} finally {
			session.close();
			log.debug("timetable Model.checkBysemester Closed");
		}
		return dto;
	}

	


}
