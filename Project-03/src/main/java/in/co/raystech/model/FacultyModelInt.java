package in.co.raystech.model;

import java.util.List;

import in.co.raystech.dto.FacultyDTO;

public interface FacultyModelInt {

	public Long add(FacultyDTO dto) throws Exception;
	public void update(FacultyDTO dto) throws Exception;
	public FacultyDTO findByPk(long id) throws Exception;
	public FacultyDTO findByEmail(String email) throws Exception;
	public void delete(FacultyDTO dto) throws Exception;
	public List<FacultyDTO> list() throws Exception;
	public List<FacultyDTO> list(int pageNo, int pageSize) throws Exception;
	public List<FacultyDTO> Search(FacultyDTO dto) throws Exception;
	public List<FacultyDTO> search(FacultyDTO dto, int pageNo, int pageSize) throws Exception;
	
	
}
