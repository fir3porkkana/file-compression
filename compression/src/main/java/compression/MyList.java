package compression;

/**
 *
 * @author tiera
 * @param <Type> the type of object that the list holds
 */
public class MyList<Type> {
    private Type type;
    private Type[] array;

    MyList() {
        this.array = (Type[]) new Object[20];
    }
    
    /**
   * Puts the provided object to an index right after the last occupied index.
   * @param object the object to be appended to the list
   */
    public void append(Type object) {
        if (array[array.length - 1] != null) {
            lengthen();
        }
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == null && array[i - 1] != null) {
                array[i] = object;
            }
            
            if (i/array.length > 3/4) {
                lengthen();
            }
        }
    }
    
    // public Type pop() {
    //     for (int i = array.length - 1; i >= 0; i--) {
    //         if (array[i] != null) {
    //             lastItem = array[i];
    //         }
    //     }
    //     return
    // }
    
    /**
   * Returns the object from the given index.
   * @param index the index where the entry is to be returned from.
   * @return the object in the given index.
   */
    public Type get(int index) {
        return array[index];
    }
    
    /**
   * Puts the given object into the given index.
   * @param index the index where the object is to be put into.
   * @param object the object that is to be put into the list.
   * 
   */
    public void put(int index, Type object) {
        if (index > array.length - 1) {
            lengthen(index/array.length + 1);
        }
        array[index] = object;
    }
    
    private void lengthen() {
        double length = array.length*1.5 + 1;
        Type[] replacement = (Type[]) new Object[(int) length];
        for (int i = 0; i < array.length; i++) {
            replacement[i] = array[i];
        }
        array = replacement;
    }
    
    private void lengthen(double multiplier) {
        double length = array.length*multiplier + 1;
        Type[] replacement = (Type[]) new Object[(int) length];
        for (int i = 0; i < array.length; i++) {
            replacement[i] = array[i];
        }
        array = replacement;
    }
}