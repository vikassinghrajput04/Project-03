package in.co.raystech.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.raystech.dto.CourseDTO;
import in.co.raystech.dto.SubjectDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.utility.HibDataSource;

/**
 * Hibernate implements of Subject model
 * @author Vikas Singh
 *
 */
public class SubjectModelHibImp implements SubjectModelInt{
	
	private static Logger log = Logger.getLogger(SubjectModelHibImp.class);
	/**
	 * add a subject
	 *
	 */
	public Long add(SubjectDTO dto) throws Exception {
		log.debug("SubjectModel.add Started");
		System.out.println(dto.getId());
		long  pk = 0;
		Session session = null;
		Transaction tx = null;
		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO cDto = cModel.findByPk(dto.getCourseId());
		dto.setCourseName(cDto.getCourseName());
		
		
		SubjectDTO duplicataSub = findByName(dto.getSubjectName());
		System.out.println("subject duplicate    "+duplicataSub);
	    // Check if create Subject already exist
	    if (duplicataSub!= null && duplicataSub.getSubjectName()!=null) {
	        throw new DuplicateRecordException("Subject already exists");}
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
			log.debug("SubjectModel.add Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			log.error("SubjectModel.add Exception");
			throw new ApplicationException("Exception in subject Add " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("SubjectModel.add Closed");
		return pk;
	}
	/**
	 * delete a subject
	 *
	 */
	public void delete(SubjectDTO dto) throws Exception {
		log.debug("SubjectModel.delete Started");
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			log.debug("SubjectModel.delete Success");
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			log.error("SubjectModel.delete Exception");
			throw new ApplicationException("Exception in subject Delete" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("SubjectModel.delete Closed");
	}
	/**
	 * update a subject
	 *
	 */
	public void update(SubjectDTO dto) throws Exception {
		log.debug("SubjectModel.update Started");
		Session session = null;
		Transaction tx = null;
		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO cDto = cModel.findByPk(dto.getCourseId());
		dto.setCourseName(cDto.getCourseName());
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			log.debug("SubjectModel.update Success");
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			log.error("SubjectModel.update Exception");
			throw new ApplicationException("Exception in subject update" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("SubjectModel.update Closed");
	}
	/**
	 * list of  subject
	 *
	 */
	public List list() throws Exception {
		log.debug("SubjectModel.list Started & Closed");
		return list(1,10);
	}
	/**
	 * list of subject with pagination parameters
	 *
	 */
	public List list(int pageNo,int pageSize) throws Exception {
		log.debug("SubjectModel.list Started");
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(SubjectDTO.class);
			if(pageSize>0){
				pageNo=((pageNo-1)*pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
			log.debug("SubjectModel.list Success");
		} catch (HibernateException e) {
			log.error("SubjectModel.list Exception");
			throw new ApplicationException("Exception : Exception in  subject list");
		} finally {
			session.close();
		}
		log.debug("SubjectModel.list Closed");
		return list;
	}
	/**
	 * search a subject
	 *
	 */
	public List search(SubjectDTO dto) throws Exception {
		log.debug("SubjectModel.search Started & Closed");
		return search(dto,1,10);
	}
	/**
	 * search a subject with pagination
	 *
	 */
	public List search(SubjectDTO dto, int pageNo, int pageSize) throws Exception {
		log.debug("SubjectModel.search Started");
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(SubjectDTO.class);
			if(dto!=null){
			if(dto.getId()!=0){
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getCourseId()>0){
				criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
			}
			if(dto.getCourseName()!=null&& dto.getCourseName().length()>0){
				criteria.add(Restrictions.like("courseName", dto.getCourseName()+"%"));
			}
			if(dto.getSubjectName()!=null&& dto.getSubjectName().length()>0){
				criteria.add(Restrictions.like("subjectName", dto.getSubjectName()+"%"));
				}
			}
			if(pageSize>0){
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
			log.debug("SubjectModel.search Success");
		} catch (HibernateException e) {
			log.error("SubjectModel.search Exception");
			throw new ApplicationException("Exception : Exception in  subject search");
		} finally {
			session.close();
		}
		log.debug("SubjectModel.search Closed");
		return list;
	}
	/**
	 * find a subject by name
	 *
	 */
	public SubjectDTO findByName(String name) throws Exception {
		log.debug("SubjectModel.findByName Started");
		Session session=null;
		SubjectDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(SubjectDTO.class);
			criteria.add(Restrictions.eq("subjectName", name));
			List list=criteria.list();
			if(list.size()==1){
				dto=(SubjectDTO) list.get(0);
			}
			log.debug("SubjectModel.findByName Success");
		} catch (HibernateException e) {
            log.error("SubjectModel.findByName Exception");
            throw new ApplicationException(
                    "Exception in getting subject by findbyName " + e.getMessage());
        } finally {
            session.close();
        }
		log.debug("SubjectModel.findByName Closed");
		return dto;
	}
	/**
	 * find a subject by pk
	 *
	 */
	public SubjectDTO findByPk(long pk) throws Exception {
		log.debug("SubjectModel.findByPk Started");
		Session session=null;
		SubjectDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(SubjectDTO) session.get(SubjectDTO.class, pk);
			log.debug("SubjectModel.findByPk Success");
		} catch (HibernateException e) {
            log.error("SubjectModel.findByPk Exception");
            throw new ApplicationException(
                    "Exception : Exception in getting subject by pk");
        } finally {
            session.close();
        }
		log.debug("SubjectModel.findByPk Closed");
		return dto;
	}

}
