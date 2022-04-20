package vending;

import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {
    private final Map<T, Integer> inventory = new HashMap<>();

    public int getQuantity(T item) {
        Integer count = inventory.get(item);
        return count == null ? 0 : count;
    }

    public void add(T item) {
        Integer count = inventory.get(item);
        inventory.put(item, count + 1);
    }

    public void deduct(T item) {
        if (hasItem(item)) {
            Integer count = inventory.get(item);
            inventory.put(item, count - 1);
        }
    }

    public boolean hasItem(T item) {
        return getQuantity(item) > 0;
    }

    public void clear() {
        inventory.clear();
    }

    public void put(T item, int quantity) {
        inventory.put(item, quantity);
    }
}
