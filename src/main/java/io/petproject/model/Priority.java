package io.petproject.model;

public enum Priority {
   HIGH,
   MEDIUM,
   LOW,
   LOWEST;

   public static Priority of(String priorityCode) {
      Priority priority;
      switch (priorityCode) {
         case "H":
            priority = Priority.HIGH; break;
         case "M":
            priority = Priority.MEDIUM; break;
         case "L":
            priority = Priority.LOW; break;
         default:
            priority = Priority.LOWEST;
      }
      return priority;
   }

}