/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainOrganizador;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author cgalv
 */
public class GestionOrganizadorTareas {
     public static ArrayList<TableroTareas> listaTableros = new ArrayList<>();
     
     public static void agregarTableros(TableroTareas oTablero){
        ArrayList<TableroTareas> newTablero = new ArrayList<>();
        newTablero.add(oTablero);
        OperacionArchivo.aniadirArchivo(newTablero);                
     }
     
     public static ArrayList traerTableros(){
         listaTableros = OperacionArchivo.leerArchivo();
         return listaTableros;
     }
     
     public static void eliminarTableros(String id){
         listaTableros = OperacionArchivo.leerArchivo();         
         TableroTareas tablero = GestionOrganizadorTareas.BuscarTablero(id);
         ArrayList<ListadoTareas> listadosTareas = tablero.leerTareasTablero();
         for(ListadoTareas item : listadosTareas){
             item.eliminarTareasLista();
         }
         tablero.eliminarTareasTablero();
         listaTableros.removeIf(x -> x.getIdentificacion().equals(id));
         ArrayList<TableroTareas> newList = new ArrayList<>();
         
         for(int i=0; i<listaTableros.size(); i++){
             TableroTareas item = listaTableros.get(i);
             newList.add(item);
         }
         OperacionArchivo.crearArchivo(newList);
     }
     
     public static void modificarTableros(String id, String nombre){
         listaTableros = OperacionArchivo.leerArchivo();
         TableroTareas tablero = GestionOrganizadorTareas.BuscarTablero(id);
         tablero.setNombre(nombre);
         EstadoGlobal.TransferenciaTablero = tablero;
         
         ArrayList<TableroTareas> newList = new ArrayList<>();
         
         for(int i=0; i<listaTableros.size(); i++){
             TableroTareas item = listaTableros.get(i);
             newList.add(item);
         }
         OperacionArchivo.crearArchivo(newList);         
     }
     
      public static int cantidadTableros(){
         listaTableros = OperacionArchivo.leerArchivo();
         return listaTableros.size();
     }
      
    public static TableroTareas BuscarTablero(String id) {  
        Optional<TableroTareas> tablero = listaTableros.stream()
            .filter(p -> p.getIdentificacion().equals(id))
            .findFirst();
        System.out.println("el tablero es: " + tablero.get().getNombre());
        return tablero.isPresent() ? tablero.get() : null;
    }
}

    