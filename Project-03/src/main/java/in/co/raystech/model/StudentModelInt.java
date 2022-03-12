package in.co.raystech.model;

import java.util.List;

import in.co.raystech.dto.StudentDTO;

public interface StudentModelInt {

	public long add(StudentDTO dto)throws Exception;
	public void delete(StudentDTO dto)throws Exception;
	public void update(StudentDTO dto)throws Exception;
	public List list()throws Exception;
	public List list(int pageNo,int pageSize)throws Exception;
	public List search(StudentDTO dto)throws Exception;
	public List search(StudentDTO dto,int pageNo,int pageSize)throws Exception;
	public StudentDTO findByPK(long pk)throws Exception;
	public StudentDTO findByEmailId(String emailId)throws Exception;
	
}
