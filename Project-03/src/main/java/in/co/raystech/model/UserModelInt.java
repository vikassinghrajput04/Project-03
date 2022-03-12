package in.co.raystech.model;

import java.util.List;

import in.co.raystech.dto.UserDTO;
import in.co.raystech.exception.ApplicationException;

public interface UserModelInt {

	public Long add(UserDTO dto) throws Exception;
	public UserDTO authenticate(String login, String pwd) throws Exception;
	public void delete(UserDTO dto) throws Exception;
	public UserDTO findByPK(Long l) throws Exception;
	public UserDTO findByLogin(String login) throws Exception;
	public boolean forgetPassword(String login) throws Exception;
	public boolean changePassword(long id, String oldPassword, String newPassword) throws Exception;
	public void update(UserDTO dto) throws Exception;
	public List<UserDTO> search(UserDTO dto) throws Exception;
	public List<UserDTO> search(UserDTO dto, int pageNo, int pageSize) throws Exception;
	public List<UserDTO> list() throws Exception;
	public List<UserDTO> list(int pageNo, int pageSize) throws Exception;
	public boolean resetPassword(UserDTO dto) throws Exception;
	public Long registerUser(UserDTO dto) throws Exception;
	public List<UserDTO> getRoles(UserDTO dto) throws Exception;
	
	
}
