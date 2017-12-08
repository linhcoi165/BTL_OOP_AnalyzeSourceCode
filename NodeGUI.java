import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class NodeGUI {
    private String name;
    private Class content;
    private NodeGUI parent;
    private ArrayList<NodeGUI> imp = new ArrayList<NodeGUI>();
    private ArrayList<NodeGUI> compositionChildren = new ArrayList<NodeGUI>();
    private int weight;
    public int x = 0, y = 0, height = 160, width = 200;

    final static float dash[] = {5.0f};
    final static BasicStroke dash1 = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

    NodeGUI(String name) {
        this.name = name;
    }

    NodeGUI(String name, NodeGUI parent, Class content) {
        this.content = content;
        this.name = name;
        this.parent = parent;
    }

    public ArrayList<NodeGUI> getCompositionChildren() {
        return compositionChildren;
    }

    public ArrayList<NodeGUI> getImp() {
        return imp;
    }

    public void setImp(ArrayList<NodeGUI> imp) {
        this.imp = imp;
    }

    public void setContent(Class content) {
        this.content = content;
    }

    public Class getContent() {
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParent(NodeGUI parent) {
        this.parent = parent;
    }

    public NodeGUI getParent() {
        return parent;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public JPanel createPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 1));
        p.setBounds(x, y, width, height);

        String s = "";
        if (this.content.isInterface) s = s + "<html><i> interface <br> " + this.name + " </i></html>";
        else if (this.content.isAbstract) s = s + "<html><i> " + this.name + " </i></html>";
        else s += this.name;
        JLabel className = new JLabel(s);
        className.setHorizontalAlignment(JLabel.CENTER);
        className.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        p.add(className);

        JTextPane attributes = new JTextPane();
        attributes.setContentType("text/html");
        attributes.setText(this.content.getStringAttributes());
        attributes.setEditable(false);
        //attributes.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

        JTextPane methods = new JTextPane();
        methods.setContentType("text/html");
        methods.setText(this.content.getStringMethods());
        methods.setEditable(false);
        //methods.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

        JScrollPane spA = new JScrollPane(attributes);
        JScrollPane spM = new JScrollPane(methods);
        p.add(spA);
        p.add(spM);
        return p;
    }

    public void drawANode(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
        if (this.weight == 0) {
            g2d.drawRect(x, y, width, height);
            g2d.drawRect(x, y, width, 30);
            g2d.drawRect(x, y, width, 40 + content.getAttributes().size()*20);

            String nodeName = "";
            if (content.isInterface) nodeName = nodeName + "interface " + name;
            else nodeName = name;
            g2d.drawString(nodeName, x + (width - name.length()*10)/2, y + 20);
            int posY = y + 20;
            for (Attribute a: content.getAttributes()) {
                posY += 20;
                g2d.drawString(a.toString(), x + 5, posY + 5);
            }

            posY += 20;
            for (Method m: content.getMethods()) {
                posY += 20;
                g2d.drawString(m.toString(), x + 5, posY + 5);
            }
        } else {
            g2d.drawRect(x, y, width, height);
            g2d.drawRect(x, y, width, 30);
            g2d.drawRect(x, y, width, 40 + content.getAttributes().size()*20);

            String nodeName = "";
            if (content.isInterface) nodeName = nodeName + "interface " + name;
            else nodeName = name;
            g2d.drawString(nodeName, x + (width - name.length()*10)/2, y + 20);
            int posY = y + 20;
            for (Attribute a: content.getAttributes()) {
                posY += 20;
                g2d.drawString(a.toString(), x + 5, posY + 5);
            }

            posY += 20;
            for (Method m: content.getMethods()) {
                posY += 20;
                g2d.drawString(m.toString(), x + 5, posY + 5);
            }
            if (parent != null) {
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(parent.x + parent.width/2, parent.y + parent.height, parent.x + parent.width/2 - 10, parent.y + parent.height+10);
                g2d.drawLine(parent.x + parent.width/2, parent.y + parent.height, parent.x + parent.width/2 + 10, parent.y + parent.height+10);
                g2d.drawLine(parent.x + parent.width/2 -10, parent.y + parent.height + 10, parent.x + parent.width/2 + 10, parent.y + parent.height+10);
                g2d.drawLine(parent.x + parent.width/2, parent.y + parent.height + 10, x + width/2, y);
            }
            if (imp.size() > 0) {
                for (NodeGUI n: imp) {
                    g2d.setStroke(dash1);
                    g2d.drawLine(x + width/2, y, n.x + n.width/2, n.y + n.height + 10);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(n.x + n.width/2, n.y + n.height, n.x + n.width/2 - 10, n.y + n.height + 10);
                    g2d.drawLine(n.x + n.width/2 + 10, n.y + n.height + 10, n.x + n.width/2 - 10, n.y + n.height + 10);
                    g2d.drawLine(n.x + n.width/2, n.y + n.height, n.x + n.width/2 + 10, n.y + n.height + 10);
                }
            }
            g2d.setStroke(new BasicStroke(1));
            if (compositionChildren.size() != 0) {
                for (NodeGUI n: compositionChildren) {
                    g2d.drawLine(n.x + n.width/2 + 50, n.y + n.height, n.x + n.width/2 + 50 - 10, n.y + n.height + 10);
                    g2d.drawLine(n.x + n.width/2 + 50, n.y + n.height, n.x + n.width/2 + 50 + 10, n.y + n.height + 10);
                    g2d.drawLine(n.x + n.width/2 + 50, n.y + n.height + 20, n.x + n.width/2 + 50 - 10, n.y + n.height + 10);
                    g2d.drawLine(n.x + n.width/2 + 50, n.y + n.height + 20, n.x + n.width/2 + 50 + 10, n.y + n.height + 10);
                    if (this.getWeight() == n.getWeight()) {
                        g2d.drawLine(x + width/2, y + height, x + width/2, y + height + 40);
                        g2d.drawLine(x + width/2, y + height + 40, n.x + n.width/2 + 50, n.y + n.height + 40);
                        g2d.drawLine(n.x + n.width/2 + 50, n.y + n.height + 20, n.x + n.width/2 + 50, n.y + n.height + 40);
                    }
                    else g2d.drawLine(x + width/2, y, n.x + n.width/2 + 50, n.y + n.height + 20);
                }
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        s = s + this.name + ": " + this.weight + "\n";
        if (this.compositionChildren.size() != 0) {
            s += "Has: ";
            for (NodeGUI n: this.compositionChildren) {
                s += n.name + ", ";
            }
        }
        return s;
    }
}
