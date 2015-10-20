package com.crossbowffs.javautils.huffman;

import com.crossbowffs.javautils.collections.BitStack;
import com.crossbowffs.javautils.collections.FrequencyMap;
import com.crossbowffs.javautils.collections.BinaryNode;

import java.util.*;

public class HuffmanEncoder {
    private static final boolean LEFT = false; // 0 bit means on the left subtree
    private static final boolean RIGHT = true; // 1 bit means on the right subtree

    public static Variabyte compress(byte[] data, BinaryNode<ByteWeight> prefixTree) {
        HashMap<Byte, Variabyte> prefixTable = createPrefixTable(prefixTree);
        ArrayList<Variabyte> variabytes = new ArrayList<Variabyte>();

        // Lookup each byte in the prefix table,
        // then append it to the end of the compressed data
        for (byte b : data) {
            variabytes.add(prefixTable.get(b));
        }

        // Merge the individual variabytes into one continuous array
        return Variabyte.merge(variabytes);
    }

    public static byte[] decompress(Variabyte data, BinaryNode<ByteWeight> prefixTree) {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        BinaryNode<ByteWeight> currNode = prefixTree;

        // Traverse the compressed data in bits
        // We use a normal for loop here to avoid boxing
        int bitLength = data.bitLength();
        for (int i = 0; i < bitLength; ++i) {
            boolean b = data.getBit(i);
            //noinspection PointlessBooleanExpression
            if (b == LEFT) currNode = currNode.left();
            //noinspection PointlessBooleanExpression
            if (b == RIGHT) currNode = currNode.right();

            // If this is a leaf node, we have decompressed one byte
            if (currNode.value().isLeaf()) {
                bytes.add(currNode.value().value());
                currNode = prefixTree;
            }
        }

        // Store decompressed bytes into an array
        byte[] byteArray = new byte[bytes.size()];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = bytes.get(i);
        }
        return byteArray;
    }

    private static BinaryNode<ByteWeight> createPrefixTree(byte[] data) {
        // Priority queue to allow fast removal of the smallest byte weight
        PriorityQueue<BinaryNode<ByteWeight>> nodeQueue = new PriorityQueue<BinaryNode<ByteWeight>>(
            new Comparator<BinaryNode<ByteWeight>>() {
                @Override
                public int compare(BinaryNode<ByteWeight> o1, BinaryNode<ByteWeight> o2) {
                    return o1.value().compareTo(o2.value());
                }
            });

        // Iterate through array and build a map of frequently used bytes
        FrequencyMap<Byte> frequencyMap = new FrequencyMap<Byte>();
        for (byte b : data) {
            frequencyMap.increment(b);
        }

        // Build queue by creating a node for each base weight
        for (Map.Entry<Byte, Integer> entry : frequencyMap.items()) {
            double probability = entry.getValue().doubleValue() / data.length;
            ByteWeight weight = new ByteWeight(entry.getKey(), probability);
            nodeQueue.offer(new BinaryNode<ByteWeight>(weight));
        }

        // Insert random values to ensure the root node is not a leaf
        while (nodeQueue.size() <= 1) {
            nodeQueue.offer(new BinaryNode<ByteWeight>(new ByteWeight((byte)0, (double)0)));
        }

        // Algorithm:
        // Remove the two nodes with the smallest probability from the queue
        // Create a new parent node with probability equal to the sum of the child node probabilities
        // Add the new parent node to the queue
        // Repeat until there is only one node in the queue
        while (nodeQueue.size() > 1) {
            BinaryNode<ByteWeight> smallNode = nodeQueue.poll();
            BinaryNode<ByteWeight> largeNode = nodeQueue.poll();
            ByteWeight totalWeight = new ByteWeight(smallNode.value(), largeNode.value());
            BinaryNode<ByteWeight> parentNode = new BinaryNode<ByteWeight>(totalWeight);
            parentNode.setLeft(smallNode);
            parentNode.setRight(largeNode);
            nodeQueue.offer(parentNode);
        }

        return nodeQueue.poll();
    }

    private static HashMap<Byte, Variabyte> createPrefixTable(BinaryNode<ByteWeight> prefixTree) {
        HashMap<Byte, Variabyte> prefixTable = new HashMap<Byte, Variabyte>();
        fillPrefixTable(prefixTable, prefixTree, new BitStack());
        return prefixTable;
    }

    private static void fillPrefixTable(HashMap<Byte, Variabyte> prefixTable,
                                        BinaryNode<ByteWeight> prefixTree,
                                        BitStack pathStack) {
        if (prefixTree.value().isLeaf()) {
            Byte fullByte = prefixTree.value().value();
            Variabyte miniByte = new Variabyte(pathStack.toByteArray(), pathStack.bitSize());
            prefixTable.put(fullByte, miniByte);
            return;
        }

        BinaryNode<ByteWeight> left = prefixTree.left();
        BinaryNode<ByteWeight> right = prefixTree.right();

        if (left != null) {
            pathStack.push(LEFT);
            fillPrefixTable(prefixTable, left, pathStack);
            pathStack.pop();
        }

        if (right != null) {
            pathStack.push(RIGHT);
            fillPrefixTable(prefixTable, right, pathStack);
            pathStack.pop();
        }
    }

    public static void main(String[] args) {
        byte[] data = "The square root of 546 is 23.36664289109!".getBytes();
        BinaryNode<ByteWeight> prefixTree = createPrefixTree(data);
        Variabyte encodedStr = compress(data, prefixTree);
        String decodedStr = new String(decompress(encodedStr, prefixTree));
        System.out.println(decodedStr);
    }
}
