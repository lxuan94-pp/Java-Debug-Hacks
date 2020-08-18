package iss.java.list;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
/**
 * Created by wenke on 2016/9/16.
 * // TODO: Modify this class to meet requirement B and C.
 */
public class MyList {
    // two guards. Do not call remove() on them!!
    private Node head;
    private Node tail;
    private int size;
    ReentrantLock lock = new ReentrantLock();

    public MyList() {
        head = new Node().setData(0).setJudge(this);
        tail = new Node().setData(0).setNext(null).setPrev(head).setJudge(this);
        head.setNext(tail);
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    /**
     * Insert a node with <pre>data</pre> after <pre>_prev</pre>.
     *
     * @param _prev
     * @param data
     * @return The node just inserted.
     */
    public Node insert(Node _prev, int data)
    {
        if (!_prev.getJudge().equals(this))
        {
            throw new RuntimeException("The insertion can only be applied to nodes that belongs to the caller linked list");
        }

            Node node = new Node().setData(data).setNext(_prev.getNext()).setPrev(_prev).setJudge(this);
                lock.lock();
            try
              {
               _prev.getNext().setPrev(node);
               _prev.setNext(node);
               ++size;

              }
           finally
              {
               lock.unlock();
              }
                return node;

    }

    /**
     * Remove the node <pre>target</pre>.
     *
     * @param target The node to remove.
     * @return the previous node of target.
     */
    public Node remove(Node target) {
        if (!target.getJudge().equals(this))
        {
            throw new RuntimeException("The remove can only be applied to nodes that belongs to the caller linked list");

        }

        if (target == head || target == tail)
        {
            throw new RuntimeException("DO NOT remove the head or tail node. They are guards.");
        }
        // shortcut "target
        lock.lock();
        try
        {
            Node prev = target.getPrev();
            Node next = target.getNext();
            prev.setNext(next);
            next.setPrev(prev);
            --size;
            return prev;
        }
        finally
        {
            lock.unlock();
        }

        // Unlike C/C++, the memory of "target" is automatically recycled by GC
        // return the previous one because it is quite likely to insert a new node after prev;}
    }
}
