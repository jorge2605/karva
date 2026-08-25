/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Patrones;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jesparza
 */
public class patronMaterial {
     private String nombre;
    private List<String> patrones;

    public patronMaterial(String nombre, String patronCadena) {
        this.nombre = nombre;
        this.patrones = Arrays.asList(patronCadena.toLowerCase().split(","));
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getPatrones() {
        return patrones;
    }
}
