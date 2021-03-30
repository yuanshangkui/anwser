package MyList;

public class ArrayListOfMyList implements MyList {
    private int size = 0;
    private Object[] arrayList = new Object[0];

    ArrayListOfMyList(){

    }

    ArrayListOfMyList(Object ... args) {
        this.addAll(args) ;
    }

    public int getSize() {
        return size;
    }

    @Override
    public Object get(int index) {
        return this.arrayList[index];
    }

    @Override
    public Object set(int index, Object element) {
        arrayList[index] = element ;
        return element;
    }

    @Override
    public boolean contains(Object o) {
        for (Object obj: arrayList) {
            if (o.equals(obj)){
                return true ;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Object[] c) {
        for (Object obj: c) {
            if (!this.contains(obj)){
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Object[] toArray() {
        return arrayList;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean add(Object e) {
        Object[] objects = new Object[this.size+1];
        if (this.size >= 0) {
            System.arraycopy(arrayList, 0, objects, 0, this.size);
        }
        objects[this.size] = e;
        ++ this.size;
        arrayList = objects ;
        return true;
    }

    @Override
    public boolean addAll(Object[] e) {
        if (e.length!=0){
            for (Object obj: e) {
                if (!this.add(obj)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean remove(Object e) {
        boolean isRemove = false ;
        for (int i=0;i<this.size;i++) {
            if (this.arrayList[i].equals(e)){
                Object[] object = new Object[this.size-1];
                int t = 0;
                for (int j=0;j<this.size;j++){
                    if (j!=i){
                        object[t++] = arrayList[j];
                    }
                }
                arrayList = object ;
                -- this.size ;
                isRemove = true;
                break;
            }
        }
        return isRemove;
    }

    @Override
    public boolean removeAll(Object[] e) {
        boolean isRemoveAll = false ;
        for (Object obj : e) {
            if (this.remove(obj)){
                isRemoveAll = true ;
            }
        }
        return isRemoveAll;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.arrayList = null;
    }

    @Override
    public int indexOf(Object o) {
        int index  = -1;
        for (int i = 0; i < this.size; i++) {
            if (arrayList[i].equals(o)){
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index  = -1;
        for (int i = this.size-1; i >= 0 ; i-- ) {
            if (arrayList[i].equals(o)){
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true ;
        } else if (o instanceof ArrayListOfMyList){
            ArrayListOfMyList obj = (ArrayListOfMyList) o;
            if (obj.size==this.size){
                for (int i = 0; i < this.size; i++) {
                    if (!this.arrayList[i].equals(obj.arrayList[i])){
                        return false;
                    }
                }
                return true;
            }
        }
        return false ;
    }

    @Override
    public String toString(){
        StringBuilder string = new StringBuilder(" [ ");
        for (int i = 0; i < this.size; i++) {
            string.append(arrayList[i].toString());
            if (i!=this.size-1){
                string.append(" , ");
            }
        }
        string.append(" ] ");
        return string.toString();
    }
}
