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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author cgalv
 */
public class ListadoTareas {

    private String IdListadoTareas;
    private String IdTableroTareas;
    private String NombreListado;
    private ArrayList<Tarea> Tareas;
    private int TotalTareas;

    /**
     * @return the IdListadoTareas
     */
    public String getIdListadoTareas() {
        return IdListadoTareas;
    }

    /**
     * @param IdListadoTareas the IdListadoTareas to set
     */
    public void setIdListadoTareas(String IdListadoTareas) {
        this.IdListadoTareas = IdListadoTareas;
    }

    /**
     * @return the IdTableroTareas
     */
    public String getIdTableroTareas() {
        return IdTableroTareas;
    }

    /**
     * @param IdTableroTareas the IdTableroTareas to set
     */
    public void setIdTableroTareas(String IdTableroTareas) {
        this.IdTableroTareas = IdTableroTareas;
    }

    /**
     * @return the NombreListado
     */
    public String getNombreListado() {
        return NombreListado;
    }

    /**
     * @param NombreListado the NombreListado to set
     */
    public void setNombreListado(String NombreListado) {
        this.NombreListado = NombreListado;
    }

    /**
     * @return the Tareas
     */
    public ArrayList<Tarea> getTareas() {
        return Tareas;
    }

    /**
     * @param Tareas the Tareas to set
     */
    public void setTareas(ArrayList<Tarea> Tareas) {
        this.Tareas = Tareas;
    }
    
     /**
     * @return the TotalTareas
     */
    public int getTotalTareas() {
        this.TotalTareas = this.leerTareasLista().size();
        return TotalTareas;
    }

    /**
     * @param TotalTareas the TotalTareas to set
     */
    public void setTotalTareas(int TotalTareas) {
        this.TotalTareas = TotalTareas;
    }
    
    public void crearArchivo(ArrayList<Tarea> lista) {
		FileWriter flwriter = null;
		try {
			//crea el flujo para escribir en el archivo
			flwriter = new FileWriter("C:/Organizador/Tareas/" + this.IdListadoTareas + ".txt");
                    try ( //crea un buffer o flujo intermedio antes de escribir directamente en el archivo
                            BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
                        for (Tarea tareas : lista) {
                            //escribe los datos en el archivo
                           bfwriter.write(tareas.getId() + "|" + tareas.getIdLista() + "|" + tareas.getNombre() + "|" + tareas.getDescripcion() + "|" + tareas.getFechaInicio() + "|" + tareas.getFechaFinal() + "|" + tareas.getVigencia() + "\n");
                        }
                        //cierra el buffer intermedio
                    }
			System.out.println("Tareas creadas satisfactoriamente..");

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

    public ArrayList leerTareasLista() {
        // crea el flujo para leer desde el archivo

        File file = new File("C:/Organizador/Tareas/" + this.IdListadoTareas + ".txt");
        ArrayList listaTareas = new ArrayList<Tarea>();
        Scanner scanner;
        try {
            //se pasa el flujo al objeto scanner
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // el objeto scanner lee linea a linea desde el archivo
                String linea = scanner.nextLine();
                Scanner delimitar = new Scanner(linea);
                //se usa una expresión regular
                //que valida que antes o despues de un pipe (|) exista cualquier cosa
                //parte la cadena recibida cada vez que encuentre un pipe				
                delimitar.useDelimiter("\\s*\\|\\s*");
                Tarea e = new Tarea();
                e.setId(delimitar.next());
                e.setIdLista(delimitar.next());
                e.setNombre(delimitar.next());
                e.setDescripcion(delimitar.next());
                e.setFechaInicio(delimitar.next());
                e.setFechaFinal(delimitar.next());
                e.setVigenciaToString(delimitar.next());
                listaTareas.add(e);
                this.Tareas = listaTareas;
            }
            //se cierra el ojeto scanner
            scanner.close();
            System.out.println("Tareas leidas satisfactoriamente..");
        } catch (FileNotFoundException e) {
            System.out.println(e);
            this.Tareas = new ArrayList<Tarea>();
        }
        return Tareas;
    }

    //añadir más tareas al archivo
    public void aniadirTareasLista(ArrayList<Tarea> lista) {
        File directorio = new File("C:/Organizador/Tareas");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
        FileWriter flwriter = null;
        try {//además de la ruta del archivo recibe un parámetro de tipo boolean, que le indican que se va añadir más registros 
            flwriter = new FileWriter("C:/Organizador/Tareas/" + this.IdListadoTareas + ".txt", true);
            try ( BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
                for (Tarea tareas : lista) {
                    //escribe los datos en el archivo
                    bfwriter.write(tareas.getId() + "|" + tareas.getIdLista() + "|" + tareas.getNombre() + "|" + tareas.getDescripcion() + "|" + tareas.getFechaInicio() + "|" + tareas.getFechaFinal() + "|" + tareas.getVigencia() + "\n");
                }
            }
            System.out.println("Tareas modificadas satisfactoriamente..");
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

    public void eliminarTareasLista() {
        File archivo = new File("C:/Organizador/Tareas/" + this.IdListadoTareas + ".txt");
        System.out.println("eliminacion de tareas de la lista " + this.NombreListado);
        if (archivo.delete()) {
            System.out.println("El fichero de tareas ha sido borrado satisfactoriamente");
        } else {
            System.out.println("El fichero de tareas no puede ser borrado");
        }
    }

    public void modificarTareasLista(ArrayList<Tarea> lista) {
        FileWriter flwriter = null;
        try {
            //crea el flujo para escribir en el archivo
            flwriter = new FileWriter("C:/Organizador/Tareas/" + this.IdListadoTareas + ".txt");
            try ( //crea un buffer o flujo intermedio antes de escribir directamente en el archivo
                     BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
                for (Tarea tareas : lista) {
                    //escribe los datos en el archivo
                    bfwriter.write(tareas.getId() + "|" + tareas.getIdLista() + "|" + tareas.getNombre() + "|" + tareas.getDescripcion() + "|" + tareas.getFechaInicio() + "|" + tareas.getFechaFinal() + "|" + tareas.getVigencia() + "\n");
                }
                //cierra el buffer intermedio
            }
            System.out.println("Tablero modificado satisfactoriamente..");

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
    
     public void modificarListas(String id, String nombre, String descripcion, String fechainicio, String fechafinal,String vigencia) throws ParseException{
         Tarea tarea = this.BuscarTarea(id);    
         tarea.setNombre(nombre);
         tarea.setFechaInicio(fechainicio);
         tarea.setFechaFinal(fechafinal);
         tarea.setNombre(nombre);
         tarea.setDescripcion(descripcion);
         if (vigencia.equals("sin datos")) {
             tarea.setVigenciaToString(vigencia);
         }else{
              tarea.setVigencia(fechafinal);
         }        
         EstadoGlobal.TransferenciaTarea = tarea;
         
         ArrayList<Tarea> newList = new ArrayList<>();
         
         for(int i=0; i<Tareas.size(); i++){
             Tarea item = Tareas.get(i);
             newList.add(item);
         }
         this.crearArchivo(newList);         
     }
     
      public void eliminarTarea(String id){       
         Tarea tarea = this.BuscarTarea(id);
         tarea.eliminarTareasLista();
         this.Tareas.removeIf(x -> x.getId().equals(id));
         ArrayList<Tarea> newList = new ArrayList<>();
         
         for(int i=0; i<this.Tareas.size(); i++){
             Tarea item = this.Tareas.get(i);
             newList.add(item);
         }
         this.crearArchivo(newList);
     }
     
      public Tarea BuscarTarea(String id) {  
        Optional<Tarea> lista = this.Tareas.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst();
        System.out.println("la tarea es: " + lista.get().getNombre());
        return lista.isPresent() ? lista.get() : null;
    }

   

}
