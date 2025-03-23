import java.awt.Graphics;

class Trazo {
    int x, y, x2, y2;
    String tipo;

    public Trazo(int x, int y, int x2, int y2, String tipo) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.tipo = tipo;
    }

    public void dibujar(Graphics g) {
        switch (tipo) {
            case "Linea":
                    g.drawLine(x, y, x2, y2);
                break;
            case "Circulo":
                    int radio = (int) Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2));
                    g.drawOval(x - radio, y - radio, radio * 2, radio * 2);
                break;
            case "Rectangulo":
                    int ancho = Math.abs(x2 -x);
                    int alto = Math.abs(y2-y);
                    g.drawRect(Math.min(x, x2), Math.min(y, y2), ancho, alto);
                break;
            default:
                break;
        }
    }

    public boolean Existe(int px, int py) {
        switch (tipo) {
            case "Linea":
                return puntoCercaDeLinea(px, py, this.x, this.y, this.x2, this.y2);
    
            case "Circulo":
                int radio = (int) Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2));
                int distancia = (int) Math.hypot(px - x, py - y);
                return distancia <= radio;
    
            case "Rectangulo":
                return (px >= Math.min(x, x2) && px <= Math.max(x, x2) &&
                        py >= Math.min(y, y2) && py <= Math.max(y, y2));
    
            default:
                return false;
        }
    }
    private boolean DistanciaLinea (int x, int y) { //Para eliminar el trazo
        double t = Math.max(0, Math.min(1, ((x - x) * (x2 - x) + (y - y) * (y2 - y)) / (Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2))));
        return Math.hypot(x - (x + t * (x2 - x)), y - (y + t * (y2 - y))) < 5;
    }

    private boolean puntoCercaDeLinea(int px, int py, int x1, int y1, int x2, int y2) { //Para saber si un punto esta cerca de la linea trazada y seleccionarla
        // Si la línea es un solo punto
        if (x1 == x2 && y1 == y2) {
            return Math.hypot(px - x1, py - y1) < 5; // Distancia euclidiana
        }
    
        // Cálculo del parámetro 't' para encontrar el punto más cercano sobre la línea
        double t = Math.max(0, Math.min(1, ((px - x1) * (x2 - x1) + (py - y1) * (y2 - y1)) / (Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))));
    
        // Coordenadas del punto más cercano en la línea
        double cx = x1 + t * (x2 - x1);
        double cy = y1 + t * (y2 - y1);
    
        // Verificar si el puntoestá cerca del punto más cercano en la línea
        return Math.hypot(px - cx, py - cy) < 5;
    }
    

    
    
    
}

