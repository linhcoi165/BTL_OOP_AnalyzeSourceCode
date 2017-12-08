import java.util.ArrayList;

public class Class {
    ArrayList<Attribute> attributes = new ArrayList<Attribute>();
    ArrayList<Method> methods = new ArrayList<Method>();
    Class parent;
    String name;
    boolean isInterface = false;
    boolean isAbstract = false;
    ArrayList<Class> generalization = new ArrayList<Class>();
    boolean seen = false;

    Class(ArrayList<Attribute> attributes, ArrayList<Method> methods, Class parent, String name) {
        this.attributes = attributes;
        this.methods = methods;
        this.parent = parent;
        this.name = name;
    }

    Class(String name) {
        this.name = name;
    }

    public void setSeen() {
        this.seen = true;
    }

    public void setInterface() {
        this.isInterface = true;
    }

    public void setAbstract() {
        this.isAbstract = true;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public String getStringAttributes() {
        String s = "<html>";
        for (Attribute att: attributes)
            s = s + att.toString() + "<br>";
        s += "</html>";
        return s;
    }

    public String getStringMethods() {
        String s = "<html>";
        for (Method m: methods)
            s = s + m.toString() + "<br>";
        s += "</html>";
        return s;
    }

    public void setMethods(ArrayList<Method> methods) {
        this.methods = methods;
    }

    public ArrayList<Method> getMethods() {
        return methods;
    }

    public void setParent(Class parent) {
        this.parent = parent;
    }

    public Class getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGeneralization(ArrayList<Class> generalization) {
        this.generalization = generalization;
    }

    public ArrayList<Class> getGeneralization() {
        return this.generalization;
    }

    @Override
    public String toString() {
        String s = "";
        if (isAbstract) s += "abstract ";
        if (isInterface) s+= "interface ";
        else s += "class ";
        s += name;
        if (this.parent != null) {
            s += " extends " + this.parent.getName() + " ";
        }

        for (Class x : generalization) {
            s = s + x.getName() + " ";
        }
        s += getStringAttributes();
        s = s + "\n";
        s += getStringMethods();
        return s;
    }
}
