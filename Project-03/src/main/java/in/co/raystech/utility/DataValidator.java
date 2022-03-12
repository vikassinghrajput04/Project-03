package in.co.raystech.utility;

import java.text.ParseException;
import java.util.Date;



/**
 * DataValidator class is used to validate yhe data enterd by user
 * @author Vikas Singh
 *
 */
public class DataValidator 
{
	 /**
     * checks if value is Name
     * 
     */
    public static boolean isName(String val){
    	
    //	String namereg = "^[a-zA-Z]+$";
   // 	String namereg = "^[a-zA-Z_-]+$";
   	String namereg = "^[^-\\s][\\p{L} .']+$";
    	
    //	     System.out.println(val);
    			if (DataValidator.isNotNull(val)) {
    				boolean check = val.matches(namereg);
    	//			System.out.println(check);
						return check;
    				}else
    				{	
    					return false;
					}	
    		}
	
	/**
	 * Checks if value of Password is in between 8 and 12 characters
	 * 
	 * 
	 */
	public static boolean isPasswordLength(String val) {

		if (isNotNull(val) && val.length() >= 8 && val.length() <= 12) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/*public static boolean isValidAge(String val)
	{
		
		boolean pass = false;
		if (isDate(val)) {
			Date cdate = new Date();
			try {
				Date userdate = DataUtility.formatter.parse(val);
				int age = cdate.getYear()-userdate.getYear();
				System.out.println("final age  "+age);
				if(age>=18){
					pass=true;
				}
			} catch (ParseException e) {
				
			}
		}
		
		return pass;
	}*/
	
	public static boolean isPassword(String pass) { // my method created
        
		System.out.println("validate pass");
		String passreg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
		//String passreg="^[0-9a-zA-Z]{5}$";
		//String spass = pass.trim();
		//int checkIndex = spass.indexOf(" ");
                                //checkIndex==-1
		if (isNotNull(pass) && pass.matches(passreg) ) {
			System.out.println("true");
			return true;
		}

		else {
			return false;
		}
	}
	
	/**
	 * check if value is Roll no.
	 *
	 * @param val the val
	 * @return true, if is roll no
	 */
	public static boolean isRollNo(String val) {

		String roll = "^[0-9]{4}[A-Z]{2}[0-9]{3}$";

		if (DataValidator.isNotNull(roll)) {
			boolean check = val.matches(roll);
			return check;
		} else {
			return false;
		}
	}
	
	/**
	 * Ckeck if value is Null
	 *
	 */
 
	public static boolean isNull(String val)
	{
		if(val==null || val.trim().length()==0)
		{
			return true;
		}else
		{
			return false;
		}
	}
	/**
	 * check if value is Not Null
	 * 
	 */
     
	public static boolean isNotNull(String val)
	{
		return !isNull(val);
	}
	/**
	 * check if an value is an Integer
	 * 
	 */
	 public static boolean isInteger(String val)
	 {
		 if(isNotNull(val))
		 {
			 try {
				 int i = Integer.parseInt(val);
				 return true;
			} catch (Exception e) {
				return false;
			}
		 }else
		 {
			 return false;
		 }
	 }
	 /**
		 * check if an value is an Long
		 * 
		 */
	 public static boolean isLong(String val)
	 {     System.out.println("in datavalidator..........."+val);
		 if(isNotNull(val))
		 {   
			 try {
				 System.out.println("method is start....."+val);
			      long l = Long.parseLong(val);
				 System.out.println("-------->in datavalidator"+val+"----"+l);
				 return true;
			} catch (Exception e) {
				return false;
			}
		 }else
		 {
			 return false;
		 }
	 }
		 
		 
		 
		 public static boolean isValidAge(String val)
			{
				
				boolean pass = false;
				if (isDate(val)) {
					Date cdate = new Date();
					try {
						Date userdate = DataUtility.formatter.parse(val);
						int age = cdate.getYear()-userdate.getYear();
						System.out.println("final age  "+age);
						if(age>=18){
							pass=true;
						}
					} catch (ParseException e) {
						
					}
				}
				
				return pass;
			}
	 /**
	  * Check if value is valid EmailId
	  *
	  */
	 public static boolean isEmail(String val)
	 {
		 String emailregex= "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	  
		 if(isNotNull(val))
		 {
			 try {
				 return val.matches(emailregex);
			} catch (Exception e) {
				return false;
			}	 
		 }else
		 {
			 return false;
		 } 
	 }
	 /**
	  * check if value is date
	  *
	  */
	   public static boolean isDate(String val)
	   {
		    Date d=null;
//		    String s = val;
//			s = s.replace("-", "/");
		    if(isNotNull(val))
		    {
		    d =	DataUtility.getDate(val);
		    }
		    return d!=null;
	   }
	   
	   /**
		 * Checks if value is valid Phone No.
		 * 
		 *
		 */
		public static boolean isPhoneNo(String val) {

			String phonereg = "^[6-9][0-9]{9}$";
//			String phonereg = "^[6-9]{10}$";

			if (isNotNull(val)) {
				try {
					return val.matches(phonereg);
				} catch (NumberFormatException e) {
					return false;
				}

			} else {
				return false;
			}
		}

		/**
		 * Checks if value of Mobile No is 10
		 * 
		 * 
		 */
		public static boolean isPhoneLength(String val) {

			if (isNotNull(val) && val.length() == 10) {
				return true;
			} else {
				return false;
			}
		}
	   
	   
	 
	 /**
	  * Test Above Methods
	  
	  */
	public static void main(String[] args) 
	{
//	  System.out.println(isNull("ssd"));
//	  System.out.println(isNotNull(""));//dought
//	  System.out.println(isInteger("2147483649"));
//	  System.out.println(isLong("50.5"));
//	  System.out.println(isEmail("rah@g.com"));
//	  System.out.println(isDate("18/11/1989"));
	  System.out.println(isName("Ankur Agrawal"));
	}
	   
}
