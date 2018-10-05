package io.petproject.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriorityTest {

   @Test
   @DisplayName("when code is 'H', lookup should return Priority.HIGH")
   public void priorityCodeHShouldBeHigh() {
      Priority p = Priority.of("H");
      assertThat(p).isEqualTo(Priority.HIGH);
   }

   @Test
   @DisplayName("when code is 'M', lookup should return Priority.MEDIUM")
   public void priorityCodeMShouldBeMedium() {
      Priority p = Priority.of("M");
      assertThat(p).isEqualTo(Priority.MEDIUM);
   }

   @Test
   @DisplayName("when code is 'L', lookup should return Priority.LOW")
   public void priorityCodeLShouldBeLow() {
      Priority p = Priority.of("L");
      assertThat(p).isEqualTo(Priority.LOW);
   }

   @Test
   @DisplayName("when code is 'C', lookup should return Priority.LOWEST")
   public void priorityCodeCShouldBeLowest() {
      Priority p = Priority.of("C");
      assertThat(p).isEqualTo(Priority.LOWEST);
   }

   @Test
   @DisplayName("when any other is given, lookup should return Priority.LOWEST")
   public void anyOtherCodeShouldBeLowest() {
      Priority p = Priority.of("anythingElse");
      assertThat(p).isEqualTo(Priority.LOWEST);
   }

}