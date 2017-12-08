public class Attribute {
    String scope;
    String type;
    String name;
    String value;
    boolean isStatic = false;
    boolean isFinal = false;

    public Attribute(String scope, String type, String name) {
        this.scope = scope;
        this.type = type;
        this.name = name;
    }

    public Attribute(String scope, String type, String name, String value) {
        this.scope = scope;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public Attribute(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public void setFinal() { isFinal = true; }
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

    public String getType(){
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (scope.equals("public")) scope = "+";
        else if (scope.equals("private")) scope = "-";
        else if (scope.equals("protected")) scope = "#";
        else scope = "~";
        if (this.value.equals("")) {
            if (isStatic) {
                return this.scope + " static " + this.type + " " + this.name;
            } else {
                return this.scope + " " + this.type + " " + this.name;
            }
        } else {
            if (isStatic) {
                return this.scope + " static " + this.type + " " + this.name + " = " + this.value;
            } else {
                return this.scope + " " + this.type + " " + this.name + " = " + this.value;
            }
        }
    }
}
