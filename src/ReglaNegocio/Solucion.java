/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ReglaNegocio;

import java.io.Serializable;

/**
 *
 * @author Jhonny
 */
public class Solucion implements Serializable {

    private float Acepabilidad;
    private int Score;
    private int Espacios_Vacios;
    private int Singletons;
    private int jugadas;
    private int jugada_alta =0;
    private int[] solucion;

    public Solucion(int tamano) {
        solucion = new int[tamano];
        for (int i = 0; i < solucion.length; i++) {
            solucion[i] = -1;
        }
    }

    public Solucion(int Score, int Singletons) {
        this.Acepabilidad = 0;
        this.Score = Score;
        this.Singletons = Singletons;
    }

    public Solucion(int[] p0, int[] p1, int tamaño) {
        solucion = new int[tamaño];
        jugada_alta=0;
        for (int i = 0; i < tamaño; i++) {
            if (i < p0.length) {
                solucion[i] = p0[i];
            } else if (i < p1.length + p0.length) {
                solucion[i] = p1[i];
            } else {
                solucion[i] = -1;
            }

        }
    }

    public Solucion() {
        this.Acepabilidad = 0;
        this.Score = 0;
        this.Singletons = 0;
    }

    public boolean AddPunto(int p) {
        for (int i = 0; i < solucion.length; i++) {
            if (solucion[i] == -1) {
                solucion[i] = p;
                return true;
            }
        }
        return false;
    }

    public float getAcepabilidad() {
        return Acepabilidad;
    }

    public void setAcepabilidad(float Acepabilidad) {
        this.Acepabilidad = Acepabilidad;
    }

    public int getScore() {
        return Score;
    }

    public int getEspaciosCromosoma() {
        int count = 0;
        for (int i = 0; i < solucion.length; i++) {
            if (solucion[i] == -1) {
                count++;
            }
        }
        return count;
    }

    public int size() {
        int count = 0;
        for (int i = 0; i < solucion.length; i++) {
            if (solucion[i] != -1) {
                count++;
            }
        }
        return count;
    }

    public static int[] subArray(int desde, int hasta, int[] array) {
        int[] arr = new int[hasta - desde];
        int index = 0;
        for (int i = desde; i <= hasta; i++) {
            arr[index] = array[desde];
            index++;
        }
        return arr;
    }

    public void printSolucionID(String detalle) {
        System.out.println(detalle);

        for (int i = 0; i < solucion.length; i++) {
            int j = solucion[i];
            System.out.print(i == solucion.length - 1 ? j : j + ",");
        }

    }

    public String printSolucionXY(String detalle, int colums) {
//        System.out.println(detalle);
        String imString = detalle + "\n";
        for (int i = 0; i < solucion.length; i++) {
            int j = solucion[i];
            int x = j / colums;
            int y = j % colums;
            if (j == -1) {
                break;
            }
            imString +="[" + x + "," + y + "]";
        }
        imString += "\nScore:......." + Score + "\nJugadas:....." + jugadas + "\nSingletons:.." + Singletons+"\nMejor Jugada:.."+jugada_alta;
        System.out.print(imString);
        return imString;
    }

    public String printSoloSolucion(int columns){
    
      String imString ="";
        for (int i = 0; i < solucion.length; i++) {
            int j = solucion[i];
            int x = j / columns;
            int y = j % columns;
            if (j == -1) {
                break;
            }
            imString +="[" + x + "," + y + "]";
        }
        return imString;
    }
    public void setScore(int Score) {
        this.Score = Score;
    }

    public int getSingletons() {
        return Singletons;
    }

    public void setSingletons(int Singletons) {
        this.Singletons = Singletons;
    }

    public int[] getSolucion() {
        return solucion;
    }

    public void setSolucion(int[] solucion) {
        this.solucion = solucion;
    }

    public void setEspacios_Vacios(int Espacios_Vacios) {
        this.Espacios_Vacios = Espacios_Vacios;
    }

    public int getEspacios_Vacios() {
        return Espacios_Vacios;
    }

    public void setJugadas(int jugadas) {
        this.jugadas = jugadas;
    }

    public int getJugadas() {
        return jugadas;
    }

    public void setJugada_alta(int jugada_alta) {
        if(jugada_alta > this.jugada_alta){
            this.jugada_alta=jugada_alta;
        }
    }

    public int getJugada_alta() {
        return jugada_alta;
    }
    
    
}
