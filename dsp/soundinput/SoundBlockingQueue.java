/*
 * Created by canti, 23.11.2004
 *
 */
package dsp.soundinput;

import java.util.*;

/*
 * This is a thread-safe queue that blocks automatically if you try to dequeue
 * from an empty queue. It's based on a linked list, so it will never fill up.
 * (You'll never block on a queue-full condition because there isn't one.)
 * 
 * This class uses the LinkedList class, introduced into the JDK at version 1.2
 * (aka Java 2). It will not work with earlier releases.
 * 
 * (c) 1999, Allen I. Holub.
 * 
 * 
 * You may not distributed this code except in binary form, incorporated into a
 * java .class file. You may use this code freely for personal purposes, but you
 * may not incorporate it into any commercial product without my express
 * permission in writing.
 * 
 * @author Allen I. Holub
 */

public class SoundBlockingQueue
{
    private LinkedList elements = new LinkedList();
    private boolean    closed   = false;

    public class Closed extends RuntimeException
    {   
        private Closed()
        {   
            super("Tried to access closed Blocking_queue");
        }
    }

    public synchronized final void add( Object new_element )
                                            throws SoundBlockingQueue.Closed
    {   
        if ( closed )
            throw new Closed();
        elements.addLast( new_element );
        notify();
    }

    /*
     * Dequeues an element; blocks if the queue is empty (until something is
     * enqueued). Be careful of nested-monitor lockout if you call this
     * function. You must ensure that there's a way to get something into the
     * queue that does not involve calling a synchronized method of whatever
     * class is blocked, waiting to dequeue something.
     * 
     * @see dequeue
     * @see enqueue
     * @return s the dequeued object always
     */
    
    public synchronized final Object take() throws InterruptedException,
                                                   SoundBlockingQueue.Closed
    {   
        try
        {  
            while ( elements.size() <= 0 )
            {   
                wait();
                if ( closed )
                    throw new Closed();
            }
            return elements.removeFirst();
        }
        catch ( NoSuchElementException e )   // Shouldn't happen
        {   
            throw new Error(
                    "Internal error (com.holub.asynch.Blocking_queue)");
        }
	}

    /*
     * The is_empty() method is inherently unreliable in a multithreaded
     * situation. In code like the following, it's possible for a thread to
     * sneak in after the test but before the dequeue operation and steal the
     * element you thought you were dequeueing.
     * 
     * SoundBlockingQueue some_queue = new SoundBlockingQueue(); //... 
     * if(!some_queue.is_empty() ) some_queue.take();
     * 
     * 
     * To do the foregoing reliably, you must synchronize on the queue as
     * follows: SoundBlockingQueue some_queue = new SoundBlockingQueue(); //... 
     * synchronized(queue ) { if( !some_queue.is_empty() ) some_queue.take(); }
     * 
     * The same effect can be achieved if the test/dequeue operation is done
     * inside a synchronized method, and the only way to add or remove queue
     * elements is from other synchronized methods.
     */

	public synchronized final boolean is_empty()
	{  
	     return elements.size() > 0;
	}

     /*
      * Releasing a blocking queue causes all threads that are blocked [waiting
      * in take() for items to be enqueued] to be released. The take()
      * call will throw a SoundBlockingQueue.Closed runtime exception instead of
      * returning normally in this case. Once a queue is closed, any attempt to
      * add() an item will also result in a SoundBlockingQueue.Closed exception
      * toss.
      */

	public synchronized void close()
	{  
         closed = true;
         notifyAll();
	}
}