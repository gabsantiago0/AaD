package Operacions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

public class OperacionsBD {

    private Connection conexion;

    Scanner sc = new Scanner(System.in);

    //Metodo abrir conexion
    public void abrirConexion() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/alumnos", "root", "Abc123.,");
        } catch (ClassNotFoundException | SQLException ex) {
            cerrarConexion();
            Logger.getLogger(OperacionsBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo cerrar conexion
    public void cerrarConexion() {

        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperacionsBD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Metodo consulta Alumno
    public void consultaAlumno() {

    }

    //Metodo engade Alumno
    public void addAlumno() {
        try {
            abrirConexion();
            String insert = "INSERT INTO ALUMNOS (DNI, NOMBRE, APELLIDOS, EDAD) VALUES (?,?,?,?)";

            System.out.println("Introduce un DNI: ");
            String insertDni = sc.nextLine();
            System.out.println("Introduce un NOMBRE: ");
            String insertNombre = sc.nextLine();
            System.out.println("Introduce un/unos APELLIDO/S: ");
            String insertApellidos = sc.nextLine();
            System.out.println("Introduce una EDAD: ");
            int insertEdad = sc.nextInt();

            try (PreparedStatement ps = conexion.prepareStatement(insert)) {
                ps.setString(1, insertDni);
                ps.setString(2, insertNombre);
                ps.setString(3, insertApellidos);
                ps.setInt(4, insertEdad);

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Has añadido un ALUMNO.");
                } else {
                    System.out.println("No se pudo añadir el ALUMNO.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperacionsBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
    }

    //Metodo borra Alumno
    public void removeAlumno() {
        try {
            abrirConexion();
            String sentenciaSql = "DELETE FROM ALUMNOS WHERE NOMBRE = ?";
            PreparedStatement sentencia = null;

            try {
                System.out.println("Escribe el nombre del ALUMNO a ELIMINAR: ");
                String removeNombre = sc.nextLine();
                sentencia = conexion.prepareStatement(sentenciaSql);
                sentencia.setString(1, removeNombre);
                int filasAfectadas = sentencia.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("ALUMNO eliminado exitosamente.");
                } else {
                    System.out.println("No se encontró ningún ALUMNO con ese nombre.");
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } finally {
                if (sentencia != null) {
                    sentencia.close();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperacionsBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
    }

    //Metodo Modifica Alumno
    public void modifyAlumno() {

    }

    //Metodo listar Alumno
    public void listarAlumno() {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            abrirConexion();
            String sentenciaSql = "SELECT NOMBRE, APELLIDOS FROM ALUMNOS";
            sentencia = conexion.prepareStatement(sentenciaSql);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                System.out.println("Nombre: " + resultado.getString("NOMBRE"));
                System.out.println("Apellidos: " + resultado.getString("APELLIDOS"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (resultado != null) {
                try {
                    resultado.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            cerrarConexion();
        }
    }
}
