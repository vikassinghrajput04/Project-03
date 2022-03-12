package in.co.raystech.model;

import java.util.List;

import in.co.raystech.dto.RoleDTO;

public interface RoleModelInt {

	public long add(RoleDTO dto) throws Exception;
	public void delete(RoleDTO dto) throws Exception;
	public void update(RoleDTO dto) throws Exception;
	public RoleDTO findByPK(long pk) throws Exception;
	public RoleDTO findByName(String Name) throws Exception;
	public List<RoleDTO> search(RoleDTO dto) throws Exception;
	public List<RoleDTO> search(RoleDTO dto, int pageNo, int pageSize) throws Exception;
	public List<RoleDTO> list() throws Exception;
	public List<RoleDTO> list(int pageNo, int pageSize) throws Exception;
	
}
