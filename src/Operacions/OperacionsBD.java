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

    public OperacionsBD() {
    }

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
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            abrirConexion();
            String insert = "SELECT DNI, NOMBRE, APELLIDOS, EDAD FROM ALUMNOSTABLA WHERE DNI = ?";
            sentencia = conexion.prepareStatement(insert);

            System.out.println("Introduce el DNI del ALUMNO: ");
            String alumnoDni = sc.nextLine();

           
            sentencia.setString(1, alumnoDni);

            resultado = sentencia.executeQuery();

            if (resultado.next()) {
       
                System.out.println("DNI: " + resultado.getString("DNI")+", Nombre: " + resultado.getString("NOMBRE")+", Apellidos: " + resultado.getString("APELLIDOS")+", Edad: " + resultado.getInt("EDAD"));
                System.out.println("\n");
              
            } else {
                System.out.println("No se encontró ningún alumno con el DNI proporcionado.");
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

    //Metodo engade Alumno
    public void addAlumno() {
        try {
            abrirConexion();
            String insert = "INSERT INTO ALUMNOSTABLA (DNI, NOMBRE, APELLIDOS, EDAD) VALUES (?,?,?,?)";

            System.out.println("Introduce un DNI: ");
            String insertDni = sc.nextLine();
            System.out.println("Introduce un NOMBRE: ");
            String insertNombre = sc.nextLine();
            System.out.println("Introduce un/unos APELLIDO/S: ");
            String insertApellidos = sc.nextLine();
            System.out.println("Introduce una EDAD: ");
            int insertEdad = Integer.valueOf(sc.nextLine());

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
            String insert = "DELETE FROM ALUMNOSTABLA WHERE DNI = ?";
            PreparedStatement sentencia = null;

            try {
                System.out.println("Escribe el DNI del ALUMNO a ELIMINAR: ");
                String removeDni = sc.nextLine();
                sentencia = conexion.prepareStatement(insert);
                sentencia.setString(1, removeDni);
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
        } catch (SQLException sqle) {
            Logger.getLogger(OperacionsBD.class.getName()).log(Level.SEVERE, null, sqle);
        } finally {
            cerrarConexion();
        }
    }

    //Metodo Modifica Alumno
    public void modifyAlumno() {
        try {
            abrirConexion();
            String updateQuery = "UPDATE ALUMNOSTABLA SET NOMBRE = ?, APELLIDOS = ?, EDAD = ? WHERE DNI = ?";

            try (PreparedStatement ps = conexion.prepareStatement(updateQuery)) {
                System.out.println("Introduce el DNI del alumno a modificar: ");
                String dniAlumno = sc.nextLine();

                System.out.println("Introduce un nuevo NOMBRE: ");
                String nuevoNombre = sc.nextLine();

                System.out.println("Introduce un nuevo APELLIDO: ");
                String nuevoApellido = sc.nextLine();

                System.out.println("Introduce una nueva EDAD: ");
                int nuevaEdad = sc.nextInt();

                ps.setString(1, nuevoNombre);
                ps.setString(2, nuevoApellido);
                ps.setInt(3, nuevaEdad);
                ps.setString(4, dniAlumno);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Alumno actualizado exitosamente.");
                } else {
                    System.out.println("No se encontró ningún alumno con el DNI proporcionado.");
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        } catch (SQLException sqle) {
            Logger.getLogger(OperacionsBD.class.getName()).log(Level.SEVERE, null, sqle);
        } finally {
            cerrarConexion();
        }
    }

    //Metodo listar Alumno
    public void listarAlumno() {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            abrirConexion();
            String sentenciaSql = "SELECT DNI, NOMBRE, APELLIDOS, EDAD FROM ALUMNOSTABLA";
            sentencia = conexion.prepareStatement(sentenciaSql);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                System.out.println(" DNI: " + resultado.getString("DNI")+", Nombre: " + resultado.getString("NOMBRE")+", Apellidos: " + resultado.getString("APELLIDOS")+", Edad: " + resultado.getInt("EDAD"));
                System.out.println("\n");
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
