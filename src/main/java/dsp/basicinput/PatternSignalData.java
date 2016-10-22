/*
 * Created on Jan 15, 2005
 *
 */
package dsp.basicinput;

import java.util.Vector;

/**
 * @author Flaviu
 *
 */

public class PatternSignalData
{
   protected class SignalValue
   {
       public final double time;
       public final double delay;
       public final double value;       
       
       SignalValue( final double time, final double delay, final double value)
       {           
           this.time  = time;
           this.delay = delay;
           this.value = value;           
       }
   }
   
   protected Vector signalValues;
   protected double patternDuration;
   
   public PatternSignalData()
   {
       signalValues = new Vector();
       patternDuration = 0;
   }
   
   public void addItem( final double delay, final double value)
   {
       signalValues.add( new SignalValue( patternDuration, delay, value));       
       patternDuration += delay;       
   }

   protected double normalized( double time)
   {
       while( time > patternDuration) time -= patternDuration;
       return time;
   }
   
   protected int getIndex( double time)
   {
       for( int i = signalValues.size() - 1; i >= 0; --i)                 
           if ( (( SignalValue) signalValues.get( i)).time < time)
              return i;                   
       return 0;
   }
   
   public double getValue( double time)   
   {
       time = normalized( time);
       final int current = getIndex( time);
       final int next    = ( current + 1) % signalValues.size();
       
       final SignalValue signalValue = ( SignalValue) signalValues.get( current);
       
       final double v1 = signalValue.value;           
       final double v2 = (( SignalValue) signalValues.get( next)).value;
       
       if ( v1 == v2)
           return v1;
       
       if ( signalValue.delay == 0)
           return ( v1 + v2) / 2;
       
       return v1 + ( time - signalValue.time) * ( v2 - v1) / signalValue.delay;     
   }
   
   public double getPatternDuration()
   {
       return patternDuration;
   }
}