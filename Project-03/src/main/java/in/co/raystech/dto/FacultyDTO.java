package in.co.raystech.dto;

import java.util.Date;

/**
 * FacultyDTO having attributes likes firstName lastName gender dOB
 * qualification emailId mobileNo collegeId collegeName courseId courseName
 * subjectId subjectName * having setter getter of attributes
 * @author Vikas Singh
 * 
 */

public class FacultyDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String gender;
	private Date dOJ;
	private String qualification;
	private String emailId;
	private String mobileNo;
	private int collegeId;
	private String collegeName;
	private int courseId;
	private String courseName;
	private int subjectId;
	private String subjectName;

	public FacultyDTO() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	public Date getdOJ() {
		return dOJ;
	}

	public void setdOJ(Date dOJ) {
		this.dOJ = dOJ;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = (int) collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = (int) courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = (int) subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getKey() {
		return id+"";
	}

	public String getValue() {
		return firstName+"";
	}
}
