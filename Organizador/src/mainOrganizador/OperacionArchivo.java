/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainOrganizador;

/**
 *
 * @author cgalv
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OperacionArchivo {
	
	//crea el archivo en disco, recibe como parámetro la lista de estudiantes
	public static void crearArchivo(ArrayList<TableroTareas> lista) {
		FileWriter flwriter = null;
		try {
			//crea el flujo para escribir en el archivo
			flwriter = new FileWriter("C:/Organizador/Tableros/tableros.txt");
                    try ( //crea un buffer o flujo intermedio antes de escribir directamente en el archivo
                            BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
                        for (TableroTareas tablero : lista) {
                            //escribe los datos en el archivo
                            bfwriter.write(tablero.getIdentificacion() + "|" + tablero.getNombre() + "|" + tablero.countTareas() + "\n");
                        }
                        //cierra el buffer intermedio
                    }
			System.out.println("Tablero creado satisfactoriamente..");

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
	
	//crea el archivo en disco, retorna la lista de estudiantes
	public static ArrayList leerArchivo() {
		// crea el flujo para leer desde el archivo
		File file = new File("C:/Organizador/Tableros/tableros.txt");
		ArrayList listaTableros= new ArrayList<>();	
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
				TableroTareas e = new TableroTareas();
				e.setIdentificacion(delimitar.next());
				e.setNombre(delimitar.next());
				e.setTotalTareas(Integer.parseInt(delimitar.next()));
				listaTableros.add(e);
			}
			//se cierra el ojeto scanner
			scanner.close();
                        System.out.println("Tableros leidos satisfactoriamente..");
		} catch (FileNotFoundException e) {
                    System.out.println(e);
		}
		return listaTableros;
	}
	
	//añadir más estudiantes al archivo
	public static void aniadirArchivo(ArrayList<TableroTareas> lista) {
                File root = new File("C:/Organizador");
                    if (!root.exists()) {
                       if (root.mkdirs()) {
                           System.out.println("Directorio creado");                        
                       } else {
                           System.out.println("Error al crear raiz");
                       }
                   }        
                File directorio = new File("C:/Organizador/Tableros");
                 if (!directorio.exists()) {
                    if (directorio.mkdirs()) {
                        System.out.println("Directorio creado");                        
                    } else {
                        System.out.println("Error al crear directorio");
                    }
                }                 
                FileWriter flwriter = null;
		try {//además de la ruta del archivo recibe un parámetro de tipo boolean, que le indican que se va añadir más registros 
			flwriter = new FileWriter("C:/Organizador/Tableros/tableros.txt", true);
                    try (BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
                        for (TableroTareas tablero : lista) {
                            //escribe los datos en el archivo
                            bfwriter.write(tablero.getIdentificacion() + "|" + tablero.getNombre() + "|" + tablero.countTareas() + "\n");
                        }
                    }
			System.out.println("Archivo modificado satisfactoriamente..");

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
        
        public static void eliminarArchivo(){
            
        }
}
