package in.co.raystech.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.raystech.dto.TimetableDTO;
import in.co.raystech.model.TimetableModelHibImp;
import in.co.raystech.model.TimetableModelInt;

public class TestTimetableModelHib {
	public static TimetableModelInt model = new TimetableModelHibImp();

	// public static TimetableModelInt model=new TimetableModelJDBCImpl();
	public static void main(String[] args) throws Exception {
		addTest();
		//deleteTest();
		// updateTest();
		//findByPkTest();
		//searchTest();
		//listTest();
	}

	public static void listTest() throws Exception {
		TimetableDTO dto = new TimetableDTO();
		List list = model.list(0, 0);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (TimetableDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubjectId());
			System.out.print("\t" + dto.getSubjectName());
			System.out.print("\t" + dto.getCourseId());
			System.out.print("\t" + dto.getCourseName());
			System.out.print("\t" + dto.getSemester());
			System.out.print("\t" + dto.getExamDate());
			System.out.println("\t" + dto.getExamTime());
		}
	}

	public static void searchTest() throws Exception {
		TimetableDTO dto1 = new TimetableDTO();
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//dto1.setId(1L);
		//dto1.setCourseName("BSc CS");
		//dto1.setSemester("first");
		//dto1.setExamDate(sdf.parse("01/04/2022"));
		List<TimetableDTO> a = (List<TimetableDTO>) model.search(dto1, 0, 0);
		for (TimetableDTO dto : a) {
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getSubjectId());
			System.out.print("\t" + dto.getSubjectName());
			System.out.print("\t" + dto.getCourseId());
			System.out.print("\t" + dto.getCourseName());
			System.out.print("\t" + dto.getSemester());
			System.out.print("\t" + dto.getExamDate());
			System.out.println("\t" + dto.getExamTime());

		}
	}


	public static void findByPkTest() throws Exception {
		TimetableDTO dto = model.findByPK(2L);
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getSubjectId());
		System.out.print("\t" + dto.getSubjectName());
		System.out.print("\t" + dto.getCourseId());
		System.out.print("\t" + dto.getCourseName());
		System.out.print("\t" + dto.getSemester());
		System.out.print("\t" + dto.getExamDate());
		System.out.println("\t" + dto.getExamTime());
	}

	public static void updateTest() throws Exception {
		TimetableDTO dto = new TimetableDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dto.setId(1L);
		dto.setSubjectId(1);
		dto.setSubjectName("physics");
		dto.setCourseId(1);
		dto.setCourseName("BSc Cs");
		dto.setSemester("first");
		dto.setExamDate(sdf.parse("12/04/2022"));
		dto.setExamTime("3hr");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		System.out.println("Updated");
	}

	public static void deleteTest() throws Exception {
		TimetableDTO dto = new TimetableDTO();
		dto.setId(3L);
		model.delete(dto);
		System.out.println("Deleted");
	}

	public static void addTest() throws Exception {
		TimetableDTO dto = new TimetableDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		dto.setSubjectId(1);
		dto.setSubjectName("Maths");
		dto.setCourseId(1);
		dto.setCourseName("BSc CS");
		dto.setSemester("first");
		dto.setExamDate(sdf.parse("01/04/2022"));
		dto.setExamTime("12.00 a.m. - 3 p.m.");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = model.add(dto);
		System.out.println("Inserted in id-- " + pk);
	}

}
