package in.co.raystech.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.raystech.dto.FacultyDTO;
import in.co.raystech.model.FacultyModelHibImp;
import in.co.raystech.model.FacultyModelInt;

public class TestFacultyHib {
	public static FacultyModelInt model = new FacultyModelHibImp();

	public static void main(String[] args) throws Exception {
		// addTest();
		// deleteTest();
		// updateTest();
		// findByPKTest();
		// findByEmailidTest();
		// searchTest();
		listTest();

	}

	public static void listTest() throws Exception {
		FacultyDTO dto = new FacultyDTO();
		List list = new ArrayList();
		list = model.list(0, 0);
		if (list.size() < 0) {
			System.out.println("Test list fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (FacultyDTO) it.next();
			System.out.print(dto.getId() + "\t");
			System.out.print(dto.getFirstName() + "\t");
			System.out.print(dto.getLastName() + "\t");
			System.out.print(dto.getdOJ() + "\t");
			System.out.print(dto.getMobileNo() + "\t");
			System.out.print(dto.getEmailId() + "\t");
			System.out.print(dto.getCollegeId() + "\t");
			System.out.print(dto.getCreatedBy() + "\t");
			System.out.print(dto.getCreatedDatetime() + "\t");
			System.out.print(dto.getModifiedBy() + "\t");
			System.out.println(dto.getModifiedDatetime() + "\t");
		}

	}

	public static void searchTest() throws Exception {
		FacultyDTO dto1 = new FacultyDTO();

		// dto1.setId(1L);

		ArrayList<FacultyDTO> a = (ArrayList<FacultyDTO>) model.Search(dto1);
		for (FacultyDTO dto : a) {
			System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t"
					+ dto.getGender() + "\t" + dto.getdOJ() + "\t" + dto.getQualification() + "\t" + dto.getMobileNo()
					+ "\t" + dto.getEmailId() + "\t" + dto.getCollegeId() + "\t" + dto.getCollegeName() + "\t"
					+ dto.getSubjectId() + "\t" + dto.getSubjectName() + "\t" + dto.getCourseId() + "\t"
					+ dto.getCourseName());

		}
	}

	public static void updateTest() throws Exception {
		FacultyDTO dto = new FacultyDTO();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dto.setId(1L);
		dto.setFirstName("Hemant");
		dto.setLastName("Sharma");
		dto.setGender("Male");
		dto.setdOJ(sdf.parse("02/12/2015"));
		dto.setQualification("BE");
		dto.setEmailId("hemant123@gmail.com");
		dto.setMobileNo("98987887778");
		dto.setCollegeId(1);
		dto.setCollegeName("svce");
		dto.setCourseId(2);
		dto.setCourseName("maths");
		dto.setSubjectId(2);
		dto.setSubjectName("physics");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		System.out.println("Updated");
	}

	public static void findByPKTest() throws Exception {
		FacultyDTO dto = model.findByPk(2L);
		System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t" + dto.getGender()
				+ "\t" + dto.getdOJ() + "\t" + dto.getQualification() + "\t" + dto.getMobileNo() + "\t"
				+ dto.getEmailId() + "\t" + dto.getCollegeId() + "\t" + dto.getCollegeName() + "\t" + dto.getSubjectId()
				+ "\t" + dto.getSubjectName() + "\t" + dto.getCourseId() + "\t" + dto.getCourseName());
	}

	public static void findByEmailidTest() throws Exception {
		FacultyDTO dto = model.findByEmail("suresh123@gmail.com");
		System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t" + dto.getGender()
				+ "\t" + dto.getdOJ() + "\t" + dto.getQualification() + "\t" + dto.getMobileNo() + "\t"
				+ dto.getEmailId() + "\t" + dto.getCollegeId() + "\t" + dto.getCollegeName() + "\t" + dto.getSubjectId()
				+ "\t" + dto.getSubjectName() + "\t" + dto.getCourseId() + "\t" + dto.getCourseName());

	}

	public static void deleteTest() throws Exception {
		FacultyDTO dto = new FacultyDTO();
		dto.setId(2L);
		model.delete(dto);
		System.out.println("Deleted!!!");
	}

	public static void addTest() throws Exception {
		FacultyDTO dto = new FacultyDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dto.setFirstName("Suresh");
		dto.setLastName("Mehta");
		dto.setGender("Male");
		dto.setdOJ(sdf.parse("01/12/2020"));
		dto.setQualification("ME");
		dto.setEmailId("suresh123@gmail.com");
		dto.setMobileNo("98987887778");
		dto.setCollegeId(2);
		// dto.setCollegeName("Sage University");
		dto.setCourseId(2);
		// dto.setCourseName("BCA");
		dto.setSubjectId(2);
		dto.setSubjectName("Physics");
		dto.setCreatedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		Long pk = model.add(dto);
		System.out.println("Inserted in id-- " + pk);
	}

}
