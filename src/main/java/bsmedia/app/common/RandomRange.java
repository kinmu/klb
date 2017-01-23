package bsmedia.app.common;

import java.util.Random;

/****************************************************
 * <PRE>
 * @Project Name	: KLB
 * @File Name		: RandomRange.java
 * @Comment			: Generate random integers in a certain range.
 * @Author			: eunjung
 * @Creation Date	: 2011. 12. 30.오후 12:06:44
 * Copyright ⓒb&s media All Right Reserved
 * </PRE>
 ****************************************************/
public final class RandomRange {

//  public static final void main(String... aArgs){
//    log("Generating random integers in the range 1..10.");
//
//    int START = 10000;
//    int END = 99999;
//    Random random = new Random();
//
//    showRandomInteger(START, END, random);
//
//  }

  public static int showRandomInteger(int aStart, int aEnd, Random aRandom){
    if ( aStart > aEnd ) {
      throw new IllegalArgumentException("Start cannot exceed End.");
    }
    //get the range, casting to long to avoid overflow problems
    long range = (long)aEnd - (long)aStart + 1;
    // compute a fraction of the range, 0 <= frac < range
    long fraction = (long)(range * aRandom.nextDouble());
    int randomNumber =  (int)(fraction + aStart);
    log("Generated : " + randomNumber);
    return randomNumber;
  }

  private static void log(String aMessage){
    System.out.println(aMessage);
  }
}
