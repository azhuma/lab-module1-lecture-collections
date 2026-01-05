package kz.lab.module1;

public class Box<T> {

    private T item;

    public T getItem() {
        return item;
    }

    public Box(T item) {
        this.item = item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
