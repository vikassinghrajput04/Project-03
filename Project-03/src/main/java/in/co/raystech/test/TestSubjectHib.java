package in.co.raystech.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.raystech.dto.SubjectDTO;
import in.co.raystech.model.SubjectModelHibImp;
import in.co.raystech.model.SubjectModelInt;

public class TestSubjectHib {

	public static SubjectModelInt model = new SubjectModelHibImp();

	// public static SubjectModelInt model=new SubjectModelJDBCImpl();
	public static void main(String[] args) throws Exception {
		//addTest();
		//deleteTest();
		// updateTest();
		 //findByPKTest();
		//findByNameTest();
		 //searchTest();
		listTest();

	}

	public static void listTest() throws Exception {
		SubjectDTO dto = new SubjectDTO();
		List list = new ArrayList();
		list = model.list(1, 100);

		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (SubjectDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubjectName());
			System.out.print("\t" + dto.getCourseId());
			System.out.print("\t" + dto.getCourseName());
			System.out.print("\t" + dto.getDescription());
			System.out.print(dto.getCreatedDatetime());
			System.out.print(dto.getModifiedBy());
			System.out.println(dto.getModifiedDatetime());
			System.out.println(dto.getValue());
		}
	}

	public static void searchTest() throws Exception {
		SubjectDTO dto1 = new SubjectDTO();
		//dto1.setId(1L);
		//dto1.setSubjectName("maths");
		// dto1.setCourseId(1);
		// dto1.setCourseName("be");

		ArrayList<SubjectDTO> a = (ArrayList<SubjectDTO>) model.search(dto1, 0, 0);
		for (SubjectDTO dto : a) {

			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubjectName());
			System.out.print("\t" + dto.getCourseId());
			System.out.print("\t" + dto.getCourseName());
			System.out.println("\t" + dto.getDescription());
		}
	}

	public static void findByNameTest() throws Exception {
		SubjectDTO dto = model.findByName("physics");
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectName());
		System.out.print("\t" + dto.getCourseId());
		System.out.print("\t" + dto.getCourseName());
		System.out.print("\t" + dto.getDescription());
	}

	public static void findByPKTest() throws Exception {
		SubjectDTO dto = model.findByPk(2L);
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectName());
		System.out.print("\t" + dto.getCourseId());
		System.out.print("\t" + dto.getCourseName());
		System.out.println("\t" + dto.getDescription());
	}

	public static void updateTest() throws Exception {
		SubjectDTO dto = new SubjectDTO();
		dto.setId(1L);
		dto.setSubjectName("Maths");
		dto.setCourseId(2);
		dto.setCourseName("BCA");
		dto.setDescription("Maths");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		System.out.println("Updated");
	}

	public static void deleteTest() throws Exception {
		SubjectDTO dto = new SubjectDTO();
		dto.setId(3L);
		model.delete(dto);
		System.out.println("Deleted");
	}

	public static void addTest() throws Exception {

		SubjectDTO dto = new SubjectDTO();
		dto.setSubjectName("Chemistry");
		dto.setCourseId(1);
		dto.setCourseName("Bsc Cs");
		dto.setDescription("Chemistry");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = model.add(dto);
		System.out.println("Inserted in id--- " + pk);
	}
}
