package com.usermisterfive;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.Objects;


public class Base {
 private static final String host = "1.1.1.1";
 private boolean running;
 public static void main(String[] args) {
  Base base = new Base();
  base.setRunning(true);

  TrayIcon trayIcon = new TrayIcon(Objects.requireNonNull(
    createImage("unknown.png")));
  try {
   SystemTray.getSystemTray().add(trayIcon);
  } catch (AWTException awtException) {
   System.out.println(awtException);
  }

  while (base.isRunning()) {
   try {
    Thread.sleep(1000);
   } catch(InterruptedException interruptedException) {
    System.out.println(interruptedException);
   }
   testAndAssignImage(trayIcon);

  }
 }
 static boolean test(int timeout) {
  boolean reachable = false;
  try {
   String reachableWord = "";
   reachable = InetAddress.getByName(host).isReachable(timeout);
   if (!reachable) {
    reachableWord = "not ";
   }
   System.out.println(host + " is " + reachableWord + "reachable within "
     + timeout + " ms");

  } catch(IOException ioException) {
   System.out.println(ioException);
  }
  return reachable;
 }
 private static Image createImage(String path) {
  URL imageURL = Base.class.getClassLoader().getResource(path);

  if (imageURL == null) {
   System.err.println("Resource not found: " + path);
   return null;
  } else {
   return (new ImageIcon(imageURL, "")).getImage();
  }
 }
 private static void testAndAssignImage(TrayIcon trayIcon) {
  if (test(1)) {
   trayIcon.setImage(createImage("1.png"));
  }
  else if (test(2)) {
   trayIcon.setImage(createImage("2.png"));
  }
  else if (test(3)) {
   trayIcon.setImage(createImage("3.png"));
  }
  else if (test(4)) {
   trayIcon.setImage(createImage("4.png"));
  }
  else if (test(5)) {
   trayIcon.setImage(createImage("5.png"));
  }
  else if (test(10)) {
   trayIcon.setImage(createImage("10.png"));
  } else if (test(100)) {
   trayIcon.setImage(createImage("100.png"));

  } else if (test(1000)) {
   trayIcon.setImage(createImage("1000.png"));
  } else {
   trayIcon.setImage(createImage("unknown.png"));
  }
 }

 public boolean isRunning() {
  return running;
 }

 public void setRunning(boolean running) {
  this.running = running;
 }
}
