package in.co.raystech.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.rule.Mode;

import in.co.raystech.dto.BaseDTO;
import in.co.raystech.dto.CollegeDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.exception.DuplicateRecordException;
import in.co.raystech.model.CollegeModelInt;
import in.co.raystech.model.ModelFactory;
import in.co.raystech.utility.DataUtility;
import in.co.raystech.utility.DataValidator;
import in.co.raystech.utility.PropertyReader;
import in.co.raystech.utility.ServletUtility;


/**
 * college functionality ctl. To perform add,delete ,update operation
 * @author Vikas Singh
 * 
 */


@WebServlet(urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CollegeCtl.class);

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Name must contain only  Characters ");
			pass = false;
			}
		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("city"))) {
			request.setAttribute("city", "City must contain only  Characters ");
			pass = false;
			}
		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("state"))) {
			request.setAttribute("state", "State must contain only  Characters ");
			pass = false;
			}
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		}else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "MobileNo must contain only 10 digit numbers starting with 6-9 ");
			pass = false;
			}
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		CollegeDTO dto = new CollegeDTO();
	
		
		dto.setName(request.getParameter("name"));
		dto.setCity(request.getParameter("city"));
		dto.setAddress(request.getParameter("address"));
		dto.setState(request.getParameter("state"));
		dto.setPhoneNo(request.getParameter("mobileNo"));
		
		populateBean(dto,request);
		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
		if (id > 0 || op != null) {
			CollegeDTO dto;
			try {
			  dto=model.findByPk(id);
			  ServletUtility.setDto(dto, request);
				
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       String op=request.getParameter("operation");
       long id=DataUtility.getLong(request.getParameter("id"));
       CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
       if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
    	   CollegeDTO dto = (CollegeDTO) populateDTO(request);
			System.out.println("...===+" + id + ">>>>>>..." + dto);
			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);
					
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

				} else {
					System.out.println("college add" + dto + "id...." + id);
					 model.add(dto);
					 ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Data succefully saved", request);
				}
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("College already exists", request);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			CollegeDTO dto = (CollegeDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.forward(ORSView.COLLEGE_LIST_CTL, request, response);
				return;
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}
