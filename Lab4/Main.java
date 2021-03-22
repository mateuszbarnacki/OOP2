import java.util.Random;

class Main {
  public static void main(String[] args) {
    test();
  }

  public static void test(){
    long a, b;
    BigInt first, second;
    Random random = new Random();
    for(int i = 0; i < 1000; ++i){
      a = -1L;
      b = -1L;
      while(a < 0){
        a = random.nextLong();
      }
      while(b < 0){
        b = random.nextLong();
      }
      first = new BigInt(Long.toString(a));
      second = new BigInt(Long.toString(b));
      System.out.println(a + " + " + b + " = " + first.add(second));
    }
    long test1 = 1L;
    long test2 = 999999999999999999L;
    first = new BigInt(Long.toString(test1));
    second = new BigInt(Long.toString(test2));
    System.out.println(test1 + " + " + test2 + " = " + first.add(second));
    System.out.println(test1 + " + " + test2 + " = " + second.add(first));
  }
}

class BigInt{
  private final byte[] value;

  public BigInt(String obj){
    if(isValid(obj)){
      byte[] val = obj.getBytes();
      this.value = val;
    }else{
      System.out.println("Couldn't create BigInt object");
      this.value = null;
    }
  }

  public BigInt(byte[] obj){
    byte[] val = obj;
    this.value = val;
  }

  public BigInt(BigInt obj){
    this(obj.getNum());
  }

  public byte[] getNum(){
    return this.value;
  }

  public BigInt add(BigInt obj){
    byte[] result;
    if(this.value == null || obj == null){
      System.out.println("Couldn't add objects");
      return null;
    }

    if(this.value.length >= obj.getNum().length){
      result = getResult(this.value, obj.getNum());
    }else{
      result = getResult(obj.getNum(), this.value);
    }

    BigInt res = new BigInt(result);
    return res; 
  }

  //Jesteśmy pewni, że pierwszy argument jest dłuższy/równy od drugiego - if w metodzie add 
  private byte[] getResult(byte[] first, byte[] second){
    int length = first.length;
    byte[] result = new byte[length];
  
    int diff = first.length - second.length;
    byte[] newObj = new byte[length];
    
    for(int i = 0; i < diff; ++i){
      newObj[i] = (byte) 0;
    } 
    for(int i = diff; i < length; ++i){
      newObj[i] = second[i-diff];  
    } 
    
    int element = 0;
    int plusOne = 0;
    int tempVal;
    int firstNum = 0;
    for(int i = length-1; i >= 0; --i){
      if(newObj[i] >= 48){
        element = newObj[i] - 48;
      }
      tempVal = first[i] + element + plusOne;
      plusOne = 0;
      if(tempVal >= 58){
        tempVal -= 10;
        plusOne = 1;
        if(i == 0){
          firstNum = 1;
        }
      }
      result[i] = (byte) tempVal;
      element = 0;
    }

    if(firstNum == 1){
      byte[] newResult = new byte[length+1];
      newResult[0] = (byte) 49;
      for(int i = 0; i < length; ++i){
        newResult[i+1] = result[i];
      }
      result = newResult;
    }

    return result;
  }

  private boolean isValid(String obj){
    boolean isNum = true;
    char element;
    for(int i = 0; i < obj.length(); ++i){
      element = obj.charAt(i);
      if(!Character.isDigit(element)){
        isNum = false;
      }
    }
    return isNum;
  }

  @Override
  public boolean equals(Object obj){
    if(this.getClass() == obj.getClass()){
      Boolean isSame = true;
      BigInt numObj = (BigInt) obj;
      for(int i = 0; i < this.value.length; ++i){
        if(this.value[i] != numObj.getNum()[i]){
          isSame = false;
        }
      }
      return isSame;
    }
    return false;
  }

  @Override
  public String toString(){
    return new String(this.value);
  }
}