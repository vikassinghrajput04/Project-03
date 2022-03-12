package in.co.raystech.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mchange.v2.codegen.bean.Property;

import in.co.raystech.dto.BaseDTO;
import in.co.raystech.dto.RoleDTO;
import in.co.raystech.dto.UserDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.model.ModelFactory;
import in.co.raystech.model.RoleModelInt;
import in.co.raystech.model.UserModelInt;
import in.co.raystech.utility.DataUtility;
import in.co.raystech.utility.DataValidator;
import in.co.raystech.utility.PropertyReader;
import in.co.raystech.utility.ServletUtility;



/**
 * login functionality controller. perform login operation
 * @author Vikas Singh
 *
 */

@WebServlet(urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";
	private static Logger log = Logger.getLogger(LoginCtl.class);

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}
		System.out.println(request.getParameter("login") + ".........." + request.getParameter("password"));

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "password"));
			pass = false;
		}
		
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();
		System.out.println(request.getParameter("login"));
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		dto.setPassword(DataUtility.getString(request.getParameter("password")));
		return dto;

	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	
		String op = request.getParameter("operation");
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		RoleModelInt model1 = ModelFactory.getInstance().getRoleModel();
		HttpSession session = request.getSession(true);
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_LOG_OUT.equals(op)) {
			session = request.getSession();
			session.invalidate();
			ServletUtility.setSuccessMessage("Logout Successfully", request);
			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
			return;
		}
		if (id > 0) {
			UserDTO dto=null;
			try {
				try {
					dto = model.findByPK(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String op = request.getParameter("operation");
		System.out.println(";;;"+op);
		HttpSession session = request.getSession(true);
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		RoleModelInt model1 = ModelFactory.getInstance().getRoleModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				System.out.println(dto.getLogin() + "///////" + dto.getPassword()+"kkkkk"+userModel);
				dto = userModel.authenticate(dto.getLogin(), dto.getPassword());
				
				if (dto != null) {
					session.setAttribute("user", dto);
					long roleId = dto.getRoleId();
					RoleDTO rdto = model1.findByPK(roleId);
					if (rdto.getName() != null) {
						session.setAttribute("role", rdto.getName());
					}
					String uri = (String) request.getParameter("uri");
					
					if (uri == null || "null".equalsIgnoreCase(uri)) {
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {
						System.out.println();
						if (rdto.getId() == 1) {
							ServletUtility.redirect(uri, request, response);
						} else {
							ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						}

						return;
					}

				} else {
					dto = (UserDTO) populateDTO(request);
					ServletUtility.setDto(dto, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
