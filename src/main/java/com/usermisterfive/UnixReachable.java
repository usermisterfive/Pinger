package com.usermisterfive;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class UnixReachable implements Reachable {
 public boolean isReachable(String host, int port, int timeout) {
  try (Socket soc = new Socket()) {
   soc.connect(new InetSocketAddress(host, port), timeout);
   return true;
  } catch (IOException ex) {
   return false;
  }

 }
}
