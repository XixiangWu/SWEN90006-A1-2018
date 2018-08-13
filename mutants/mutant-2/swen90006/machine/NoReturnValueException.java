package swen90006.machine;

public class NoReturnValueException extends RuntimeException
{
  public NoReturnValueException() {}

  public String toString()
  {
    return "NoReturnValueException";
  }
}
