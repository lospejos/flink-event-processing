public class Order {

   private Long id;
   private String category;
   private Priority priority;

   public Order(Long id, String category, Priority priority) {
      this.id = id;
      this.category = category;
      this.priority = priority;
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

}
