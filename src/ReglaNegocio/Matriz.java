/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ReglaNegocio;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Jhonny
 */
public class Matriz implements Serializable {

    public Tile[][] Matriz;
    public int N;
    public int M;
    private int Score;
    public int Jugadas;
    public int Singletons;
    public ArrayList<String> solucion;
    public Solucion sol;

    public Matriz(int n, int m) {
        this.Matriz = new Tile[n][m];
        this.N = n;
        this.M = m;
        Score = 0;
        Jugadas = 0;
        Singletons = 0;
        sol = new Solucion((n * m) / 2);
        solucion = new ArrayList<>();
        llenarMatriz();
    }

    Matriz(int n, int m, int[][] tiles) {
        this.Matriz = new Tile[n][m];
        this.N = n;
        this.M = m;
        Score = 0;
        Jugadas = 0;
        Singletons = 0;
        sol = new Solucion((n * m) / 2);
        solucion = new ArrayList<>();
        llenarMatriz(tiles);
    }

    public int getScore() {
        return Score;
    }

    private void llenarMatriz() {
        int id = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Tile t = new Tile(id, enteroAleatorio(1, 5));
                Matriz[i][j] = t;
                id++;
            }
        }
    }

    public void imprimir(String detalle) {
        System.out.println(detalle);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(Matriz[i][j].color + "  ");
            }
            System.out.println();
        }
        
    }

    @Override
    public String toString() {
        String imprecion = "";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(Matriz[i][j].color + "  ");
                imprecion += Matriz[i][j].color + "  ";
            }
            imprecion += "\n\t\t\t\t\t";
        }
        return imprecion;

    }

    public void actualizarMatriz() {
        actualizarMatriz(Matriz);
    }

    private void actualizarMatriz(Tile[][] matriz) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                try {
                    if (matriz[i][j].color == 0 && matriz[i - 1][j].color != 0) {
                        matriz[i][j].color = matriz[i - 1][j].color;
                        matriz[i - 1][j].color = 0;
                        i = 0;
                        j = 0;
                    }
                } catch (Exception e) {
                }
            }
        }
        boolean ocupadas = false;
        boolean falta = false;
        int index;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                try {
                    if (matriz[j][i].color != 0) {
                        ocupadas = true;
                        falta = true;
                        break;
                    }
                } catch (Exception e) {
                }
            }
            if (!ocupadas && falta == true) {
                falta = false;
                index = i;

                for (int j = 0; j < i; j++) {
                    int x = 0;
                    while (x < N) {
                        try {
                            int colorTemp = matriz[x][index - 1].color;
                            matriz[x][index].color = colorTemp;
                            matriz[x][index - 1].color = 0;
                            x++;
                        } catch (Exception e) {
                        }

                    }
                    index--;
                }
                i = 0;
            }
            ocupadas = false;
        }
    }

    public void ImprimirSolucion(String detalle) {
        System.out.println(detalle);
        String imprecion = "";
        for (int i = 0; i < solucion.size(); i++) {
            imprecion += i != solucion.size() - 1 ? "(" + solucion.get(i) + ");" : "(" + solucion.get(i) + ")";
        }
        System.out.println(imprecion + " Singletons = " + Singletons);
    }

    private int enteroAleatorio(int Min, int Max) {
        return Min + (int) (Math.random() * ((Max - Min) + 1));
    }

    public int Count() {
        int numero = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (Matriz[i][j].color != 0) {
                    numero++;
                }
            }
        }
        return numero;
    }

    public int EspaciosVacios() {
        int sum = 0;
        for (int i = 0; i < Matriz.length; i++) {
            for (int j = 0; j < Matriz[i].length; j++) {
                Tile tiles = Matriz[i][j];
                if (tiles.color == 0) {
                    sum++;
                }

            }
        }
        return sum;


    }

    public void setScore(int Score) {
        sol.setScore(Score);
        this.Score = Score;
    }

    private void llenarMatriz(int[][] tiles) {
        int id = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Tile t = new Tile(id, tiles[i][j]);
                Matriz[i][j] = t;
                id++;
            }
        }
    }
}
