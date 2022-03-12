package in.co.raystech.dto;
/**
 * RoleDTO having final attributes likes admin,student,college_school,kiosk
 * and attributes name,description
 * 
 * @author Vikas Singh
 */

public class RoleDTO extends BaseDTO {

	
	private static final long serialVersionUID = 1L;
	
	public static int ADMIN;
	public static int STUDENT;
	public static int COLLEGE_SCHOOL;
	public static int KIOSK;
	private String name;
	private String description;
	
	public RoleDTO() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getKey() {
		return id+"";
	}

	public String getValue() {
		return name+"";
	}
	
	

}
