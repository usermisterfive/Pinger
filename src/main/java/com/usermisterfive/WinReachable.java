package com.usermisterfive;

import java.io.IOException;
import java.net.InetAddress;

public class WinReachable implements Reachable {
 public boolean isReachable(String host, int port, int timeout) {
  try {
   return InetAddress.getByName(host).isReachable(timeout);
  } catch (IOException ioException) {
   System.out.println(ioException);
   return false;
  }
 }
}
