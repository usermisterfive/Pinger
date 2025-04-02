package com.usermisterfive;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class BaseTest {

 @Test
 void createImageTest() {
  assertNotNull(Base.createImage("1.png"));
  assertNull(Base.createImage("non-existing.png"));
 }
}
