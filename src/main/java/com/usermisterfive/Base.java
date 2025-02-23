package com.usermisterfive;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.Objects;


public class Base {
 private static final String host = "1.1.1.1";
 public static void main(String[] args) {
  while (true) {
   TrayIcon trayIcon = setIcon2();
   try {
    Thread.sleep(1000);
   } catch(InterruptedException interruptedException) {
    System.out.println(interruptedException);
   }
   SystemTray.getSystemTray().remove(trayIcon);

  }
 }
 static boolean test(int timeout) {
  boolean isSuccessful = false;
  try {
   String isReachable = "";
   isSuccessful = InetAddress.getByName(host).isReachable(timeout);
   if (!isSuccessful) {
    isReachable = "not ";
   }
   System.out.println(host + " is " + isReachable + "reachable within "
     + timeout + " ms");

  } catch(IOException ioException) {
   System.out.println(ioException);
  }
  return isSuccessful;
 }
 private static Image createImage(String path, String description) {
  URL imageURL = Base.class.getClassLoader().getResource(path);

  if (imageURL == null) {
   System.err.println("Resource not found: " + path);
   return null;
  } else {
   return (new ImageIcon(imageURL, description)).getImage();
  }
 }
 private static TrayIcon setIcon(String path) {
  TrayIcon trayIcon = new TrayIcon(Objects.requireNonNull(
    createImage(path, "")));
  try {
   SystemTray.getSystemTray().add(trayIcon);
  } catch (AWTException awtException) {
   System.out.println(awtException);
  }
  return trayIcon;

 }
 private static TrayIcon setIcon2() {
  TrayIcon trayIcon;
  if (test(1)) {
   trayIcon = setIcon("1.png");
  }
  else if (test(2)) {
   trayIcon = setIcon("2.png");
  }
  else if (test(3)) {
   trayIcon = setIcon("3.png");
  }
  else if (test(4)) {
   trayIcon = setIcon("4.png");
  }
  else if (test(5)) {
   trayIcon = setIcon("5.png");
  }
  else if (test(10)) {
   trayIcon = setIcon("10.png");
  } else if (test(100)) {
   trayIcon = setIcon("100.png");

  } else if (test(1000)) {
   trayIcon = setIcon("1000.png");
  } else {
   trayIcon = setIcon("unknown.png");
  }
  return trayIcon;
 }
}
