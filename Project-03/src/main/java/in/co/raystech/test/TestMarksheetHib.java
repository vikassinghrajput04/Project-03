package in.co.raystech.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import in.co.raystech.dto.MarksheetDTO;
import in.co.raystech.model.MarksheetModelHibImp;
import in.co.raystech.model.MarksheetModelInt;

public class TestMarksheetHib {
	public static MarksheetModelInt model = new MarksheetModelHibImp();

	public static void main(String[] args) throws Exception {
		//addTest();
		// deleteTest();
		//updateTest();
		 //findByPkTest();
		//findByEmailIdTest();
		//listTest();
		//searchTest();
		// testGetMeritList();
		findByRollNoTest();

	}

	private static void findByRollNoTest() throws Exception {
		MarksheetDTO dto = new MarksheetDTO();
		dto = model.findByRollNo("1201AA104");
		if (dto.getName()==null) {
			System.out.println("No Record Found");
		}
		System.out.println(dto.getName()+""+dto.getRollNo()+""+dto.getPhysics()+""+dto.getChemistry()+""+dto.getMaths());
		
	}

	public static void testGetMeritList() throws Exception {
		MarksheetDTO dto = null;
		List list = new ArrayList();
		list = model.getMeritList(1, 10);

		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (MarksheetDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getRollNo());
			System.out.print("\t" + dto.getStudentId());
			System.out.print("\t" + dto.getName());
			System.out.print("\t" + dto.getPhysics());
			System.out.print("\t" + dto.getChemistry());
			System.out.println("\t" + dto.getMaths());
		}
	}

	public static void listTest() throws Exception {
		MarksheetDTO dto = new MarksheetDTO();
		List list = new ArrayList();
		list = model.list(1, 100);

		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (MarksheetDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getRollNo());
			System.out.print("\t" + dto.getStudentId());
			System.out.print("\t" + dto.getName());
			System.out.print("\t" + dto.getPhysics());
			System.out.print("\t" + dto.getChemistry());
			System.out.println("\t" + dto.getMaths());
		}
	}

	public static void searchTest() throws Exception {
		MarksheetDTO dto1 = new MarksheetDTO();
		//dto1.setId(1L);
		// dto1.setRollNo("101");
		ArrayList<MarksheetDTO> a = (ArrayList<MarksheetDTO>) model.search(dto1, 0, 0);
		for (MarksheetDTO dto : a) {

			System.out.print(dto.getId());
			System.out.print("\t" + dto.getRollNo());
			System.out.print("\t" + dto.getStudentId());
			System.out.print("\t" + dto.getName());
			System.out.print("\t" + dto.getPhysics());
			System.out.print("\t" + dto.getChemistry());
			System.out.println("\t" + dto.getMaths());
		}
	}

	public static void findByEmailIdTest() throws Exception {
		MarksheetDTO dto = model.findByRollNo("1201AA102");
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getRollNo());
		System.out.print("\t" + dto.getStudentId());
		System.out.print("\t" + dto.getName());
		System.out.print("\t" + dto.getPhysics());
		System.out.print("\t" + dto.getChemistry());
		System.out.println("\t" + dto.getMaths());
	}

	public static void findByPkTest() throws Exception {
		MarksheetDTO dto = model.findByPK(2L);
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getRollNo());
		System.out.print("\t" + dto.getStudentId());
		System.out.print("\t" + dto.getName());
		System.out.print("\t" + dto.getPhysics());
		System.out.print("\t" + dto.getChemistry());
		System.out.println("\t" + dto.getMaths());
	}

	public static void deleteTest() throws Exception {
		MarksheetDTO dto = new MarksheetDTO();
		dto.setId(1L);
		model.delete(dto);
		System.out.println("Deleted!!!");
	}

	public static void addTest() throws Exception {
		MarksheetDTO dto = new MarksheetDTO();
		dto.setChemistry(80);
		dto.setMaths(80);
		dto.setRollNo("1201AA102");
		dto.setStudentId((long) 3);
		dto.setPhysics(90);
		dto.setName("Hemant");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(dto);
		System.out.println("Inserted!!!");

	}

	public static void updateTest() throws Exception {
		MarksheetDTO dto = new MarksheetDTO();
		dto.setId(1L);
		dto.setChemistry(80);
		dto.setMaths(90);
		dto.setRollNo("1201AA101");
		dto.setStudentId((long) 2);
		dto.setPhysics(25);
		dto.setName("Shivam");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		System.out.println("Updated!!!");
	}
}
