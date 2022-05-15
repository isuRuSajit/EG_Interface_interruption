package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Servlet implementation class InterruptionsAPI
 */
@WebServlet("/InterruptionsAPI")
public class InterruptionsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Interruption interruptionObj = new Interruption();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InterruptionsAPI() {

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String output = interruptionObj.insertInterruption(request.getParameter("intAriaCode"), 
				request.getParameter("intAriaName"),
				request.getParameter("intDate"), 
				request.getParameter("intTime"));
		response.getWriter().write(output);
	}

	
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params) {
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {
		}
		return map;
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		String output = interruptionObj.updateInterruption(paras.get("hidInterruptionIDSave").toString(), 
				paras.get("intAriaCode").toString(),
				paras.get("intAriaName").toString(), 
				paras.get("intDate").toString(),
				paras.get("intTime").toString());
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		String output = interruptionObj.deleteInterruption(paras.get("intID").toString());
		response.getWriter().write(output);
	}

}
