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
* Klasa Student dziedzicząca po klasie Man.
* @author Mateusz Barnacki
*/
class Student extends Man {
  /** Zmienna przechowująca numer indeksu. */
  private final int indexNumber;
  /** Tablica przechowująca wartości ocen*/ 
  private final double[] grades;

  /**Konstruktor klasy Student */
  public Student(String name, String surname, int idxNumber, double firstGrade, double secondGrade, double thirdGrade){
    /** Wywołanie konstruktora klasy Man */
    super(name, surname);
    this.indexNumber = idxNumber;
    this.grades = new double[3];
    this.grades[0] = firstGrade;
    this.grades[1] = secondGrade;
    this.grades[2] = thirdGrade; 
  }

  /** Metoda obliczająca wartość średnią. 
      @return wartość średnia obliczona na bazie ocen umieszczonych wa tablicy
  */
  public double average(){
    double sum = 0.0;
    for(double element : this.grades){
      sum += element;
    }
    return (sum / this.grades.length);
  }

  /** Metoda porównująca dwa obiekty odziedziczona z klasy Man.
      @param ob obiekt do porównania

      W celu uzyskania ładnego outputu zgodnego z przykładowym można odkomentować zakomentowany fragment kodu i zakomentować linię niżej, jednak przy takim rozwiązaniu kod traci na uniwersalności (po umieszczeniu klas w package'ach zmieni sią nazwa klasy i kod nie będzie działał prawidłowo). W związku z tym postanowiłem porównywać obiekty na podstawie wartości średniej.
      @return obiekt o większej średniej bądź null
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

  /** Nadpisana metoda toString, StringBuilder użyty ze względu na większą wydajność metody append() w stosunku do "+" w klasie String.
    @return napis sformatowany zgodnie z przykładowym outputem
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

/** Klasa przechowująca dane absolwentów */

class Graduate extends Student{
  /** Pole przechowujące wartość stopnia naukowego */
  private final String degree;
  /** Pole przechowujące rok ukończenia studiów */
  private final int endYear;

/**Konstruktor klasy Graduate */
  public Graduate(String degree, String name, String surname, int idxNumber, int endYear, double firstGrade, double secondGrade, double thirdGrade){
    /** Wywołanie konstruktora klasy Student */
    super(name, surname, idxNumber, firstGrade, secondGrade, thirdGrade);
    this.degree = degree;
    this.endYear = endYear;
  }

/** Nadpisana metoda toString, StringBuilder użyty ze względu na większą wydajność metody append() w stosunku do "+" w klasie String.
    @return napis sformatowany zgodnie z przykładowym outputem
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

/** Klasa przechowująca dane dziekanów */

class Dean extends Man{
  /** Pole przechowujące rok rozpoczęcia sprawowania urzędu */
  private final int beginTerm;
  /** Pole przechowujące rok zakończenia sprawowania urzędu */
  private final int endTerm;
  /** Pole przechowujące stopień naukowy */
  private final String degree;

/** Konstruktor klasy Dean */
  public Dean(String degree, String name, String surname, int begin, int end){
    /** Wywołanie konstruktora klasy Student */
    super(name, surname);
    this.degree = degree;
    this.beginTerm = begin;
    this.endTerm = end;
  }
 
 /** Getter zwracający rok końca sprawowania urzędu*/
  public int getEndTerm(){
    return this.endTerm;
  }

  /** Metoda average odwołująca się do metody w klasie Man.
      Powód zakomentowania napisu został wyjaśniony w opisie metody compare() w klasie Student.
      @return 0.0
  */
  public double average(){
    //System.out.print(" [Average not applicable] ");
    return super.average();
  }


/** Metoda porównująca dwa obiekty odziedziczona z klasy Man.
      @param ob obiekt do porównania

      W celu uzyskania ładnego outputu zgodnego z przykładowym można odkomentować zakomentowany fragment kodu i zakomentować linię niżej, jednak przy takim rozwiązaniu kod traci na uniwersalności (po umieszczeniu klas w package'ach zmieni sią nazwa klasy i kod nie będzie działał prawidłowo). W związku z tym postanowiłem porównywać obiekty na podstawie wartości średniej.
      @return obiekt o późniejszym ukończeniu sprawowania urzędu bądź null
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

/** Nadpisana metoda toString, StringBuilder użyty ze względu na większą wydajność metody append() w stosunku do "+" w klasie String.
    @return napis sformatowany zgodnie z przykładowym outputem
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
