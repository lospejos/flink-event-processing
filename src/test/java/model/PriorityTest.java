package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriorityTest {

   @Test
   @DisplayName("let: code 'H' lookup to be model.Priority.HIGH")
   public void priorityCodeHShouldBeHigh() {
      Priority p = Priority.of('H');
      Assertions.assertThat(p).isEqualTo(Priority.HIGH);
   }

   @Test
   @DisplayName("let: code 'M' lookup to be model.Priority.MEDIUM")
   public void priorityCodeMShouldBeMedium() {
      Priority p = Priority.of('M');
      Assertions.assertThat(p).isEqualTo(Priority.MEDIUM);
   }

   @Test
   @DisplayName("let: code 'L' lookup to be model.Priority.LOW")
   public void priorityCodeLShouldBeLow() {
      Priority p = Priority.of('L');
      Assertions.assertThat(p).isEqualTo(Priority.LOW);
   }

   @Test
   @DisplayName("let: code 'C' lookup be model.Priority.LOWEST")
   public void priorityCodeCShouldBeLowest() {
      Priority p = Priority.of('C');
      Assertions.assertThat(p).isEqualTo(Priority.LOWEST);
   }

   @Test
   @DisplayName("let: any other code lookup be model.Priority.LOWEST")
   public void anyOtherCodeShouldBeLowest() {
      Priority p = Priority.of('Z');
      Assertions.assertThat(p).isEqualTo(Priority.LOWEST);
   }

}