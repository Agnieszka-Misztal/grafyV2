import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch {

    private Queue<ListNode> queue;

    private Queue<MatrixNode> queue2;
    private ArrayList<MatrixNode> nodes2=new ArrayList<MatrixNode>();

    public BreadthFirstSearch()

    {
        queue = new LinkedList<ListNode>();
        queue2 = new LinkedList<MatrixNode>();
    }

    public void bfs(ListNode node)
    {
        queue.add(node);
        node.visited=true;
        while (!queue.isEmpty())
        {
            ListNode element=queue.remove();
            System.out.print(element.data + " ");
            List<ListNode> neighbours=element.getNeighbours();
            for (int i = 0; i < neighbours.size(); i++) {
                ListNode n=neighbours.get(i);
                if(n!=null && !n.visited)
                {
                    queue.add(n);
                    n.visited=true;

                }
            }
        }
    }

    public void addNode(MatrixNode node)
    {
        nodes2.add(node);
    }

    public ArrayList<MatrixNode> findNeighbours(int adjacency_matrix[][],MatrixNode x)
    {
        int nodeIndex=-1;

        ArrayList<MatrixNode> neighbours=new ArrayList<MatrixNode>();
        for (int i = 0; i < nodes2.size(); i++) {
            if(nodes2.get(i).equals(x))
            {
                nodeIndex=i;
                break;
            }
        }

        if(nodeIndex!=-1)
        {
            for (int j = 0; j < adjacency_matrix[nodeIndex].length; j++) {
                if(adjacency_matrix[nodeIndex][j]==1)
                {
                    neighbours.add(nodes2.get(j));
                }
            }
        }
        return neighbours;
    }

    public void bfs(int adjacency_matrix[][], MatrixNode node)
    {
        queue2.add(node);
        node.visited=true;
        while (!queue2.isEmpty())
        {
            MatrixNode element=queue2.remove();
            System.out.printf(element.data + " ");
            ArrayList<MatrixNode> neighbours=findNeighbours(adjacency_matrix,element);
            for (int i = 0; i < neighbours.size(); i++) {
                MatrixNode n=neighbours.get(i);
                if(n!=null && !n.visited)
                {
                    queue2.add(n);
                    n.visited=true;

                }
            }

        }
    }
}
