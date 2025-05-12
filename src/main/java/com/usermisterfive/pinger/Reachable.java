package com.usermisterfive.pinger;

import java.io.IOException;

interface Reachable {
 boolean isReachable(String host, int port, int timeout) throws IOException;
}
