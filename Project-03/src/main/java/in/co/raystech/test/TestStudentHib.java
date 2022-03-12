package in.co.raystech.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.raystech.dto.StudentDTO;
import in.co.raystech.model.StudentModelHibImp;
import in.co.raystech.model.StudentModelInt;

public class TestStudentHib {
		public static StudentModelInt model = new StudentModelHibImp();
		public static void main(String[] args) throws Exception {
			// addTest(); 
			//deleteTest(); 
			 //updateTest();  
			//findByPkTest(); 
			// findByEmailIdTest(); 
			listTest(); 
			//searchTest(); 
		}

		public static void searchTest() throws Exception {
			StudentDTO dto=new StudentDTO();
			//dto.setId(1L);
			//dto.setFirstName("ram");
			// dto.setLastName("agrawals");
			// dto.setMobileNO("989");
			// dto.setRoleId(101);
			// dto.setUnSuccessfullLogin(1);

			
			ArrayList<StudentDTO> a = (ArrayList<StudentDTO>) model.search(dto);
			for (StudentDTO uu : a) {

				System.out.println(uu.getId() + "\t" + uu.getFirstName() + "\t" + uu.getLastName() + "\t" + uu.getDob()
						+ "\t" + uu.getEmail() + "\t" + uu.getMobileNo() + "\t" + uu.getCollegeId() + "\t"
						+ uu.getCollegeName());}
		}

		public static void listTest() throws Exception {
			StudentDTO dto=new StudentDTO();
			
	       
	        List list = new ArrayList();
	        list = model.list(1,100);
	        if (list.size() < 0) {
	            System.out.println("Test list fail");
	        }
	        Iterator it = list.iterator();
	        while (it.hasNext()) {
	            dto = (StudentDTO) it.next();
	            System.out.print(dto.getId());
	            System.out.print(dto.getFirstName());
	            System.out.print(dto.getLastName());
	            System.out.print(dto.getDob());
	            System.out.print(dto.getMobileNo());
	            System.out.print(dto.getEmail());
	            System.out.print(dto.getCollegeId());
	            System.out.print(dto.getCreatedBy());
	            System.out.print(dto.getCreatedDatetime());
	            System.out.print(dto.getModifiedBy());
	            System.out.println(dto.getModifiedDatetime());
	            System.out.println(dto.getValue());
	        }
		}

		public static void findByEmailIdTest() throws Exception {
			StudentDTO dto=model.findByEmailId("ram123@gmail.com");
			System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t" + dto.getDob() + "\t"
					+ dto.getEmail() + "\t" + dto.getMobileNo() + "\t" + dto.getCollegeId() + "\t" + dto.getCollegeName());
			
		}

		public static void findByPkTest() throws Exception {
			
			StudentDTO dto=model.findByPK(3L);
			System.out.println(dto.getId() + "\t" + dto.getFirstName() + "\t" + dto.getLastName() + "\t" + dto.getDob() + "\t"
					+ dto.getEmail() + "\t" + dto.getMobileNo() + "\t" + dto.getCollegeId() + "\t" + dto.getCollegeName());
		}

		public static void updateTest() throws Exception {
	            StudentDTO dto = new StudentDTO();
	            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	        dto.setId(1L);
	        dto.setFirstName("Ram");
	        dto.setLastName("sharma");
	        dto.setDob(sdf.parse("15/01/1990"));
	        dto.setMobileNo("9165254357");
	        dto.setEmail("ram123@gmail.com");
	        dto.setCollegeId(1);
	        dto.setCollegeName("Mb Khalsa");
	        dto.setCreatedBy("Admin");
			dto.setModifiedBy("Admin");
			dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
			dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	        model.update(dto); 
	        System.out.println("Updated");
		}

		public static void deleteTest() throws Exception {
			  StudentDTO dto = new StudentDTO();
	          long pk = 2;
	          dto.setId(pk);
	          model.delete(dto);
	          System.out.println("Deleted");
	          
		}

		public static void addTest() throws Exception {
			StudentDTO dto = new StudentDTO();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			dto.setId(1L);
			dto.setFirstName("Raju");
			dto.setLastName("Verma");
			dto.setDob(sdf.parse("20/05/1990"));
			dto.setMobileNo("94066553787");
			dto.setEmail("raju123@gmail.com");
			dto.setCollegeId(1);
			dto.setCollegeName("Mb Khalsa College");
			dto.setCreatedBy("Admin");
			dto.setModifiedBy("Admin");
			dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
			dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(dto);
			System.out.println("Inserted in id--- "+ pk);
			

		}
}
