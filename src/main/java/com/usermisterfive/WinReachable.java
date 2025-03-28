package com.usermisterfive;

import java.io.IOException;
import java.net.InetAddress;

public class WinReachable implements Reachable {
 public boolean isReachable(String host, int port, int timeout)
   throws IOException {
  return InetAddress.getByName(host).isReachable(timeout);
 }
}
