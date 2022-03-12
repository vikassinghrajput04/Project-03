package in.co.raystech.model;

import java.util.List;

import in.co.raystech.dto.SubjectDTO;

public interface SubjectModelInt {

	public Long add(SubjectDTO dto) throws Exception;
	public void delete(SubjectDTO dto) throws Exception;
	public void update(SubjectDTO dto) throws Exception;
	public SubjectDTO findByName(String name) throws Exception;
	public SubjectDTO findByPk(long pk) throws Exception;
	public List<SubjectDTO> search(SubjectDTO dto) throws Exception;
	public List<SubjectDTO> search(SubjectDTO dto, int pageNo, int pageSize) throws Exception;
	public List<SubjectDTO> list() throws Exception;
	public List<SubjectDTO> list(int pageNo, int pageSize) throws Exception;
	
	
}
