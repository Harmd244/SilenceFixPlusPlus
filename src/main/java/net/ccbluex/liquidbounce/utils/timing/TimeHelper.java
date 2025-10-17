/*    */ package net.ccbluex.liquidbounce.utils.timing;
/*    */ 
/*    */ public class TimeHelper {
/*  4 */   public long lastMs = 0L;
/*    */   
/*    */   public void reset() {
/*  7 */     this.lastMs = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public boolean delay(long nextDelay) {
/* 11 */     return (System.currentTimeMillis() - this.lastMs >= nextDelay);
/*    */   }
/*    */   
/*    */   public boolean delay(float nextDelay, boolean reset) {
/* 15 */     if ((float)(System.currentTimeMillis() - this.lastMs) >= nextDelay) {
/* 16 */       if (reset) {
/* 17 */         reset();
/*    */       }
/* 19 */       return true;
/*    */     } 
/* 21 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isDelayComplete(double valueState) {
/* 25 */     return ((System.currentTimeMillis() - this.lastMs) >= valueState);
/*    */   }
/*    */   
/*    */   public long getElapsedTime() {
/* 29 */     return System.currentTimeMillis() - this.lastMs;
/*    */   }
/*    */   
/*    */   public long getLastMs() {
/* 33 */     return this.lastMs;
/*    */   }
/*    */ }


/* Location:              C:\Users\DSJ_L\Desktop\DeOBF.jar!\dev\xinxi\\utils\TimeHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */