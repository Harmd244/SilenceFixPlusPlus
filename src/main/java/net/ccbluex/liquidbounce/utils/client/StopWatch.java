/*    */ package net.ccbluex.liquidbounce.utils.client;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class StopWatch {
/*    */   private long millis;
/*    */   
/*    */   public void setMillis(long millis) {
/*  9 */     this.millis = millis;
/*    */   }
/*    */   
/*    */   public static long randomDelay(int minDelay, int maxDelay) {
/* 13 */     return nextInt(minDelay, maxDelay);
/*    */   }
/*    */   
/*    */   public static int nextInt(int startInclusive, int endExclusive) {
/* 17 */     return (endExclusive - startInclusive <= 0) ? startInclusive : (startInclusive + (new Random()).nextInt(endExclusive - startInclusive));
/*    */   }
/*    */ 
/*    */   
/*    */   public StopWatch() {
/* 22 */     reset();
/*    */   }
/*    */   
/*    */   public boolean finished(long delay) {
/* 26 */     return (System.currentTimeMillis() - delay >= this.millis);
/*    */   }
/*    */   public boolean hasTimeElapsed(double time) {
/* 29 */     return hasTimeElapsed((long)time);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 33 */     this.millis = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public long getMillis() {
/* 37 */     return this.millis;
/*    */   }
/*    */   
/*    */   public long getElapsedTime() {
/* 41 */     return System.currentTimeMillis() - this.millis;
/*    */   }
/*    */ }


/* Location:              C:\Users\DSJ_L\Desktop\DeOBF.jar!\dev\xinxi\\utils\client\StopWatch.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */