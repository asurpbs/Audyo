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
    
    /**
     * Use to get the length of the doubly linked list
     * @return the all elements in the doubly linked list
     */
    public int getNoOfElements() {
        return this.noOfElements;
    }
    
    /**
     * Check is the doubly linked list empty or not?
     * @return True when the list is empty
     */
    public boolean isEmpty() {
        return this.noOfElements == 0;
    }
    
    /**
     * insert an elements to head
     * @param entity - Object
     */
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
    
    /**
     * insert elements to tail
     * @param entity - object
     */
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
    
    /**
     * Use to access the head element
     * 
     * @return the head object
     * @throws Exception when the list is empty
     */
    public Object getfirstElement() throws Exception {
        if (!this.isEmpty()) return this.head.getObject();
        else throw new Exception("List is empty.");
    }
    
    /**
     * Use to access the tail element
     * 
     * @return the tail object
     * @throws Exception when the list is empty
     */
    public Object getlastElement() throws Exception {
        if (!this.isEmpty()) return this.tail.getObject();
        else throw new Exception("List is empty.");
    }
    
    /**
     * Delete the head element
     * @throws Exception when the list is empty 
     */
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
    
    /**
     * Delete the tail element
     * @throws Exception when the list is empty 
     */
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
    
    /**
     * Use to delete selected element from the list
     * @param entity -object
     * @throws Exception when the list is empty
     */
    public void delete(Object entity) throws Exception {
        if (this.isEmpty()) throw new Exception("List is empty.");
        ListNode temp = this.head;
        while (temp != null && !temp.getObject().equals(entity)) {
            temp = temp.next;
        }
        if (temp == null) throw new Exception("Item was not found");
        if (this.tail == this.head) {
            this.tail = this.head = null;
        } else if (temp == this.head) {
            this.head = this.head.next;
            this.head.previous = null;
        } else if (temp == this.tail) {
            this.tail = this.tail.previous;
            this.tail.next = null;
        } else {
            temp.previous.next = temp.next;
            temp.next.previous = temp.previous;
        }
        this.noOfElements--;
    }
    
    /**
     * Delete all objects
     * @throws Exception when the list is empty
     */
    public void deleteAll() throws Exception {
        while (!this.isEmpty()) {
            this.deleteFromFront();
        }
        this.noOfElements = 0;
    }
    
    /**
     * Get the all objects as an array
     * 
     * @return the all objects in the linked list as an objects array
     */
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
    
    /**
     * Check, the object does exist or not?
     * @param entity - The object 
     * @return True when the object exists in the list
     */
    public boolean isExist(Object entity) {
        if (this.isEmpty()) return false;
        ListNode temp = this.head;
        while (temp != null) {
            if (temp.getObject().equals(entity)) return true;
            temp = temp.next;
        }
        return false;
    }
}
