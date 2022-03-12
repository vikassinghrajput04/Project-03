package in.co.raystech.dto;



/**
 * marksheet JavaDto encapsulates marksheet attributes
 * @author Vikas Singh
 *
 */

public class MarksheetDTO extends BaseDTO{
	
	private String rollNo;
	private long studentId;
	private String name;
	private int physics;
	private int chemistry;
	private int maths;
	public String getRollNo() {
	return rollNo;
}

public void setRollNo(String rollNo) {
	this.rollNo = rollNo;
}

public Long getStudentId() {
	return studentId;
}

public void setStudentId(Long studentId) {
	this.studentId = studentId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Integer getPhysics() {
	return physics;
}

public void setPhysics(Integer physics) {
	this.physics = physics;
}

public Integer getChemistry() {
	return chemistry;
}

public void setChemistry(Integer chemistry) {
	this.chemistry = chemistry;
}

public Integer getMaths() {
	return maths;
}

public void setMaths(Integer maths) {
	this.maths = maths;
}

	public String getKey() {
		return id+"";
	}

	public String getValue() {
		return rollNo+"";
	}

}
