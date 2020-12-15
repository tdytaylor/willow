package com.fourinone;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigContextTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getMulBean() {
    ConfigContext.getMulBean();
  }

  @Test
  void getYMMZ() {
    String ymmz = ConfigContext.getYMMZ();
    System.out.println(ymmz);
  }
}