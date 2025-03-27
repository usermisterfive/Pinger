package com.usermisterfive;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import javax.swing.ImageIcon;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Base {
 private static final String HOST = "1.1.1.1";
 private boolean running;
 private final static List<Integer> TIMEOUTS_LIST = new ArrayList<>();

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

  TIMEOUTS_LIST.add(1);
  TIMEOUTS_LIST.add(2);
  TIMEOUTS_LIST.add(3);
  TIMEOUTS_LIST.add(4);
  TIMEOUTS_LIST.add(5);
  TIMEOUTS_LIST.add(10);
  TIMEOUTS_LIST.add(20);
  TIMEOUTS_LIST.add(30);
  TIMEOUTS_LIST.add(40);
  TIMEOUTS_LIST.add(50);
  TIMEOUTS_LIST.add(60);
  TIMEOUTS_LIST.add(70);
  TIMEOUTS_LIST.add(80);
  TIMEOUTS_LIST.add(90);
  TIMEOUTS_LIST.add(100);
  TIMEOUTS_LIST.add(1000);

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
   int timeoutsSum = testAndAssignImage(trayIcon, reachable);
   if (timeoutsSum > PAUSE) {
    pause1 = 0;
   } else {
    pause1 = PAUSE - timeoutsSum;
   }
  }

  SystemTray.getSystemTray().remove(trayIcon);
 }

 private static boolean test(int timeout, Reachable reachable1) {
  boolean reachable;
  String reachableWord = "";
  reachable = reachable1.isReachable(HOST, 443, timeout);
  if (!reachable) {
   reachableWord = "not ";
  }
  System.out.println(HOST + " is " + reachableWord + "reachable within "
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
  int timeoutsSum = 0;

  boolean isReached = false;

  for (int iteration = 0; iteration < TIMEOUTS_LIST.size(); iteration++) {
   if (TIMEOUTS_LIST.get(iteration).equals(TIMEOUTS_LIST.get(0))
     && test(TIMEOUTS_LIST.get(iteration), reachable)) {
    isReached = true;
    trayIcon.setImage(createImage(TIMEOUTS_LIST.get(iteration)+ ".png"));
    break;
   } else if (!TIMEOUTS_LIST.get(iteration).equals(TIMEOUTS_LIST.get(0))
     && test(TIMEOUTS_LIST.get(iteration), reachable)) {
    isReached = true;

    for (int timeout : TIMEOUTS_LIST.subList(0, iteration)) {
     timeoutsSum += timeout;
    }
    trayIcon.setImage(createImage(TIMEOUTS_LIST.get(iteration) + ".png"));
    break;
   }
  }

  if (!isReached) {
   trayIcon.setImage(createImage("unknown.png"));
   for (int timeout : TIMEOUTS_LIST) {
    timeoutsSum += timeout;
   }
  }

  System.out.println("timeoutsSum=" + timeoutsSum);

  return timeoutsSum;
 }

 public boolean isRunning() {
  return running;
 }

 public void setRunning(boolean running) {
  this.running = running;
 }
}
