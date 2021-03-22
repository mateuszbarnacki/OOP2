import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    static BufferedReader brIn = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        String[] arguments = getFileNames();
        try {
            Path path = FileSystems.getDefault().getPath(arguments[0]);
            BufferedReader br = Files.newBufferedReader(path);
            List<FuncArgs> firstFunction = getDataFromFile(br);
            path = FileSystems.getDefault().getPath(arguments[1]);
            br = Files.newBufferedReader(path);
            List<FuncArgs> secondFunction = getDataFromFile(br);
            List<FuncArgs> results = new ArrayList<>();
            double tempValue = 0.;
            if(firstFunction.size() == secondFunction.size()){
                for(int i = 0; i < firstFunction.size(); ++i){
                    if(firstFunction.get(i).getX() == secondFunction.get(i).getX()){
                        tempValue = firstFunction.get(i).getY() + secondFunction.get(i).getY();
                        FuncArgs funcArg = new FuncArgs(firstFunction.get(i).getX(), tempValue);
                        results.add(funcArg);
                    }else{
                        System.out.println("Difference in data sets!");
                        return;
                    }
                }
                saveData(results);
            }else{
                System.out.println("Difference in data sets!");
                return;
            }
        }catch(IOException e){
            System.out.println("IOException during opening files: " + e.getMessage());
            e.printStackTrace();
            return;
        } finally{
            try {
                if (brIn != null) brIn.close();
            } catch (IOException e) {
                System.out.println("Couldn't close input stream: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static String[] getFileNames(){
        String[] arguments = new String[2];
        System.out.println("Podaj sciezki dwoch plikow, kazda z nich zatwierdz przyciskiem \'Enter\': ");
        try {
            for(int i = 0; i < arguments.length; ++i){
                arguments[i] = brIn.readLine();
            }
        }catch(IOException e){
            System.out.println("getFileNames() IOException: " + e.getMessage());
            e.printStackTrace();
        }
        return arguments;
    }

    public static List<FuncArgs> getDataFromFile(BufferedReader br){
        String input = "";
        List<FuncArgs> data = new ArrayList<>();
        try {
            while ((input = br.readLine()) != null) {
                String[] temp = input.split(" ");
                FuncArgs funcArgs = new FuncArgs(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]));
                data.add(funcArgs);
            }
        } catch(IOException e){
            System.out.println("getDataFromFile() IOException: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                if(br != null) br.close();
            }catch(IOException e){
                System.out.println("getDataFromFile() couldn't close the buffer: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return data;
    }

    public static void saveData(List<FuncArgs> funcArgs){
        System.out.println("Podaj sciezke pliku wynikowego: ");
        try {
            String input = brIn.readLine();
            String[] data = input.split(" ");
            File file;
            while (true) {
                file = new File(data[0]);
                if (file.exists()) {
                    System.out.println("Taki plik już istnieje, czy chcesz go nadpisac? (\'tak\', \'nie\')");
                    input = brIn.readLine();
                    if (input.equalsIgnoreCase("tak")) {
                        writeData(data[0], funcArgs);
                        break;
                    } else {
                        System.out.println("Podaj sciezke pliku wynikowego: ");
                        input = brIn.readLine();
                        data = input.split(" ");
                    }
                } else {
                    writeData(data[0], funcArgs);
                    break;
                }
            }
        } catch (IOException e){
            System.out.println("Couldn't read filename in saveData(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void writeData(String filename, List<FuncArgs> funcArgs){
        BufferedWriter bw = null;
        try{
            bw = Files.newBufferedWriter(Paths.get(filename));
            Iterator<FuncArgs> iter = funcArgs.iterator();
            String output;
            while(iter.hasNext()){
                FuncArgs temp = iter.next();
                output = temp.getX() + " " + temp.getY() + "\n";
                bw.write(output);
            }
        }catch (IOException e){
            System.out.println("IOException in writeData(): " + e.getMessage());
            e.printStackTrace();
        }finally{
            try{
                if(bw != null) bw.close();
            }catch(IOException e) {
                System.out.println("Couldn't close the buffer in writeData(): " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

class FuncArgs{
    private double x;
    private double y;

    public FuncArgs(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }
}
