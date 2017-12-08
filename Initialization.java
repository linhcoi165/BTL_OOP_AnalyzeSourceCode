import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

public class Initialization {
    public static void readFile(ArrayList<Class> listOfClass) throws Exception {
        String pathname = "C:\\Users\\User\\Downloads\\test2";
        File folder = new File(pathname);
        File[] listOfFile = folder.listFiles();

        for (File file : listOfFile) {
            if (file.isFile()) {
                String pathOfFile = pathname + "\\" + file.getName();
                FileReader fileReader = new FileReader(new File(pathOfFile));

                BufferedReader reader = new BufferedReader(fileReader);

                String line = null;
                Stack<String> stack = new Stack<String>();
                boolean isInMethod = false;

                while ((line = reader.readLine()) != null) {
                    line = line.replace("(", " ( ");
                    line = line.replace(")", " ) ");
                    line = line.replace(";", " ;");
                    line = line.replace("{", " { ");
                    line = line.replace("}", " }");
                    line = line.replace(",", " , ");
                    line = line.replace("=", " = ");
                    line = line.replaceAll("\\s+", " ");
                    line = line.trim();
                    String[] listWord;
                    listWord = line.split(" ");

                    if ((listWord.length >= 2) && (!isInMethod) && (!listWord[0].equals("import"))) {
                        String lastWord = listWord[listWord.length - 1];
                        if (line.contains("class") || line.contains("interface")) {
                            Analyze.burnClass(listWord, listOfClass);
                        } else if (lastWord.endsWith(";")) {
                            if (line.contains("abstract")  || listOfClass.get(listOfClass.size()-1).isInterface) {
                                Analyze.burnMethod(line, listWord, listOfClass);
                            } else Analyze.burnAttribute(line, listWord, listOfClass);
                        } else if (lastWord.endsWith(")") || lastWord.endsWith("{") || lastWord.endsWith("}")) {
                            Analyze.burnMethod(line, listWord, listOfClass);
                        }
                    }
                    if (line.indexOf('{') != -1) {
                        stack.push("{");
                    }

                    if (line.indexOf('}') != -1) {
                        stack.pop();
                        isInMethod = false;
                    }

                    if (stack.size() > 1) isInMethod = true;
                }
                reader.close();
            }
        }
    }
}
