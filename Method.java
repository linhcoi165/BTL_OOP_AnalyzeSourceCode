import java.util.ArrayList;

public class Method {
    String scope;
    String type;
    String name;
    boolean isStatic = false;
    boolean isAbstract = false;
    ArrayList<Parameter> parameters = new ArrayList<Parameter>();

    public Method(String scope, String type, String name, ArrayList<Parameter> parameters) {
        this.name = name;
        this.scope = scope;
        this.type = type;
        this.parameters = parameters;
    }

    public Method(String scope, String type, String name) {
        this.scope = scope;
        this.type = type;
        this.name = name;
    }

    public Method(String type, String name) {
        this.type = type;
        this.name = name;
    }

    Method(String name) {
        this.name = name;
    }

    public void setAbstract() { isAbstract = true; }
    public void setStatic() {
        isStatic = true;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public void setArguments(ArrayList<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String detail = "";
        if (scope.equals("public")) scope = "+";
        else if (scope.equals("private")) scope = "-";
        else if (scope.equals("protected")) scope = "#";
        else scope = "~";

        if (isStatic) {
            detail = detail + this.scope + " static " + this.type + " " + this.name + "(";
        }
        else if (isAbstract) {
            detail = detail + this.scope + " abstract " + this.type + " " + this.name + "(";
        } else {
            detail = detail + this.scope + " " + this.type + " " + this.name + "(";
        }
        for (int i = 0; i < parameters.size(); i++) {
            detail = detail + parameters.get(i).toString() + ((i != parameters.size() - 1) ? ", " : "");
        }
        detail = detail + ")";
        return detail;
    }
}
