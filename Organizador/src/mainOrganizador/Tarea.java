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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author cgalv
 */
public class Tarea {
    private String Id;
    private String IdLista;
    private String Nombre;
    private String Descripcion;
    private int Posicion;
    private String FechaInicio;
    private String FechaFinal;
    private String Vigencia;
    private ArrayList<ListaActividades> listaActividades = new ArrayList();
    /**
     * @return the Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return the IdLista
     */
    public String getIdLista() {
        return IdLista;
    }

    /**
     * @param IdLista the IdLista to set
     */
    public void setIdLista(String IdLista) {
        this.IdLista = IdLista;
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    /**
     * @return the Descripcion
     */
    public String getDescripcion() {
        return Descripcion;
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    /**
     * @return the Posicion
     */
    public int getPosicion() {
        return Posicion;
    }

    /**
     * @param Posicion the Posicion to set
     */
    public void setPosicion(int Posicion) {
        this.Posicion = Posicion;
    }

    /**
     * @return the FechaInicio
     */
    public String getFechaInicio() {
        return FechaInicio;
    }

    /**
     * @param FechaInicio the FechaInicio to set
     */
    public void setFechaInicio(String FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    /**
     * @return the FechaFinal
     */
    public String getFechaFinal() {
        return FechaFinal;
    }

    /**
     * @param FechaFinal the FechaFinal to set
     */
    public void setFechaFinal(String FechaFinal) {
        this.FechaFinal = FechaFinal;
    }

    /**
     * @return the Vigencia
     */
    public String getVigencia() {
        return Vigencia;
    }

    /**
     */
    public void setVigencia(String FechaFinal) throws ParseException {
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        Date fechahoy = new Date();
        String fechahoystring = fecha.format(fechahoy);
        Date dt_2 = fecha.parse(FechaFinal);
        Date dt_1 = fecha.parse(fechahoystring);
        String Vigencia = "";
        if (dt_1.compareTo(dt_2) > 0) {  
            Vigencia = "Vencida"; 
        } // el método compareTo devuelve el valor mayor que 0 si esta Fecha está después del argumento Fecha.  
        else if (dt_1.compareTo(dt_2) < 0) {  
            Vigencia = "En tiempo"; 
        } // el método compareTo devuelve el valor menor que 0 si esta Fecha es anterior al argumento Fecha; 
        else if (dt_1.compareTo(dt_2) == 0) {  
            Vigencia = "Por vencer";  
        } // el método compareTo devuelve el valor 0 si el argumento Fecha es igual a la segunda Fecha;
        else {  
            System.out.println("¡Pareces ser un viajero del tiempo!"); 
        }         
        this.Vigencia = Vigencia;
    }
    
    public void setVigenciaToString(String Vigencia){
          this.Vigencia = Vigencia;
    }
    
     public void crearArchivo(ArrayList<ListaActividades> lista) {
		FileWriter flwriter = null;
		try {
			//crea el flujo para escribir en el archivo
			flwriter = new FileWriter("C:/Organizador/ListaActividades/" + this.Id + ".txt");
                    try ( //crea un buffer o flujo intermedio antes de escribir directamente en el archivo
                            BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
                        for (ListaActividades actividades : lista) {
                            //escribe los datos en el archivo
                           bfwriter.write(actividades.getIdLista() + "|" + actividades.getIdTarea() + "|" + actividades.getIdTablero() + "|" + actividades.getNombreLista() + "|" + actividades.getPorcentaje() + "\n");
                        }
                        //cierra el buffer intermedio
                    }
			System.out.println("Lista de actividades creadas satisfactoriamente..");

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

        File file = new File("C:/Organizador/ListaActividades/" + this.Id + ".txt");
        ArrayList listaTareas = new ArrayList<ListaActividades>();
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
                ListaActividades e = new ListaActividades();
                e.setIdLista(delimitar.next());
                e.setIdTarea(delimitar.next());
                e.setIdTablero(delimitar.next());
                e.setNombreLista(delimitar.next());
                e.setPorcentaje(Float.parseFloat(delimitar.next()));
                listaTareas.add(e);                
            }
            this.listaActividades = listaTareas;
            //se cierra el ojeto scanner
            scanner.close();
            System.out.println("Lista actividades leidas satisfactoriamente..");
        } catch (FileNotFoundException e) {
            System.out.println(e);
            this.listaActividades = new ArrayList<ListaActividades>();
        }
        return listaActividades;
    }

    //añadir más tareas al archivo
    public void aniadirTareasLista(ArrayList<ListaActividades> lista) {
        File directorio = new File("C:/Organizador/ListaActividades");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
        FileWriter flwriter = null;
        try {//además de la ruta del archivo recibe un parámetro de tipo boolean, que le indican que se va añadir más registros 
            flwriter = new FileWriter("C:/Organizador/ListaActividades/" + this.Id + ".txt", true);
            try ( BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
                for (ListaActividades actividades : lista) {
                    //escribe los datos en el archivo
                    bfwriter.write(actividades.getIdLista() + "|" + actividades.getIdTarea() + "|" + actividades.getIdTablero() + "|" + actividades.getNombreLista() + "|" + actividades.getPorcentaje() + "\n");
                }
            }
            System.out.println("Lista de actividades modificadas satisfactoriamente..");
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
        File archivo = new File("C:/Organizador/ListaActividades/" + this.Id + ".txt");
        System.out.println("eliminacion de tareas de la lista " + this.Nombre);
        if (archivo.delete()) {
            System.out.println("El fichero de listas de actividades ha sido borrado satisfactoriamente");
        } else {
            System.out.println("El fichero de listas de actividades no puede ser borrado");
        }
    }
    
     public void modificarListas(String id, String nombre){
         ListaActividades lista = this.BuscarLista(id);    
         lista.setNombreLista(nombre);        
         EstadoGlobal.TransferenciaListadoActividades = lista;
         
         ArrayList<ListaActividades> newList = new ArrayList<>();
         
         for(int i=0; i<listaActividades.size(); i++){
             ListaActividades item = listaActividades.get(i);
             newList.add(item);
         }
         this.crearArchivo(newList);         
     }
     
      public ListaActividades BuscarLista(String id) {  
        Optional<ListaActividades> lista = this.listaActividades.stream()
            .filter(p -> p.getIdLista().equals(id))
            .findFirst();
        System.out.println("la lista de actividades es: " + lista.get().getNombreLista());
        return lista.isPresent() ? lista.get() : null;
    }
    
    
    
}
