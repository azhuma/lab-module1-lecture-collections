package kz.lab.module1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Advanced {
    static String x;

    public static void main(String[] args) {
        System.out.println("advanced");

        //demoRecords();
        //demoGenerics();
        demoLambda();
    }

    private static void demoRecords() {
        System.out.println("records");

        // связано с понятием immutable
        Point p = new Point(1, 2);
        System.out.println(p.getX());

        PointRec pReq = new PointRec(1, 2);
        System.out.println(pReq.y());

        var p2 = new Point(1, 2);
        System.out.println(p.equals(p2));

        var pReq2 = new PointRec(1, 2);
        System.out.println(pReq.equals(pReq2));

        System.out.println(p);
        System.out.println(pReq);

    }

    private static void demoGenerics() {
        System.out.println("generics");
        // generic это тип с typed parameter
        // нельзя использовать примитивы
        // некоторые классы не могут быть дженериками

        Box<Integer> box = new Box<>(1); //diamond оператор
        System.out.println(box.getItem());

        Box<String> box2 = new Box<String>("3");
        System.out.println(box2.getItem());

        // как написать фабричный метод?

        // wildcards
        Box<Animal> box3 = new Box<>(new Cat()); // можно добавить кота
        Box<Dog> box4 = new Box<>(new Dog()); // отдельный бокс с собакой
        //box3 = box4; // не работает!

        Box<? extends Animal> box5 = box4; //wildcard - теперь животное - собака
        //box5.setItem(new Dog());

        Box<? super Animal> box6 = box3;
        box6.setItem(new Dog());
    }

    private static void demoLambda() {
        var names = new ArrayList<>(List.of("four", "two", "three", "one"));

        // анонимный класс
        names.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });
        System.out.println(names);

        // лямбда
        names.sort((o1, o2) -> Integer.compare(o1.length(), o2.length()));
        // для параметров можно использовать типы, var или не указывать

        callMyFunc("me", s -> "hello " + s);
        callMyFunc("reference", Advanced::func);

        // Стандартные функциональные интерфейсы
        // Function: T->R
        // Supplier: ()->R
        // Consumer: T->void
        // Predicate: T->boolean

        // отдельная тема с pure functions для функционального стиля
        names.forEach(name -> setX());

        var count = 0;
        //names.forEach(name -> System.out.println(++count + name));

        try (FileWriter f = new FileWriter("README.md", true);) {
            for (String name : names) {
                f.write(name + "\n");
            }

            // в виде ляюбды
            //names.forEach(name -> f.write(name););
            // проблема c checked exception

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void callMyFunc(String param, MyFunction func) {
        String output = func.apply(param);
        System.out.println(output);
    }

    public static String func(String param) {
        return "method " + param;
    }

    public static void setX() {
        x = "hello";
    }
}

interface Animal {}
class Cat implements Animal {}
class Dog implements Animal {}
