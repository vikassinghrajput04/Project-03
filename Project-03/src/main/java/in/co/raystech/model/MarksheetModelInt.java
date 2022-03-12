package in.co.raystech.model;

import java.util.List;

import in.co.raystech.dto.MarksheetDTO;

public interface MarksheetModelInt {

	public Long add(MarksheetDTO dto) throws Exception;
	public void delete(MarksheetDTO dto) throws Exception;
	public MarksheetDTO findByPK(long pk) throws Exception;
	public MarksheetDTO findByRollNo(String rollNo) throws Exception;
	public List<MarksheetDTO> getMeritList(int pageNo, int pageSize) throws Exception;
	public void update(MarksheetDTO dto) throws Exception;
	public List<MarksheetDTO> search(MarksheetDTO dto) throws Exception;
	public List<MarksheetDTO> search(MarksheetDTO dto, int pageNo, int pageSize) throws Exception;
	public List<MarksheetDTO> list() throws Exception;
	public List<MarksheetDTO> list(int pageNo, int pageSize) throws Exception;
	
}
