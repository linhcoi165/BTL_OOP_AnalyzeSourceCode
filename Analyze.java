import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Analyze {

    public static void burnClass(String[] listWord, ArrayList<Class> classList) {
        for (int i = 0; i < listWord.length; i++) {
            if (listWord[i].equals("class")) {
                if (i > 0 && listWord[i-1].equals("abstract")) {
                    classList.add(new Class(listWord[i+1]));
                    classList.get(classList.size() - 1).setAbstract();
                } else classList.add(new Class(listWord[i+1]));
            }
            else if (listWord[i].equals("interface")) {
                classList.add(new Class(listWord[i+1]));
                classList.get(classList.size()-1).setInterface();
            }

            if (listWord[i].equals("extends") && !listWord[i+1].startsWith("J")) {
                classList.get(classList.size()-1).setParent(new Class(listWord[i+1]));
            }

            if (listWord[i].equals("implements")) {
                i++;
                while (!listWord[i].equals("{") && !listWord[i].equals("throws")) {
                    if (!listWord[i].equals(",")) {
                        classList.get(classList.size()-1).generalization.add(new Class(listWord[i]));
                    }
                    i++;
                }
            }
        }
    }

    public static void burnAttribute(String line, String[] listWord, ArrayList<Class> classList) {
        String scope = "";
        String type = "";
        String name = "";
        String value = "";
        boolean isStatic = false;
        boolean isFinal = false;
        if (line.indexOf('=') != -1) {
            int posOfEqualSign = 0;
            for (int i = 0; i < listWord.length; i++) {
                if (listWord[i].equals("=")) {
                    posOfEqualSign = i;
                    break;
                }
            }
            if (posOfEqualSign > 1) {
                name += listWord[posOfEqualSign - 1];
                type += listWord[posOfEqualSign - 2];
            }
            if (line.contains("new")) {
                for (int i = posOfEqualSign+1; i < listWord.length-1; i++)
                    value += listWord[i];
            }
            else {
                value += listWord[posOfEqualSign+1];
            }
            for (int i = 0; i < posOfEqualSign; i++) {
                if (listWord[i].equals("public")) {
                    scope += "public";
                }
                if (listWord[i].equals("private")) {
                    scope += "private";
                }
                if (listWord[i].equals("static")) {
                    isStatic = true;
                }
                if (listWord[i].equals("final")) {
                    isFinal = true;
                }
            }
        } else {
            name += listWord[listWord.length-2];
            type += listWord[listWord.length-3];
            for (int i = 0; i < listWord.length-1; i++) {
                if (listWord[i].equals("public")) {
                    scope += "public";
                }
                if (listWord[i].equals("private")) {
                    scope += "private";
                }
                if (listWord[i].equals("static")) {
                    isStatic = true;
                }
            }
        }
        Attribute attribute = new Attribute(scope, type, name, value);
        if (isStatic) attribute.setStatic();
        if (isFinal) attribute.setFinal();
        classList.get(classList.size()-1).getAttributes().add(attribute);
    }

    public static void burnMethod(String line, String[] listWord, ArrayList<Class> classList) {
        String scope = "";
        String type = "";
        String name = "";
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        boolean isStatic = false;
        boolean isAbstract = false;
        int posOfOpen = 0;
        for (int i = 0; i < listWord.length; i++) {
            if (listWord[i].equals("(")) {
                posOfOpen = i;
                break;
            }
        }
        for (int i = 0; i < posOfOpen; i++) {
            if (listWord[i].equals("public")) {
                scope += "public";
            }
            else if (listWord[i].equals("private")) {
                scope += "private";
            }
            else if (listWord[i].equals("static")) {
                isStatic = true;
            }
            else if (listWord[i].equals("abstract")) {
                isAbstract = true;
            }
            else if (listWord[i].equals(classList.get(classList.size()-1).getName())) {
                name += listWord[i];
            } else {
                if (i != posOfOpen-1) {
                    type += listWord[i];
                    name += listWord[i + 1];
                }
            }
        }
        parameters = burnArgument(listWord);
        Method method = new Method(scope, type, name, parameters);
        if (isStatic) method.setStatic();
        if (isAbstract) method.setAbstract();
        classList.get(classList.size()-1).getMethods().add(method);
    }

    private static ArrayList<Parameter> burnArgument(String[] listWord) {
        int posOfBegin = 0;
        int posOfEnd = 0;
        ArrayList<Parameter> arg = new ArrayList<Parameter>();
        for (int i = 0; i < listWord.length; i++) {
            if (listWord[i].equals("(")) posOfBegin = i;
            if (listWord[i].equals(")")) posOfEnd = i;
        }
        if (posOfEnd - posOfBegin >= 2) {
            for (int i = posOfBegin+1; i < posOfEnd-1; i+=2) {
                if (!listWord[i].equals(","))
                    arg.add(new Parameter(listWord[i], listWord[i+1]));
                else i--;
            }
        }
        return arg;
    }

    private static int getIndex(ArrayList<NodeGUI> listNode, Class parent) {
        int index = 0;
        for (int i = 0; i < listNode.size(); i++) {
            if (listNode.get(i).getName().equals(parent.getName())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static void setWeightNode(ArrayList<Class> listClass, ArrayList<NodeGUI> listNode) {
        Queue<Class> queue = new LinkedList<Class>();
        for (Class c: listClass) {
            if (c.getParent() == null)
                queue.add(c);
        }
        while (!queue.isEmpty()) {
            Class x = queue.remove();
            x.setSeen();
            NodeGUI n = new NodeGUI(x.getName());
            n.setContent(x);
            if (x.getParent() == null && x.generalization.size()==0) n.setWeight(0);
            else if (x.getGeneralization() != null){
                n.setParent(listNode.get(getIndex(listNode, x.getParent())));
                n.setWeight(n.getParent().getWeight() + 1);
                if (x.generalization.size() != 0) {
                    for (Class p: x.generalization) {
                        n.getImp().add(listNode.get(getIndex(listNode, p)));
                    }
                }
            } else {
                n.setWeight(1);
                if (x.generalization.size() != 0) {
                    for (Class p: x.generalization) {
                        n.getImp().add(listNode.get(getIndex(listNode, p)));
                    }
                }
            }
            listNode.add(n);
            for (Class a: listClass) {
                if (a.getParent() != null && a.getParent().getName().equals(x.getName()) && !a.seen)
                    queue.add(a);
            }
        }
    }

    public static void setHasA(ArrayList<NodeGUI> listNode) {
        for (NodeGUI n: listNode) {
            for (Attribute a: n.getContent().getAttributes()) {
                for (NodeGUI node : listNode) {
                    if (node != n && a.getType().contains(node.getName())) {
                        n.getCompositionChildren().add(node);
                    }
                }
            }
        }
    }
}
