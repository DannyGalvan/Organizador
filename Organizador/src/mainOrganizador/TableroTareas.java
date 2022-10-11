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
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author cgalv
 */
public class TableroTareas {
    
    private String Identificacion;
    private String Nombre;
    private ArrayList<ListadoTareas> Tareas = new ArrayList();
    private int TotalTareas;
    
    public String getIdentificacion() {
        return this.Identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.Identificacion = identificacion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public ArrayList<ListadoTareas> getTareas() {
        return Tareas;
    }

    public void addTareas(ListadoTareas tarea) {
        this.Tareas.add(tarea);
    }    
    
    public int countTareas(){
        return this.leerTareasTablero().size();
    }
    
    public int getTotalTareas() {
        this.TotalTareas = this.leerTareasTablero().size();
        return TotalTareas;
    }

    public void setTotalTareas(int totalTareas) {
        this.TotalTareas = totalTareas;
    }
    
    public void crearArchivo(ArrayList<ListadoTareas> lista) {
		FileWriter flwriter = null;
		try {
			//crea el flujo para escribir en el archivo
			flwriter = new FileWriter("C:/Organizador/ListaTareas/" + this.Identificacion + ".txt");
                    try ( //crea un buffer o flujo intermedio antes de escribir directamente en el archivo
                            BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
                        for (ListadoTareas tareas : lista) {
                            //escribe los datos en el archivo
                           bfwriter.write(tareas.getIdListadoTareas() + "|" + tareas.getIdTableroTareas()+ "|" + tareas.getNombreListado() + "\n");
                        }
                        //cierra el buffer intermedio
                    }
			System.out.println("Lista creado satisfactoriamente..");

		} catch (IOException e) {
		} finally {
			if (flwriter != null) {
				try {//cierra el flujo principal
					flwriter.close();
				} catch (IOException e) {
				}
			}
		}
    }
    
    public ArrayList leerTareasTablero() {
		// crea el flujo para leer desde el archivo
                           
		File file = new File("C:/Organizador/ListaTareas/" + this.Identificacion + ".txt");
		ArrayList listaTareas= new ArrayList<ListadoTareas>();	
		Scanner scanner;
		try {
			//se pasa el flujo al objeto scanner
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				// el objeto scanner lee linea a linea desde el archivo
				String linea = scanner.nextLine();
				Scanner delimitar = new Scanner(linea);
				//se usa una expresión regular
				//que valida que antes o despues de una coma (,) exista cualquier cosa
				//parte la cadena recibida cada vez que encuentre una coma				
				delimitar.useDelimiter("\\s*\\|\\s*");
				ListadoTareas e = new ListadoTareas();
                                e.setIdListadoTareas(delimitar.next());
                                e.setIdTableroTareas(delimitar.next());
                                e.setNombreListado(delimitar.next());
				listaTareas.add(e);
                                this.Tareas = listaTareas;
			}
			//se cierra el ojeto scanner
			scanner.close();
                        System.out.println("Listas leidas satisfactoriamente..");
		} catch (FileNotFoundException e) {
                    System.out.println(e);
                    this.Tareas = new ArrayList<ListadoTareas>();
		}
		return Tareas ;
	}
	
    //añadir más tareas al archivo
    public void aniadirTareasTablero(ArrayList<ListadoTareas> lista) {
            File directorio = new File("C:/Organizador/ListaTareas");
                if (!directorio.exists()) {
                   if (directorio.mkdirs()) {
                       System.out.println("Directorio creado");                        
                   } else {
                       System.out.println("Error al crear directorio");
                   }
               }      
            FileWriter flwriter = null;
            try {//además de la ruta del archivo recibe un parámetro de tipo boolean, que le indican que se va añadir más registros 
                    flwriter = new FileWriter("C:/Organizador/ListaTareas/" + this.Identificacion + ".txt", true);
                try (BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
                    for (ListadoTareas tareas : lista) {
                        //escribe los datos en el archivo
                        bfwriter.write(tareas.getIdListadoTareas() + "|" + tareas.getIdTableroTareas()+ "|" + tareas.getNombreListado() + "\n");
                    }
                }
                    System.out.println("Listas modificadas satisfactoriamente..");

            } catch (IOException e) {
            } finally {
                    if (flwriter != null) {
                            try {
                                    flwriter.close();
                            } catch (IOException e) {
                            }
                    }
            }
    }
    
    public void eliminarTareasTablero(){
        File archivo = new File("C:/Organizador/ListaTareas/" + this.Identificacion + ".txt");
        if (archivo.delete()) {
            System.out.println("El fichero de listas ha sido borrado satisfactoriamente");
        }else{
           System.out.println("El fichero de listas no puede ser borrado");
        }
    }
    
    public void eliminarListado(String id){
        this.Tareas = this.leerTareasTablero();         
         ListadoTareas listado = this.BuscarListado(id);
         listado.eliminarTareasLista();
         this.Tareas.removeIf(x -> x.getIdListadoTareas().equals(id));
         ArrayList<ListadoTareas> newList = new ArrayList<>();
         
         for(int i=0; i<this.Tareas.size(); i++){
             ListadoTareas item = this.Tareas.get(i);
             newList.add(item);
         }
         this.crearArchivo(newList);
     }
        
    public void modificarListas(String id, String nombre){
         Tareas = this.leerTareasTablero();
         ListadoTareas listado = this.BuscarListado(id);    
         listado.setNombreListado(nombre);        
         EstadoGlobal.TransferencialistadoTareas = listado;
         
         ArrayList<ListadoTareas> newList = new ArrayList<>();
         
         for(int i=0; i<Tareas.size(); i++){
             ListadoTareas item = Tareas.get(i);
             newList.add(item);
         }
         this.crearArchivo(newList);         
     }
        
        public ListadoTareas BuscarListado(String id) {  
        Optional<ListadoTareas> lista = this.Tareas.stream()
            .filter(p -> p.getIdListadoTareas().equals(id))
            .findFirst();
        System.out.println("el tablero es: " + lista.get().getNombreListado());
        return lista.isPresent() ? lista.get() : null;
    }
}
