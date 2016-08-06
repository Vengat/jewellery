package org.jewelry.experiments;

import java.util.*;
import java.lang.*;
import java.net.*;

public class GetClassDirectory
{
  public static void main(String args[]) {
	  GetClassDirectory gc =new GetClassDirectory();
	  System.out.println(gc.getClass().getResource("Hanuman.jpg"));
  URL url=new Object().getClass().getResource("Object.class");
  System.out.println(url);
  }
}