import java.lang.String;

class Main {
  public static void main(String[] args) {
    Result result = new Result(args[0]);
    result.algorithm();
    System.out.println(result.getResult());
  }
}

class StackOverflowException extends Exception{
  public StackOverflowException(String str){
    super(str);
  }
}

class StackUnderflowException extends Exception{
  public StackUnderflowException(String str){
    super(str);
  }
}

class Stack<T>{
  private final T[] arr;
  private int maxSize;
  private int pointer;

  public Stack(int max){
    @SuppressWarnings("unchecked")
    final T[] stack = (T[]) new Object[max];
    this.arr = stack;
    this.maxSize = max;
    this.pointer = 0;
  }

  public boolean isEmpty(){
    if(this.pointer == 0) return true;
    return false;
  }

  public boolean isFull(){
    if(this.pointer == maxSize) return true;
    return false;
  }

  public void push(T x) throws StackOverflowException{
    if(this.pointer+1 > maxSize) throw new StackOverflowException("Pełny stos");
    else this.arr[this.pointer++] = x;
  }

  public T pop() throws StackUnderflowException {
    T temp = null;
    if(this.pointer-1 >= 0) temp = this.arr[this.pointer-1];
    else throw new StackUnderflowException("Pusty stos");
    this.arr[this.pointer-1] = null;
    this.pointer -= 1;
    return temp;
  }

  public int getPointer(){
    return this.pointer;
  }

  public String toString(){
    String str = "";
    for(T element : arr){
      str += element;
      str += " ";
    }
    return str;
  }
}

class Result{
  private Stack<String> stack; 
  private String res;
  private String[] expression;

  public Result(String str){
    this.stack = new Stack<String>(30);
    this.res = "";
    this.expression = str.split("");
  }

  public String getResult(){
    return this.res;
  }

  public void algorithm(){
    for(int i = 0; i < this.expression.length; ++i){
      if((this.expression[i].charAt(0) != '+') && (this.expression[i].charAt(0) != '-') && (this.expression[i].charAt(0) != '*') && (this.expression[i].charAt(0) != '/')){
        try{
          this.stack.push(this.expression[i]);
        }catch(StackOverflowException e){
          System.out.println(e.getMessage());
        }
      } else {
        try{
          String secArg = this.stack.pop();
          String firstArg = this.stack.pop();
          String temp = "(" + firstArg + this.expression[i] + secArg + ")";
          this.stack.push(temp);
        } catch(StackUnderflowException e){
          System.out.println("BLAD DANYCH WEJSCIOWYCH! Na stosie jest za malo elementow, zeby wykonac operacje!");
        } catch(StackOverflowException e){
          System.out.println(e.getMessage());
        }
      }
    }

    String ans = "";
    try{
      ans = this.stack.pop();
    }catch(StackUnderflowException e){
          e.getMessage(); 
    }
    this.res = "";
    if(!stack.isEmpty()){
      String tmp = "BLAD DANYCH WEJSCIOWYCH! Koniec algorytmu, a stos nie jest pusty: ";
      while(!stack.isEmpty()){
        try{
          tmp = tmp + (this.stack.pop() + ", ");
        }catch(StackUnderflowException e){
          e.getMessage(); 
        }
      }
      this.res = tmp;
    }
    this.res = this.res + ans;  
  }
}