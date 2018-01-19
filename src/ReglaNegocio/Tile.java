/*
 * Tile.java
 *
 * Informacion de la version
 *
 * Fecha
 *
 * Copyright
 */
package ReglaNegocio;

import java.io.Serializable;

/**
 *
 * @author Jhonny
 */
public class Tile implements Serializable {

    int color;
    public int ID =-1;

    public Tile(int id, int color) {
        this.color = color;
        this.ID =id; 
    }

    public Tile(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
