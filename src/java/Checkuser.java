/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author plabrunee
 */
@WebServlet(urlPatterns = {"/checkuser"})
public class Checkuser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            
            String usuario = request.getParameter("inputEmail");
            String clave = request.getParameter("inputPassword");
            
            String sql = "SELECT usuario, clave, nombre, apellido FROM usuarios"
                    + " WHERE usuario = '"+ usuario +"'"
                    + " AND clave = '"+ clave +"';"
            ;
            
            Persistencia base = new Persistencia();
            ResultSet resultado;
            resultado = base.consultaSQL(sql);
            
            int registros = 0;
            
            while (resultado.next()) {
                registros++;
            }
            
            resultado.first();
            
            if (registros == 0) {
                out.println("No existen usuarios con éstos datos.<br/>");
                out.println("<br/><a href='login.html'>Volver</a>");
            } else {
                
                while (resultado.next()) {
                    out.println(resultado.getString("usuario"));
                    out.println(resultado.getString("nombre"));
                    out.println(resultado.getString("apellido") + "<br/>");
                }

                out.println("<h1>Proyecto en: " + request.getContextPath() + "</h1>");
                out.println("<h3>Usuario <strong>" + request.getParameter("inputEmail") + "</strong> logueado correctamente.</h3>");
                out.println("<br/><br/><a href='login.html'>Volver</a>");
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Checkuser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Checkuser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
