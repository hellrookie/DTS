package hellpoet.dts;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeploySolution extends HttpServlet {
	private static final long serialVersionUID = 2036739917108327606L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String copyClassesMsg = "";
		String writeConfigMsg = "";
		
		String configFilePath = request.getParameter("ConfigFilePath");
		String servletName = request.getParameter("ServletName");
		String servletUrlPattern = request.getParameter("ServletUrlPattern");
		String servletClass = request.getParameter("ServletClass");
		String classesSrcPath = request.getParameter("ClassesSrcPath");
		String classesDestPath = request.getParameter("ClassesDestPath");
		boolean overwrite = (request.getParameter("OverwriteClasses") != null);
		
		CopyClassHandler copyClassHandler = CopyClassHandler.getInstance();
		ConfigEditHandler configEditHandler = ConfigEditHandler.getInstance();
		try {
			copyClassHandler.copyClasses(classesSrcPath, classesDestPath, overwrite);
			copyClassesMsg = "Success";
		} catch (Exception e) {
			copyClassesMsg = e.getMessage();
		}
		
		try{
			configEditHandler.WriteConfigFile(configFilePath, servletName, servletClass, servletUrlPattern);
			writeConfigMsg = "Success";
		} catch (Exception e) {
			writeConfigMsg = e.getMessage();
		}
		
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
		writer.println("<body>");
		writer.println("<body>");
		writer.println("Copy classes result: " + copyClassesMsg);
		writer.println("Edit config file result: " + writeConfigMsg);
		writer.println("</body>");
		writer.println("</html>");
	}
}