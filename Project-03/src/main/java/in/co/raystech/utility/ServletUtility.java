package in.co.raystech.utility;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.raystech.controller.BaseCtl;
import in.co.raystech.controller.ORSView;
import in.co.raystech.dto.BaseDTO;



/**
 * ServletUtility provides the servlet util services 
 * @author Vikas Singh
 *
 */
public class ServletUtility {
	 /**
     * Forward to given JSP/Servlet
     *
     * 
     */
    public static void forward(String page, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    /**
     * Redirect to given JSP/Servlet
     *
     * 
     */
    public static void redirect(String page, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(page);
    }

    /**
     * Redirect to Application Error Handler Page
     *
     *
     */
    public static void handleException(Exception e, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("exception", e);
        response.sendRedirect(ORSView.ERROR_CTL);

    }

    /**
     * Gets error message from request
     *
     * 
     */
    public static String getErrorMessage(String property,
            HttpServletRequest request) {
        String val = (String) request.getAttribute(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Gets a message from request
     *
     * 
     */
    public static String getMessage(String property, HttpServletRequest request) {
        String val = (String) request.getAttribute(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Sets error message to request
     *
     *
     */
    public static void setErrorMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_ERROR, msg);
    }

    /**
     * Gets error message from request
     *
     * 
     */
    public static String getErrorMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
	 * Sets success message to request
	 * 
	 * 
	 * @param msg reads
	 * @param request reads
	 */ 
	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
	}

	/**
	 * Gets success message from request
	 * 
	 * 
	 * @param request reads
	 * @return values String
	 */
	public static String getSuccessMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}
    /**
     * Sets default DTO to request
     *
     *
     */
    public static void setDto(BaseDTO dto, HttpServletRequest request) {
        request.setAttribute("dto", dto);
    }

    /**
     * Gets default DTO from request
     *
     *
     */

    public static BaseDTO getDto(HttpServletRequest request) {
        return (BaseDTO) request.getAttribute("dto");
    }

    /**
     * Get request parameter to display. If value is null then return empty
     * string
     *
     * 
     */

    public static String getParameter(String property,
            HttpServletRequest request) {
        String val = (String) request.getParameter(property);
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }

    /**
     * Sets default List to request
     *
     * 
     */
    public static void setList(List list, HttpServletRequest request) {
        request.setAttribute("list", list);
    }

    /**
     * Gets default list from request
     *
     *
     */
    public static List getList(HttpServletRequest request) {
        return (List) request.getAttribute("list");
    }

    /**
     * Sets Page Number for List pages
     *
     * 
     */
    public static void setPageNo(int pageNo, HttpServletRequest request) {
        request.setAttribute("pageNo", pageNo);
    }

    /**
     * Gets Page Number for List pages
     *
     *
     */
    public static int getPageNo(HttpServletRequest request) {
    	 int a=  (Integer) request.getAttribute("pageNo");
         return a;
    }

    /**
     * Sets Page Size for list pages
     *
     * 
     */
    public static void setPageSize(int pageSize, HttpServletRequest request) {
        request.setAttribute("pageSize", pageSize);
    }

    /**
     * Gets Page Size for List pages
     *
     * 
     */
    public static int getPageSize(HttpServletRequest request) {
        int pageSize= (Integer) request.getAttribute("pageSize");
        return pageSize;
     }
   
}
