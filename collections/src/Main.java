import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        //demoArrays();
        //demoList();
        //demoMap();
        //demoStream();
    }

    private static void demoArrays() {
        // Создание

        // Классика - резерв памяти
        int x[] = new int[3]; //c-style - не рекомендовано
        x[0] = 1; // хотя конечно работает

        int[] y = new int[3];
        y[0] = 2;

        // статика
        int[] ints = new int [] {1, 2, 3}; // полный синтаксис
        int[] ints2 = {1, 2, 3}; // короткий

        // различие
        ints = new int[] {5, 6, 7}; // работает для существующей переменной
        //ints2 = {5, 6, 7}; // работает только в объявлении переменной

        // утилита Arrays
        // работают с ссылкой на класс - если можно так выразиться
        System.out.println(ints); // дефолтовый toString()
        System.out.println(Arrays.toString(ints));

        Arrays.fill(ints, 5);
        System.out.println(Arrays.toString(ints));

        // streams
        int[] ints3 = IntStream.of(1, 2, 3).toArray();
        System.out.println(Arrays.toString(ints3));

        int[] ints4 = IntStream.rangeClosed(1, 8).toArray();
        System.out.println(Arrays.toString(ints4));

        // одна из проблем с массивом - увеличение размера
        System.out.println("size before=" + ints.length);
        ints = Arrays.copyOf(ints, ints.length + 1); // заново резервируется память
        System.out.printf("size after=%5d%n", ints.length);

    }

    private static void demoList() {
        // Создание
        //List<int> list = new ArrayList<>(); // с примитивами не работает

        List<Integer> list2 = new ArrayList<Integer>(); // если слева тип то справа не нужно (diamond)
        var list3 = new ArrayList<>(); // не указаны типы - будет Object
        var list4 = new ArrayList<Integer>(); // так правильнее

        // Классика
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);

        // Из массива
        List<Integer> ints2 = Arrays.asList(1, 2, 3);
        //ints2.add(4); // проходит компиляцию но работать не будет

        // Проблема - фиксированный размер
        // Как решить проблему
        List<Integer> ints3 = new ArrayList<>(Arrays.asList(1, 2, 3));

        // double brace - анти паттерн
        List<Integer> ints4 = new ArrayList<>() {{ add(1); add(2); }};

        // stream - нет точного контракта но использовать можно
        List<Integer> ints5 = Stream.of(1, 2, 3).toList();

        // immutable list - можно только читать но память+треды
        List<Integer> ints6 = List.of(1, 2, 3); // перегрузка до 10 переменных
        System.out.println(ints6);
        //ints6.add(4);

        // можно как и в случае выше применить тот же фикс для модификаций
        List<Integer> ints7 = new LinkedList<>(List.of(1, 2, 3));
        System.out.println(ints7);
        ints7.addFirst(4);
        System.out.println(ints7);

        // Что под капотом?
        List<Integer> ints8 = new ArrayList<>(1);
        ints8.addAll(List.of(1, 2, 3, 4, 5, 6)); // будет увеличен размер

        // Зачем нужен итератор?
        System.out.println(ints8);

        // можем словить исключение
        for (Integer item : ints8) {
            //if (item == 3) ints8.remove(item);
        }
        System.out.println(ints8);

        // через итератор
        Iterator<Integer> iterator = ints8.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 3) {
                iterator.remove();
            }
        }
        System.out.println(ints8);

        // лямбда
        ints8.removeIf(x -> x == 2);
        System.out.println(ints8);
    }

    private static void demoMap() {
        //  Создание

        // Классика
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");

        System.out.println(map);

        // фабрика для immutable - аналогично листу
        Map<Integer, String> map2 = Map.of(4, "four", 5, "five");
        // перегрузка до 10

        // более 10 другой метод
        Map<Integer, String> map3 = Map.ofEntries(
                Map.entry(4, "four"),
                Map.entry(5, "five"));

        // уже знаем как сделать мофифицируемым
        Map<Integer, String> map4 = new HashMap<>(Map.of(6, "six"));

        // что под капотом у HashMap?
        // бакиты - изначально 16
        // load factor - 0.75
        // далее бакиты удваиваются
        // внутри бакита до 8 - linkedlist, посел rb tree

        // Представления
        var keys = map.keySet();
        System.out.println("keys: " + keys);
        keys.remove(1);
        System.out.println(map); // удаление произошло в самой коллекции
        // но
        //keys.add(9);
        System.out.println(map);

        // Перебор элементов
        // раньше
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry);
        }
        // сейчас
        map.forEach((k, v) -> System.out.println(k + ": " + v));

        // если не найден элемент
        System.out.println(map.get(100));
        System.out.println(map.getOrDefault(100, "not defined"));

        System.out.println(map.computeIfAbsent(100, k -> "not defined"));

        // что нужно всегда помнить при работе с HashMap
        // ключ должен быть immutable так как по нему работает hashCode()
    }

    private static void demoStream() {
        class Order {
            final String name;
            final String category;
            final double cost;
            Order(String name, String category, double cost) {
                this.name = name;
                this.category = category;
                this.cost = cost;
            }
        }

        List<Order> orders = List.of(
                new Order("java", "book", 30.20),
                new Order("water", "food", 3.21),
                new Order("pen", "tools", 1.01)
        );

        // найдем заказы с ценой меньше 10, отсортируем и выведем

        orders
                .stream()
                .filter(order -> order.cost < 10)
                //.sorted(Comparator.comparingDouble(Order::getCost))
                .sorted((o1, o2) -> Double.compare(o1.cost, o2.cost))
                //.forEach(System.out::println);
                .map(x -> x.name)
                .forEach(System.out::println);

        // посчитаем общую стоимость
        double total = orders
                .stream()
                .map(x -> x.cost)
                .reduce(0.0, Double::sum);
        System.out.println(total);

        orders
                .stream()
                .collect(Collectors.groupingBy(x-> x.category, Collectors.counting()))
                .forEach((k, v) -> System.out.println(k + ": " + v));
    }
}