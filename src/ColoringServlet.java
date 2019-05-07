

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ColoringServlet
 */

public class ColoringServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String[] keywords = {
			"abstract", "asssert", 
			"boolean", "break", "byte",
			"case", "catch", "char", "class", "continue",
			"default", "do", "double",
			"else", "enum", "extends",
			"final", "finally", "float",
			"for", "if", "implements", "import", "instanceof", "int", "interface",
			"long",
			"native", "new", "null",
			"package", "public", "private", "protected", 
			"return", 
			"short", "static", "strictfp", "super", "swtich", "synchronized",
			"this", "throw", "throws", "transient", "try",
			"void", "volatile", "while"};
	
	// Literals include boolean literals, numerical literals, floating point literals, 
	// character literals, string literals etc
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ColoringServlet() {
        super();
    }

    public String readACharLiteral(String s, int index, int lens) {
    	// read until it ends with ' but not with \' 
    	String curshortstr = "'";
    	int curindex = index + 1;
    	while (curindex < lens) {
    		char curchar = s.charAt(curindex);
    		if (curchar == '\'') {
    			// check whether the previous string is \
    			if (s.charAt(curindex - 1) != '\\'){
    				// it should be an end
    				curshortstr += curchar;
    				index++;
    				break;
    			}
    			else { 
    				curshortstr += curchar;
    				index++;
    				continue;
    			}
    		}
    		else {
    			curshortstr += curchar;
    			index++;
    		}
    	}
    	return curshortstr;
    }
    
    public String readAStrLiteral(String s, int index, int lens) {
    	// read until it ends with "  but not with \" 
    	String curshortstr = "\"";
    	int curindex = index + 1;
    	while (curindex < lens) {
    		char curchar = s.charAt(curindex);
    		if (curchar == '\"') {
    			// check whether the previous string is \
    			if (s.charAt(curindex - 1) != '\\'){
    				// it should be an end
    				curshortstr += curchar;
    				break;
    			}
    			else { 
    				curshortstr += curchar;
    				continue;
    			}
    		}
    		else {
    			curshortstr += curchar;
    			index++;
    		}
    	}
    	return curshortstr;
    }
    
    public String readAVarOrKeywordOrBooleanLiteral(String s, int index, int lens) {
    	// Assume variables begin with a letter
    	// true, false are literals
    	// keywords
    	// or other 
    	// read until meeting a non letter, digit, and _
    	String curshortstr = "";
    	char curchar = s.charAt(index);
    	while ((index < lens) && (Character.isLetterOrDigit(curchar)) || (curchar=='_')) {
    		curshortstr += curchar;
    		index++;
    		if (index < lens)
    			curchar = s.charAt(index);
    	}
    	return curshortstr;
    }
    
    public String readANumLiteral(String s, int index, int lens) {
    	// all numbers, ending with a f or F or L
    	// all numbers, begin with - or a digit
    	// doubles, in the middle there is a e, and ending with d
    	
    	String curshortstr = "";
    	char curchar = s.charAt(index);
    	boolean eflag = true;
    	while ((index < lens) && (Character.isDigit(curchar)) || (curchar=='e')) {
    		if (curchar=='e') {
    			// if already eflag, then something is wrong
    			eflag = true;
    		}
    		curshortstr += curchar;
    		index++;
    		if (index < lens)
    			curchar = s.charAt(index);
    	}
    	// if the following letter is F, f, L, or d (check whether eflag)
    	if (index < lens) {
    		curchar = s.charAt(index);
    		if ((curchar == 'F') || (curchar == 'f') || (curchar == 'L') || (curchar == 'd')) {
    			if ((curchar == 'd') && eflag)
    				curshortstr += curchar;
    			else if ((curchar == 'd') && !eflag) {
    				System.out.println("wrong format of a number");
    			}
    			else {
    				curshortstr += curchar;
    			}
    		}
    	}
    	return curshortstr;
    }
    
    public String readAComment(String s, int index, int lens) {
    	String curshortstr = "";
    	char curchar = s.charAt(index);
    	while ((index < lens) && (curchar!='\n')){
    		curshortstr += curchar;
    		index++;
    		if (index < lens)
    			curchar = s.charAt(index);
    	}
    	return curshortstr;
    }
    
  
    public String wrapNumber(String shortstr) {
    	return "<span class='blue'>" + shortstr + "</span>";
    }
    
    public String wrapVarKeywordBoolean(String shortstr) {
    	if (shortstr.equals("true") || shortstr.equals("false")) {
    		return "<span class='blue'>" + shortstr + "</span>";
    	}
    	for (int i=0; i<keywords.length; i++) {
    		if (shortstr.equals(keywords[i])) {
    			return "<span class='navy'>" + shortstr + "</span>";
    		}
    	}    			
    	return shortstr;
    }
    
    public String wrapCharLiteral(String shortstr) {
    	return "<span class='blue'>" + shortstr + "</span>";
    }
    
    public String wrapStrLiteral(String shortstr) {
    	// modify this method to make the string literal showing blue
    	return shortstr;
    }
    
    public String wrapComment(String shortstr) {
    	// modify this method to make the comment showing green
    	return shortstr;
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		// handle coloring 
		// get the content string 
		//doGet(request, response);
		response.setContentType("text/html");
		// get parameter's value
		String javaprogramstr = request.getParameter("javaprogramstr"); 
		PrintWriter out = response.getWriter();
		System.out.println("javaprogramstr = " + javaprogramstr);
		 
		 out.println("<HTML>");
		 out.println("<head>");
		 
		 out.println("<style>");
		 out.println(".blue{color:blue;} ");
		 out.println(".navy{color:navy;} ");
		 out.println(".green{color:green;} ");
		 out.println("</style>");
		 out.println("</head>");
		 out.println("<body>");
		 out.println("<h1>Colored Java Program</h1>");
		 String newJavaProgramStr = "";
		 // read a char, and decide what to do
		 if (javaprogramstr != null) {
			 int lengthstr = javaprogramstr.length();
			 int curindex = 0;
			 while (curindex < lengthstr) {
				 char curchar = javaprogramstr.charAt(curindex);
				 if (curchar == ' ') {
					 newJavaProgramStr += ' ';
					 curindex++;
					 continue;
				 }
				 if ((curchar=='/') &&
				 	(curindex + 1 < lengthstr) && (javaprogramstr.charAt(curindex+1)=='/')){
				    // it is a comment
					 String curstr = readAComment(javaprogramstr, curindex, lengthstr);
					 // wrap current str to 
					 String newcurstr = wrapComment(curstr);
					 System.out.println("read a string " + newcurstr);
					 newJavaProgramStr += newcurstr;
					 curindex += curstr.length();
					 continue;					 
				 }
				 if ((curchar=='-') || (Character.isDigit(curchar))){
					 // it could be a number
					 String curstr = readANumLiteral(javaprogramstr, curindex, lengthstr);
					 // wrap current str to 
					 String newcurstr = wrapNumber(curstr);
					 System.out.println("read a string " + newcurstr);
					 newJavaProgramStr += newcurstr;
					 curindex += curstr.length();
					 continue;
				 }
				 if (Character.isLetter(curchar)) {
					 String curstr = readAVarOrKeywordOrBooleanLiteral(javaprogramstr, curindex, lengthstr);
					 String newcurstr = wrapVarKeywordBoolean(curstr);
					 System.out.println("read a string " + newcurstr);
					 newJavaProgramStr += newcurstr;
					 curindex += curstr.length();
					 continue;
				 }
				 // character literal
				 if (curchar == '\'') {
					 String curstr = readACharLiteral(javaprogramstr, curindex, lengthstr);
					 String newcurstr = wrapCharLiteral(curstr);
					 System.out.println("read a string " + newcurstr);
					 newJavaProgramStr += newcurstr;
					 curindex += curstr.length();
					 continue;
				 } 
				 // string literal
				 if (curchar == '\"') {
					 String curstr = readAStrLiteral(javaprogramstr, curindex, lengthstr);
					 String newcurstr = wrapStrLiteral(curstr);
					 System.out.println("read a string " + newcurstr);
					 newJavaProgramStr += newcurstr;
					 curindex += curstr.length();
					 continue;
				 }
				 // all other cases, ] }, {, [ operators etc
				 if (curchar == '\n')
					 newJavaProgramStr += "<br>";
				 else 
					 newJavaProgramStr += curchar;
				 curindex++;
			 }
		 }
		 System.out.println(newJavaProgramStr);
		 out.println(newJavaProgramStr);
		 out.println("</body></html>");
		 out.close();
	}

}
