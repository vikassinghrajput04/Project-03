package in.co.raystech.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.raystech.dto.CourseDTO;
import in.co.raystech.model.CourseModelHibImp;
import in.co.raystech.model.CourseModelInt;

public class TestCourseHib {

	public static CourseModelInt model = new CourseModelHibImp();

	public static void main(String[] args) throws Exception {
		//addTest();
		//deleteTest();
		//updateTest();
		//findByNameTest();
		//findByPkTest();
		//listTest();
		searchTest();
	}

	public static void searchTest() throws Exception {
		CourseDTO cbean1 = new CourseDTO();
		cbean1.setCourseName("BCA");
		List<CourseDTO> a = (List<CourseDTO>) model.search(cbean1, 0, 0);
		for (CourseDTO dto : a) {
			System.out.println(
					dto.getId() + "\t" + dto.getCourseName() + "\t" + dto.getDescription() + "\t" + dto.getDuration());
		}
	}

	public static void listTest() throws Exception {
		CourseDTO dto = new CourseDTO();
		List list = new ArrayList();
		list = model.list(0, 0);
		if (list.size() < 0) {
			System.out.println("list fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (CourseDTO) it.next();
			System.out.println(
					dto.getId() + "\t" + dto.getCourseName() + "\t" + dto.getDescription() + "\t" + dto.getDuration());
		}
	}

	public static void findByPkTest() throws Exception {
		CourseDTO dto = model.findByPk(1);
		System.out.println(
				dto.getId() + "\t" + dto.getCourseName() + "\t" + dto.getDescription() + "\t" + dto.getDuration());
	}

	public static void findByNameTest() throws Exception {
		CourseDTO dto = model.findByName("BSc CS");
		System.out.println(
				dto.getId() + "\t" + dto.getCourseName() + "\t" + dto.getDescription() + "\t" + dto.getDuration());

	}

	public static void updateTest() throws Exception {
		CourseDTO dto = new CourseDTO();
		dto.setId(2L);
		dto.setCourseName("BCA");
		dto.setDescription("Bachelor of Computer Application");
		dto.setDuration("3year");
		dto.setCreatedBy("admin");
		dto.setModifiedBy("admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		System.out.println("Updated!!!");
	}

	public static void deleteTest() throws Exception {
		CourseDTO dto = new CourseDTO();
		dto.setId(3L);
		model.delete(dto);
		System.out.println("Deleted!!!");
	}

	public static void addTest() throws Exception {
		CourseDTO dto = new CourseDTO();

		dto.setCourseName("BCA");
		dto.setDescription("Bachelor of Computer Application");
		dto.setDuration("3year");
		dto.setCreatedBy("admin");
		dto.setModifiedBy("admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = model.add(dto);
		System.out.println("Inserted in id-- "+pk);

	}
}
