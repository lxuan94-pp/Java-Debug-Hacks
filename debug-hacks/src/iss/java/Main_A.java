package iss.java;


import iss.java.list.MyList;
import iss.java.list.Node;


import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

/**
 * Created by wenke on 2016/9/16.
 */
public class Main_A
{
    public static Lock lock = new ReentrantLock();
    public static void main(String[] args)throws   InterruptedException
    {
        // TODO: Implement a multithreaded test case against requirement A.
        MyList list = new MyList();
        list.insert(list.getHead(), 5);
        list.insert(list.getHead(), 4);
        list.insert(list.getHead(), 3);
        list.insert(list.getHead(), 2);
        list.insert(list.getHead(), 1);

        Thread t1 = new Thread()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < 1000000; ++i)
                {
                    lock.lock();
                    try
                    {
                        for (Node n = list.getHead().getNext(); n != list.getTail(); n = n.getNext())
                        {
                            int data = n.getData();
                            data--;
                            n.setData(data);
                        }
                    }
                    finally
                    {
                        lock.unlock();
                    }
                }
            }
        };

        Thread t2 = new Thread()
        {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; ++i)
                {
                    lock.lock();
                  try
                  {
                      for (Node n = list.getHead().getNext(); n != list.getTail(); n = n.getNext())
                      {
                          int data = n.getData();
                          data++;
                          n.setData(data);
                      }
                  }
                  finally
                  {
                      lock.unlock();
                  }



                }

            }

        };

        t1.start();
        t2.start();

        Thread.sleep(1000);

        for (Node n = list.getHead().getNext(); n!=list.getTail(); n=n.getNext())
        {
            System.out.printf("%d ", n.getData());
        }
    }
}