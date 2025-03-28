package com.usermisterfive;

import java.io.IOException;

interface Reachable {
 boolean isReachable(String host, int port, int timeout) throws IOException;
}
