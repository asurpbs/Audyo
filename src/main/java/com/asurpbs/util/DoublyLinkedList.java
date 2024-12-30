/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asurpbs.util;

/**
 *
 * @author Mithila Prabashwara
 */

public class DoublyLinkedList {
    private ListNode head, tail;
    private int noOfElements;
    
    public DoublyLinkedList() {
        this.head = this.tail = null;
        this.noOfElements = 0;
    }
    
    // return the number of elements in the dublylinked list
    public int getNoOfElements() {
        return this.noOfElements;
    }
    
    // check the list, is empty or not
    public boolean isEmpty() {
        return this.noOfElements == 0;
    }
    
    //insert elements to head
    public void insertToHead(Object entity) {
        ListNode node = new ListNode(entity);
        if (this.isEmpty()) this.head = this.tail = node; 
        else {
            node.next = this.head;
            this.head.previous = node;
            this.head = node;
        }
        this.noOfElements ++;
    }
    
    //insert elements to tail
    public void insertToTail(Object entity) {
        ListNode node = new ListNode(entity);
        if (this.isEmpty()) this.head = this.tail = node; 
        else {
            this.tail.next = node;
            node.previous = this.tail;
            this.tail = node;
        }
        this.noOfElements ++;
    }
    
    //access head
    public Object getfirstElement() throws Exception {
        if (!this.isEmpty()) return this.head.getObject();
        else throw new Exception("List is empty.");
    }
    
    //access tail
    public Object getlastElement() throws Exception {
        if (!this.isEmpty()) return this.tail.getObject();
        else throw new Exception("List is empty.");
    }
    
    //delete from front
    public void deleteFromFront() throws Exception {
        if (this.isEmpty()) throw new Exception("List is empty.");
        // (-) Object entity = this.head.getObject();
        if (this.head == this.tail) this.head = this.tail = null; 
        else {
            this.head = this.head.next;
            this.head.previous = null;
        }
        this.noOfElements-- ;
    }
    
    //delete from last
    public void deleteFromLast() throws Exception {
        if (this.isEmpty()) throw new Exception("List is empty.");
        // (-) Object entity = this.tail.getObject();
        if (this.head == this.tail) this.head = this.tail = null; 
        else {
            this.tail = this.tail.previous;
            this.tail.next = null;
        }
        this.noOfElements-- ;
    }
    
    //delete a selected element
    public void delete(Object entity) throws Exception {
        if (this.isEmpty()) throw new Exception("List is empty.");
        ListNode temp = this.head;
        while (temp.getObject().equals(entity)) {
            if (temp.next == null) throw new Exception("Item was not found");
            temp = temp.next;
        } if (this.tail == this.head) this.tail = this.head = null;
        else if (temp == this.head) {
            this.head = this.head.next;
            this.head.previous = null;
        } else if (temp == this.tail) {
            this.tail = this.tail.previous;
            this.tail.next = null;
        } else {
            temp.previous.next = temp.next;
            temp.next.previous = temp.previous;
            
        }
        this.noOfElements-- ;
    }
    
    //delete all objects
    public void deleteAll() throws Exception {
        while (!this.isEmpty()) {
            this.deleteFromFront();
        }
        this.noOfElements = 0;
    }
    
    //return object as list
    public Object[] getObjectArray() {
        if (this.isEmpty()) {
            return new Object[0];
        }
        Object[] arrObjects = new Object[this.noOfElements];
        ListNode temp = this.head;
        for (int i = 0; i < this.noOfElements; i++) {
            arrObjects[i] = temp.getObject();
            temp = temp.next;
        }
        return arrObjects;
    }
}
