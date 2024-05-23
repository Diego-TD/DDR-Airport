package com.ddr.ui.home;

public class CircularLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private Node<T> current; // Nodo actual

    public CircularLinkedList() {
        head = null;
        tail = null;
        size = 0;
        current = null;
    }

    // Método para agregar un elemento a la lista
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
            head.next = tail;
            tail.prev = head;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = head;
            head.prev = tail;
        }
        size++;
    }

    // Método para obtener el nodo actual
    public Node<T> getCurrentNode() {
        return current;
    }

    // Método para mover al siguiente nodo en la lista
    public void next() {
        if (current == null) {
            current = head;
        } else {
            current = current.next;
        }
    }

    // Método para obtener el dato del nodo actual
    public T getCurrentData() {
        if (current != null) {
            return current.getData();
        }
        return null;
    }

    // Método para obtener el tamaño de la lista
    public int size() {
        return size;
    }

    // Clase Node interna
    public static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        public T getData() {
            return data;
        }
    }
}
