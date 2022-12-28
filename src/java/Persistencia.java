
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author plabrunee
 */
public class Persistencia {
    
    private Connection conn;
    private ResultSet resu;
    private PreparedStatement prepSt;
    private ResultSetMetaData rsm;
    
    public String servidor, basededatos, usuario, clave, script;
    
    public Connection conectar() {
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            servidor = "localhost/";
            basededatos = "cac22542proyecto";
            usuario = "root";
            clave = "";
            
            conn = DriverManager.getConnection("jdbc:mysql://" + servidor + basededatos + "?autoReconnect=true&useSSL=false",usuario,clave);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return conn;
        
    }
    
    //======================================================================================
    public ResultSet consultaSQL(String consulta) throws SQLException {
        
        prepSt = conectar().prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resu = prepSt.executeQuery();
        rsm = resu.getMetaData();
        
        return resu;
        
    }
    
    public int insertaUsuario(String usuario, String clave, String nombre, String apellido) throws SQLException {
        
        PreparedStatement ps;
        Connection conn = conectar();
        
        ps = conn.prepareStatement("INSERT INTO usuarios (usuario, clave, nombre, apellido) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        
        ps.setString(1, usuario);
        ps.setString(2, clave);
        ps.setString(3, nombre);
        ps.setString(4, apellido);
        
        int i = ps.executeUpdate();
        
        ps.close();
        conn.close();
        //ResultSet rs = psInsertaUsuario.getGeneratedKeys();
        
        return i;
        
    }
}
