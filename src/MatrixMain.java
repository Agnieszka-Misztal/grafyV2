import net.sourceforge.gxl.GXLDocument;
import net.sourceforge.gxl.GXLEdge;
import net.sourceforge.gxl.GXLGraphElement;
import net.sourceforge.gxl.GXLNode;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MatrixMain {

    public static void main(String[] args) throws IOException, SAXException {
        GXLDocument gxlDocument = new GXLDocument(new File("simpleExample5.gxl"));

        List<String> idNode = new ArrayList<String>();

        Map<String,ArrayList<String>> _mapa = new HashMap<String, ArrayList<String>>();

        if (gxlDocument == null)
        {
            System.out.println("file not loaded");
            return;
        }

        //pobranie informacji o liczbie lementów w grafie nr 0
        int elements = gxlDocument.getDocumentElement().getGraphAt(0).getGraphElementCount();
        System.out.println("liczba elementow grafu " + elements);

        //zmienna do przechowywania ilości wierzchołków
        int liczbaNodow = 0;

        for (int i = 0; i < elements; i++) {
            //pobranie elementu z grafu
            GXLGraphElement graphElementAt = gxlDocument.getDocumentElement().getGraphAt(0).getGraphElementAt(i);

            //sprawdzamy czy element jest wierzchołkiem
            if (graphElementAt instanceof GXLNode) {

                //dodajemy jego id/nazwę do kolejkcji
                idNode.add(graphElementAt.getID());

                //jeżeli wierzchołek jeszcze nie jest w kolejkcji mapowania to dodajemy go
                if(!_mapa.containsKey(graphElementAt.getID()))
                {
                    _mapa.put(graphElementAt.getID(),new ArrayList<String>());
                }

                //podnosimy liczbę wierzchołków
                liczbaNodow++;
            }

            //sprawdzamy czy element jest krawędzią
            if ((graphElementAt instanceof GXLEdge)) {

                //dodajemy informację o tym że jeden wierzchołek wskazuje na drugi
                //najpierw zabezpieczenie żeby sprawdzić czy mamy wierzchołek źródłowy w mapie
                if(!_mapa.containsKey(((GXLEdge) graphElementAt).getSourceID()))
                {
                    _mapa.put(((GXLEdge) graphElementAt).getSourceID(),new ArrayList<String>());
                    _mapa.get(((GXLEdge) graphElementAt).getSourceID()).add(((GXLEdge) graphElementAt).getTargetID());
                }else
                {
                    _mapa.get(((GXLEdge) graphElementAt).getSourceID()).add(((GXLEdge) graphElementAt).getTargetID());
                }

                if(!((GXLEdge) graphElementAt).isDirected())
                {
                    //teraz dodajemy w drugą stronę z wierzchołka docelowego do źrodłowego
                    if(!_mapa.containsKey(((GXLEdge) graphElementAt).getTargetID()))
                    {
                        _mapa.put(((GXLEdge) graphElementAt).getTargetID(),new ArrayList<String>());
                        _mapa.get(((GXLEdge) graphElementAt).getTargetID()).add(((GXLEdge) graphElementAt).getSourceID());
                    }else
                    {
                        _mapa.get(((GXLEdge) graphElementAt).getTargetID()).add(((GXLEdge) graphElementAt).getSourceID());
                    }
                }
            }
        }

        //dodaje 2 spacje na początku żeby ładnie wyświetlić macierz
        System.out.printf("  ");

        //wypisuje sobie wszystkie nody dla kolumn
        for (String id : idNode) {
            System.out.printf(id + " ");
        }

        System.out.printf("\n");

        //docelowa macierz sąsiedztwa
        int adjacencyMatrix[][] = new int[liczbaNodow][liczbaNodow];


        //wypisuję macierz na ekranie i przy okazji wpisuję ją do pamięci
        for (int i = 0; i < liczbaNodow; i++)//wiersze
        {
            for (int j = 0; j < liczbaNodow; j++)//kolumny
            {
                //wypisanie nazwy noda dla wiersza
                if (j == 0)
                {
                    System.out.printf(idNode.get(i) + " ");
                }

                //jeżeli wieżchołek i wskazuje na j
                if (_mapa.get(idNode.get(i)).contains(idNode.get(j)))
                {
                    System.out.printf("1 ");
                    adjacencyMatrix[i][j] = 1;
                }else
                {
                    System.out.printf("0 ");
                    adjacencyMatrix[i][j] = 0;
                }
                //System.out.printf("%d",matrix[i][j]);
            }
            System.out.printf("\n");
        }

        //dfs
        DepthFirstSearch dfs = new DepthFirstSearch();

        //BFS
        BreadthFirstSearch bfs = new BreadthFirstSearch();

        //pomocnicza lista dla wszystkich wierzchołków
        ArrayList<MatrixNode> nodes = new ArrayList<>();

        //dodanie wierzchołków
        for (String id : idNode) {
            MatrixNode newNode = new MatrixNode(id);

            //dodanie do listy pomocniczej
            nodes.add(newNode);

            //dodanie do klas każdego algorytmu
            dfs.AddNode(newNode);
            bfs.addNode(newNode);
        }

        //wylicz dsf na podstawie macierzy sąsiedztwa oraz wierzchołka początkowego
        System.out.printf("\nDFS - algorytm rekurencyjny\n");
        dfs.dfs(adjacencyMatrix, nodes.get(0));

        //wyczyszczenie znaczników
        dfs.clearVisitedFlags();


        System.out.printf("\n\nDFS - algorytm stosu\n");
        dfs.dfsUsingStack(adjacencyMatrix, nodes.get(0));

        //wyczyszczenie znaczników
        dfs.clearVisitedFlags();

        System.out.printf("\n\nBFS - algorytm stosu\n");
        bfs.bfs(adjacencyMatrix,nodes.get(0));
    }
}
