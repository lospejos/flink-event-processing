import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class SalesOrder {

   private Integer unitsSold;
   private BigDecimal unitPrice;
   private BigDecimal unitCost;
   private BigDecimal totalRevenue;
   private BigDecimal totalCost;
   private BigDecimal totalProfit;

   public SalesOrder(Integer unitsSold, BigDecimal unitPrice, BigDecimal unitCost,
                     BigDecimal totalRevenue, BigDecimal totalCost, BigDecimal totalProfit) {
      setUnitsSold(unitsSold);
      setUnitPrice(unitPrice);
      setUnitCost(unitCost);
      setTotalRevenue(totalRevenue);
      setTotalCost(totalCost);
      setTotalProfit(totalProfit);
   }

   public SalesOrder(Integer unitsSold, BigDecimal unitPrice, BigDecimal unitCost) {
      this(unitsSold, unitPrice, unitCost, null, null, null);
   }

   public Integer getUnitsSold() {
      return unitsSold;
   }

   public BigDecimal getUnitPrice() {
      return unitPrice;
   }

   public BigDecimal getUnitCost() {
      return unitCost;
   }

   public BigDecimal getTotalRevenue() {
      return totalRevenue;
   }

   public BigDecimal getTotalCost() {
      return totalCost;
   }

   public BigDecimal getTotalProfit() {
      return totalProfit;
   }


   private void setUnitsSold(Integer unitsSold) {
      checkNotNull(unitsSold, "Units Sold cannot be null");
      checkArgument(unitsSold > 0, "Units Sold must be greater than 0");
      this.unitsSold = unitsSold;
   }

   private void setUnitPrice(BigDecimal unitPrice) {
      checkNotNull(unitPrice, "Unit Price cannot be null");
      checkArgument(unitPrice.compareTo(BigDecimal.ZERO) > 0, "Unit Price must be greater than 0");
      this.unitPrice = unitPrice;
   }

   private void setUnitCost(BigDecimal unitCost) {
      checkNotNull(unitCost, "Unit Cost cannot be null");
      checkArgument(unitCost.compareTo(BigDecimal.ZERO) > 0, "Unit Cost must be greater than 0");
      this.unitCost = unitCost;
   }

   private void setTotalRevenue(BigDecimal totalRevenue) {
      if (totalRevenue == null) {
         this.totalRevenue = getUnitPrice().multiply(BigDecimal.valueOf(getUnitsSold()));
      } else {
         this.totalRevenue = totalRevenue;
      }
   }

   private void setTotalCost(BigDecimal totalCost) {
      if (totalCost == null) {
         this.totalCost = getUnitCost().multiply(BigDecimal.valueOf(getUnitsSold()));
      } else {
         this.totalCost = totalCost;
      }
   }

   private void setTotalProfit(BigDecimal totalProfit) {
      this.totalProfit = getTotalRevenue().subtract(getTotalCost());
   }

}
