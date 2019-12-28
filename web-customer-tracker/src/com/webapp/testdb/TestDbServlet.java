package com.webapp.testdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestDbServlet")
public class TestDbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TestDbServlet() {
        super();
    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username="root";
		String password="hareesh";
		String jdbcUrl="jdbc:mysql://localhost:3306/sample";
		String driver="com.mysql.jdbc.Driver";
		try {
			PrintWriter out=response.getWriter();
			out.println("Connection to the database");
			Class.forName(driver);
			Connection connection=DriverManager.getConnection(jdbcUrl, username, password);
			out.println("SUCCESS!");
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {

		}
	}

}
