/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ReglaNegocio;

import Basicas.Serialization;
import java.util.ArrayList;

/**
 *
 * @author Jhonny
 */
public class Bebbled {

    public Matriz Tablero;
    public ArrayList<Integer> adyacencias;
    public ArrayList<Solucion> poblacion;
    public int GENERACIONES;
    public int PINI;
    public float FUNCSINGLETONS;
    public float FUNCSCORE;
    public double probElitismo = 0.6f;

    public Bebbled(int n, int m) {
        GENERACIONES = 0;
        Tablero = new Matriz(n, m);
        poblacion = new ArrayList<>();
    }

    public Bebbled(int n, int m, int[][] tiles) {
        GENERACIONES = 0;
        Tablero = new Matriz(n, m, tiles);
        poblacion = new ArrayList<>();
    }

    public Solucion EjecutarAlgoritmoVoraz() {
        Matriz copia = Serialization.copy(Tablero);
        int candidato = Integer.MIN_VALUE;
        int cantAdy = 0;
        int columns = copia.M;

        while (copia.Count() > getSingletons(copia)) {
            int x;
            int y;

            for (int i = 0; i < (copia.M * copia.N - 1); i++) {
                x = i / columns;
                y = i % columns;
                buscarAdyacentes(x, y, copia);
                if (adyacencias.size() > cantAdy) {
                    cantAdy = adyacencias.size();
                    candidato = i;
                }
            }
            x = candidato / columns;
            y = candidato % columns;
            buscarAdyacentes(x, y, copia);
            ejecutarMovimiento(x, y, copia);
            cantAdy = 0;
            candidato = Integer.MIN_VALUE;
        }
        return copia.sol;
    }

    public Solucion EjecutarAlgoritmoGenetico() {
        GenerarPoblacionInicial(Tablero);
        EvaluarPoblacion(Tablero);
        int generacion = 0;

        while (generacion < GENERACIONES) {
            ArrayList<Solucion> GeneracionNueva = new ArrayList<>();
            for (int i = 0; i < PINI; i++) {
                int suma = 0;

                for (Solucion solucion : poblacion) {
                    suma += solucion.getAcepabilidad();
                }
                Solucion padre = poblacion.get(SeleccionarPadre(suma));
                Solucion madre = poblacion.get(SeleccionarPadre(suma));

                Solucion decendienteA = cruzar(padre, madre, true);
                Solucion decendienteB = cruzar(padre, madre, false);
                GeneracionNueva.add(decendienteA);
                i++;
                GeneracionNueva.add(decendienteB);
                i++;
                if (Math.random() > probElitismo) {
                    int retorno = 0;
                    float acepta = poblacion.get(0).getAcepabilidad();
                    for (int k = 1; k < poblacion.size(); k++) {
                        Solucion solucion = poblacion.get(k);
                        if (solucion.getAcepabilidad() > acepta) {
                            acepta = solucion.getAcepabilidad();
                            retorno = k;
                        }
                    }
                    GeneracionNueva.add(Serialization.copy((poblacion.get(retorno))));
                    poblacion.get(retorno).setAcepabilidad(0);
                    i++;
                }
            }
            poblacion = GeneracionNueva;
            EvaluarPoblacion(Tablero);
            generacion++;
        }
        float acepta = poblacion.get(0).getAcepabilidad();
        int retorno = 0;
        for (int i = 1; i < poblacion.size(); i++) {
            Solucion solucion = poblacion.get(i);
            if (solucion.getAcepabilidad() > acepta) {
                acepta = solucion.getAcepabilidad();
                retorno = i;
            }
        }
        return poblacion.get(retorno);
    }

    public float FuncionSeleccion(Solucion S) {

        float a = (float) (S.getScore() * FUNCSCORE
                + S.getEspacios_Vacios() * FUNCSINGLETONS);
        return a;
    }

    public void GenerarPoblacionInicial(Matriz T) {
        for (int i = 0; i < PINI; i++) {
            Matriz temp = Serialization.copy(T);
            resolverMatriz(temp);
            temp.sol.setEspacios_Vacios(temp.EspaciosVacios());
            temp.sol.setJugadas(temp.Jugadas);
            poblacion.add(temp.sol);
            //temp.imprimir("Estado Matriz: " + temp.Singletons + " : " + temp.getScore());
            //temp.sol.printSolucionXY("Solucion", temp.M);
        }
    }

    public void EvaluarPoblacion(Matriz T) {
        for (Solucion solucion : poblacion) {
            Matriz temp = Serialization.copy(T);
            resolverMatriz(temp, solucion);
            temp.sol.setSingletons(getSingletons(temp));
            temp.sol.setEspacios_Vacios(temp.EspaciosVacios());
            solucion.setAcepabilidad((FuncionSeleccion(solucion)));
        }
    }

    public int SeleccionarPadre(int suma) {

        return ruleta(suma);

    }

    public Solucion cruzar(Solucion P, Solucion M, boolean D) {
        int corte;
        if (M.size() > P.size()) {
            corte = enteroAleatorio(0, P.size());
        } else {
            corte = enteroAleatorio(0, M.size());
        }

        if (D) {
            try {
                Solucion hijo = new Solucion(Solucion.subArray(0, corte, P.getSolucion()), Solucion.subArray(corte, M.size(), M.getSolucion()), 10);
                mutar(hijo);
                return hijo;
            } catch (Exception E) {
                return P;
            }
        } else {
            try {
                Solucion hijo = new Solucion(Solucion.subArray(0, corte, M.getSolucion()), Solucion.subArray(corte, P.size(), P.getSolucion()), 10);
                mutar(hijo);
                return hijo;
            } catch (Exception E) {
                return M;
            }
        }
    }

    public void mutar(Solucion s) {
        int random = enteroAleatorio(0, s.size());
        if (random >= enteroAleatorio(0, s.size())) {
            for (int i = 0; i < 3; i++) {
                int genId = enteroAleatorio(0, Tablero.M * Tablero.N - 1);
                s.getSolucion()[random] = genId;
                random = enteroAleatorio(0, s.size());
            }
        }
    }

    private int ruleta(int f) {
        int random = enteroAleatorio(0, f);
        float suma = 0;
        for (int i = 0; i < poblacion.size(); i++) {
            Solucion solucion = poblacion.get(i);
            suma += solucion.getAcepabilidad();
            if (suma >= random) {
                return i;
            }
        }
        return -1;
    }

    public Solucion resolverMatriz(Matriz M) {

        while (M.Count() > getSingletons(M)) {

            int pos = enteroAleatorio(0, (M.M * M.N) - 1);
            int x = pos / M.M;
            int y = pos % M.M;
            buscarAdyacentes(x, y, M);
            if (adyacencias.size() > 0) {
                ejecutarMovimiento(x, y, M);
            }
        }
        return M.sol;
    }

    public void resolverMatriz(Matriz M, Solucion s) {
        int[] puntos = s.getSolucion();
        for (int i = 0; i < s.size(); i++) {
            int pos = puntos[i];
            int x = pos / M.M;
            int y = pos % M.M;
            buscarAdyacentes(x, y, M);
            if (adyacencias.size() > 0) {
                ejecutarMovimiento(x, y, M);
            }
        }
        M.sol.setJugadas(M.Jugadas);


    }

    public void ejecutarMovimiento(int x, int y, Matriz M) {
        int ady = adyacencias.size();
        if (ady > 1) {
            for (int i = 0; i < ady; i++) {
                int pos = adyacencias.get(i);
                int k = pos / M.M;
                int j = pos % M.M;
                cambiarColor(k, j, 0, M);
            }
            M.actualizarMatriz();
            M.sol.AddPunto(M.Matriz[x][y].ID);
            M.solucion.add(x + "," + y);
            M.sol.setJugada_alta(getTableScore(ady));
            M.setScore(M.getScore() + getTableScore(ady));
            M.Jugadas++;
            M.sol.setJugadas(M.Jugadas);
            int singletons = getSingletons(M);
            M.sol.setSingletons(singletons);
        }
    }

    private void cambiarColor(int i, int j, int color, Matriz M) {
        M.Matriz[i][j].color = color;
    }

    public void buscarAdyacentes(int x, int y, Matriz M) {
        adyacencias = new ArrayList<>();
        if (M.Matriz[x][y].color != 0) {
            buscarAdyacentes(x, y, M, M.Matriz[x][y].color);
        }
    }

    public int getSingletons(Matriz M) {
        M.Singletons = 0;
        for (int i = 0; i < M.N; i++) {
            for (int j = 0; j < M.M; j++) {
                if (M.Matriz[i][j].color != 0) {
                    if (((j + 1) == M.M) || M.Matriz[i][j].color != M.Matriz[i][j + 1].color) {
                        if ((j == 0) || M.Matriz[i][j].color != M.Matriz[i][j - 1].color) {
                            if (((i + 1) == M.N) || M.Matriz[i][j].color != M.Matriz[i + 1][j].color) {
                                if ((i == 0) || M.Matriz[i][j].color != M.Matriz[i - 1][j].color) {
                                    M.Singletons++;
                                }
                            }
                        }
                    }
                }

            }
        }
        return M.Singletons;
    }

    private void buscarAdyacentes(int x, int y, Matriz M, int color) {
        if (x < 0 || y < 0) {
            return;
        }
        if (x >= M.N || y >= M.M) {
            return;
        }
        try {
            if (M.Matriz[x][y].color == color && permitirAdyacencia(M.Matriz[x][y].ID)) {
                adyacencias.add(M.Matriz[x][y].ID);
                buscarAdyacentes(x - 1, y, M, color);
                buscarAdyacentes(x + 1, y, M, color);
                buscarAdyacentes(x, y - 1, M, color);
                buscarAdyacentes(x, y + 1, M, color);
            }
        } catch (Exception e) {
            System.err.println(e.toString() + x + "," + y);
        }
    }

    public void imprimirAdyacentes(String detalle) {
        for (int i = 0; i < adyacencias.size(); i++) {
            int pos = adyacencias.get(i);
            int x = pos / Tablero.M;
            int y = pos % Tablero.M;
            System.out.println(x + "," + y);
        }
    }

    private boolean permitirAdyacencia(int id) {
        for (int i = 0; i < adyacencias.size(); i++) {
            int punto = adyacencias.get(i);
            if (punto == id) {
                return false;
            }
        }
        return true;
    }

    private int getTableScore(int N) {
        return N * (N - 1);
    }

    public int getScore() {
        return Tablero.getScore();
    }

    public int getJugadas() {
        return Tablero.Jugadas;
    }

    private int enteroAleatorio(int Min, int Max) {
        return Min + (int) (Math.random() * ((Max - Min) + 1));
    }
}
