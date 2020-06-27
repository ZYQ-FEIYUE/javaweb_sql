package com.servlet;

import java.io.IOException;
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
@WebServlet("/DeviceQueryTest")
public class DeviceQuery extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DeviceQuery() {
		super();
	}
	
	@Override
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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Connection con;
		Statement sql;
		String email = request.getParameter("email").trim();	//email
		String uri = "jdbc:mysql://localhost/MakeFriend";
		try {
			con = DriverManager.getConnection(uri,"root","19981228");
			String condition = "select * from member where email = '"+email+"'";
			sql = con.prepareStatement(condition);
			ResultSet rSet = sql.executeQuery(condition);
			Gson gson = new Gson();
			if(rSet.next()) {		//找到邮箱
				String personJson = rSet.getString("person");
				Person person = gson.fromJson(personJson, Person.class);
				person.setStatus(true);
				String outJson = gson.toJson(person, Person.class);
//				out.println("login successfully!");
				out.print(outJson);
			}
			else {
//				out.println("can not register!");
				Person person = new Person();
				person.setStatus(false);
				String jsonStr = gson.toJson(person, Person.class);
				out.print(jsonStr);
			}
			out.flush();
			out.close();
			sql.close();
			con.close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
