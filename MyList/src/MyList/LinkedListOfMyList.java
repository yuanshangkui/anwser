package MyList;

class Node {
    Node(){

    }

    Node(Object o ){
        this.data = o;
    }

    Node previous;
    Object data ;
    Node next ;
}
public class LinkedListOfMyList implements MyList{
    private int size = 0;
    private Node head = new Node();
    private Node last = new Node();
    {
        head.previous = null ;
        head.data = null;
        head.next = last ;

        last.data = null ;
        last.next = null ;
        last.previous = head ;
    }

    @Override
    public Object get(int index) {
        if (this.size<index||index<=0){
            throw new RuntimeException("OverArrayRange!");
        } else {
            Node pointer = head ;
            int count = 0;
            while (pointer.next != last) {
                pointer = pointer.next ;
                ++count ;
                if (count == index ){
                    return pointer.data ;
                }
            }
        }
        return null ;
    }

    @Override
    public Object set(int index, Object element) {
        if (this.size<index){
            throw new RuntimeException("OverArrayRange!");
        } else {
            Node pointer = head ;
            int count = 0;
            while (pointer.next != last) {
                pointer = pointer.next ;
                ++count ;
                if (count == index ){
                    pointer.data = element ;
                    return pointer.data ;
                }
            }
        }
        return null ;
    }

    @Override
    public boolean contains(Object o) {
        Node pointer = head ;
        while (pointer.next != last) {
            pointer = pointer.next ;
            if ( pointer.data.equals(o) ){
                return true ;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Object[] c) {
        if (c.length!=0){
            for (Object o : c) {
                if ( !this.contains(o) ) {
                    return false;
                }
            }
            return true ;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[this.size];
        Node pointer = head ;
        int count = 0 ;
        while (pointer.next != last) {
            pointer = pointer.next ;
            objects[count ++] = pointer.data ;
        }
        return objects;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean add(Object e) {
        Node pointer1 = this.head ;
        Node pointer2 = this.head.next ;
        Node inObj = new Node(e);
        pointer1.next = inObj ;
        inObj.next = pointer2 ;
        pointer2 .previous = inObj ;
        inObj.previous = pointer1 ;
        ++ this.size ;
        return true ;
    }

    @Override
    public boolean addAll(Object[] e) {
        if (e.length != 0){
            for ( Object o : e ){
                if(!this.add(o)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean remove(Object e) {
        Node pointer = head ;
        boolean isRemove = false ;
        while (pointer.next != last) {
            pointer = pointer.next ;
            if ( pointer.data.equals(e) ){
                pointer.previous.next = pointer.next;
                pointer.next.previous = pointer.previous;
                this.size -- ;
                isRemove = true ;
            }
        }
        return isRemove;
    }

    @Override
    public boolean removeAll(Object[] e) {
        boolean isRemoveAll = false ;
        if(e.length!=0){
            for ( Object o :e ){
                if (remove(o)){
                    isRemoveAll = true;
                }
            }
        }
        return isRemoveAll;
    }

    @Override
    public void clear() {
        head.next = last ;
        last.previous = head ;
        this.size = 0 ;
    }

    @Override
    public boolean equals(Object o){
        boolean isEqual = true ;
        if (o!=this){
             if ( o instanceof LinkedListOfMyList){
                LinkedListOfMyList obj = (LinkedListOfMyList) o ;
                if (this.size != obj.size ){
                    isEqual = false ;
                } else {
                    Node pointer1 = this.head;
                    Node pointer2 = obj.head;
                    while (pointer1.next!=this.last){
                        pointer1 = pointer1.next;
                        pointer2 = pointer2.next;
                        if ( !pointer1.data.equals(pointer2.data)){
                            isEqual = false ;
                            break;
                        }
                    }
                }
            }
        }
        return isEqual ;
    }

    @Override
    public int indexOf(Object o) {
        int index = 1 ;
        Node pointer = head ;
        while ( pointer.next != last ){
            pointer = pointer.next ;
            if ( pointer.data.equals(o) ) {
                return index;
            }
            ++ index ;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = this.size ;
        Node pointer = last ;
        while ( pointer.previous != head ){
            pointer = pointer.previous ;
            if ( pointer.data.equals(o) ) {
                return index;
            }
            -- index ;
        }
        return -1;
    }

    @Override
    public String toString(){
        StringBuilder string = new StringBuilder("[ ");
        Node pointer = this.head ;
        while ( pointer.next != this.last ){
            pointer = pointer.next ;
            string.append(pointer.data.toString());
            if (pointer.next != last ){
                string.append(" , ");
            }
        }
        string.append(" ]");
        return string.toString();
    }
}
