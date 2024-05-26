package aston.bootcamp;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class ArrayListTests {
    @Rule
    @SuppressWarnings("deprecation")
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void DefaultConstructor_DefaultInit_CorrectInit() {
        ArrayList<Integer> testArr = new ArrayList<>();
        java.util.ArrayList<Integer> origArr = new java.util.ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        origArr.add(1);
        origArr.add(2);
        origArr.add(3);
        origArr.add(4);
        Assert.assertEquals(origArr.get(0), testArr.get(0));
        Assert.assertEquals(origArr.get(1), testArr.get(1));
        Assert.assertEquals(origArr.get(2), testArr.get(2));
        Assert.assertEquals(origArr.get(3), testArr.get(3));
    }

    @Test
    public void InitConstructor_NegativeInitialCapacity_IllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        ArrayList<Integer> testArr = new ArrayList<>(-5);
    }

    @Test
    public void InitConstructor_ZeroInitialCapacity_CorrectInit() {
        ArrayList<Integer> testArr = new ArrayList<>(0);
        testArr.add(1);
        testArr.add(2);
        java.util.ArrayList<Integer> origArr = new java.util.ArrayList<>(0);
        origArr.add(1);
        origArr.add(2);
        Assert.assertEquals(origArr.get(0), testArr.get(0));
        Assert.assertEquals(origArr.get(1), testArr.get(1));
    }

    @Test
    public void InitConstructor_NormalInitCapacity_CorrectInit() {
        ArrayList<Integer> testArr = new ArrayList<>(5);
        java.util.ArrayList<Integer> origArr = new java.util.ArrayList<>(5);
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        origArr.add(1);
        origArr.add(2);
        origArr.add(3);
        origArr.add(4);
        origArr.add(5);
        Assert.assertEquals(origArr.get(0), testArr.get(0));
        Assert.assertEquals(origArr.get(1), testArr.get(1));
        Assert.assertEquals(origArr.get(2), testArr.get(2));
        Assert.assertEquals(origArr.get(3), testArr.get(3));
        Assert.assertEquals(origArr.get(4), testArr.get(4));
    }

    @Test
    public void InitConstructor_MaxValueInitialCapacity_OutOfMemoryException() {
        thrown.expect(OutOfMemoryError.class);
        ArrayList<Integer> testArr = new ArrayList<>(Integer.MAX_VALUE);
    }

    @Test
    public void CollectionConstructor_EmptyCollection_EmptyArrayList() {
        Set<Integer> testSet = new HashSet<>();
        ArrayList<Integer> testArr = new ArrayList<>(testSet);
        Assert.assertTrue(testArr.isEmpty());
    }

    @Test
    public void CollectionConstructor_NonEmptyCollection_NonEmptyArrayList() {
        Set<Integer> testSet = Set.of(1, 2, 3, 4, 5, 6, 7);
        ArrayList<Integer> testArr = new ArrayList<>(testSet);
        Assert.assertTrue(testArr.contains(1));
        Assert.assertTrue(testArr.contains(2));
        Assert.assertTrue(testArr.contains(3));
        Assert.assertTrue(testArr.contains(4));
        Assert.assertTrue(testArr.contains(5));
        Assert.assertTrue(testArr.contains(6));
        Assert.assertTrue(testArr.contains(7));
    }

    @Test
    public void CopyConstructor_EmptyArrayList_EmptyArrayList() {
        ArrayList<Integer> copyArr = new ArrayList<>();
        ArrayList<Integer> testArr = new ArrayList<>(copyArr);
        Assert.assertTrue(testArr.isEmpty());
    }

    @Test
    public void CopyConstructor_NonEmptyArrayList_NonEmptyArrayList() {
        ArrayList<Integer> copyArr = new ArrayList<>();
        copyArr.add(1);
        copyArr.add(2);
        copyArr.add(3);
        copyArr.add(4);
        ArrayList<Integer> testArr = new ArrayList<>(copyArr);
        for (int i = 0; i < testArr.size(); ++i) {
            Assert.assertEquals(copyArr.get(i), testArr.get(i));
        }
    }

    @Test
    public void Add_1000000Elems_NormalAdd() {
        ArrayList<String> testArr = new ArrayList<>();
        for (int i = 0; i < 1000000; ++i) {
            testArr.add(UUID.randomUUID().toString());
        }
        Assert.assertEquals(1000000, testArr.size());
    }

    @Test
    public void AddIndex_AddToNonExistingIndex_IndexOutOfBoundException() {
        thrown.expect(IndexOutOfBoundsException.class);
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(10);
        testArr.add(11);
        testArr.add(12);
        testArr.add(4, 13);
    }

    @Test
    public void AddIndex_AddToExistingIndex_NormalAdd() {
        ArrayList<Integer> testArr = new ArrayList<>();
        java.util.ArrayList<Integer> origArr = new java.util.ArrayList<>();
        origArr.add(0, 1);
        origArr.add(0, 2);
        origArr.add(0, 3);
        testArr.add(0, 1);
        testArr.add(0, 2);
        testArr.add(0, 3);
        for (int i = 0; i < origArr.size(); ++i) {
            Assert.assertEquals(origArr.get(i), testArr.get(i));
        }
    }

    @Test
    public void Remove_CorrectIndex_CorrectDeletion() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(101);
        for (int i = 1; i <= 100000; ++i) {
            testArr.add(ThreadLocalRandom.current().nextInt());
        }
        Assert.assertEquals(100001, testArr.size());
        for (int i = 0; i < 100000; ++i) {
            testArr.remove(1);
        }
        int lastElem = testArr.get(0);
        Assert.assertEquals(1, testArr.size());
        int testLastElem = testArr.remove(0);
        Assert.assertEquals(lastElem, testLastElem);
    }

    @Test
    public void RemoveIncorrectIndex_IndexOutOfBoundException() {
        thrown.expect(IndexOutOfBoundsException.class);
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.remove(-1);
    }

    @Test
    public void RemoveObject_RemoveNull_CorrectRemove() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(null);
        Assert.assertEquals(3, testArr.size());
        testArr.remove(null);
        Assert.assertEquals(2, testArr.size());
    }

    @Test
    public void RemoveObject_ExistingObject_CorrectRemove() {
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("1");
        testArr.add("2");
        testArr.add("3");
        testArr.add("4");
        Assert.assertEquals(4, testArr.size());
        testArr.remove("4");
        Assert.assertEquals(3, testArr.size());
    }

    @Test
    public void RemoveObject_RemoveNonExistingObject_ReturnFalse() {
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("1");
        testArr.add("2");
        testArr.add("3");
        testArr.add("4");
        Assert.assertEquals(4, testArr.size());
        Assert.assertFalse(testArr.remove("5"));
    }

    @Test
    public void Clear_DeleteList5Elems_SizeIsZero() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        Assert.assertEquals(5, testArr.size());
        testArr.clear();
        Assert.assertEquals(0, testArr.size());
    }

    @Test
    public void Set_IncorrectIndex_IndexOutOfBoundException() {
        thrown.expect(IndexOutOfBoundsException.class);
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        testArr.set(-1, 6);
    }

    @Test
    public void Set_CorrectIndex_CorrectReplace() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        int lastElem = testArr.get(4);
        int testLastElem = testArr.set(4, 10);
        Integer newLastElem = 10;
        Assert.assertEquals(lastElem, testLastElem);
        Assert.assertEquals(newLastElem, testArr.get(4));
    }

    @Test
    public void TrimToSize_EmptyList_NoError() {
        ArrayList<Integer> testArr = new ArrayList<>(10000);
        testArr.trimToSize();
        Assert.assertEquals(0, testArr.size());
    }

    @Test
    public void TrimToSize_NonEmptyList_NoError() {
        ArrayList<Integer> testArr = new ArrayList<>(10000);
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.trimToSize();
        Assert.assertEquals(4, testArr.size());
    }

    @Test
    public void IndexOf_NullObject_IndexIs2() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(null);
        Assert.assertEquals(2, testArr.indexOf(null));
    }

    @Test
    public void IndexOf_NormalObject_IndexIs3() {
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("1");
        testArr.add("2");
        testArr.add("3");
        testArr.add("4");
        Assert.assertEquals(3, testArr.indexOf("4"));
    }

    @Test
    public void IndexOf_NonExistingObject_IndexIsMin1() {
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("1");
        testArr.add("2");
        testArr.add("3");
        testArr.add("4");
        Assert.assertEquals(-1, testArr.indexOf("gegbfvew"));
    }

    @Test
    public void LastIndexOf_ExistingObject_IndexIs5() {
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("1");
        testArr.add("2");
        testArr.add("3");
        testArr.add("4");
        testArr.add("4");
        testArr.add("4");
        Assert.assertEquals(5, testArr.lastIndexOf("4"));
    }

    @Test
    public void AddAll_EmptyCollection_ReturnFalse() {
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("1");
        testArr.add("2");
        testArr.add("3");
        java.util.ArrayList<String> emptyArr = new java.util.ArrayList<>();
        Assert.assertFalse(testArr.addAll(emptyArr));
    }

    @Test
    public void AddAll_NonEmptyCollection_ReturnTrue() {
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("1");
        testArr.add("2");
        testArr.add("3");
        java.util.ArrayList<String> copyArr = new java.util.ArrayList<>();
        copyArr.add("4");
        copyArr.add("5");
        copyArr.add("6");
        Assert.assertTrue(testArr.addAll(copyArr));
        Assert.assertEquals(copyArr.get(0), testArr.get(3));
        Assert.assertEquals(copyArr.get(1), testArr.get(4));
        Assert.assertEquals(copyArr.get(2), testArr.get(5));
    }

    @Test
    public void MyAddAll_NonEmptyCollection_ReturnTrue() {
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("1");
        testArr.add("2");
        testArr.add("3");
        ArrayList<String> copyArr = new ArrayList<>();
        copyArr.add("4");
        copyArr.add("5");
        copyArr.add("6");
        Assert.assertTrue(testArr.addAll(copyArr));
        Assert.assertEquals(copyArr.get(0), testArr.get(3));
        Assert.assertEquals(copyArr.get(1), testArr.get(4));
        Assert.assertEquals(copyArr.get(2), testArr.get(5));
    }

    @Test
    public void RemoveAll_EmptyCollection_ReturnFalse() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        java.util.ArrayList<Integer> emptyArr = new java.util.ArrayList<>();
        Assert.assertFalse(testArr.removeAll(emptyArr));
    }

    @Test
    public void RemoveAll_NonEmptyCollection_ReturnTrue() {
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("1");
        testArr.add("2");
        testArr.add("3");
        java.util.ArrayList<String> copyArr = new java.util.ArrayList<>();
        copyArr.add("1");
        copyArr.add("2");
        Assert.assertTrue(testArr.removeAll(copyArr));
        Assert.assertEquals("3", testArr.get(0));
    }

    @Test
    public void MyRemoveAll_NonEmptyCollection_ReturnTrue() {
        ArrayList<String> testArr = new ArrayList<>();
        testArr.add("1");
        testArr.add("2");
        testArr.add("3");
        ArrayList<String> copyArr = new ArrayList<>();
        copyArr.add("1");
        copyArr.add("2");
        Assert.assertTrue(testArr.removeAll(copyArr));
        Assert.assertEquals("3", testArr.get(0));
    }

    @Test
    public void comparatorSort_7Persons_CorrectSort() {
        PersonTest p1 = new PersonTest(19, "Nik");
        PersonTest p2 = new PersonTest(21, "Alice");
        PersonTest p3 = new PersonTest(8, "Alex");
        PersonTest p4 = new PersonTest(54, "Pasha");
        PersonTest p5 = new PersonTest(37, "Marina");
        PersonTest p6 = new PersonTest(2, "Max");
        PersonTest p7 = new PersonTest(60, "Igor");
        ArrayList<PersonTest> testArr = new ArrayList<>();
        testArr.add(p1);
        testArr.add(p2);
        testArr.add(p3);
        testArr.add(p4);
        testArr.add(p5);
        testArr.add(p6);
        testArr.add(p7);
        testArr.sort((x, y) -> y.getAge() - x.getAge());
        Assert.assertEquals(p7, testArr.get(0));
        Assert.assertEquals(p4, testArr.get(1));
        Assert.assertEquals(p5, testArr.get(2));
        Assert.assertEquals(p2, testArr.get(3));
        Assert.assertEquals(p1, testArr.get(4));
        Assert.assertEquals(p3, testArr.get(5));
        Assert.assertEquals(p6, testArr.get(6));
    }

    @Test
    public void ComparableSort_7Persons_CorrectSort() {
        PersonTest p1 = new PersonTest(19, "Nik");
        PersonTest p2 = new PersonTest(21, "Alice");
        PersonTest p3 = new PersonTest(8, "Alex");
        PersonTest p4 = new PersonTest(54, "Pasha");
        PersonTest p5 = new PersonTest(37, "Marina");
        PersonTest p6 = new PersonTest(2, "Max");
        PersonTest p7 = new PersonTest(60, "Igor");
        ArrayList<PersonTest> testArr = new ArrayList<>();
        testArr.add(p1);
        testArr.add(p2);
        testArr.add(p3);
        testArr.add(p4);
        testArr.add(p5);
        testArr.add(p6);
        testArr.add(p7);
        ArrayList.sort(testArr);
        Assert.assertEquals(p7, testArr.get(6));
        Assert.assertEquals(p4, testArr.get(5));
        Assert.assertEquals(p5, testArr.get(4));
        Assert.assertEquals(p2, testArr.get(3));
        Assert.assertEquals(p1, testArr.get(2));
        Assert.assertEquals(p3, testArr.get(1));
        Assert.assertEquals(p6, testArr.get(0));
    }

    @Test
    public void ListIterator_IterateCollectionFromStartForward_NoError() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        testArr.add(6);
        testArr.add(7);
        testArr.add(8);
        testArr.add(9);
        testArr.add(10);
        ListIterator<Integer> testIter = testArr.listIterator();
        Integer testVal = 1;
        while (testIter.hasNext()) {
            Assert.assertEquals(testVal, testIter.next());
            testVal += 1;
        }
    }

    @Test
    public void ListIterator_IterateCollectionFrom4IndexForward_NoError() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        testArr.add(6);
        testArr.add(7);
        testArr.add(8);
        testArr.add(9);
        testArr.add(10);
        ListIterator<Integer> testIter = testArr.listIterator(4);
        Integer testVal = 5;
        while (testIter.hasNext()) {
            Assert.assertEquals(testVal, testIter.next());
            testVal += 1;
        }
    }

    @Test
    public void ListIterator_IterateCollectionFromStartBack_NoError() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        testArr.add(6);
        testArr.add(7);
        testArr.add(8);
        testArr.add(9);
        testArr.add(10);
        ListIterator<Integer> testIter = testArr.listIterator(9);
        Integer testVal = 9;
        while (testIter.hasPrevious()) {
            Assert.assertEquals(testVal, testIter.previous());
            testVal -= 1;
        }
    }

    @Test
    public void ListIterator_IterateCollectionFromEndBack_NoSuchElementException() {
        thrown.expect(NoSuchElementException.class);
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        testArr.add(6);
        testArr.add(7);
        testArr.add(8);
        testArr.add(9);
        testArr.add(10);
        ListIterator<Integer> testIter = testArr.listIterator(10);
        Integer testVal = 9;
        while (testIter.hasPrevious()) {
            Assert.assertEquals(testVal, testIter.previous());
            testVal -= 1;
        }
    }

    @Test
    public void ListIterator_IterateCollectionFromEndForward_NoSuchElementException() {
        thrown.expect(NoSuchElementException.class);
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        testArr.add(6);
        testArr.add(7);
        testArr.add(8);
        testArr.add(9);
        testArr.add(10);
        ListIterator<Integer> testIter = testArr.listIterator(10);
        Integer i = testIter.next();
    }

    @Test
    public void ListIterator_IncorrectIterator_IndexOutOfBoundException() {
        thrown.expect(IndexOutOfBoundsException.class);
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        testArr.add(6);
        testArr.add(7);
        testArr.add(8);
        testArr.add(9);
        testArr.add(10);
        ListIterator<Integer> testIter = testArr.listIterator(11);
    }

    @Test
    public void ListIterator_NextIndexAndPrevIndex_CorrectIndexes() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        ListIterator<Integer> testIter = testArr.listIterator();
        testIter.next();
        testIter.next();
        testIter.next();
        Assert.assertEquals(3, testIter.nextIndex());
        Assert.assertEquals(2, testIter.previousIndex());
    }

    @Test
    public void ListIterator_Remove_IncorrectRemove_IllegalStateException() {
        thrown.expect(IllegalStateException.class);
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        ListIterator<Integer> testIter = testArr.listIterator();
        testIter.remove();
    }

    @Test
    public void ListIterator_Remove_CorrectRemove() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        ListIterator<Integer> testIter = testArr.listIterator();
        testIter.next();
        testIter.remove();
        Assert.assertEquals(4, testArr.size());
    }

    @Test
    public void ListIterator_Set_IncorrectSet_IllegalStateException() {
        thrown.expect(IllegalStateException.class);
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        ListIterator<Integer> testIter = testArr.listIterator();
        testIter.set(3);
    }

    @Test
    public void ListIterator_Set_CorrectSet() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        ListIterator<Integer> testIter = testArr.listIterator();
        testIter.next();
        testIter.set(10);
        Integer newVal = 10;
        Assert.assertEquals(newVal, testArr.get(0));
    }

    @Test
    public void ListIterator_Add_CorrectAdd() {
        ArrayList<Integer> testArr = new ArrayList<>();
        testArr.add(1);
        testArr.add(2);
        testArr.add(3);
        testArr.add(4);
        testArr.add(5);
        ListIterator<Integer> testIter = testArr.listIterator();
        testIter.next();
        testIter.add(10);
        Integer newVal = 10;
        Assert.assertEquals(newVal, testArr.get(1));
    }

    @Test
    public void ListIterator_ForEachRemaining_ZeroAllAges() {
        PersonTest p1 = new PersonTest(19, "Nik");
        PersonTest p2 = new PersonTest(21, "Alice");
        PersonTest p3 = new PersonTest(8, "Alex");
        PersonTest p4 = new PersonTest(54, "Pasha");
        PersonTest p5 = new PersonTest(37, "Marina");
        PersonTest p6 = new PersonTest(2, "Max");
        PersonTest p7 = new PersonTest(60, "Igor");
        ArrayList<PersonTest> testArr = new ArrayList<>();
        testArr.add(p1);
        testArr.add(p2);
        testArr.add(p3);
        testArr.add(p4);
        testArr.add(p5);
        testArr.add(p6);
        testArr.add(p7);
        ListIterator<PersonTest> testIter = testArr.listIterator();
        testIter.forEachRemaining(x -> x.setAge(0));
        for (int i = 0; i < testArr.size(); ++i) {
            Assert.assertEquals(0, testArr.get(i).getAge());
        }
    }
}
