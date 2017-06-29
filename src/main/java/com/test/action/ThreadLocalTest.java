package com.test.action;

import java.util.Random;
import java.util.logging.StreamHandler;

/**
 * Created by 58 on 2016/1/28.
 */
public class ThreadLocalTest implements Runnable {

    private ThreadLocal<Student> threadLocal = new ThreadLocal<Student>();

    @Override
    public void run() {
        String threadCurrentName = Thread.currentThread().getName();
        System.out.println("线程名字："+threadCurrentName);
        Random random = new Random();
        int age = random.nextInt();
        Student student = (Student) threadLocal.get();
        if(student == null){
            student = new Student();
            threadLocal.set(student);
        }
        student.setAge(age);
        System.out.println("线程 " + threadCurrentName + " first 设置age " + student.getAge());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程 "+threadCurrentName+" second 设置age "+student.getAge());
    }

    public static void main(String[] args) {
        ThreadLocalTest threadLocalTest = new ThreadLocalTest();
        Thread t1 = new Thread(threadLocalTest,"Thread A");
        Thread t2 = new Thread(threadLocalTest, "Thread B");
        t1.start();
        t2.start();
    }
}

class Student{

    int age;

    public Student() {
    }

    public Student(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
