package in.co.raystech.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.raystech.dto.BaseDTO;
import in.co.raystech.dto.TimetableDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.model.CourseModelInt;
import in.co.raystech.model.ModelFactory;
import in.co.raystech.model.SubjectModelInt;
import in.co.raystech.model.TimetableModelInt;
import in.co.raystech.utility.DataUtility;
import in.co.raystech.utility.DataValidator;
import in.co.raystech.utility.PropertyReader;
import in.co.raystech.utility.ServletUtility;


/**
 * Timetable functionality controller. to perform add,delete and update
 * operation
 * 
 * @author Vikas Singh
 *
 */
@WebServlet(urlPatterns = { "/ctl/TimetableCtl" })
public class TimeTableCtl extends BaseCtl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TimeTableCtl.class);

	protected void preload(HttpServletRequest request) {
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		SubjectModelInt model1 = ModelFactory.getInstance().getSubjectModel();
		try {
			List l = model.list();
			List l1 = model1.list();
			request.setAttribute("courseList", l);
			request.setAttribute("subjectList", l1);
		} catch (Exception e) {
			log.error(e);
		}
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("time table validate start");
		boolean pass = true;
		String examDate = request.getParameter("examDate");
		System.out.println("pass-" + pass);
		System.out.println("kkkkkkk" + pass);
		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "course Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "subject Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("semesterId"))) {
			request.setAttribute("semesterId", PropertyReader.getValue("error.require", "semester"));
			pass = false;
		}

		if (DataValidator.isNull(examDate)) {
			request.setAttribute("examDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		} else if (!DataValidator.isDate(examDate)) {
			request.setAttribute("examDate", PropertyReader.getValue("error.date", "Exam Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("examId"))) {
			request.setAttribute("examId", PropertyReader.getValue("error.require", "exam Time"));
			pass = false;
		}
		log.debug("time table validate end");
		System.out.println("kjkj>>>>" + pass);
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("time table populate start");
		TimetableDTO dto = new TimetableDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCourseId(DataUtility.getInt(request.getParameter("courseId")));
		dto.setSemester(DataUtility.getString(request.getParameter("semesterId")));
		dto.setSubjectId(DataUtility.getInt(request.getParameter("subjectId")));
		dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		dto.setExamTime(DataUtility.getString(request.getParameter("examId")));
		populateBean(dto, request);
		log.debug("time table populate end");
		System.out.println("<<<>>>>>>++.." + dto);

		return dto;
	}

	/**
	 * Display Logics inside this method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("time table do get start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		TimetableModelInt model = ModelFactory.getInstance().getTimetableModel();
		if (id > 0 || op != null) {
			TimetableDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
		log.debug("time table doget end");
	}

	/**
	 * Submit logic inside it
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("method post..............");
		log.debug("time table dopost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		TimetableModelInt model = ModelFactory.getInstance().getTimetableModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			TimetableDTO dto = (TimetableDTO) populateDTO(request);
			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {
					long pk = model.add(dto);
					System.out.println("Record Inserted in id---- "+pk);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
			// ServletUtility.setBean(bean, request);
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			TimetableDTO dto = (TimetableDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("time table dopost end");
	}

	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}

}
