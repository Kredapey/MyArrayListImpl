package aston.bootcamp;

import java.util.*;
import java.util.function.Consumer;

/**
 * <p>Моя реализация класса ArrayList из пакета java.util.ArrayList.
 * Внутри представляет из себя автоматически расширяемый массив.
 * Этот класс предоставляет методы для добавления данных в массив,
 * их изменения, удаления и манипуляции над ними.</>
 * <p>Методы {@code size}, {@code get}, {@code set}, {@code listIterator} и {@code isEmpty}
 * выполняются за константное время (O(1)). Вставка элемента в конец массива также
 * занимает константное время, вставка же элемента в середину массива и удаление элемента
 * занимает O(n) времени.</p>
 * <p>Обратите внимание на то, что класс не потокобезопасен и не подходит
 * для работы в конкурентной среде.</p>
 * <p>Класс предоставляет метод {@code listIterator}, который возвращает объект типа
 * ListIterator для обхода коллекции</p>
 *
 * @param <T> тип элементов в коллекции
 * @author Никита Трофимов
 * @since 1.0
 */
public class ArrayList<T> {
    /**
     * Стандартный размер создаваемого массива внутри ArrayList
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * Пустой массив, необходимый для инициализации пустого ArrayList
     */
    private static final Object[] EMPTY_ARRAY = {};
    /**
     * Максимальный размер выделенной памяти для внутреннего массива.
     * Не Integer.MAX_VALUE из-за ограничений виртуальной машины
     */
    private static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;
    /**
     * Поле-маркер для стандартного роста внутреннего массива при расширении
     */
    private static final int DEFAULT_GROWTH = -1;
    /**
     * Внутренний массив, хранящий все элементы
     */
    private Object[] array;
    /**
     * Количество элементов в массиве
     */
    private int size;

    /**
     * Стандартный конструктор, создающий внутренний массив на 10 элементов
     */
    public ArrayList() {
        array = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Конструктор, задающий размер, указанный пользователем
     *
     * @param initialCapacity размер массива, который нужно создать
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        } else if (initialCapacity == 0) {
            array = EMPTY_ARRAY;
        } else {
            array = new Object[initialCapacity];
        }
    }

    /**
     * Конструктор, создающий ArrayList на основе коллекции
     *
     * @param target коллекция, откуда будут взяты элементы
     */
    public ArrayList(Collection<? extends T> target) {
        Object[] targetArr = target.toArray();
        size = targetArr.length;
        if (size > 0) {
            array = Arrays.copyOf(targetArr, size, Object[].class);
        } else {
            array = EMPTY_ARRAY;
        }
    }


    /**
     * Конструктор, создающий ArrayList на основе другого ArrayList
     *
     * @param target ArrayList, откуда будут взяты элементы
     */
    public ArrayList(ArrayList<? extends T> target) {
        Object[] targetArr = target.toArray();
        size = targetArr.length;
        if (size > 0) {
            array = targetArr;
        } else {
            array = EMPTY_ARRAY;
        }
    }

    /**
     * Метод для роста внутреннего массива. Вызывает другой метод, куда передает поле-маркер,
     * которое указывает, что внутренний массив нужно расширить стандартным образом
     */
    private void grow() {
        grow(DEFAULT_GROWTH);
    }

    /**
     * Метод, который осуществляет расширение внутреннего массива При передаче в него маркера
     * DEFAULT_GROWTH расширяет массив в 1,5 раза.
     *
     * @param growth указывает на сколько должен расти внутренний массив
     */
    private void grow(int growth) {
        int capacity = array.length;
        if (capacity > 0) {
            int capGrowth = growth == DEFAULT_GROWTH ? ((capacity / 2) + 1) : (growth - capacity);
            int arrLen = capacity + capGrowth;
            if (arrLen > 0 && arrLen <= MAX_CAPACITY) {
                array = Arrays.copyOf(array, arrLen);
            } else if (arrLen < 0) {
                throw new OutOfMemoryError("Required array length is too long");
            } else {
                array = Arrays.copyOf(array, MAX_CAPACITY);
            }
        } else {
            array = new Object[Math.max(DEFAULT_CAPACITY, growth)];
        }
    }

    /**
     * Добавляет элемент в конец ArrayList. Если размер недостаточен,
     * вызывается метод grow() для расширения внутреннего массива.
     * В лучшем случае выполняется за O(1), в худшем за O(n)
     *
     * @param value элемент, который будет добавлен.
     * @return boolean-значение, отображающее успешность произведенной операции.
     */
    public boolean add(T value) {
        if (size == array.length) {
            grow();
        }
        array[size] = value;
        size += 1;
        return true;
    }

    /**
     * Вставляет элемент в ArrayList по индексу. Но при этом индекс должен быть <= size.
     * Выполняется за О(n)
     *
     * @param index индекс, куда нужно вставить элемент
     * @param value элемент, который будет добавлен
     * @throws IndexOutOfBoundsException index < 0 || index > size
     */
    public void add(int index, T value) {
        checkAddBounds(index);
        if (size == array.length) {
            grow();
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size += 1;
    }

    /**
     * Возвращает элемент по индексу за константное время O(1)
     *
     * @param index индекс, по которому мы хотим получить элемент
     * @return элемент по индексу
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkRmBounds(index);
        return (T) array[index];
    }

    /**
     * Проверка индекса по границам ArrayList (для добавления элементов)
     *
     * @param index проверяемый индекс
     * @throws IndexOutOfBoundsException если проверка не пройдена
     */
    private void checkAddBounds(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index" + index + " is out of bound");
        }
    }

    /**
     * Проверка индекса по границам ArrayList (для удаления и изменения элементов в коллекции)
     *
     * @param index проверяемый индекс
     * @throws IndexOutOfBoundsException если проверка не пройдена
     */
    private void checkRmBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index" + index + " is out of bound");
        }
    }

    /**
     * Удаляет элемент из ArrayList по индексу. Выполняется за O(n)
     *
     * @param index индекс по которому нужно удалить элемент
     * @return удаленный элемент
     * @throws IndexOutOfBoundsException если (index < 0 || index >= size)
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkRmBounds(index);
        T remElement = (T) array[index];
        removeLogic(index);
        return remElement;
    }

    /**
     * Логика удаления. Основана на вызове метода System.arraycopy
     *
     * @param index индекс, по которому нужно удалить элемент
     */
    private void removeLogic(int index) {
        int newSize = size - 1;
        if (index < newSize) {
            System.arraycopy(array, index + 1, array, index, newSize);
        }
        array[newSize] = null;
        size -= 1;
    }

    /**
     * Удаление элемента в ArrayList по передаваемому объекту. Выполняется за O(n)
     *
     * @param remObj объект, который необходимо удалить
     * @return успешность проведенного удаления
     */
    public boolean remove(Object remObj) {
        boolean match = false;
        for (int i = 0; i < size; ++i) {
            if (remObj == null) {
                if (array[i] == remObj) {
                    match = true;
                }
            } else {
                if (remObj.equals(array[i])) {
                    match = true;
                }
            }
            if (match) {
                removeLogic(i);
                break;
            }
        }
        return match;
    }

    /**
     * Очистка ArrayList
     */
    public void clear() {
        for (int i = 0; i < size; ++i) {
            array[i] = null;
        }
        size = 0;
    }

    /**
     * Замена элемента по индексу в ArrayList
     *
     * @param index индекс, по которому необходимо заменить элемент
     * @param value элемент, который будет добавлен в ArrayList
     * @return замененный элемент
     * @throws IndexOutOfBoundsException если (index < 0 || index >= size)
     */
    public T set(int index, T value) {
        checkRmBounds(index);
        @SuppressWarnings("unchecked") T prevElem = (T) array[index];
        array[index] = value;
        return prevElem;
    }

    /**
     * Возвращает количество элементов в ArrayList
     *
     * @return количество элементов в ArrayList
     */
    public int size() {
        return size;
    }

    /**
     * Превращает ArrayList в массив
     *
     * @return массив, основанный на элементах ArrayList
     */
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    /**
     * Проверяет пуст ли ArrayList
     *
     * @return true - если пуст, false - если есть элементы
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Обрезает размер выделенной памяти до размеров ArrayList
     */
    public void trimToSize() {
        if (size < array.length) {
            if (size == 0) {
                array = EMPTY_ARRAY;
            } else {
                array = Arrays.copyOf(array, size);
            }
        }
    }

    /**
     * Возвращает индекс первого вхождения элемента в ArrayList или -1
     *
     * @param value искомый элемент
     * @return индекс первого вхождения элемента или -1
     */
    public int indexOf(Object value) {
        for (int i = 0; i < size; ++i) {
            int tempRes = indexOfLogic(value, i);
            if (tempRes != -1) return tempRes;
        }
        return -1;
    }

    /**
     * Возвращает индекс последнего вхождения элемента в ArrayList или -1
     *
     * @param value искомый элемент
     * @return индекс последнего вхождения или -1
     */
    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; --i) {
            int tempRes = indexOfLogic(value, i);
            if (tempRes != -1) return tempRes;
        }
        return -1;
    }

    /**
     * Логика поиска элемента
     *
     * @param value искомый элемент
     * @param i     индекс массива, элемент которого сейчас проверяется
     * @return либо возвращает индекс, либо -1
     */
    private int indexOfLogic(Object value, int i) {
        if (value == null) {
            if (array[i] == value) {
                return i;
            }
        } else {
            if (value.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Добавляет все элементы указанной коллекции в ArrayList
     *
     * @param copyCol коллекция, эоементы которой нужно добавить
     * @return true - если ArrayList изменился, false - если нет
     */
    public boolean addAll(Collection<? extends T> copyCol) {
        boolean result = true;
        Object[] copyArr = copyCol.toArray();
        result = addAllLogic(copyArr, result);
        return result;
    }

    /**
     * Логика добавления нескольких элементов
     *
     * @param copyArr массив откуда будут добавлять
     * @param result  маркер, отображающий успешность или неуспешность добавления
     * @return возвращает result
     */
    private boolean addAllLogic(Object[] copyArr, boolean result) {
        int copyLen = copyArr.length;
        if (copyLen == 0) {
            result = false;
        } else {
            if (copyLen > array.length - size) {
                grow(size + copyLen);
            }
            System.arraycopy(copyArr, 0, array, size, copyLen);
            size += copyLen;
        }
        return result;
    }

    /**
     * Добавляет все элементы указанного ArrayList в текущий ArrayList
     *
     * @param copyCol ArrayList, элементы которого нужно добавить
     * @return true - если произошло изменение коллекции, false - если нет
     */
    public boolean addAll(ArrayList<? extends T> copyCol) {
        boolean result = true;
        Object[] copyArr = copyCol.toArray();
        result = addAllLogic(copyArr, result);
        return result;
    }

    /**
     * Удаление всех элементов переданной коллекции в текущем ArrayList
     *
     * @param elems коллекция, указывающая, какие элементы надо удалить
     * @return false - если ArrayList не изменился, true - если произошло удаление
     */
    public boolean removeAll(Collection<? extends T> elems) {
        boolean result = false;
        Object[] arrElems = elems.toArray();
        if (arrElems.length == 0) {
            return false;
        } else {
            result = removeAllLogic(arrElems, result);
        }
        return result;
    }

    /**
     * Удвление всех элементов переданного ArrayList в текущем ArrayList
     *
     * @param elems ArrayList, элементы которого нужно удалить
     * @return false - если ArrayList не изменился, true - если произошло удаление
     */
    public boolean removeAll(ArrayList<? extends T> elems) {
        boolean result = false;
        Object[] arrElems = elems.toArray();
        if (arrElems.length == 0) {
            return false;
        } else {
            result = removeAllLogic(arrElems, result);
        }
        return result;
    }

    /**
     * Логика удаления нескольких элементов
     *
     * @param arrElems Массив элементов, которые нужно удалить
     * @param result   результат
     * @return возвращает result
     */
    private boolean removeAllLogic(Object[] arrElems, boolean result) {
        for (int i = 0; i < size; ++i) {
            boolean tempRes = false;
            for (Object arrElem : arrElems) {
                if (arrElem == null) {
                    if (arrElem == array[i]) {
                        tempRes = true;
                    }
                } else {
                    if (arrElem.equals(array[i])) {
                        tempRes = true;
                    }
                }
                if (tempRes) {
                    result = true;
                    removeLogic(i);
                    i -= 1;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Проверяет содержится ли элемент в ArrayList
     *
     * @param obj искомый элемент
     * @return true - если элемент есть в коллекции, false - если нет
     */
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    /**
     * Сортировка, основанная на переданном компараторе. Используется алгоритм быстрой сортировки
     *
     * @param comparator компаратор, согласно которому сравниваются объекты
     */
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> comparator) {
        comparatorQuickSort((T[]) array, comparator, 0, size - 1);
    }

    /**
     * Сортировка для ArrayList, элементы которой имплементируют интерфейс Comparable.
     * Используется алгоритм быстрой сортировки
     *
     * @param arr ArrayList, который необходимо отсортировать
     */
    public static void sort(ArrayList<? extends Comparable> arr) {
        comparableQuickSort(arr, 0, arr.size() - 1);
    }

    @Override
    public String toString() {
        return "ArrayList{" +
                "array=" + Arrays.toString(array) +
                '}';
    }

    /**
     * Реализация быстрой сортировки с компаратором
     *
     * @param arr        массив T, который нужно отсортировать
     * @param comparator компаратор, предоставляющий инструмент для сравнения
     * @param low        минимальный элемент, разделяющий массив
     * @param high       максимальный элемент, разделяющий массив
     */
    private void comparatorQuickSort(T[] arr, Comparator<? super T> comparator, int low, int high) {
        if (low < high) {
            int pivot = comparatorQuickSortLogic(arr, comparator, low, high);
            comparatorQuickSort(arr, comparator, low, pivot - 1);
            comparatorQuickSort(arr, comparator, pivot + 1, high);
        }
    }

    /**
     * Логика быстрой сортировки с компаратором. В качестве разделяющего элемента берется последний
     * элемент коллекции. Все элементы, что меньше него, идут влево, все что больше - вправо.
     *
     * @param arr        массив T, который нужно отсортировать
     * @param comparator компаратор, предоставляющий инструмент для сравнения
     * @param low        минимальный элемент, разделяющий массив
     * @param high       максимальный элемент, разделяющий массив
     * @return следующий индекс, по которому нужно разделить массив
     */
    private int comparatorQuickSortLogic(T[] arr, Comparator<? super T> comparator, int low, int high) {
        T pivot = arr[high];
        int sortIndex = low - 1;
        for (int i = low; i < high; ++i) {
            if (comparator.compare(arr[i], pivot) < 0) {
                sortIndex++;
                T temp = arr[sortIndex];
                arr[sortIndex] = arr[i];
                arr[i] = temp;
            }
        }
        T temp = arr[sortIndex + 1];
        arr[sortIndex + 1] = arr[high];
        arr[high] = temp;
        return sortIndex + 1;
    }

    /**
     * Реализация быстрой сортировки с Comparable.
     *
     * @param arr  ArrayList, который нужно отсортировать
     * @param low  минимальный элемент, разделяющий массив
     * @param high максимальный элемент, разделяющий массив
     */
    private static void comparableQuickSort(ArrayList<? extends Comparable> arr,
                                            int low, int high) {
        if (low < high) {
            int pivot = comparableQuickSortLogic(arr, low, high);
            comparableQuickSort(arr, low, pivot - 1);
            comparableQuickSort(arr, pivot + 1, high);
        }
    }

    /**
     * Логика быстрой сортировки с компаратором. В качестве разделяющего элемента берется последний
     * элемент коллекции. Все элементы, что меньше него, идут влево, все что больше - вправо.
     *
     * @param arr  ArrayList, который нужно отсортировать
     * @param low  минимальный элемент, разделяющий массив
     * @param high максимальный элемент, разделяющий массив
     * @return следующий индекс, по которому нужно разделить массив
     */
    @SuppressWarnings("unchecked")
    private static int comparableQuickSortLogic(ArrayList<? extends Comparable> arr,
                                                int low, int high) {
        Comparable pivot = arr.get(high);
        int sortIndex = low - 1;
        for (int i = low; i < high; ++i) {
            if (arr.get(i).compareTo(pivot) < 0) {
                sortIndex++;
                Comparable temp = arr.get(sortIndex);
                arr.array[sortIndex] = arr.array[i];
                arr.array[i] = temp;
            }
        }
        Comparable temp = arr.get(sortIndex + 1);
        arr.array[sortIndex + 1] = arr.array[high];
        arr.array[high] = temp;
        return sortIndex + 1;
    }

    /**
     * Возвращает ListIterator<T> для перебора ArrayList
     *
     * @param index индекс, с которого должен стартовать итератор
     * @return объект типа ListIterator<T>
     */
    public ListIterator<T> listIterator(int index) {
        checkAddBounds(index);
        return new ListIter(index);
    }

    /**
     * Возвращает ListIterator<T> для перебора ArrayList с указателем на 0 элементе
     *
     * @return объект типа ListIterator<T>
     */
    public ListIterator<T> listIterator() {
        return new ListIter(0);
    }

    /**
     * Класс, реализующий методы ListIterator, нужен для перебора ArrayList.
     * Итерация может происходить как вперед, так и назад. Также возможно добавление,
     * удаление и замена элементов коллекции при помощи итератора
     */
    private class ListIter implements ListIterator<T> {
        /**
         * Указатель на элемент
         */
        private int pointer;
        /**
         * Указатель на последний использовавшийся элемент
         */
        private int last = -1;

        /**
         * Конструктор, показывающий, на какой элемент ArrayList ставить указатель
         *
         * @param index номер элемента
         */
        ListIter(int index) {
            pointer = index;
        }

        /**
         * Проверяет, существует ли следующий элемент в коллекции
         *
         * @return true - если есть следующий элемент, false - если его нет
         */
        @Override
        public boolean hasNext() {
            return pointer != size;
        }

        /**
         * Переставляет указатель на следующий элемент и возвращает его
         *
         * @return следующий элемент итерации
         * @throws NoSuchElementException если (pointer >= size)
         */
        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (pointer >= size) {
                throw new NoSuchElementException();
            }
            last = pointer;
            pointer += 1;
            return (T) array[last];
        }

        /**
         * Проверяет, существует ли предыдущий элемент
         *
         * @return true - если есть предыдущий элемент, false - если его нет
         */
        @Override
        public boolean hasPrevious() {
            return pointer != 0;
        }

        /**
         * Переставляет указатель на предыдущий элемент и возвращает его
         *
         * @return предыдущий элемент ArrayList
         * @throws NoSuchElementException если (pointer < 0 || pointer >= size)
         */
        @Override
        @SuppressWarnings("unchecked")
        public T previous() {
            int tempCursor = pointer - 1;
            if (pointer < 0 || pointer >= size) {
                throw new NoSuchElementException();
            }
            pointer = tempCursor;
            last = pointer;
            return (T) array[last];
        }

        /**
         * Возвращает следующий индекс элемента
         *
         * @return следующий индекс элемента
         */
        @Override
        public int nextIndex() {
            return pointer;
        }

        /**
         * Возвращает предыдущий индекс элемента
         *
         * @return предыдущий индекс элемента
         */
        @Override
        public int previousIndex() {
            return pointer - 1;
        }

        /**
         * Удаляет элемент, на котором сейчас находится указатель, из ArrayList
         *
         * @throws IllegalStateException если last < 0 (Нечего удалять)
         */
        @Override
        public void remove() {
            if (last < 0) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(pointer);
            pointer = last;
            last = -1;
        }

        /**
         * Заменяет элемент, на котором сейчас стоит указатель
         *
         * @param t Элемент, на который нужно заменить
         * @throws IllegalStateException если last < 0 (Нечего заменять)
         */
        @Override
        public void set(T t) {
            if (last < 0) {
                throw new IllegalStateException();
            }
            ArrayList.this.set(last, t);
        }

        /**
         * Метод, вставляющий элемент в месте, где находится указатель
         *
         * @param t элемент для вставки
         */
        @Override
        public void add(T t) {
            ArrayList.this.add(pointer, t);
            pointer += 1;
            last = -1;
        }


        /**
         * Все оставшиеся элементы итерации выполняют действие, передающееся в качестве аргумента
         *
         * @param action Действие, которое нужно совершить всем омтавшимся элементам итерации
         */
        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super T> action) {
            if (pointer < ArrayList.this.size) {
                for (int i = pointer; i < size; ++i) {
                    action.accept((T) array[i]);
                    pointer = i;
                    last = i - 1;
                }
            }
        }
    }
}
