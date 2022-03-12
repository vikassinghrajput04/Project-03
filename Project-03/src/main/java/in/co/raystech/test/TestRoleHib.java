package in.co.raystech.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import in.co.raystech.dto.RoleDTO;
import in.co.raystech.model.RoleModelHibImp;
import in.co.raystech.model.RoleModelInt;

public class TestRoleHib {
	public static RoleModelInt model = new RoleModelHibImp();

	public static void main(String[] args) throws Exception {
		//addTest();
		//deleteTest();
		// updateTest();
		findByPkTest();
		 //findByNameTest();
		//searchTest();
		//listTest();
	}

	public static void listTest() throws Exception {
		List list = model.list(0, 100);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			RoleDTO dto = (RoleDTO) it.next();
			System.out.println(dto.getId() + "\t" + dto.getName() + "\t" + dto.getDescription());
		}

	}

	public static void searchTest() throws Exception {
		RoleDTO dto1 = new RoleDTO();
		//dto1.setId(1L);
		//dto1.setName("admin");
		List<RoleDTO> a = (List<RoleDTO>) model.search(dto1, 0, 0);
		for (RoleDTO dto : a) {
			System.out.println(dto.getId() + "\t" + dto.getName() + "\t" + dto.getDescription());
		}
	}

	public static void findByNameTest() throws Exception {
		RoleDTO dto = model.findByName("student");
		System.out.println(dto.getId() + "\t" + dto.getName() + "\t" + dto.getDescription());
	}

	public static void updateTest() throws Exception {
		RoleDTO dto = new RoleDTO();
		dto.setId(1L);
		dto.setName("Admin");
		dto.setDescription("Admin");
		dto.setCreatedBy("admin");
		dto.setModifiedBy("admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		System.out.println("Updated");
	}

	public static void findByPkTest() throws Exception {
		RoleDTO dto = model.findByPK(1);
		System.out.println(dto.getId() + "\t" + dto.getName() + "\t" + dto.getDescription());
	}

	public static void deleteTest() throws Exception {
		RoleDTO dto = new RoleDTO();
		dto.setId(3L);
		model.delete(dto);
		System.out.println("Deleted");
	}

	public static void addTest() throws Exception {
		RoleDTO dto = new RoleDTO();
		dto.setName("Faculty");
		dto.setDescription("Faculty");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = model.add(dto);
		System.out.println("Inserted in id-- "+pk);
	}
}
