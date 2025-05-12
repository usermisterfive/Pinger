package com.usermisterfive.pinger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class UnixReachable implements Reachable {
 public boolean isReachable(String host, int port, int timeout) throws IOException {
  try (Socket soc = new Socket()) {
   soc.connect(new InetSocketAddress(host, port), timeout);
   return true;
  } catch (SocketTimeoutException socketTimeoutException) {
   return false;
  }
 }
}
