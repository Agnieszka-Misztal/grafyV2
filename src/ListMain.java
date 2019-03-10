
import net.sourceforge.gxl.GXLDocument;
import net.sourceforge.gxl.GXLEdge;
import net.sourceforge.gxl.GXLGraphElement;
import net.sourceforge.gxl.GXLNode;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ListMain {

    public static void main(String[] args) throws IOException, SAXException {

        GXLDocument gxlDocument = new GXLDocument(new File("simpleExample5.gxl"));

        List<String> idNode = new ArrayList<String>();

        Map<String,ArrayList<String>> _mapa = new TreeMap<String, ArrayList<String>>();

        if (gxlDocument == null)
        {
            System.out.println("file not loaded");
            return;
        }

        //pobranie informacji o liczbie lementów w grafie nr 0
        int elements = gxlDocument.getDocumentElement().getGraphAt(0).getGraphElementCount();

        for (int i = 0; i < elements; i++) {
            //pobranie elementu z grafu
            GXLGraphElement graphElementAt = gxlDocument.getDocumentElement().getGraphAt(0).getGraphElementAt(i);

            //sprawdzamy czy element jest wierzchołkiem
            if (graphElementAt instanceof GXLNode) {

                GXLNode graphNode = (GXLNode)graphElementAt;

                //dodajemy jego id/nazwę do kolejkcji
                idNode.add(graphNode.getID());

                //jeżeli wierzchołek jeszcze nie jest w kolejkcji mapowania to dodajemy go
                if(!_mapa.containsKey(graphNode.getID()))
                {
                    _mapa.put(graphNode.getID(),new ArrayList<String>());
                }
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

        //wyświetlam listę sąsiedztwa
        for (Map.Entry<String, ArrayList<String>> entry : _mapa.entrySet()) {
            String startNode = entry.getKey();
            ArrayList<String> targetNodes = entry.getValue();

            System.out.printf(startNode + " : ");

            for (String targetNode : targetNodes) {
                System.out.printf(targetNode + " ");
            }

            System.out.printf("\n");
        }


        //utworzenie listy sąsiedztwa...
        Map<String,ListNode> listMap = new TreeMap<String,ListNode>();

        //utworzenie elementów listy dla wszystkich wierzchołków
        for (String node : idNode)
        {
            listMap.put(node,new ListNode(node));
        }

        //przypisanie elementom listy wierzchołków docelowych
        for (Map.Entry<String, ArrayList<String>> entry : _mapa.entrySet()) {
            String startNode = entry.getKey();
            ArrayList<String> targetNodes = entry.getValue();

            for (String targetNode : targetNodes) {
                listMap.get(startNode).addneighbours(listMap.get(targetNode));
            }
        }

        //Przechodzenie grafów w głąb
        DepthFirstSearch dfs = new DepthFirstSearch();

//        //wykorzystanie algorytmu rekurencyjnego
//        System.out.printf("\nDFS - algorytm rekurencyjny\n");
//        dfs.dfs(listMap.get(idNode.get(0)));
//
//        //reset widoczności wierzchołków dla kolejnego przechodzenia po grafie
//        for (String node : idNode)
//        {
//            listMap.get(node).visited = false;
//        }


        System.out.printf("\n\nDFS - algorytm ze stosem\n");

        //wykorzystanie algorytmu ze stosem
        dfs.dfsUsingStack(listMap.get(idNode.get(0)));

        //reset widoczności wierzchołków dla kolejnego przechodzenia po grafie
        for (String node : idNode)
        {
            listMap.get(node).visited = false;
        }


        System.out.printf("\n\nBFS - algorytm rekurencyjny\n");

        //Przechodzenie grafów wszerz
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        bfs.bfs(listMap.get(idNode.get(0)));
    }
}

