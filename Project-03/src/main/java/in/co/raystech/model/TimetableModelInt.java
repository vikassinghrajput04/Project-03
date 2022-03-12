package in.co.raystech.model;

import java.util.Date;
import java.util.List;
import in.co.raystech.dto.TimetableDTO;

public interface TimetableModelInt {
	
	public Long add(TimetableDTO tb) throws Exception;
	public void delete(TimetableDTO tb) throws Exception;
	public void update(TimetableDTO tb) throws Exception;
	public TimetableDTO findByPK(long pk) throws Exception;
	public List<TimetableDTO> search(TimetableDTO dto) throws Exception;
	public List<TimetableDTO> search(TimetableDTO tb, int pageNo, int pageSize) throws Exception;
	public List<TimetableDTO> list() throws Exception;
	public List<TimetableDTO> list(int pageNo, int pageSize) throws Exception;
	public TimetableDTO checkBysemester(long CourseId, long SubjectId, String semester, Date ExamDate) throws Exception;
	public TimetableDTO checkBySubjectName(long courseId, long subjectId, Date examDate) throws Exception;
	public TimetableDTO checkByCourseName(long courseId, Date examDate) throws Exception;
	

}
