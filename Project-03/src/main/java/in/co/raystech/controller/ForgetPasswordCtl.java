package in.co.raystech.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.raystech.dto.BaseDTO;
import in.co.raystech.dto.UserDTO;
import in.co.raystech.exception.RecordNotFoundException;
import in.co.raystech.model.ModelFactory;
import in.co.raystech.model.UserModelInt;
import in.co.raystech.utility.DataUtility;
import in.co.raystech.utility.DataValidator;
import in.co.raystech.utility.PropertyReader;
import in.co.raystech.utility.ServletUtility;


/**
 * forget password ctl.To perform password send in email
 * @author Vikas Singh
 *
 */
@WebServlet(urlPatterns = { "/ForgetPasswordCtl" })
public class ForgetPasswordCtl extends BaseCtl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ForgetPasswordCtl.class);

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "login"));
			pass = false;
		}
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		populateBean(dto,request);
		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("do get method started");
		System.out.println("forget password......doget");
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("do get method started");
		String op = request.getParameter("operation");
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		UserDTO dto = (UserDTO) populateDTO(request);
		if (OP_GO.equalsIgnoreCase(op)) {
			try {
				System.out.println("forget password......"+dto.getLogin()+";;;"+userModel);
				boolean flag=userModel.forgetPassword(dto.getLogin());
				System.out.println("in post method"+flag);
				ServletUtility.setSuccessMessage("password has been send to your login id", request);
			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
				log.error(e);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			ServletUtility.forward(getView(), request, response);
		}

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}
