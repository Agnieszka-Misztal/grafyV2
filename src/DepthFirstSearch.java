import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch {
    // Initialize an arraylist of nodes to store all nodes
    ArrayList<MatrixNode> nodes = new ArrayList<>();

    // The Node class


    public void AddNode(MatrixNode node)
    {
        nodes.add(node);
    }

    // The method to find the neighbours of a given node using
    // using adjacency matrix.
    public ArrayList<MatrixNode> findNeighbours(int adjacency_matrix[][],MatrixNode x)
    {
        int nodeIndex=-1;

        ArrayList<MatrixNode> neighbours=new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            if(nodes.get(i).equals(x))
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
                    neighbours.add(nodes.get(j));
                }
            }
        }
        return neighbours;
    }


    // Recursive DFS
    public  void dfs(int adjacency_matrix[][], MatrixNode node)
    {

        System.out.print(node.data + " ");
        ArrayList<MatrixNode> neighbours=findNeighbours(adjacency_matrix,node);
        node.visited=true;
        for (int i = 0; i < neighbours.size(); i++) {
            MatrixNode n=neighbours.get(i);
            if(n!=null && !n.visited)
            {
                dfs(adjacency_matrix,n);
            }
        }
    }

    public  void dfsUsingStack(int adjacency_matrix[][], MatrixNode node)
    {
        Stack<MatrixNode> stack=new  Stack<>();
        stack.add(node);
        node.visited=true;
        while (!stack.isEmpty())
        {
            MatrixNode element=stack.pop();
            System.out.print(element.data + " ");

            ArrayList<MatrixNode> neighbours=findNeighbours(adjacency_matrix,element);
            for (int i = 0; i < neighbours.size(); i++) {
                MatrixNode n=neighbours.get(i);
                if(n!=null &&!n.visited)
                {
                    stack.add(n);
                    n.visited=true;

                }
            }
        }
    }

    public  void dfs(ListNode node)
    {
        System.out.print(node.data + " ");
        List<ListNode> neighbours=node.getNeighbours();
        node.visited=true;
        for (int i = 0; i < neighbours.size(); i++) {
            ListNode n=neighbours.get(i);
            if(n!=null && !n.visited)
            {
                dfs(n);
            }
        }
    }

    public  void dfsUsingStack(ListNode node)
    {
        Stack<ListNode> stack=new  Stack<ListNode>();
        stack.add(node);
        node.visited=true;
        while (!stack.isEmpty())
        {
            ListNode element=stack.pop();
            System.out.print(element.data + " ");

            List<ListNode> neighbours=element.getNeighbours();
            for (int i = 0; i < neighbours.size(); i++) {
                ListNode n=neighbours.get(i);
                if(n!=null && !n.visited)
                {
                    stack.add(n);
                    n.visited=true;

                }
            }
        }
    }

    public void clearVisitedFlags() {
        for(int i = 0; i < nodes.size(); i++ ){
            nodes.get(i).visited = false;
        }
    }

}
