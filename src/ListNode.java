import java.util.ArrayList;
import java.util.List;

public class ListNode {

    String data;
    boolean visited;
    List<ListNode> neighbours;

    ListNode(String data)
    {
        this.data=data;
        this.neighbours=new ArrayList<>();
    }
    public void addneighbours(ListNode neighbourNode)
    {
        this.neighbours.add(neighbourNode);
    }
    public List getNeighbours() {
        return neighbours;
    }
    public void setNeighbours(List neighbours) {
        this.neighbours = neighbours;
    }
}
