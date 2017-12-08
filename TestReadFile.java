import javax.swing.*;
import java.awt.*;
import java.util.*;

public class TestReadFile {
    public  static  void main (String[] args) throws Exception {
        try {
            ArrayList<Class> listOfClass = new ArrayList<Class>();
            ArrayList<NodeGUI> listNode = new ArrayList<NodeGUI>();

            Initialization.readFile(listOfClass);

            Analyze.setWeightNode(listOfClass, listNode);
            Analyze.setHasA(listNode);

            /*for (Class ex : listOfClass) {
                System.out.print(ex.toString());
                System.out.println("\n");
            }*/
            /*for (NodeGUI node: listNode) {
                System.out.println(node.toString());
                System.out.println("\n");
            }*/
            /*for (NodeGUI node : listNode) {
                System.out.println(node.getName() + ": " + node.getWeight());
            }*/

            ClassDiagram uml = new ClassDiagram(listNode);
            uml.setPreferredSize(new Dimension(350*listNode.size(), 800));
            JScrollPane scrollPane = new JScrollPane(uml);

            JFrame frame = new JFrame();
            frame.add(scrollPane);
            frame.setVisible(true);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
