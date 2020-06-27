package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
@WebServlet("/DeviceMessageTest")
public class DeviceMessage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeviceMessage() {
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
		String person_encode = request.getParameter("person").trim();	//person
		String person_message = URLDecoder.decode(person_encode, "utf-8");
		String uri = "jdbc:mysql://localhost/MakeFriend";
		try {
			con = DriverManager.getConnection(uri,"root","19981228");
			String condition = "select * from member where email = '"+email+"'";
			String update_con = "update member set person = ? where email = ?";
			sql = con.prepareStatement(condition);
			ResultSet rSet = sql.executeQuery(condition);
			Person person = new Person();
			Gson gson = new Gson();
			if(rSet.next()) {		//找到邮箱
				PreparedStatement ps_sql = con.prepareStatement(update_con);
				ps_sql.setString(1, person_message);
				ps_sql.setString(2, email);		//通过邮箱更新
				int row = ps_sql.executeUpdate();
				if (row > 0) {
					person.setStatus(true);		//更新成功
//					out.print("register successful!");
				} else {
					person.setStatus(false);	//失败
					
				}
				String jsonStr = gson.toJson(person, Person.class);		//输出结果
				out.print(jsonStr);
				ps_sql.close();
			}
			else {
//				out.println("can not register!");
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
