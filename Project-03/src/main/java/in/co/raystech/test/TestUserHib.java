package in.co.raystech.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.raystech.dto.UserDTO;
import in.co.raystech.model.UserModelHibImp;
import in.co.raystech.model.UserModelInt;

public class TestUserHib {
	public static UserModelInt model = new UserModelHibImp();
	// public static UserModelInt model = new UserModelJDBCImpl();

	public static void main(String[] args) throws Exception {
		// addTest();
		// updateTest();
		// deleteTest();
		// findByPKTest();
		// findByLoginTest();
		// listTest();
		//searchTest();
		authenticateTest();
	}

	private static void authenticateTest() throws Exception {
		UserDTO dto = model.authenticate("vikassinghr786@gmail.com", "Vikas@1234");
		if (dto.getFirstName()!=null) {
			System.out.println("Authentication success of "+dto.getFirstName()+"Role id--"+dto.getRoleId());
			
		} else {
			System.out.println("Authenticate Failed");
		}
	}

	private static void findByLoginTest() throws Exception {

		UserDTO dto = model.findByLogin("vikassinghr786@gmail.com");
		System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t" + dto.getLogin()
				+ "\t" + dto.getPassword() + "\t" + dto.getDob() + "\t" + dto.getMobileNo() + "\t" + dto.getRoleId()
				+ "\t" + dto.getUnSuccessfulLogin() + "\t" + dto.getGender() + "\t");

	}

	public static void searchTest() throws Exception {
		UserDTO dto = new UserDTO();
		// dto.setId(2L);
		// dto.setFirstName("Vikas");
		//dto.setLastName("Rajput");
		// dto.setLogin("vikassinghr786@gmail.com");
		 //dto.setPassword("Vikas@123");
		 //dto.setRoleId(1);
		//dto.setGender("male");

		List<UserDTO> a = (List<UserDTO>) model.search(dto, 0, 0);

		for (UserDTO udto1 : a) {
			System.out.println(udto1.getId() + "\t" + udto1.getFirstName() + "\t" + udto1.getLastName() + "\t"
					+ udto1.getLogin() + "\t" + udto1.getPassword() + "\t" + udto1.getDob() + "\t" + udto1.getMobileNo()
					+ "\t" + udto1.getRoleId());
		}
	}

	public static void listTest() throws Exception {
		UserDTO dto = new UserDTO();
		List list = new ArrayList();
		list = model.list(1, 10);
		if (list.size() < 0) {
			System.out.println("list fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (UserDTO) it.next();
			System.out.print(dto.getId());
			System.out.print(dto.getFirstName());
			System.out.print(dto.getLastName());
			System.out.print(dto.getLogin());
			System.out.print(dto.getPassword());
			System.out.print(dto.getDob());
			System.out.print(dto.getRoleId());
			System.out.print(dto.getUnSuccessfulLogin());
			System.out.print(dto.getGender());
			System.out.print(dto.getLastLogin());
			System.out.print(dto.getMobileNo());
			System.out.print(dto.getCreatedBy());
			System.out.print(dto.getModifiedBy());
			System.out.print(dto.getCreatedDatetime());
			System.out.println(dto.getModifiedDatetime());
		}
	}

	public static void findByPKTest() throws Exception {
		UserDTO dto = model.findByPK(1L);
		System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t" + dto.getLogin()
				+ "\t" + dto.getPassword() + "\t" + dto.getDob() + "\t" + dto.getMobileNo() + "\t" + dto.getRoleId()
				+ "\t" + dto.getUnSuccessfulLogin() + "\t" + dto.getGender() + "\t" + dto.getLastLogin() + "\t" + "\t"
				+ dto.getLastLogin() + "\t" + dto.getRegisteredIP());
	}

	public static void addTest() throws Exception {
		UserDTO dto = new UserDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dto.setFirstName("Nishant");
		dto.setLastName("Jain	");
		dto.setDob(sdf.parse("05/10/1999"));
		dto.setConfirmPassword("nishant123");
		dto.setPassword("nishant123");
		dto.setLogin("nishant123@gmail.com");
		dto.setGender("male");
		dto.setUnSuccessfulLogin(0);
		dto.setCreatedBy("admin");
		dto.setModifiedBy("admin");
		dto.setRoleId(2);
		dto.setMobileNo("9895554884");
		dto.setRegisteredIP(null);
		dto.setLastLoginIP(null);
		dto.setLastLogin(null);
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = model.add(dto);
		System.out.println("Inserted in id--- " + pk);
	}

	public static void deleteTest() throws Exception {
		UserDTO dto = new UserDTO();
		dto.setId(3L);
		model.delete(dto);
		System.out.println("delete data successfully");
	}

	public static void updateTest() throws Exception {
		UserDTO dto = new UserDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		dto.setId(2L);
		dto.setFirstName("Harsh");
		dto.setLastName("Patidar");
		dto.setDob(sdf.parse("01-01-1994"));
		dto.setConfirmPassword("Harsh@123");
		dto.setPassword("Harsh@123");
		dto.setLogin("Harsh4544@gmail.com");
		dto.setGender("male");
		dto.setUnSuccessfulLogin(1);
		dto.setCreatedBy("admin");
		dto.setModifiedBy("admin");
		dto.setRoleId(1);
		dto.setMobileNo("8406653787");
		dto.setRegisteredIP(null);
		dto.setLastLoginIP(null);
		dto.setLastLogin(null);
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		System.out.println("Updated");
	}

}
