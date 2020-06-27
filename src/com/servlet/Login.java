package com.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class LoginTest
 */
@WebServlet("/LoginTest")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.setContentType("text/html;charset=utf-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		Connection con;
		Statement sql;
		String email = request.getParameter("email").trim();
		String password = request.getParameter("password").trim(); 
		String uri = "jdbc:mysql://localhost/MakeFriend";
		try {
			con = DriverManager.getConnection(uri,"root","19981228");
			String condition = "select * from member where email = '"+email+"' and password = '"+password+"'";
			sql = con.prepareStatement(condition);
			ResultSet rSet = sql.executeQuery(condition);
			Gson gson = new Gson();
			if(rSet.next()) {
				String name = rSet.getString("name");
				String personJson = rSet.getString("person");
				Person person = gson.fromJson(personJson, Person.class);
				person.setStatus(true);
				person.setName(name);
				String outJson = gson.toJson(person, Person.class);
//				out.println("login successfully!");
				out.print(outJson);
				
			}
			else {
				Person person = new Person();
				person.setStatus(false);
				String outJson = gson.toJson(person, Person.class); 
				out.print(outJson);
//		        response.setCharacterEncoding("utf-8");
//		        response.setHeader("content-type","text/html;charset=UTF-8");
//		        PrintWriter writer = response.getWriter();
//		        String requestURL = request.getRequestURL().toString();
//		        String requestURI =  request.getRequestURI();
//		        String queryString = request.getQueryString();
//		        String pathInfo = request.getPathInfo();
//		        String remoteAddr = request.getRemoteAddr();
//		        int remotePort = request.getRemotePort();
//		        String remoteHost = request.getRemoteHost();
//		        String localAddr = request.getLocalAddr();
//		        String localName = request.getLocalName();
//		        int localPort = request.getLocalPort();
//		        writer.println("requestURL:"+requestURL);
//		        writer.println("<br/>");
//		        writer.println("requestURI:"+requestURI);
//		        writer.println("<br/>");
//		        writer.println("queryString:"+queryString);
//		        writer.println("<br/>");
//		        writer.println("pathInfo:"+pathInfo);
//		        writer.println("<br/>");
//		        writer.println("remoteAddr:"+remoteAddr);
//		        writer.println("<br/>");
//		        writer.println("remoteHost:"+remoteHost);
//		        writer.println("<br/>");
//		        writer.println("localAddr:"+localAddr);
//		        writer.println("<br/>");
//		        writer.println("localName:"+localName);
//		        writer.println("<br/>");
//		        writer.println("remotePort:"+remotePort);
//		        writer.println("<br/>");
//		        writer.println("localPort:"+localPort);
//		        writer.flush();
//		        writer.close();
			}
			out.flush();
			out.close();
			con.close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
