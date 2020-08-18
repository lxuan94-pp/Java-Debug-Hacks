package iss.java;

import iss.java.list.MyList;
import iss.java.list.Node;


import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

/**
 * Created by wenke on 2016/9/16.
 */
public class Main_B {
    public static Lock lock = new ReentrantLock();
    public static void main(String[] args)throws   InterruptedException {
        // TODO: Write a multithreaded testcase against requirement B.
        // NOTE: Testcase for requirement C is not required.
        MyList list2 = new MyList();
        list2.insert(list2.getHead(), -5);
        list2.insert(list2.getHead(), -4);
        list2.insert(list2.getHead(), -3);
        list2.insert(list2.getHead(), -2);
        list2.insert(list2.getHead(), -1);

        InsertThread insertThread = new InsertThread(list2);
        RemoveThread removeThread = new RemoveThread(list2);

        insertThread.start();
        removeThread.start();

        Thread.sleep(1000);

        // Traverse from head to tail, exclude head and tail
        for (Node n = list2.getHead().getNext(); n!=list2.getTail(); n=n.getNext()) {
            System.out.printf("%d ", n.getData());
        }
        System.out.println();

        // Traverse from tail to head, exclude tail and head.
        for (Node n = list2.getTail().getPrev(); n!=list2.getHead(); n=n.getPrev()) {
            System.out.printf("%d ", n.getData());
        }

        //print the number of node

    }
}

class InsertThread extends Thread
{
    private MyList list2;

    public MyList getlist2() {
        return list2;
    }

    public void setlist2(MyList list2) {
        this.list2 = list2;
    }

    public InsertThread(MyList list2) {
        this.list2 = list2;
    }

    @Override
    public void run() {


        for (int i = 0; i < 1000; i++)
        {
            list2.insert(list2.getHead(), i);
        }
    }
}

class RemoveThread extends Thread
{
    private MyList list2;

    public MyList getlist2() {
        return list2;
    }

    public void setTheList(MyList list2) {
        this.list2 = list2;
    }
    public RemoveThread(MyList list2)
    {
        this.list2=list2;
    }

    @Override
    public void run()
    {
        for (int i=0;i<1000;i++)
        {
        if (list2.getHead().getNext().equals(list2.getTail()))
        {
            i--;
        }
        else
            {
                list2.remove(list2.getHead().getNext());
            }

        }
    }
}
