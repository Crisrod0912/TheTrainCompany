package the_train_company;

public class Grafo {

    private final int[][][] matrizAdyacencia;
    private final int numVertices;

    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        this.matrizAdyacencia = new int[numVertices][numVertices][2];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matrizAdyacencia[i][j] = null;
            }
        }
    }

    public void agregarArista(int origen, int destino, int[] peso) {
        if (origen >= 0 && origen < numVertices && destino >= 0 && destino < numVertices) {
            matrizAdyacencia[origen][destino] = peso;
        } else {
            System.out.println("Los vértices están fuera de rango.");
        }
    }

    public int[] dijkstra(int nodoOrigen, int nodoDestino) {
        boolean[] visitado = new boolean[numVertices];
        int[][] distancia = new int[numVertices][2];
        for (int i = 0; i < numVertices; i++) {
            distancia[i][0] = Integer.MAX_VALUE;
        }
        distancia[nodoOrigen][0] = 0;

        while (true) {
            int verticeMenorDistancia = obtenerVerticeMenorDistancia(visitado, distancia);
            if (verticeMenorDistancia == -1) {
                break;
            }

            visitado[verticeMenorDistancia] = true;

            for (int v = 0; v < numVertices; v++) {
                if (matrizAdyacencia[verticeMenorDistancia][v] != null && !visitado[v] && matrizAdyacencia[verticeMenorDistancia][v][0] != Integer.MAX_VALUE
                        && distancia[verticeMenorDistancia][0] != Integer.MAX_VALUE
                        && distancia[verticeMenorDistancia][0] + matrizAdyacencia[verticeMenorDistancia][v][0] < distancia[v][0]) {
                    distancia[v][0] = distancia[verticeMenorDistancia][0] + matrizAdyacencia[verticeMenorDistancia][v][0];
                    distancia[v][1] = distancia[verticeMenorDistancia][1] + matrizAdyacencia[verticeMenorDistancia][v][1];
                }
            }
        }
        return distancia[nodoDestino];
    }

    public int getSiguiente(int nodo) {
        for (int i = 0; i < numVertices; i++) {
            if (matrizAdyacencia[nodo][i] != null) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerVerticeMenorDistancia(boolean[] visitado, int[][] distancia) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < numVertices; v++) {
            if (!visitado[v] && distancia[v][0] <= min) {
                min = distancia[v][0];
                minIndex = v;
            }
        }

        return minIndex;
    }
}
