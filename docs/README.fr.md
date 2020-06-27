# ITI 1521 - Lab 07 - Exceptions

## Objective d’apprentissage

* **Comprendre** la différence entre les exceptions à déclaration obligatoire et à déclaration non obligatoire (« checked » et « unchecked »)

* **Définir** l’usage des mots-clé try, catch, finally, throw, et throws.

* **Implémenter** vos propres exceptions

* **Throwing** et **Catching** Java exceptions

* **Extending** les implementations de stack et dictionaire avec Exception Handling.


## Soumission

Lire le [junit instructions](JUNIT.en.md) au besoin pour aider
avec les tests du lab.

Lire le [Submission Guidelines](SUBMISSION.en.md) avec attention.
Les erreurs de soumission vont affecté votre évaluation.

Soumettre les réponses des exercises 1, 2, 3. Votre soumission doit inclure:

* Exercise1.java
* Account.java
* NotEnoughMoneyException.java
* Stack.java
* ArrayStack.java
* DynamicArrayStack.java
* FullStackException.java
* Map.java
* Dictionary.java
* Pair.java

## Exceptions

Les **exceptions** sont des « problèmes » qui surviennent lors de l’exécution d’un programme. Quand une exception est lancée, le flot d’exécution du programme est interrompu, et si l’exception n’est pas gérée, le programme se terminera.

Certaines exceptions sont causées par des erreurs commises par l'utilisateur, d'autres par celles des programmeurs ou parfois il s'agit d'un problème du côté des ressources physiques.

Quelques exemples d’exceptions sont :

* Un usager entre une valeur invalide
* Un fichier à ouvrir n’est pas trouvé

Il y a deux types d’exceptions : les exceptions à déclaration obligatoire et les exceptions à déclaration non obligatoire.

### Exceptions à déclaration obligatoire (« Checked exceptions »)

Une exception à déclaration obligatoire, aussi connue sous le nom de « **checked exception** », est une exception qui sera vérifiée par le compilateur. Ces exceptions ne peuvent pas être ignorées et doivent explicitement être traitées par le programmer, nous verrons comment plus tard.

Un exemple d’exception à déclaration obligatoire est l’exception **FileNotFoundException**. Lorsque l’on tente d’utiliser un **FileReader**, (nous explorerons les FileReader dans un autre laboratoire; ils sont utilisés pour lire le contenu d’un fichier) si le fichier spécifié dans le constructeur du FileReader n’existe pas, nous obtiendrons l’exception **FileNotFoundException**.

```java
import java.io.File;
import java.io.FileReader;

public class FilenotFound_Demo {

   public static void main(String args[]) {
      readFile();
   }

   public static void readFile() {
      File file = new File("E://file.txt");
      FileReader fr = new FileReader(file);
   }
}
```

Si vous tentez de compiler ce code, vous obtiendrez ce message :

```java
FilenotFound_Demo.java:11: error: unreported exception FileNotFoundException; must be caught or declared to be thrown
      FileReader fr = new FileReader(file);
                      ^
1 error
```

### Les exceptions à déclaration non obligatoire (« unchecked exceptions »):

Les **exceptions à déclaration non obligatoire**, aussi connue sous le nom de « **unchecked exceptions** » sont des exceptions qui ne requièrent pas d'être explicitement traitées par le programmeur. Elles incluent les **bogues**, les **erreurs de logiques** ainsi que les **mauvaises utilisations d’un « API »**.

Voice une hiérarchie d'exception:

![Hiarchy of exception calsses in Java](lab7_img1_Exception_Hiarchy.png "Hiarchy of exception calsses in Java")

**Figure 1 :** Hiérarchie de classe d'exception dans Java.

L'exception de Java sous `Error` et `RuntimeException` sont des exceptions qui ne sont pas vérifier au « compile time ».

Un exemple d’exception à déclaration non obligatoire avec lequel vous être probablement déjà familier est l’exception **ArrayIndexOutOfBoundsException** qui survient lorsque vous tentez d’accéder à l’index d’un tableau qui est à l’extérieur de ses frontières.
```java
public class ArrayOutOfBound_Demo {

  public static void main(String args[]) {
    createArray();
  }

  public static void createArray() {
    int[] myArray=new int[2];
    myArray[2]=1;
  }
}
```

Si vous tentez de compiler ce code, vous pourrez le faire sans problèmes. Par contre, quand vous tentez d’exécuter le code, vous obtiendrez ce message :
```java
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 2
        at ArrayOutOfBound_Demo.createArray(ArrayOutOfBound_Demo.java:8)
        at ArrayOutOfBound_Demo.main(ArrayOutOfBound_Demo.java:4)
```

### Throw et Throws:

Si une méthode ne gère pas une **exception à déclaration obligatoire**, on doit déclarer à la fin de la définition de la méthode qu’elle peut lancer une exception, ainsi que le type d’exception en question. Cela se fait par l’utilisation du mot-clé **throws**. Lorsque vous lancez une exception, soit en créant une nouvelle exception ou en lançant une exception qui a été attrapée, vous devez utiliser le mot-clé **throw**.

Il est important de comprendre que :

* **Mot-clé throws** : se trouve dans la déclaration de la méthode et indique que la méthode peut lancer une exception à déclaration obligatoire suivie du type d’exécution en question. Facultativement, on peut indiquer quelles exceptions à déclaration non obligatoire seront tirées.
* **Mot-clé throw** : est utilisé pour lancer une exception, que ce soit une exception **à déclaration obligatoire ou à déclaration non obligatoire**.

**Lancer** une exception signifie que le flot du programme est interrompu et que l’exception est retournée à la méthode ayant appelé cette méthode, et ce, récursivement jusqu’à ce que l’exception soit **attrapée**.

Une méthode peut avoir plusieurs throws; il suffit de les séparer par une virgule comme ceci :
```java
public void myMethod() throws ExceptionA, ExceptionB {
  //some code
}
```
### Try, Catch et Finally:

Tout code qui est susceptible de causer une exception devrait être entouré par un bloc **try**. Un bloc try doit être suivi par un bloc **catch**. Si une exception survient et est lancée lors de l’exécution du bloc try, elle sera attrapée par le bloc catch associé avec ce type d’exception.

Un bloc catch implique que le type de l'exception que nous essayons d'attrapé soit déclaré. Si l'exception que notre bloc catch essaie d'attrapper est lancée, c'est le code qui se trouve dans ce bloc qui sera immédiatement exécuté (et non le reste de notre bloc try). Les bloc catch suivent donc toujours un bloc try. Il peut aussi y avoir un ou plusieurs blocs catch, mais sachez qu'un seul sera exécuté!

**Conseil**: uniquement le premier bloc catch qui correspond à l'exception lancée sera exécuté. Il est donc important que vous placiez toujours vos blocs catch en ordre du plus scécifique au moins spécifique.

Un bloc finally sera toujours exécuté après une déclaration try-catch qu'il y ait ou non eu une exception lancée ou attrapée.

Dans l'exemple suivant, nous essayons (**try**) d'exécuter la méthode throwingMethod, qui lance (throws) **IOException**. Lorsque l'exception est lancée, elle est attrappée dans la méthode main avec la déclaration catch. Puisque la IOException est une **checked** exception, la signature de la méthode throwingMethod se doit de terminer avec le mot-clé **throws** accompagné du **type** de l'exception (IOException dans notre cas)
```java
import java.io.IOException;
public class TryCatchThrow_Demo {

  public static void main(String args[]) {
    try {
      System.out.println("start the try block");
      throwingMethod();
    } catch(IOException e) {
      System.out.println("The exception is caught");
    }
  }

  public static void throwingMethod() throws IOException {
    //some code...
    System.out.println("Running the method throwingMethod");
    throw new IOException();
  }
}
```
Après l'exécution du code, nous obtenons:

```
start the try block
Running the method throwingMethod
The exception is caught
```
Dans le prochain exemple, on peut voir que nous n'avons pas besoin d'utiliser le mot-clé **throws**, car il s'agit d'une **unchecked exception**. Nous utilisons aussi deux blocs catch, soit un pour gérer une exception spécifique et un autre pour attraper et définir d'une manière par défaut à n'importe quelles autres exceptions. **catch(Exception e)** attrapera toutes les exceptions, puisque toutes ces classes sont des sous-classes de la classe **Exception**. Nous avons aussi une déclaration avec finally qui sera toujours exécutée.

**Rappel**: l'exception la plus spécifique doit être placée en premier!
```java
public class ArrayOutOfBound2_Demo {

  public static void main(String args[]) {
    try {
      System.out.println("start the try block");
      createArray();
    } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("The specific exception is caught");
    } catch(Exception e) {
      System.out.println("the generic Exception is caught");
    } finally{
      System.out.println("Executing the finally block");
    }
  }

  public static void createArray() {
    int[] myArray=new int[2];
    myArray[2]=1;
  }
}
```
En exécutant ce code, on obtient:

```
start the try block
The specific exception is caught
Executing the finally block
```
Essayez d'inverser l'ordre des blocs catch et voyez ce qui se produit!

### Exercise 1

###### Ficher à soumettre:

* Exercise1.java


Complétez le code suivant afin qu'il compile et que vous attrapiez chaque type d'exceptions lancé par la méthode randomException. Imprimez le type de l'exception ainsi que le exceptionNumber générez aléatoirement. Retournez le nom de l'exception ou "NoException" si aucune exception n'a été levée.

Vous pouvez utiliser le code dans le fichier Exercise1.java. Vous devez soumettre ce fichier.
```java
import java.io.IOException;

public class Exercise1 {

  public static void throwException(int exceptionNumber) throws Exception{
    if (exceptionNumber==1) {
      throw new Exception();
    }
    if (exceptionNumber==2) {
      throw new ArrayIndexOutOfBoundsException();
    }
    if (exceptionNumber==3) {
      throw new IOException();
    }
    if (exceptionNumber==4) {
      throw new IllegalArgumentException();
    }
    if (exceptionNumber==5) {
      throw new NullPointerException();
    }
  }

  /**
   * Returns the name of the exception thrown by the method throwException.
   * Method that handles the exceptions thrown by the throwException method.
   *
   * @param exceptionNumber
   * @return The exception name or "NoException" if no exception was caught.
   */
  public static String catchException(int exceptionNumber) {
    try {
      throwException(exceptionNumber);
    }
    // YOUR CODE HERE
    return "NoException";
  }

  public static void main(String[] args) {
    int exceptionNumber=(int)(Math.random()*5) + 1;
    String exceptionName = catchException(exceptionNumber);
    System.out.println("Exception number: " + exceptionNumber);
    System.out.println("Exception name: " + exceptionName);
  }

}
```

Exécutez le code quelques fois devraient vous donner des résultats similaires à :

```java
>java Exercise1
The exception type is: ArrayIndexOutOfBoundsException, the exceptionNumber is: 2
Exception number: 2
Exception name: ArrayIndexOutOfBoundsException

>java Exercise1
The exception type is: NullPointerException, the exceptionNumber is: 5
Exception number: 5
Exception name: NullPointerException

>java Exercise1
The exception type is: ArrayIndexOutOfBoundsException, the exceptionNumber is: 2
Exception number: 2
Exception name: ArrayIndexOutOfBoundsException

>java Exercise1
The exception type is: Exception, the exceptionNumber is: 1
Exception number: 1
Exception name: Exception
```

### Créer des exceptions:

Les exceptions sont des classes; elles contiennent des méthodes et des variables tout comme les classes habituelles. Toutes les exceptions doivent être enfants de **Exception**. Pour créer des exceptions à déclaration obligatoire (checked), vous devez dériver de la classe Exception, tandis que pour les exceptions à déclaration non obligatoire (unchecked) vous devez dériver de la classe RuntimeException.

Par exemple, nous voulons parfois lancer une exception si un nombre négatif est donné. Nous créerions donc la classe NegativeNumberException.
```java
public class NegativeNumberException extends IllegalArgumentException{

  private int number;

  public NegativeNumberException(int number) {
    super("Cannot us a negative number: "+ number);
    this.number=number;
  }

  public int getNumber() {
    return number;
  }
}
```

**Noté** que l’appelle au super constructeur permettra a la méthode par défaut getMessage de retourner le String mis en paramètre. Il existe plusieurs autres méthodes hériter de la classe Exception incluant toString.

Cette nouvelle exception créée peut maintenant être utilisée comme une exception régulière.
```java
public class NegativeNumberException_Demo{

  public static void main(String args[]) {
    try {
      printSquareRoot(4);
      printSquareRoot(-1);
    } catch(NegativeNumberException e) {
      System.out.println(e.getMessage());
      System.out.println("the number " + e.getNumber() + " is invalid");
    }
  }

  public static void printSquareRoot(int x) throws NegativeNumberException{
    if (x<0) {
      throw new NegativeNumberException(x);
    }

    System.out.println("the square root of " + x + " is "+Math.sqrt(x));
  }
}
```
En exécutant ce code, on obtient:

    the square root of 4 is 2.0
    Cannot us a negative number: -1
    the number -1 is invalid

### Exercise 2:

###### Ficher à soumettre:

* Account.java
* NotEnoughMoneyException.java

Pour cet exercice, vous devez créer une classe nommée Account. Cette classe représente un compte de banque. Elle possède un constructeur qui initialise la variable balance à 0. La classe possède aussi les méthodes: 

*  **deposit** qui reçoit en paramètre un nombre de type double et ajoute cette valeur à la balance du compte;
*  **withdraw** qui reçoit elle aussi un nombre de type double, mais cette fois retire ce montant de la balance du compte;
*  **getBalance** (la méthode d'accès pour balance) - si le montant à retirer est supérieur à la balance du compte, vous devrez lancer l'exception `NotEnoughMoneyException`.

Vous devrez créer la classe `NotEnoughMoneyException` (dans un autre fichier Java). Cette exception que vous créerez doit dériver de la classe **IllegalStateException**, il aura les méthodes:

* **getAmount** qui retourne le montant qui a été demandée d'être retiré;
* **getBalance** qui retourne le montant du compte au moment de création de l’exception;
* **getMissingAmount** qui retourne le montant manquant pour pouvoir effectuer le retrait.

L’exception devrait aussi invoquer son super constructeur afin que la méthode **getMessage** puisse donner un message indiquant que le montant ne peut pas être retiré.

Pour cet exercice, vous devez soumettre les fichiers Account.java et NotEnoughMoneyException.java.

```java
public class Exercise2 {

  public static void main(String args[]) {
    try {
       Account myAccount=new Account();
       myAccount.deposit(600);
       myAccount.withdraw(300);
       myAccount.withdraw(400);
    } catch(NotEnoughMoneyException e) {
       System.out.println(e.getMessage());
       System.out.println("You are missing " + e.getMissingAmount() + "$");
    }
  }
}
```
Lorsque vous exécuterez le programme Exercise2.java, vous devriez avoir une sortie similaire à ceci:

```java
new balance=600.0$
new balance=300.0$
you have not enought money to withdraw 400.0$
You are missing 100.0$
```

### Exercise 3:

###### Ficher à soumettre:

* ArrayStack.java
* DynamicArrayStack.java
* Stack.java
* FullStackException.java
* Dictionary.java
* Map.java
* Pair.java


#### ArrayStack

Ajouter au code de la classe ArrayStack.java (du laboratoire précédent), gestions des exceptions. Vous devez utiliser les exceptions suivantes:

* `EmptyStackException` (de **java.util**) quant la pile est vide dans la méthode peek et pop;
* `FullStackException` (créer une nouvelle exception à déclaration obligatoire) pour la méthode push, exception levée quand la pile est pleine

#### DynamicArrayStack

Ajouter au code de la classe DynmaicArrayStack.java (du laboratoire précédent), gestions des exceptions. Vous devez utiliser l'exception EmptyStackException (de java.util) dans la méthode peek et pop.

**Note:** Vous pouvez utiliser comme code de démarrage l'implémentation du laboratoire précédent pourr `ArrayStack.java`, `DynamicArrayStack.java`, et `Stack.java`, mais la recommandation est de travailler sur votre solution du laboratoire précédent ou d'utiliser les solutions publiées avec la mise en œuvre complète du laboratoire précédent.

#### Dictionary

Changer la classe **Dictionary** afin d’inclure des exceptions pour que tous les tests de la classe **TestL7Dictionary** passent avec succès. Par exemple, la méthode **put** dans la classe **Dictionary** devrait inclure la gestion d’exceptions suivante:
```java
public void put(String key, Integer value) {

  if (key == null) {
    throw new NullPointerException("the key is null");
  }

  if (count == elems.length) {
    increaseCapacity();
  }

  // Similarly to the array-based implementation
  // of a stack, adding elements at the end
  elems[count] = new Pair(key, value);
  count++;
}
```

### Resources

* [https://docs.oracle.com/javase/tutorial/getStarted/application/index.html](https://docs.oracle.com/javase/tutorial/getStarted/application/index.html)
* [https://docs.oracle.com/javase/tutorial/getStarted/cupojava/win32.html](https://docs.oracle.com/javase/tutorial/getStarted/cupojava/win32.html)
* [https://docs.oracle.com/javase/tutorial/getStarted/cupojava/unix.html](https://docs.oracle.com/javase/tutorial/getStarted/cupojava/unix.html)
* [https://docs.oracle.com/javase/tutorial/getStarted/problems/index.html](https://docs.oracle.com/javase/tutorial/getStarted/problems/index.html)
