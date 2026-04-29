package mypackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public login() {
		super();
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

		// 1. DICHIARAZIONE UNICA DELLA SESSIONE
		HttpSession session = request.getSession();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Connection conn = Connessione.getCon();
		try {
			// --- CONTROLLO STUDENTE ---
			Statement smt = conn.createStatement();
			ResultSet rs = smt.executeQuery("select username,password from studente");

			while (rs.next()) {
				if (rs.getString("username").equalsIgnoreCase(username)
						&& rs.getString("password").equalsIgnoreCase(password)) {
					PreparedStatement smt1 = conn
							.prepareStatement("select matricola, nome, cognome from studente where username=?");
					smt1.setString(1, username);
					ResultSet rs1 = smt1.executeQuery();
					rs1.next();
					String matricola = rs1.getString("matricola");
					String nome = rs1.getString("nome");
					String cognome = rs1.getString("cognome");
					Statement smt2 = conn.createStatement();
					ResultSet rs2 = smt2.executeQuery(
							"select idcorso,materia,nome,cognome from corso join professore on cattedra=idprofessore");

					session.setAttribute("matricola", matricola);
					session.setAttribute("cognome", cognome);
					session.setAttribute("nome", nome);

					request.setAttribute("tabella_corso", rs2);
					RequestDispatcher rd = request.getRequestDispatcher("studente.jsp");
					rd.forward(request, response);
					return;
				}
			}

			// --- CONTROLLO PROFESSORE ---
			Statement smt3 = conn.createStatement();
			ResultSet rs3 = smt3.executeQuery("select username,password from professore");
			while (rs3.next()) {
				if (rs3.getString("username").equalsIgnoreCase(username)
						&& rs3.getString("password").equalsIgnoreCase(password)) {

					PreparedStatement smt4 = conn
							.prepareStatement("select nome,cognome,idProfessore from professore where username=?");
					smt4.setString(1, username);
					ResultSet rs4 = smt4.executeQuery();
					rs4.next();
					String nome = rs4.getString("nome");
					String cognome = rs4.getString("cognome");
					int idProfessore = rs4.getInt("idProfessore");

					PreparedStatement smt5 = conn
							.prepareStatement("select idcorso,materia from corso where cattedra=?");
					smt5.setInt(1, idProfessore);
					ResultSet rs5 = smt5.executeQuery();
					rs5.next();
					int idcorso = rs5.getInt("idcorso");
					String materia = rs5.getString("materia");

					PreparedStatement smt6 = conn
							.prepareStatement("select idAppello,Data from appello where Materia=?");
					smt6.setInt(1, idcorso);
					ResultSet appelli = smt6.executeQuery();

					session.setAttribute("nome", nome);
					session.setAttribute("cognome", cognome);
					session.setAttribute("materia", materia);
					request.setAttribute("appelli", appelli);

					RequestDispatcher rd4 = request.getRequestDispatcher("professore.jsp");
					rd4.forward(request, response);
					return;
				}
			}

			// --- CONTROLLO SEGRETERIA (Giuseppe Russo) ---
			Statement smtSegreteria = conn.createStatement();
			ResultSet rsSegreteria = smtSegreteria.executeQuery("select * from segreteria");

			while (rsSegreteria.next()) {
				if (rsSegreteria.getString("username").equalsIgnoreCase(username)
						&& rsSegreteria.getString("password").equals(password)) {

					// Usiamo la sessione già dichiarata sopra
					session.setAttribute("nome", rsSegreteria.getString("nome"));
					session.setAttribute("cognome", rsSegreteria.getString("cognome"));

					// Chiamiamo la Servlet della segreteria
					RequestDispatcher rdS = request.getRequestDispatcher("Segreteria");
					rdS.forward(request, response);
					return;
				}
			}

			// --- SE NON TROVA NULLA ---
			RequestDispatcher rd3 = request.getRequestDispatcher("index.jsp");
			request.setAttribute("messaggio", "username e password non sono presenti");
			rd3.forward(request, response);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
