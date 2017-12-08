import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClassDiagram extends JPanel{
    ArrayList<NodeGUI> listNode = new ArrayList<NodeGUI>();

    public ClassDiagram(ArrayList<NodeGUI> listNode) {
        this.listNode = listNode;
    }

    private void setNode() {
        int count = 0;
        int weight = 0;

        for (NodeGUI n: listNode) {
            if (n.getWeight() == weight) {
                count++;
                n.x = n.width*count*2 - 80*count;
            } else {
                weight++;
                count = 0;
            }
            n.height = 60 + n.getContent().getAttributes().size()*20 + n.getContent().getMethods().size()*20;
            n.y = n.getWeight() * (n.height + 80);
            /*JPanel p = n.createPanel();
            frame.add(p);*/
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setNode();
        for (NodeGUI n: listNode) {
            n.drawANode(g);
        }
    }
}
