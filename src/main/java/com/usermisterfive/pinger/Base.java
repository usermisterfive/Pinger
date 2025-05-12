package com.usermisterfive.pinger;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import javax.swing.ImageIcon;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Base {
 private static final Logger LOGGER = LogManager.getLogger(Base.class);
 private static final String HOST = "1.1.1.1";
 private boolean running;
 private static final List<Integer> TIMEOUTS_LIST = List
   .of(1, 2, 3, 4, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 1000);
 private static final Image UNKNOWN_IMAGE = createImage("unknown.png");

 @SuppressWarnings("BusyWait")
 public static void main(String[] args) {
  LOGGER.debug("enter");

  final Base base = new Base();
  base.setRunning(true);
  final Reachable reachable = Reachables.get();

  final TrayIcon trayIcon = new TrayIcon(Objects.requireNonNull(UNKNOWN_IMAGE));
  final MenuItem exitMenuItem = new MenuItem("Exit");
  exitMenuItem.addActionListener(actionEvent -> base.setRunning(false));
  final PopupMenu popupMenu = new PopupMenu();
  popupMenu.add(exitMenuItem);
  trayIcon.setPopupMenu(popupMenu);

  try {
   SystemTray.getSystemTray().add(trayIcon);
  } catch (AWTException awtException) {
   LOGGER.error("", awtException);
  }

  final int PAUSE = 1000;
  int pause1 = PAUSE;

  while (base.isRunning()) {
   LOGGER.debug("enter while");
   int timeoutsSum = 0;
   try {
    Thread.sleep(pause1);
    timeoutsSum = testAndAssignImage(trayIcon, reachable);
   } catch(InterruptedException | IOException exception) {
    LOGGER.error("", exception);
    trayIcon.setImage(UNKNOWN_IMAGE);
   }
   if (timeoutsSum > PAUSE) {
    pause1 = 0;
   } else {
    pause1 = PAUSE - timeoutsSum;
   }
   LOGGER.debug("exit while");
  }

  SystemTray.getSystemTray().remove(trayIcon);
  LOGGER.debug("exit");
 }

 private static boolean test(int timeout, Reachable reachable1)
   throws IOException {
  LOGGER.debug("enter");
  boolean reachable;
  String reachableWord = "";
  reachable = reachable1.isReachable(HOST, 443, timeout);
  if (!reachable) {
   reachableWord = "not ";
  }
  LOGGER.debug("{} is {} reachable within {} ms",
    HOST, reachableWord, timeout);

  LOGGER.debug("exit");
  return reachable;
 }

 public static Image createImage(String path) {
  LOGGER.debug("enter");
  URL imageURL = Base.class.getClassLoader().getResource(path);
  Image image = null;
  if (imageURL == null) {
   LOGGER.error("Resource not found: {}", path);
  } else {
   image = (new ImageIcon(imageURL, "")).getImage();
  }
  LOGGER.debug("exit");
  return image;
 }

 private static int testAndAssignImage(TrayIcon trayIcon, Reachable reachable)
   throws IOException {
  LOGGER.debug("enter");
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
   trayIcon.setImage(UNKNOWN_IMAGE);
   for (int timeout : TIMEOUTS_LIST) {
    timeoutsSum += timeout;
   }
  }

  LOGGER.debug("timeoutsSum={}", timeoutsSum);
  LOGGER.debug("exit");
  return timeoutsSum;
 }

 public boolean isRunning() {
  return running;
 }

 public void setRunning(boolean running) {
  this.running = running;
 }
}
