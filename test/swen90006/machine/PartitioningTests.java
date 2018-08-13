package swen90006.machine;

import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

import org.junit.*;
import static org.junit.Assert.*;

public class PartitioningTests
{
  //Any method annotated with "@Before" will be executed before each test,
  //allowing the tester to set up some shared resources.
  @Before public void setUp()
  {
  }

  //Any method annotated with "@After" will be executed after each test,
  //allowing the tester to release any shared resources used in the setup.
  @After public void tearDown()
  {
  }

  //Any method annotation with "@Test" is executed as a test.
  @Test public void aTest()
  {
    //the assertEquals method used to check whether two values are
    //equal, using the equals method
    final int expected = 2;
    final int actual = 1 + 1;
    assertEquals(expected, actual);
  }

  @Test public void anotherTest()
  {
    List<String> list = new ArrayList<String>();
    list.add("a");
    list.add("b");

    //the assertTrue method is used to check whether something holds.
    assertTrue(list.contains("a"));
  }

  //Test test opens a file and executes the machine
  @Test public void aFileOpenTest()
  {
    final List<String> lines = readInstructions("examples/array.s");
    Machine m = new Machine();
    assertEquals(m.execute(lines), 45);
  }
  
  //To test an exception, specify the expected exception after the @Test
  @Test(expected = java.io.IOException.class) 
    public void anExceptionTest()
    throws Throwable
  {
    throw new java.io.IOException();
  }

  //This test should fail.
  //To provide additional feedback when a test fails, an error message
  //can be included
  @Test public void aFailedTest()
  {
    //include a message for better feedback
    final int expected = 2;
    final int actual = 1 + 2;
    assertEquals("Some failure message", expected, actual);
  }

  //Read in a file containing a program and convert into a list of
  //string instructions
  private List<String> readInstructions(String file)
  {
    Charset charset = Charset.forName("UTF-8");
    List<String> lines = null;
    try {
      lines = Files.readAllLines(FileSystems.getDefault().getPath(file), charset);
    }
    catch (Exception e){
      System.err.println("Invalid input file! (stacktrace follows)");
      e.printStackTrace(System.err);
      System.exit(1);
    }
    return lines;
  }
}
