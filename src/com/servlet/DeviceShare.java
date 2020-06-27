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
@WebServlet("/ShareTest")
public class DeviceShare extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DeviceShare() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Connection con;
		Statement sql;
		String email = request.getParameter("email").trim();
		String uri = "jdbc:mysql://localhost/MakeFriend";
		try {
			con = DriverManager.getConnection(uri,"root","19981228");
			String condition = "select * from member where email = '"+email+"'";
			sql = con.prepareStatement(condition);
			ResultSet rSet = sql.executeQuery(condition);
			Person person = new Person();
			Gson gson = new Gson();
			if(rSet.next()) {	//邮箱存在
				person.setStatus(true);
			}
			else {
				person.setStatus(false);
			}
			String jsonStr = gson.toJson(person, Person.class);
			out.print(jsonStr);
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

}
