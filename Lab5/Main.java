import java.lang.StringBuilder;

class Main {
  public static void main(String[] args) {
   Man[] staff = new Man[6];
        staff[0] = new Student("Jan", "Kowalski", 211152, 3.5, 4.0, 5.0);
        staff[1] = new Student("Marek", "Styczen", 211150, 4.5, 3.5, 5.0);
        staff[2] = new Graduate("inz.", "Franciszek", "Nowak", 223123, 2018, 5.0, 4.5, 4.0);
        staff[3] = new Graduate("mgr inz.", "Lukasz", "Maj", 200122, 2012, 5.0, 4.5, 3.0);
        staff[4] = new Dean("prof.", "Wojciech", "Luzny", 2008, 2012);
        staff[5] = new Dean("prof.", "Janusz", "Wolny", 2012, 2020);
        
        printStaff(staff);

        System.out.println();
        System.out.println(staff[0].compare(staff[1]));
        System.out.println(staff[1].compare(staff[0]));
        System.out.println(staff[1].compare(staff[2]));
        System.out.println(staff[1].compare(staff[4]));

        System.out.println();
        System.out.println(staff[4].compare(staff[5]));
        System.out.println(staff[5].compare(staff[4]));
        System.out.println(staff[5].compare(staff[0]));
  }

  public static void printStaff(Man[] tab) {
        for (var c : tab)
            if (c != null) {
                System.out.println(c.getClass().getName() + ": " + c);
                System.out.println("  Average = " + c.average());
                System.out.println();
            } else
                break;
    }
}

abstract class Man {
    private final String name;
    private final String surname;

    // DOPISAC: Konstruktor
    public Man(String name, String surname){
      this.name = name;
      this.surname = surname;
    }
    // DOPISAC: Metody typu get
    public String getName(){
      return this.name;
    }

    public String getSurname(){
      return this.surname;
    }
    // DOPISAC: toString zwracajacy lancuch z imieniem i nazwiskiem
    public String toString(){
      return this.name + " " + this.surname;
    }

    abstract public Man compare(Man ob);

    public double average() {
        return 0.0;
    }
}

/**
* Klasa Student dziedzicz??ca po klasie Man.
* @author Mateusz Barnacki
*/
class Student extends Man {
  /** Zmienna przechowuj??ca numer indeksu. */
  private final int indexNumber;
  /** Tablica przechowuj??ca warto??ci ocen*/ 
  private final double[] grades;

  /**Konstruktor klasy Student */
  public Student(String name, String surname, int idxNumber, double firstGrade, double secondGrade, double thirdGrade){
    /** Wywo??anie konstruktora klasy Man */
    super(name, surname);
    this.indexNumber = idxNumber;
    this.grades = new double[3];
    this.grades[0] = firstGrade;
    this.grades[1] = secondGrade;
    this.grades[2] = thirdGrade; 
  }

  /** Metoda obliczaj??ca warto???? ??redni??. 
      @return warto???? ??rednia obliczona na bazie ocen umieszczonych wa tablicy
  */
  public double average(){
    double sum = 0.0;
    for(double element : this.grades){
      sum += element;
    }
    return (sum / this.grades.length);
  }

  /** Metoda por??wnuj??ca dwa obiekty odziedziczona z klasy Man.
      @param ob obiekt do por??wnania

      W celu uzyskania ??adnego outputu zgodnego z przyk??adowym mo??na odkomentowa?? zakomentowany fragment kodu i zakomentowa?? lini?? ni??ej, jednak przy takim rozwi??zaniu kod traci na uniwersalno??ci (po umieszczeniu klas w package'ach zmieni si?? nazwa klasy i kod nie b??dzie dzia??a?? prawid??owo). W zwi??zku z tym postanowi??em por??wnywa?? obiekty na podstawie warto??ci ??redniej.
      @return obiekt o wi??kszej ??redniej b??d?? null
  */

  public Man compare(Man ob){
    if(ob != null){
      //if(!ob.getClass().getName().equals("Dean"))
      if(ob.average() > 0.0){
        if(this.average() > ob.average()){
          return this;
        }
        return ob;
      }
      return null;
    }
    return this;
  } 

  /** Nadpisana metoda toString, StringBuilder u??yty ze wzgl??du na wi??ksz?? wydajno???? metody append() w stosunku do "+" w klasie String.
    @return napis sformatowany zgodnie z przyk??adowym outputem
  */

  public String toString(){
    StringBuilder str = new StringBuilder(super.toString());
    str.append(", id number: ");
    str.append(this.indexNumber);
    str.append(", grades: [");
    for(double element : this.grades){
      if(element != this.grades[this.grades.length-1]){
        str.append(element);
        str.append(", ");
      }else{
        str.append(element);
        str.append("]");
      }
    }

    return str.toString();
  }
}

/** Klasa przechowuj??ca dane absolwent??w */

class Graduate extends Student{
  /** Pole przechowuj??ce warto???? stopnia naukowego */
  private final String degree;
  /** Pole przechowuj??ce rok uko??czenia studi??w */
  private final int endYear;

/**Konstruktor klasy Graduate */
  public Graduate(String degree, String name, String surname, int idxNumber, int endYear, double firstGrade, double secondGrade, double thirdGrade){
    /** Wywo??anie konstruktora klasy Student */
    super(name, surname, idxNumber, firstGrade, secondGrade, thirdGrade);
    this.degree = degree;
    this.endYear = endYear;
  }

/** Nadpisana metoda toString, StringBuilder u??yty ze wzgl??du na wi??ksz?? wydajno???? metody append() w stosunku do "+" w klasie String.
    @return napis sformatowany zgodnie z przyk??adowym outputem
  */
  public String toString(){
    StringBuilder str = new StringBuilder(this.degree);
    str.append(" ");
    str.append(super.toString());
    str.append(", year of graduation: ");
    str.append(this.endYear);

    return str.toString();
  }
}

/** Klasa przechowuj??ca dane dziekan??w */

class Dean extends Man{
  /** Pole przechowuj??ce rok rozpocz??cia sprawowania urz??du */
  private final int beginTerm;
  /** Pole przechowuj??ce rok zako??czenia sprawowania urz??du */
  private final int endTerm;
  /** Pole przechowuj??ce stopie?? naukowy */
  private final String degree;

/** Konstruktor klasy Dean */
  public Dean(String degree, String name, String surname, int begin, int end){
    /** Wywo??anie konstruktora klasy Student */
    super(name, surname);
    this.degree = degree;
    this.beginTerm = begin;
    this.endTerm = end;
  }
 
 /** Getter zwracaj??cy rok ko??ca sprawowania urz??du*/
  public int getEndTerm(){
    return this.endTerm;
  }

  /** Metoda average odwo??uj??ca si?? do metody w klasie Man.
      Pow??d zakomentowania napisu zosta?? wyja??niony w opisie metody compare() w klasie Student.
      @return 0.0
  */
  public double average(){
    //System.out.print(" [Average not applicable] ");
    return super.average();
  }


/** Metoda por??wnuj??ca dwa obiekty odziedziczona z klasy Man.
      @param ob obiekt do por??wnania

      W celu uzyskania ??adnego outputu zgodnego z przyk??adowym mo??na odkomentowa?? zakomentowany fragment kodu i zakomentowa?? lini?? ni??ej, jednak przy takim rozwi??zaniu kod traci na uniwersalno??ci (po umieszczeniu klas w package'ach zmieni si?? nazwa klasy i kod nie b??dzie dzia??a?? prawid??owo). W zwi??zku z tym postanowi??em por??wnywa?? obiekty na podstawie warto??ci ??redniej.
      @return obiekt o p????niejszym uko??czeniu sprawowania urz??du b??d?? null
  */

  public Man compare(Man ob){
    if(ob != null){
      //if(ob.getClass().getName().equals("Dean"))
      if(ob.average() == 0.0){
        Dean temp = (Dean) ob;
        if(this.endTerm > temp.getEndTerm()){
          return this;
        }
        return temp;
      }
      return null;
    }
    return this;
  }

/** Nadpisana metoda toString, StringBuilder u??yty ze wzgl??du na wi??ksz?? wydajno???? metody append() w stosunku do "+" w klasie String.
    @return napis sformatowany zgodnie z przyk??adowym outputem
  */

  public String toString(){
    StringBuilder str = new StringBuilder(this.degree);
    str.append(" ");
    str.append(super.toString());
    str.append(", Dean of the Faculty from ");
    str.append(this.beginTerm);
    str.append(" to ");
    str.append(this.endTerm);
    
    return str.toString();
  }
}
