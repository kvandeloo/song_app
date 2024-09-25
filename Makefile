runApp: SongAPP.class SongInterface.class IterableRedBlackTree.class IterableSortedCollection.class RedBlackTree.class BinarySearchTree.class SortedCollectionInterface.class
	java SongAPP	
runTest: IterableRedBlackTreeTest.class IterableRedBlackTree.class IterableSortedCollection.java RedBlackTree.class BinarySearchTree.class SortedCollectionInterface.class
	java -jar junit5.jar --class-path . --select-class IterableRedBlackTreeTest
SongAPP.class: SongAPP.java IterableSortedCollection.class IterableRedBlackTree.class SongInterface.class
	javac SongAPP.java
SongInterface.class: SongInterface.java
	javac SongInterface.java
IterableRedBlackTree.class: IterableRedBlackTree.java RedBlackTree.class IterableSortedCollection.class
	javac IterableRedBlackTree.java
IterableSortedCollection.class: IterableSortedCollection.java SortedCollectionInterface.class
	javac IterableSortedCollection.java
RedBlackTree.class: RedBlackTree.java BinarySearchTree.class SortedCollectionInterface.class
	javac RedBlackTree.java
BinarySearchTree.class: BinarySearchTree.java SortedCollectionInterface.class
	javac BinarySearchTree.java
SortedCollectionInterface.class: SortedCollectionInterface.java
	javac SortedCollectionInterface.java
IterableRedBlackTreeTest.class: IterableRedBlackTree.java
	javac -cp ./:junit5.jar IterableRedBlackTreeTest.java
