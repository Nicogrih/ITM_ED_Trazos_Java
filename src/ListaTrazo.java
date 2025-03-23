import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.io.BufferedReader;
public class ListaTrazo
{
    Nodo cabeza;

    public ListaTrazo()
    {
        cabeza = null;
    }
    public void agregarTrazo (Trazo NuevoTrazo)
    {
        Nodo nuevoNodo = new Nodo(NuevoTrazo);
    if (cabeza == null) {
        cabeza = nuevoNodo;
    } else {
        Nodo apuntador = cabeza;
        while (apuntador.siguiente != null) {
            apuntador = apuntador.siguiente;
        }
        apuntador.siguiente = nuevoNodo;
    }
    }
    public void mostrarTrazo(Graphics g){
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            apuntador.trazo.dibujar(g);
            apuntador = apuntador.siguiente;
        }
    }
    public Nodo seleccionarTrazo(int x, int y) {
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            if (apuntador.trazo != null && apuntador.trazo.Existe(x, y)) {
                return apuntador; //si el trazo contiene el punto
            }
            apuntador = apuntador.siguiente;
        }
        return null; //no se encontró un trazo
    }
    //Eliminar
    public void eliminarTrazo(Nodo nodoEliminar) {
        if (cabeza == null || nodoEliminar == null) {
            JOptionPane.showMessageDialog(null, "No se encontró ningún trazo para eliminar.");
            return;
        }
    
        if (cabeza == nodoEliminar) {
            cabeza = cabeza.siguiente;
        } else {
            Nodo antecesor = cabeza;
            while (antecesor.siguiente != null && antecesor.siguiente != nodoEliminar) {
                antecesor = antecesor.siguiente;
            }
            if (antecesor.siguiente == nodoEliminar) { // Eliminar el nodo
                antecesor.siguiente = nodoEliminar.siguiente;
            }
        }
    
        JOptionPane.showMessageDialog(null, "Trazo eliminado.");
    }

    public int getLongitud() {
        int contador = 0;
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            contador++;
            apuntador = apuntador.siguiente;
        }
        return contador;
    }

    public boolean guardarDibujo(String nombreArchivo){
        String[] datos = new String[getLongitud()];

        int fila = -1;
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            fila++;
            datos[fila] = apuntador.trazo.toString(); // Guardamos la representación del trazo
            apuntador = apuntador.siguiente;
        }
        return Archivo.guardarArchivo(nombreArchivo, datos);
    }

    public boolean cargarDibujo(String nombreArchivo) {
        String[] datos = Archivo.leerArchivo(nombreArchivo);
    
        // Verificar si el archivo se leyó correctamente
        if (datos == null || datos.length == 0) {
            JOptionPane.showMessageDialog(null, "Error: El archivo está vacío o no se pudo leer.");
            return false;
        }
    
        // Limpiar lista antes de cargar
        cabeza = null;
        Nodo ultimo = null;
    
        for (String linea : datos) {
            System.out.println("Leyendo línea: " + linea);  // Depuración
    
            String[] partes = linea.split(";");
            if (partes.length < 5) {
                JOptionPane.showMessageDialog(null, "Error en el formato del archivo: " + linea);
                continue;
            }
    
            try {
                String tipo = partes[0];
                int x1 = Integer.parseInt(partes[1]);
                int y1 = Integer.parseInt(partes[2]);
                int x2 = Integer.parseInt(partes[3]);
                int y2 = Integer.parseInt(partes[4]);
    
                // Crear el trazo según el tipo
                Trazo nuevoTrazo;
                if (tipo.equals("Linea")) {
                    nuevoTrazo = new Trazo(x1, y1, x2, y2, "Linea");
                } else if (tipo.equals("Circulo")) {
                    nuevoTrazo = new Trazo(x1, y1, x2, y2, "Circulo");
                } else if (tipo.equals("Rectangulo")) {
                    nuevoTrazo = new Trazo(x1, y1, x2, y2, "Rectangulo");
                } else {
                    JOptionPane.showMessageDialog(null, "Tipo de trazo desconocido: " + tipo);
                    continue;
                }
    
                // Agregar a la lista
                Nodo nuevoNodo = new Nodo(nuevoTrazo);
                if (cabeza == null) {
                    cabeza = nuevoNodo;
                } else {
                    ultimo.siguiente = nuevoNodo;
                }
                ultimo = nuevoNodo;
                System.out.println("Trazo agregado: " + nuevoTrazo);  // Depuración
    
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error en los datos del archivo. Verifique los valores numéricos.");
                return false;
            }
        }
    
        // Verificar si se cargaron los trazos correctamente
        if (cabeza == null) {
            JOptionPane.showMessageDialog(null, "No se cargaron trazos.");
            return false;
        } else {
            JOptionPane.showMessageDialog(null, "Dibujo cargado exitosamente.");
            return true;
        }
    }
    
    
}
