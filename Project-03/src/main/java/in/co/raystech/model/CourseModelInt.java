package in.co.raystech.model;

import java.util.List;

import in.co.raystech.dto.CourseDTO;

public interface CourseModelInt {
	
	public Long add(CourseDTO dto) throws Exception;
	public void update(CourseDTO dto) throws Exception;
	public void delete(CourseDTO dto) throws Exception;
	public CourseDTO findByPk(long i) throws Exception;
	public CourseDTO findByName(String name) throws Exception;
	public List<CourseDTO> search(CourseDTO dto) throws Exception;
	public List<CourseDTO> search(CourseDTO dto, int pageNo, int pageSize) throws Exception;
	public List<CourseDTO> list() throws Exception;
	public List<CourseDTO> list(int pageNo,int pageSize) throws Exception;
	
	

}
