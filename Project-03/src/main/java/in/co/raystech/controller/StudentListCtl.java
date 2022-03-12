package in.co.raystech.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.rule.Mode;

import in.co.raystech.dto.BaseDTO;
import in.co.raystech.dto.StudentDTO;
import in.co.raystech.exception.ApplicationException;
import in.co.raystech.model.CollegeModelInt;
import in.co.raystech.model.ModelFactory;
import in.co.raystech.model.StudentModelInt;
import in.co.raystech.utility.DataUtility;
import in.co.raystech.utility.PropertyReader;
import in.co.raystech.utility.ServletUtility;


/**
 * student functionality controller,to perform list and search operation
 * @author Vikas Singh
 *
 */
@ WebServlet(name="StudentListCtl",urlPatterns={"/ctl/StudentListCtl"})
public class StudentListCtl extends BaseCtl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(StudentListCtl.class);
    
    protected void preload(HttpServletRequest request) {
		CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
		try {
			List list=model.list();
			request.setAttribute("collegeList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        StudentDTO dto = new StudentDTO();

        dto.setFirstName(DataUtility.getString(request
                .getParameter("firstName")));
        dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
        dto.setEmail(DataUtility.getString(request.getParameter("email")));
        dto.setCollegeId(DataUtility.getInt(request.getParameter("collegeId")));
        populateBean(dto,request);
       System.out.println("dto--"+dto.getCollegeId()+"....."+dto.getFirstName()+"''"+dto.getEmail());
        return dto;
    }

    /**
     * Contains Display logics
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("StudentListCtl doGet Start");
        List list = null;
        List next=null;
        int pageNo = 1;

        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        StudentDTO dto = (StudentDTO) populateDTO(request);

        String op = DataUtility.getString(request.getParameter("operation"));

        StudentModelInt model =ModelFactory.getInstance().getStudentModel();
        try {
            list = model.search(dto, pageNo, pageSize);
            next = model.search(dto, pageNo+1, pageSize);
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }if(next==null||next.size()==0){
				request.setAttribute("nextListSize", 0);
				
			}else{
				request.setAttribute("nextListSize", next.size());
			}
            ServletUtility.setList(list, request);

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        } catch (Exception e) {
			e.printStackTrace();
		}
        log.debug("StudentListCtl doGet End");
    }

    /**
     * Contains Submit logics
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("StudentListCtl doPost Start");
        List list = null;
        List next=null;
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;

        StudentDTO dto = (StudentDTO) populateDTO(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        StudentModelInt model =ModelFactory.getInstance().getStudentModel();
         String[] ids=request.getParameterValues("ids");
        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
                    || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            }else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
				return;
			}else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					StudentDTO deletebean = new StudentDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
            
			 if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;
			}
			 dto = (StudentDTO) populateDTO(request);
            list = model.search(dto, pageNo, pageSize);
            ServletUtility.setDto(dto, request);
            next = model.search(dto, pageNo+1, pageSize);
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found ", request);
            }if(next==null||next.size()==0){
				request.setAttribute("nextListSize", 0);
				/* ServletUtility.setErrorMessage("No record found ", request); */
				
				
			}else{
				request.setAttribute("nextListSize", next.size());
			}
            
            ServletUtility.setList(list, request);

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        } catch (Exception e) {
			e.printStackTrace();
		}
        log.debug("StudentListCtl doGet End");
    }

    @Override
    protected String getView() {
        return ORSView.STUDENT_LIST_VIEW;
    }
}

