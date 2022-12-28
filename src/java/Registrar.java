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
@WebServlet(name = "registrar", urlPatterns = {"/registrar"})
public class Registrar extends HttpServlet {

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

            String nombre = request.getParameter("inputNombre") ;
            String apellido = request.getParameter("inputApellido");
            String usuario = request.getParameter("inputUsuario");
            String clave = request.getParameter("inputPassword");
            
            Persistencia base = new Persistencia();
            
            /*  Insertamos el nuevo usuario         */
            int i = base.insertaUsuario(usuario, clave, nombre, apellido);
            out.println(i+" usuario/s generado/s.");
            
            /*  Chequeamos el usuario creado        */
            
            ResultSet resultado;
            String sql2 = "SELECT usuario, clave, nombre, apellido FROM usuarios"
                    + " WHERE usuario = '"+ usuario +"'"
                    + " AND clave = '"+ clave +"';"
            ;
            
            resultado = base.consultaSQL(sql2);
            
            while (resultado.next()) {
                    out.println("<br/><br/>Usuario ");
                    out.println(resultado.getString("usuario")+"<br/>");
                    out.println("Nombre ");
                    out.println(resultado.getString("nombre")+"<br/>");
                    out.println("Apellido ");
                    out.println(resultado.getString("apellido")+"<br/>");
                    out.println("<br/>Generado.<br/>");
            }
            
            out.println("<br/><br/><a href='login.html'>Volver</a>");
            
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
            Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
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
