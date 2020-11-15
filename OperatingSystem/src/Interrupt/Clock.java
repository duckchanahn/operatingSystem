package Interrupt;

import java.util.Timer;
import java.util.TimerTask;

public class Clock{
      Timer timer;
      boolean timeExpiredCheck = false;
      public boolean checkTime() {return this.timeExpiredCheck;}
      final int TIMESLICE = 3;
      
      public Clock() {
         this.timeExpiredCheck = false;
         timer = new Timer();
         long delay = 0;
         TimerTask timerTask = new TimerTask() {
            int time = TIMESLICE;
            @Override
            public void run() {
               if(timeExpiredCheck == false) {
               if(time<0) {
                  timeExpiredCheck = true;
                  // 인터럽트 셋팅
               }else {
                  System.out.println("TIMESLICE: " + time);
                  if(time == 0) {
                     timeExpiredCheck = true;
                  }
                  time--;
               }
            }
            }
            
         };
         timerTask.run();
         timer.schedule(timerTask, delay, TIMESLICE);
      }
   
      public void cancel() {
         timer.cancel();
         timeExpiredCheck = false;
         System.out.println("TIMESLICE 종료");
      }

   }