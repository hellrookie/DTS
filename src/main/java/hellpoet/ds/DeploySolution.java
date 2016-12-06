package hellpoet.ds;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeploySolution extends HttpServlet {
	private static final long serialVersionUID = 2036739917108327606L;

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		
		String copyClassesMsg = "";
		String writeConfigMsg = "";
		
		String servletName = request.getParameter("ServletName");
		String servletUrlPattern = request.getParameter("ServletUriPattern");
		String servletClass = request.getParameter("ServletClass");
		String classesSrcPath = request.getParameter("ClassesSrcPath");
		String classesDestPath = request.getParameter("ClassesDestPath");
		boolean overwrite = Boolean.getBoolean(request.getParameter("OverwriteClasses"));
		
		CopyClassHandler copyClassHandler = CopyClassHandler.getInstance();
		try {
			copyClassHandler.copyClasses(classesSrcPath, classesDestPath, overwrite);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			copyClassesMsg = e.getMessage();
		}
	}
}