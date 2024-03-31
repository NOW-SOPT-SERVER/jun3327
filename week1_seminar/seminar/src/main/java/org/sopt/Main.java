package org.sopt;

import org.sopt.classes.Person;
import org.sopt.classes.PersonBuilder;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("김환준", 25, "male");
        person.walk();

        System.out.println(person.getName());
        
        person.setName("기요밍");

        System.out.println(person.getName());

        Person personWithBuilder = new
                PersonBuilder()
                .name("도소현")
                .age(24)
                .sex("female")
                .build();

        Person personWithFactoryMethod = Person.create("김환준", 25, "male");
    }
}