/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Basicas.Serialization;
import ReglaNegocio.Bebbled;
import ReglaNegocio.Matriz;
import ReglaNegocio.Solucion;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Jhonny
 */
public class Resultados {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here



        String sFichero = "ReporteGenetico.txt";
        File fichero = new File(sFichero);

//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero, true));
//            int numero = 100;
//            bw.write("Puntaje obtenido\tNúmero de movimientos\tNúmero de Singletons Finales\tJugada con mayor puntaje\tSolución+"
//                    + "(secuencia de Jugadas)\tMatriz\n");
//            for (int i = 0; i < numero; i++) {
//                Bebbled bebbled = new Bebbled(15, 15);
//                bebbled.FUNCSCORE = 1;
//                bebbled.FUNCSINGLETONS = 0;
//                bebbled.PINI = 100;
//                bebbled.GENERACIONES = 50;
//                String maString = bebbled.Tablero.toString();
//                bw.write("\n");
//                bebbled.poblacion = new ArrayList<>();
//                Solucion s = bebbled.EjecutarAlgoritmoGenetico();
//                String impresion = s.getScore() + "\t"
//                        + s.getJugadas() + "\t"
//                        + s.getSingletons() + "\t"
//                        + s.getJugada_alta() + "\t"
//                        + s.printSoloSolucion(15) + "\t"
//                        + maString + "\n";
//                bw.write(impresion);
//            }
//            bw.close();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
        sFichero = "ReporteVoraz.txt";
        fichero = new File(sFichero);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero, true));
            int numero = 100;
            bw.write("Puntaje obtenido\tNúmero de movimientos\tNúmero de Singletons Finales\tJugada con mayor puntaje\tSolución+"
                    + "(secuencia de Jugadas)\tMatriz\n");
            for (int i = 0; i < numero; i++) {
                Bebbled bebbled = new Bebbled(15, 15);
                String maString = bebbled.Tablero.toString();
                bw.write("\n");
                bebbled.poblacion = new ArrayList<>();
                Solucion s = bebbled.EjecutarAlgoritmoVoraz();
                String impresion = s.getScore() + "\t"
                        + s.getJugadas() + "\t"
                        + s.getSingletons() + "\t"
                        + s.getJugada_alta() + "\t"
                        + s.printSoloSolucion(15) + "\t"
                        + maString + "\n";
                bw.write(impresion);
            }
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }



    }
}
