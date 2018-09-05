import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

public class Order {

   private Long id;
   private String category;
   private Priority priority;
   private SalesOrder salesOrder;

   public Order(Long id, String category, Priority priority, SalesOrder salesOrder) {
      setId(id);
      setCategory(category);
      setPriority(priority);
      setSalesOrder(salesOrder);
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

   public SalesOrder getSalesOrder() {
      return salesOrder;
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

   private void setSalesOrder(SalesOrder salesOrder) {
      checkNotNull(salesOrder, "Sales Order cannot be null");
      this.salesOrder = salesOrder;
   }
}
