# QuickFind
Design decisions:
	1. Used TreeMap in place of HashMap in order to get the best runtime while removing a node which has link count of 1. So the next replaced value will be found quicker since the keys are ordered in a Treemap for the average case.
	2. QuickFind runs in O(1). Since this is the most frequent operation, we are guaranteeing the constant run time.
	3. Union and delete are expensive operations, but since it is mentioned that it can be a little expensive, we are using a O(n) algorithm.
	4. Added utility functions to print the graph datastructure. Also I have helper methods that iterates through the HashMap to make it more modular.
	5. 


How to run this program:
	1. Name the input file "input.txt" and place it in the folder containing the source program.
	2. Compile the program using javac QuickFind.java
	3. Run the file using java QuickFind