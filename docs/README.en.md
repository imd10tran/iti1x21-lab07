# ITI 1121 - Lab 07 - Exceptions

## Learning objectives

* **Understanding** the difference between checked and unchecked exceptions

* **Defining** the use of the try, catch, finally, throw and throws keywords

* **Implementing** your own exception

* **Throwing** and **Catching** Java exceptions

* **Extending** the stack and dictionary implementations with Exception Handling.


## Submission

Please read the [junit instructions](JUNIT.en.md) for help
running the tests for this lab.

Please read the [Submission Guidelines](SUBMISSION.en.md) carefully.
Errors in submitting will affect your grades.

Submit answers for the Exercises 1, 2, 3. Your submission has to include the following files:

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

**Exceptions** are “problems” that occur during the execution of a program. When an exception occurs, the normal flow of the program is interrupted. If the exception is unhandled, the program will terminate.

Some exceptions can be caused by user error, others by programming error or even by failure of some physical resource.

Some examples of sources of exceptions are:

* A user enters an invalid data
* A file that needs to be opened cannot be found

There are two categories of exceptions: **Checked** and **Unchecked** exceptions.

### Checked exceptions:

A **checked** exception is an exception that cannot be ignored and needs to be explicitly handled by the programmer, we will see how later. These exceptions are checked at **compile time**.

A common example of a checked exception is the **FileNotFoundException**. When using a **FileReader** (We will explore FileReader further in another lab; they essentially allow you to read the content of a file). If the file specified in the constructor of the FileReader does not exist, then **FileNotFoundException** occurs.
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

If you try to compile this code, you will get this message:
```java
FilenotFound_Demo.java:11: error: unreported exception FileNotFoundException; must be caught or declared to be thrown
      FileReader fr = new FileReader(file);
                      ^
1 error
```

### Unchecked exceptions:

**Unchecked** exceptions are exceptions that do not need to be explicitly handled by programmers. They include bugs, logical errors, and improper use of an API.

Here is a hierarchy exception:

![Hiarchy of exception calsses in Java](lab7_img1_Exception_Hiarchy.png "Hiarchy of exception calsses in Java")

**Figure 1 :** Hiearchy of exception calsses in Java

The Java exceptions under `Error` and `RuntimeException` are exceptions that are not checked at compile time.

An example of unchecked exception you might be familiar with is the **ArrayIndexOutOfBoundsException** that occurs when you try to access the index of an array that is not within the boundary of the array.
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

If you try to compile this code it will do so without errors. However, when you try to run the code, you will get this message:
```java
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 2
        at ArrayOutOfBound_Demo.createArray(ArrayOutOfBound_Demo.java:8)
        at ArrayOutOfBound_Demo.main(ArrayOutOfBound_Demo.java:4)
```

### Throw and Throws:

If a method does not handle a checked exception, it needs to declare at the end of its definition that it **throws** that exception. You can also throw an exception, either by creating a new exception or by throwing an exception that was just caught, by using the **throw** keyword.

It is important that you remember:

* The keyword **throws**: in the method declaration, indicates what **checked exception** can be thrown by that method, and, optionally, some or all of the **unchecked** exceptions that can be thrown by that method.
* The keyword **throw**: used in the code of a method to throw an exception, whether **checked** or **unchecked**.

Throwing an exception translates into sending the exception to the caller of this method.

A method can specify multiple exceptions after the **throws** keyword; they need to be separated with comas like this:
```java
public void myMethod() throws ExceptionA, ExceptionB {
  //some code
}
```
### Try, Catch and Finally:

Any code that is likely to cause an exception should be surrounded by a try block. A try block should be followed by a catch block. If an exception occurs during the execution of the try block and the exception is thrown, the exception may then be caught by a catch block associated to that exception (**exception handling**).

A catch statement involves declaring the type of exception that we are trying to catch. If the exception that we are trying to catch is thrown, the code inside the catch block will be executed. Catch blocks always follow a try block, there can be one or multiple catch blocks but only one will be executed.

**Important observation**: only the first catch statement encountered that is valid will be executed, it is therefore important to always put the catch statements in order from the most specific to the least.

A finally statement is a block of code that will always be executed after a try-catch whether or not an exception was thrown and or caught.

In this example, we **try** to run the method throwingMethod, which throws **IOException**. When the exception is thrown, is it caught in the main method by the **catch** statement. Since the IOException is a **checked** exception, the method signature for throwingMethod has to finish with the keyword **throws** and the type of exception (IOException)
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
When running this code you will see:

```
start the try block
Running the method throwingMethod
The exception is caught
```
Now let's update the ArrayOutOfBound_Demo shown before, this new version has been modified to handle the exception. You can see that we do not need to use the throws keyword because it is an unchecked exception. We also have 2 catch statements, one to handle a specific exception **ArrayIndexOutOfBoundsException** and the other to catch and react in a default manner to any other exceptions that might be thrown. **catch(Exception e)** will catch every exception as they are under the hierarchy of the class **Exception**. We also have a finally statement that will always be executed.

**Note**: the most specific exception has to be placed first
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
When running this code you will see:

```
start the try block
The specific exception is caught
Executing the finally block
```
Try to reverse the order of the catch statements to see what happens!

### Exercise 1

###### Files to submit:

* Exercise1.java


Complete the following code so that it compiles and that you catch each type of exception thrown by the method **throwException** and return the name of the exception as a String. The default value to be returned is "NoException" (if none of the specified exceptions have been thrown).

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

Running the code a few times should give a result similar to this:

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

### Creating exceptions:

Exceptions are classes; they contain methods and fields just like any other class. All the exceptions need to be a child of **Exception**. To create a checked exception you need to extend the class Exception (or a class that extends Exception), for unchecked exception you need to extend the class RuntimeException (or a class that extends RuntimeException).

For example, we might want to throw an exception if a given number is negative. We would then create the class NegativeNumberException as in the example below.
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

**Note** : The call to the super constructor will allow the default method **getMethod** to return the String given as parameter. There are many other method inherited from the class **Exception** including **toString**

That new exception that we have created can now be used like a regular exception:
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
When running this code you should see:

    the square root of 4 is 2.0
    Cannot us a negative number: -1
    the number -1 is invalid

### Exercise 2:

###### Files to submit:

* Account.java
* NotEnoughMoneyException.java

For this exercise, you will need to create a new file containing a class named **Account**. This class represents a bank account and it has a constructor that sets the variable balance to 0. It also has the methods:

*  **deposit** that takes a double as a parameter and adds that number to the balance;
*  **withdraw** that takes a double as a parameter and removes that amount from the balance (both methods should print the new balance);
*  **getBalance** (the getter method for balance) - if the amount to withdraw is higher than the balance, you need to throw the `NotEnoughMoneyException`.

You will need to create the class `NotEnoughMoneyException` (in a separate java file). This class will extend **IllegalStateException**, it will have the methods:

* **getAmount** that returns the amount that was requested;
* **getBalance** that returns the balance at the time when the exception was created;
* **getMissingAmount** that returns the amount of money needed to withdraw the requested amount.

The exception should also invoke it’s super constructor with a custom message that indicates what amount cannot be withdrawn.

Entry point program example:

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
When running the code above, you should get an output similar to this:

```java
new balance=600.0$
new balance=300.0$
you have not enought money to withdraw 400.0$
You are missing 100.0$
```
### Exercise 3:

###### Files to submit:

* ArrayStack.java
* DynamicArrayStack.java
* Stack.java
* FullStackException.java
* Dictionary.java
* Map.java
* Pair.java


#### ArrayStack

Add to the implementations of ArrayStack from the previous laboratory, Exception Handling for the following cases:

* `EmptyStackException` (from **java.util**) for the methods peek and pop when the stack is empty;
* `FullStackException` (create a new custom checked exception) for the method push when the stack is full.

#### DynamicArrayStack

Add to the implementations of DynamicArrayStack from the previous laboratory, Exception Handling for the methods peek and pop (using `EmptyStackException` from **java.util**).

**Note:** You can use as starter code the implementation from the previous laboratory for `ArrayStack.java`, `DynamicArrayStack.java`, and `Stack.java`, but the recommendation is to work on top of your solution from the previous laboratory or use the posted solutions with the full implementation of the previous laboratory.

#### Dictionary

Edit the **Dictionary** class to include exceptions so that when you run the class **TestL7Dictionary**, all tests pass. For example, the method **put** in the class **Dictionary** should *also* include the following exception handling:
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
