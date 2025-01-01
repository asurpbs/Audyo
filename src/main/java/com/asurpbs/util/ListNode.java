/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asurpbs.util;

/**
 *
 * @author Mithila Prabashwara
 */

public class ListNode {
    Object entity;
    public ListNode next, previous;
    
    public ListNode(Object entity) {
        this.entity = entity;
        this.previous = this.next = null;
    }
    
    /**
     * Get an object of the list
     * @return the object
     */
    public Object getObject() {
        return this.entity;
    }
}