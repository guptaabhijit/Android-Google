package com.google.engedu.puzzle8;


import java.util.*;


public class myPriorityQueue extends AbstractCollection
{
    private static class DefaultComparator implements Comparator
    {
        public int compare (Object o1, Object o2)
        {
            return ((Comparable) o1).compareTo(o2);
        }
    }
    private Comparator myComp = new DefaultComparator();
    private int        mySize;
    private ArrayList  myList;


    private class PQItr implements Iterator
    {
        public Object next()
        {
            return myList.get(myCursor);
        }

        public boolean hasNext()
        {
            return myCursor <= mySize;
        }

        public void remove()
        {
            throw new UnsupportedOperationException("remove not implemented");
        }

        private int myCursor = 1;
    }


    public void clear(){
        if(isEmpty()){
            return;
        }
        else{

            while (this.size() > 0)
            {
                this.remove();
            }
        }

    }


    public myPriorityQueue( )
    {
        myList = new ArrayList<>(32);
        myList.add(null);             // first slot has index 1
        mySize = 0;
    }


    public myPriorityQueue(Comparator comp)
    {
        this();
        myComp = comp;
    }


    public myPriorityQueue(Collection coll,Comparator comp)
    {
        this();
        this.myComp = comp;
        myList.addAll(coll);
        mySize = coll.size();

        for(int k=coll.size() /2; k >= 1; k--)
        {
            heapify(k);
        }
    }

    public myPriorityQueue(Collection coll)
    {
        this();

        myList.addAll(coll);
        mySize = coll.size();

        for(int k=coll.size()/2; k >= 1; k--)
        {
            heapify(k);
        }
    }


    public boolean add(Object o)
    {
        myList.add(o);        // stored, but not correct location
        mySize++;             // added element, update count
        int k = mySize;       // location of new element

        while (k > 1 && myComp.compare(myList.get(k/2), o) > 0)
        {
            myList.set(k, myList.get(k/2));
            k /= 2;
        }
        myList.set(k,o);

        return true;
    }

    public int size()
    {
        return mySize;
    }


    public boolean isEmpty()
    {
        return mySize == 0;
    }



    public Object remove()
    {
        if (! isEmpty())
        {
            Object hold = myList.get(1);

            myList.set(1, myList.get(mySize));  // move last to top
            myList.remove(mySize);              // pop last off
            mySize--;
            if (mySize > 1)
            {
                heapify(1);
            }
            return hold;
        }
        return null;
    }


    public Object peek()
    {
        return myList.get(1);
    }


    public Iterator iterator()
    {
        return new PQItr();
    }



    private void heapify(int vroot)
    {
        Object last = myList.get(vroot);
        int child, k = vroot;
        while (2*k <= mySize)
        {
            child = 2*k;
            if (child < mySize &&
                    myComp.compare(myList.get(child),
                            myList.get(child+1)) > 0)
            {
                child++;
            }
            if (myComp.compare(last, myList.get(child)) <= 0)
            {
                break;
            }
            else
            {
                myList.set(k, myList.get(child));
                k = child;
            }
        }
        myList.set(k, last);
    }



    public static void main(String args[])
    {
        /*ArrayList<TemplateClass> list = new ArrayList<>();
        TemplateClass t1 = new TemplateClass(1, "abhijit", 21);
        TemplateClass t2 = new TemplateClass(2,"rahul",20);
        TemplateClass t3 = new TemplateClass(3,"siddhant",21);
*/
    	/*list.add("ab");
    	list.add("cd");
    	list.add("aa");
    	list.add("bb");
    	list.add("z");
    	list.add("bba");
    	*/

     /*   list.add(t3);
        list.add(t2);
        list.add(t1);

        Comparator<Integer> mycom = new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                return o2.intValue()-o1.intValue();
            }
        };
        Comparator<String> stringcom = new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                // TODO Auto-generated method stub
                return o2.compareTo(o1);
            }
        };
        Comparator<TemplateClass> tempCom = new Comparator<TemplateClass>() {

            @Override
            public int compare(TemplateClass arg0, TemplateClass arg1) {
                // TODO Auto-generated method stub
                return arg0.priority().compareTo(arg1.priority());
            }
        };
        myPriorityQueue pq = new myPriorityQueue(list,tempCom);

        while (pq.size() > 0)
        {
            System.out.println(pq.peek().toString());
            pq.remove();
        }*/
    }
}
