package com.usermisterfive;

public class Reachables {
 static Reachable get() {
  if (System.getProperty("os.name").toLowerCase().contains("windows")) {
   return new WinReachable();
  } else {
   return new UnixReachable();
  }
 }
}
