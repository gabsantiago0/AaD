/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operacions;

import java.util.Scanner;

/**
 *
 * @author node
 */
public class ProgramaAlumnos {

    public static void main(String[] args) {

        try {
            System.out.println("Bienvenido al programa de gestion de ALUMNOS: ");
            Scanner sc = new Scanner(System.in);
            //Creamos el objeto OperacionsBD, para utilizar sus metodos: 
            OperacionsBD oB = new OperacionsBD();
            boolean on = true;

            while (on) {
                System.out.println("Elige una opcion:\n"
                        + "1. AÑADIR un ALUMNO\n"
                        + "2. MODIFICAR un ALUMNO\n"
                        + "3. BORRAR un ALUMNO\n"
                        + "4. CONSULTA un ALUMNO\n"
                        + "5. LISTA todos los ALUMNO\n"
                        + "6. SALIR del PROGRAMA\n");
               int eleccion = Integer.parseInt(sc.nextLine());
                switch (eleccion) {
                    case 1:
                        System.out.println("Creando un ALUMNO: ");
                        oB.addAlumno();
                        break;
                    case 2:
                        System.out.println("MODIFICANDO un ALUMNO: ");
                        oB.modifyAlumno();
                        break;
                    case 3:
                        System.out.println("BORRANDO un ALUMNO: ");
                        oB.removeAlumno();
                        break;
                    case 4:
                        System.out.println("CONSULTANDO un ALUMNO: ");
                        oB.consultaAlumno();
                        break;
                    case 5:
                        System.out.println("LISTANDO a todos los ALUMNOS: ");
                        oB.listarAlumno();
                        break;
                    case 6:
                        on = false;
                        System.out.println("SALIENDO DEL PROGRAMA");
                        break;
                }
            }

        } catch (Exception e) {
        }

    }
}
