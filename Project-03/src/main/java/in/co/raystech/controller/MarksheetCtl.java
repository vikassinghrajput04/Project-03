package in.co.raystech.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.raystech.dto.BaseDTO;
import in.co.raystech.dto.MarksheetDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.model.MarksheetModelInt;
import in.co.raystech.model.ModelFactory;
import in.co.raystech.model.StudentModelInt;
import in.co.raystech.utility.DataUtility;
import in.co.raystech.utility.DataValidator;
import in.co.raystech.utility.PropertyReader;
import in.co.raystech.utility.ServletUtility;


/**
 * marksheeet functionality controller.to perform add,delete and update operation
 * @author Vikas Singh
 *
 */
@WebServlet(urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	protected void preload(HttpServletRequest request) {
		StudentModelInt model = ModelFactory.getInstance().getStudentModel();
		try {
			List li = model.list();
			request.setAttribute("studenList", li);
			System.out.println("list size student ----"+li.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("marksheet validate bean start");
		System.out.println("marks ctl");
		boolean pass = true;
		String id = request.getParameter("studentId");
		System.out.println("kjkljk" + id);
		if (DataValidator.isNull(request.getParameter("roll"))) {
			request.setAttribute("roll", PropertyReader.getValue("error.require", "Roll No"));
			pass = false;
		} else if (!DataValidator.isRollNo(request.getParameter("roll"))) {
			request.setAttribute("roll", "Please Enter Valid Roll No");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.require", "physics"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.require", "maths "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "chemistry"));
			pass = false;
		}

		if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Physics Marks"));
			pass = false;

		}
		if (DataUtility.getInt(request.getParameter("physics")) > 100
				|| DataUtility.getInt(request.getParameter("physics")) < 0) {

			request.setAttribute("physics", "marks must be less than 100 and greater than 0");
			pass = false;
		}
		if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "chemistry marks"));
		}
		if (DataUtility.getInt(request.getParameter("chemistry")) > 100
				|| DataUtility.getInt(request.getParameter("chemistry")) < 0) {

			request.setAttribute("chemistry", "marks less than less than 100 and greater than 0");
			pass = false;
		}
		if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "maths marks"));
			pass = false;
		}
		if (DataUtility.getInt(request.getParameter("maths")) > 100
				|| DataUtility.getInt(request.getParameter("maths")) < 0) {

			request.setAttribute("maths", "marks must be less than 100 and greater than 0");
			pass = false;
		}
		log.debug("marksheet validate bean end");
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("marksheet populate bean start");
		
		MarksheetDTO dto = new MarksheetDTO();
		String id = request.getParameter("studentId");
		String id1=id.trim();
		dto.setRollNo(request.getParameter("roll"));
		
		dto.setStudentId(DataUtility.getLong(id1));
		System.out.println("...........--->"+dto.getStudentId()+"......."+dto.getRollNo());
		dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		System.out.println("kkkkkkkkkkk"+dto.getPhysics());
		dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		dto.setMaths(DataUtility.getInt(request.getParameter("maths")));
		populateBean(dto,request);
		log.debug("marksheet populate bean end");
		return dto;

	}

	/**
	 * Display Logics inside this method
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("marksheet ctl doget  start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		if (id > 0) {
			MarksheetDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("marksheet ctl doget  end");
	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("marksheet ctl dopost  start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("--------"+id);
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {
					System.out.println("in post method.........." + dto.getRollNo()+"...."+dto.getStudentId());
					
					model.add(dto);
					//System.out.println("hmmmmmm" + pk);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
					ServletUtility.setDto(dto, request);
				}
				

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				System.out.println("in catch");
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("marksheet ctl dopost  end");
	}

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}

}
