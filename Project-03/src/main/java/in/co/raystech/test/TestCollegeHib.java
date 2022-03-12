package in.co.raystech.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.raystech.dto.CollegeDTO;
import in.co.raystech.model.CollegeModelHibImp;
import in.co.raystech.model.CollegeModelInt;

public class TestCollegeHib {
	public static CollegeModelInt model=new CollegeModelHibImp();
	public static void main(String[] args) throws Exception {
		//testAdd();
		//testDelete();
		//testUpdate();
		//testFindByName();
		//testFindById();
		//testFindByCity();
		//testSearch();
		testList();
		
	}

	public static void testList() throws Exception {
		List list = model.list(1, 100);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			CollegeDTO dto = (CollegeDTO) it.next();
			System.out.print(dto.getId()+"\t");
			System.out.print(dto.getName()+"\t");
			System.out.print(dto.getAddress()+"\t");
			System.out.print(dto.getCity()+"\t");
			System.out.print(dto.getState()+"\t");
			System.out.print(dto.getPhoneNo()+"\t");
			System.out.print(dto.getCreatedBy()+"\t");
			System.out.print(dto.getModifiedBy()+"\t");
			System.out.print(dto.getCreatedDatetime()+"\t");
			System.out.println(dto.getModifiedDatetime()+"\t");
			
		}

	}

	public static void testSearch() throws Exception {
		CollegeDTO dto = new CollegeDTO();
		//dto.setName("Sage University");
		List list = model.search(dto, 1, 100);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (CollegeDTO) it.next();
			System.out.print(dto.getId()+"\t");
			System.out.print(dto.getName()+"\t");
			System.out.print(dto.getAddress()+"\t");
			System.out.print(dto.getCity()+"\t");
			System.out.print(dto.getState()+"\t");
			System.out.print(dto.getPhoneNo()+"\t");
			System.out.print(dto.getCreatedBy()+"\t");
			System.out.print(dto.getModifiedBy()+"\t");
			System.out.print(dto.getCreatedDatetime()+"\t");
			System.out.println(dto.getModifiedDatetime()+"\t");
			
			
		}
	}

	public static void testFindByCity() throws Exception {
		CollegeDTO dto = new CollegeDTO();
		dto =  model.findByCity("Indore");
		System.out.print(dto.getId()+"\t");
		System.out.print(dto.getName()+"\t");
		System.out.print(dto.getAddress()+"\t");
		System.out.print(dto.getCity()+"\t");
		System.out.print(dto.getState()+"\t");
		System.out.print(dto.getPhoneNo()+"\t");
		System.out.print(dto.getCreatedBy()+"\t");
		System.out.print(dto.getModifiedBy()+"\t");
		System.out.print(dto.getCreatedDatetime()+"\t");
		System.out.println(dto.getModifiedDatetime()+"\t");
	
	}

	public static void testFindById() throws Exception {
		CollegeDTO dto = new CollegeDTO();
		dto =  model.findByPk(2l);
		System.out.print(dto.getId()+"\t");
		System.out.print(dto.getName()+"\t");
		System.out.print(dto.getAddress()+"\t");
		System.out.print(dto.getCity()+"\t");
		System.out.print(dto.getState()+"\t");
		System.out.print(dto.getPhoneNo()+"\t");
		System.out.print(dto.getCreatedBy()+"\t");
		System.out.print(dto.getModifiedBy()+"\t");
		System.out.print(dto.getCreatedDatetime()+"\t");
		System.out.println(dto.getModifiedDatetime()+"\t");
	
		
	}

	public static void testFindByName() throws Exception {
		CollegeDTO dto = new CollegeDTO();
		dto =  model.findByName("Sage University");
		System.out.print(dto.getId()+"\t");
		System.out.print(dto.getName()+"\t");
		System.out.print(dto.getAddress()+"\t");
		System.out.print(dto.getCity()+"\t");
		System.out.print(dto.getState()+"\t");
		System.out.print(dto.getPhoneNo()+"\t");
		System.out.print(dto.getCreatedBy()+"\t");
		System.out.print(dto.getModifiedBy()+"\t");
		System.out.print(dto.getCreatedDatetime()+"\t");
		System.out.println(dto.getModifiedDatetime()+"\t");
	
	}

	public static void testUpdate() throws Exception {
		CollegeDTO dto = new CollegeDTO();
		dto.setId(2);
		dto.setName("MB Khalsa College");
	     dto.setAddress("Rajmohhalla Square");
	     dto.setState("MadhyaPradesh");
	     dto.setCity("indore");
	     dto.setPhoneNo("9584578");
	     dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	  	model.update(dto);
		System.out.println("Updated!!!");
		
	}

	public static void testDelete() throws Exception {
		CollegeDTO dto = new CollegeDTO();
		dto.setId(3);
		model.delete(dto);
		System.out.println("Deleted!!!");
		
		
	}

	public static void testAdd() throws Exception {
		CollegeDTO dto = new CollegeDTO();
	     dto.setName("Holkar College");
	     dto.setAddress("Near Janjeervala Square");
	     dto.setState("MadhyaPradesh");
	     dto.setCity("indore");
	     dto.setPhoneNo("2542484");
	     dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	     long pk = model.add(dto); 
	     System.out.println("Inserted in id--- "+pk);
	}

}
