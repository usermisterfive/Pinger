package com.usermisterfive;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import javax.swing.ImageIcon;

import java.net.URL;
import java.util.Objects;


public class Base {
 private static final String host = "1.1.1.1";
 private boolean running;
 @SuppressWarnings({"ThrowablePrintedToSystemOut", "BusyWait"})
 public static void main(String[] args) {
  final Base base = new Base();
  base.setRunning(true);
  final Reachable reachable = Reachables.get();

  final TrayIcon trayIcon = new TrayIcon(Objects.requireNonNull(
    createImage("unknown.png")));
  final MenuItem exitMenuItem = new MenuItem("Exit");
  exitMenuItem.addActionListener(actionEvent -> base.setRunning(false));
  final PopupMenu popupMenu = new PopupMenu();
  popupMenu.add(exitMenuItem);
  trayIcon.setPopupMenu(popupMenu);

  try {
   SystemTray.getSystemTray().add(trayIcon);
  } catch (AWTException awtException) {
   System.out.println(awtException);
  }
  final int PAUSE = 1000;
  int pause1 = PAUSE;
  while (base.isRunning()) {
   try {
    Thread.sleep(pause1);
   } catch(InterruptedException interruptedException) {
    System.out.println(interruptedException);
   }
   int timeouts = testAndAssignImage(trayIcon, reachable);
   if (timeouts > PAUSE) {
    pause1 = 0;
   } else {
    pause1 = PAUSE - timeouts;
   }
  }
  SystemTray.getSystemTray().remove(trayIcon);
 }
 private static boolean test(int timeout, Reachable reachable1) {
  boolean reachable;
  String reachableWord = "";
  reachable = reachable1.isReachable(host, 443, timeout);
  if (!reachable) {
   reachableWord = "not ";
  }
  System.out.println(host + " is " + reachableWord + "reachable within "
    + timeout + " ms");

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
 private static int testAndAssignImage(TrayIcon trayIcon, Reachable reachable) {
  int timeouts = 0;
  int one = 1;
  int two = 2;
  int three = 3;
  int four = 4;
  int five = 5;
  int ten = 10;
  int hundred = 100;
  int thousand = 1000;
  if (test(one, reachable)) {
   trayIcon.setImage(createImage(one + ".png"));
  }
  else if (test(two, reachable)) {
   timeouts += one;
   trayIcon.setImage(createImage(two + ".png"));
  }
  else if (test(three, reachable)) {
   timeouts += two;
   trayIcon.setImage(createImage(three + ".png"));
  }
  else if (test(four, reachable)) {
   timeouts += three;
   trayIcon.setImage(createImage(four + ".png"));
  }
  else if (test(five, reachable)) {
   timeouts += four;
   trayIcon.setImage(createImage(five + ".png"));
  }
  else if (test(ten, reachable)) {
   timeouts += five;
   trayIcon.setImage(createImage(ten + ".png"));
  } else if (test(hundred, reachable)) {
   timeouts += ten;
   trayIcon.setImage(createImage(hundred + ".png"));

  } else if (test(thousand, reachable)) {
   timeouts += hundred;
   trayIcon.setImage(createImage(thousand + ".png"));
  } else {
   timeouts += thousand;
   trayIcon.setImage(createImage("unknown.png"));
  }
  return timeouts;
 }

 public boolean isRunning() {
  return running;
 }

 public void setRunning(boolean running) {
  this.running = running;
 }
}
