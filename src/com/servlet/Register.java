package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
/**
 * Servlet implementation class LoginTest
 */
@WebServlet("/RegisterTest")
public class Register extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Register() {
		super();
	}
//    public void init(ServletConfig config) throws ServletException {
//		// TODO Auto-generated method stub
//		super.init(config);
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	@Override
	public void init(ServletConfig config) throws ServletException {
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
//		response.setContentType("text/html;charset=utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Connection con;
		Statement sql;
		String name = request.getParameter("name").trim();
		String email = request.getParameter("email").trim();
		String password = request.getParameter("password").trim();
		String uri = "jdbc:mysql://localhost/MakeFriend";
		try {
			con = DriverManager.getConnection(uri,"root","19981228");
			String condition = "select * from member where email = '"+email+"'";
			String insert_con = "insert into member(name, email, password, person) values(?, ?, ?, ?)";
			sql = con.prepareStatement(condition);
			ResultSet rSet = sql.executeQuery(condition);
			Person person = new Person();
			Gson gson = new Gson();
			if(rSet.next()) {
//				out.println("can not register!");
				person.setStatus(false);
				String jsonStr = gson.toJson(person, Person.class);
				out.print(jsonStr);
			}
			else {
				person.setStatus(true);		//设置状态
				person.setName(name);		//设置名字
				String jsonStr = gson.toJson(person, Person.class);
				PreparedStatement ps_sql = con.prepareStatement(insert_con);
				ps_sql.setString(1, name);
				ps_sql.setString(2, email);
				ps_sql.setString(3, password);
				ps_sql.setString(4, jsonStr);
				int row = ps_sql.executeUpdate();
				if (row > 0) {
					out.print(jsonStr);
//					out.print("register successful!");
				}
				ps_sql.close();
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
