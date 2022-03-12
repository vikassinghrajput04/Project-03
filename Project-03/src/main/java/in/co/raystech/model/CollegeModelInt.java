package in.co.raystech.model;

import java.util.List;

import in.co.raystech.dto.CollegeDTO;

public interface CollegeModelInt {
	
	public long add(CollegeDTO dto) throws Exception ;
	public void delete(CollegeDTO dto) throws Exception;
	public CollegeDTO findByName(String name) throws Exception;
	public CollegeDTO findByPk(long id) throws Exception;
	public void update(CollegeDTO dto) throws Exception;
	public CollegeDTO findByCity(String city) throws Exception;
	public List<CollegeDTO> search(CollegeDTO dto, int pageNo, int pageSize) throws Exception;
	public List<CollegeDTO> list(int pageNo, int pageSize) throws Exception;
	public List list() throws Exception;
	
	

}
