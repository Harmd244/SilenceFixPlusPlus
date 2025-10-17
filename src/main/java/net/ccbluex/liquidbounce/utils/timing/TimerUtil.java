/*    */ package net.ccbluex.liquidbounce.utils.timing;
/*    */ 
/*    */ public class TimerUtil {
/*    */   private boolean run = true;
/*  5 */   public long lastMS = System.currentTimeMillis();
/*  6 */   private long time = System.currentTimeMillis();
/*    */   
/*    */   public TimerUtil(boolean run) {
/*  9 */     this.run = run;
/*    */   }
/*    */   
/*    */   public void start() {
/* 13 */     this.run = true;
/*    */   }
/*    */   
/*    */   public void stop() {
/* 17 */     this.run = false;
/*    */   }
/*    */   
/*    */   public long getTimePassed() {
/* 21 */     return System.currentTimeMillis() - this.lastMS;
/*    */   }
/*    */   public long getTimeElapsed() {
/* 24 */     return System.currentTimeMillis() - this.lastMS;
/*    */   }
/*    */   public boolean hasTimePassed(long time) {
/* 27 */     return (System.currentTimeMillis() - this.lastMS >= time);
/*    */   }
/*    */   public void reset() {
/* 30 */     this.lastMS = System.currentTimeMillis();
/* 31 */     this.time = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public long getElapsedTime() {
/* 35 */     return this.run ? (System.currentTimeMillis() - this.time) : 0L;
/*    */   }
/*    */   
/*    */   public boolean hasTimeElapsed(long milliseconds) {
/* 39 */     return (this.run && getElapsedTime() >= milliseconds);
/*    */   }
/*    */   
/*    */   public boolean hasTimeElapsed(long time, boolean reset) {
/* 43 */     if (System.currentTimeMillis() - this.lastMS > time) {
/* 44 */       if (reset) reset(); 
/* 45 */       return true;
/*    */     } 
/*    */     
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   public void delay(long milliseconds) {
/* 52 */     this.time += milliseconds;
/*    */   }
/*    */   
/*    */   public static long getCurrentTime() {
/* 56 */     return System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public boolean isOver(long milliseconds) {
/* 60 */     return (System.currentTimeMillis() - this.time > milliseconds);
/*    */   }
/*    */   
/*    */   public long remainingTime(long milliseconds) {
/* 64 */     long elapsedTime = System.currentTimeMillis() - this.time;
/* 65 */     return (elapsedTime < milliseconds) ? (milliseconds - elapsedTime) : 0L;
/*    */   }
/*    */   
/*    */   public boolean isRun() {
/* 69 */     return this.run;
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 73 */     return this.time;
/*    */   }
/*    */   
/*    */   public void setRun(boolean run) {
/* 77 */     this.run = run;
/*    */   }
/*    */   
/*    */   public void setTime(long time) {
/* 81 */     this.time = time;
/*    */   }
/*    */   
/*    */   public TimerUtil() {}
/*    */ }


/* Location:              C:\Users\DSJ_L\Desktop\DeOBF.jar!\dev\xinxi\\utils\TimerUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */