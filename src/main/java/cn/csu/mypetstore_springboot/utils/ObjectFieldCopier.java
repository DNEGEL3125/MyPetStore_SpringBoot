package cn.csu.mypetstore_springboot.utils;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ObjectFieldCopier {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        // Create instances of the objects you want to compare
        YourObject a = new YourObject();
        YourObject b = new YourObject();

        a.nestedObject.nestedName = "Hello world";

        // Copy fields from 'a' to 'b', recursively checking for null values
        copyFieldsIfNotNullRecursively(a, b);

        // Print the fields of object 'b' after copying
        System.out.println(b);
    }

    public static Field findIdField(Entity a) {
        Field[] fields = a.getClass().getFields();
        for (var field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                return field;
            }
        }

        return null;
    }

    public static boolean isEntity(Field field) {
        Entity annotation = field.getAnnotation(Entity.class);

        return annotation != null;
    }

    public static boolean isId(Field field) {
        return field.getAnnotation(Id.class) != null;
    }

    public static boolean isManyToOne(Field field) {
        return field.getAnnotation(ManyToOne.class) != null;
    }

    public static boolean isManyToMany(Field field) {
        return field.getAnnotation(ManyToMany.class) != null;
    }

    // Define a method to recursively copy fields from 'a' to 'b'
    public static void copyFieldsIfNotNullRecursively(Object a, Object b) throws IllegalAccessException {
        // Get the class of the objects
        Class<?> clazz = a.getClass();

        // Get all fields of the class, including inherited fields
        Field[] fields = clazz.getDeclaredFields();

        // Iterate through each field
        for (Field field : fields) {
            // Set the field accessible (in case it's private)
            field.setAccessible(true);

            // Skip final fields
            if ((field.getModifiers() & java.lang.reflect.Modifier.FINAL) != 0) {
                continue;
            }
            // Skip static fields
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                continue;
            }


            // Get the value of the field from object 'a'
            Object valueA = field.get(a);

            // Get the value of the field from object 'b'
            Object valueB = field.get(b);


            if (valueA == null) {
                continue;
            }
            // Copy from `a` directly, don't need to copy recursively
            if (valueB == null) {
                field.set(b, valueA);
                continue;
            }

            // If the field type is annotated with @Entity and not null, recursively copy its fields
            if (isEntity(field)) {
                copyFieldsIfNotNullRecursively(valueA, valueB);
            }
            // Else copy from `a` directly
            else {
                field.set(b, valueA);
            }
        }
    }

    // Define your object class here
    static class YourObject {
        private final int id = 1;
        private final String name = "example";
        private final NestedObject nestedObject = new NestedObject();

        // Define more fields as needed

        @Override
        public String toString() {
            return "YourObject{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", nestedObject=" + nestedObject +
                    '}';
        }
    }

    // Define a nested object class for testing
    @Entity
    static class NestedObject {

        @Id
        private Integer nestedId = 2;
        private String nestedName = "nestedExample";

        // Define more fields as needed

        @Override
        public String toString() {
            return "NestedObject{" +
                    "nestedId=" + nestedId +
                    ", nestedName='" + nestedName + '\'' +
                    '}';
        }
    }
}
