package com.test.action.concurrent.pool;

/**
 * Created by pangming on 2017/5/23.
 */
public class BlockQueueListTest {

    public static void main(String[] args) {
        BlockQueueList<Integer> blockQueueList = new BlockQueueList<Integer>();
        blockQueueList.add(1);
        blockQueueList.add(2);
        blockQueueList.add(3);
        System.out.println(blockQueueList.take());
        System.out.println(blockQueueList.take());
        System.out.println(blockQueueList.take());
    }
}
