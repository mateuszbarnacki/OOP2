import java.math.BigInteger;
import java.lang.Integer;

class Main {
  public static void main(String[] args) {
    int testValue = Integer.parseInt(args[0]);
    KeyGenerator generator = new KeyGenerator();
    generator.codeNumber(testValue);
    System.out.println("Value before generating key: " + testValue);
    System.out.println("Decoded value: " +  generator.decodeNumber());
  }
}

class KeyGenerator{
  private BigInteger firstPrime;
  private BigInteger secondPrime;
  private BigInteger result;
  private BigInteger fiNNumber;
  private BigInteger eNumber;
  private BigInteger dNumber;
  
  private BigInteger codedNumber;

  public KeyGenerator(){
    this.firstPrime = new BigInteger("397");
    this.secondPrime = new BigInteger("103");
    this.result = this.firstPrime.multiply(this.secondPrime);
    this.firstPrime = new BigInteger("396");
    this.secondPrime = new BigInteger("102");
    this.fiNNumber = this.firstPrime.multiply(this.secondPrime);
    BigInteger tempVal = new BigInteger("1");
    for(int i = 3; i < 100; ++i){
      if(this.fiNNumber.gcd(tempVal.valueOf((long)i)).intValue() == 1){
          this.eNumber = new BigInteger(Integer.toString(i));
          break;
      }
    }
    this.dNumber = extendedEuclid();
    System.out.println("Public key (e, n): " + this.eNumber + " " + this.result);
    System.out.println("Private key (d, n): " + this.dNumber + " " + this.result);
  }

  private BigInteger extendedEuclid(){
    int x0 = 1;
    int x = 0;
    int q, temp;
    BigInteger b0 = new BigInteger(this.eNumber.toString());
    BigInteger eCopy = new BigInteger(this.eNumber.toString());
    BigInteger fiCopy = new BigInteger(this.fiNNumber.toString());
    while(eCopy.intValue() != 0){
      q = (fiCopy.divide(eCopy)).intValue();
      temp = x;
      x = x0 - q * x;
      x0 = temp;
      temp = (fiCopy.mod(eCopy)).intValue();
      fiCopy = eCopy;
      eCopy = new BigInteger(Integer.toString(temp));
    }

    if(x0 < 0){
        x0 = x0 + b0.intValue();
    }

    BigInteger big = new BigInteger(Integer.toString(x0));

    return big;
  }

  public void codeNumber(int num){
    BigInteger tempVal = new BigInteger(Integer.toString(num));
    this.codedNumber = tempVal.modPow(this.eNumber, this.result);
  }

  public int decodeNumber(){
    int res = -1;
    res = (this.codedNumber.modPow(this.dNumber, this.result)).intValue();
    return res;
  }

}