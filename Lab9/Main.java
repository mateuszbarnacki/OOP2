import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

class Main {
  public static void main(String[] args) {
   try{
     String[] values = splitArgs(args[0]);
     try{
        Class<?> mat = Class.forName("java.lang.Math");
        if((values.length == 1) || (values.length > 3)){
          System.out.println("Zla liczba argumentow funkcji! Podaj jedna lub dwie liczby.");
          return;
        }else{
          for(int i = 1; i < values.length; ++i){
            if(!isValid(values[i])){
              System.out.println("Argumenty funkcji musza byc liczbami!");
              return;
            }
          }
          Class<?>[] argsTypes = new Class<?>[values.length-1];
          for(int i = 0; i < values.length-1; ++i){
            argsTypes[i] = Double.TYPE;
          }
          try{
            Method method = mat.getMethod(values[0], argsTypes);
              Object obj = new Object();
              Object[] methArgs = new Object[values.length-1];
              for(int i = 1; i < values.length; ++i){
                methArgs[i-1] = Double.parseDouble(values[i]);
              }
              try{
                Object returnObj = method.invoke(obj,methArgs);
                Double returnValue = (Double)returnObj;
                System.out.println("Wynik: " + returnValue);
              }catch(IllegalAccessException | InvocationTargetException e){
                System.out.println(e.getMessage());
                return;
              }
          }catch(NoSuchMethodException | SecurityException e){
            System.out.println("Nie ma takiej metody!");
            return;
          }
          
        } 
      }catch(ClassNotFoundException e){
        System.out.println("ClassNotFoundException: " + e.getMessage());
        return;
      }
   }catch(ArrayIndexOutOfBoundsException e){
     System.out.println("Nie podano nic do obliczenia!");
     return;
   }
   
 }

  public static String[] splitArgs(String arg){
     return Arrays.stream(arg.split("[\\s+(),]")).filter(w -> w.isEmpty() == false).toArray(String[]::new);
  }

  public static boolean isValid(String str){
    int numOfDots = 0;
    boolean isNum = true;
    for(int i = 0; i < str.length(); ++i){
      if((str.charAt(i) < '0') || (str.charAt(i) > '9')){
        if(str.charAt(i) == '.'){
          numOfDots++;
        }else{
          isNum = false;
        }
      }
    }
    if(numOfDots > 1){
      isNum = false;
    }
    return isNum;
  }
}