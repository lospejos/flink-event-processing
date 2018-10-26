package io.petproject.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

public class OrderMetadata {

   private Long id;
   private String category;
   private Priority priority;

   public OrderMetadata(Long id, String category, Priority priority) {
      setId(id);
      setCategory(category);
      setPriority(priority);
   }

   public Long getId() {
      return id;
   }

   public String getCategory() {
      return category;
   }

   public Priority getPriority() {
      return priority;
   }

   private void setId(Long id) {
      checkNotNull(id, "ID cannot be null");
      checkArgument(id > 0, "ID must be greater than 0");
      this.id = id;
   }

   private void setCategory(String category) {
      checkArgument(!isNullOrEmpty(category), "Category cannot be null or blank");
      this.category = category;
   }

   private void setPriority(Priority priority) {
      checkNotNull(priority, "Priority cannot be null");
      this.priority = priority;
   }

}
