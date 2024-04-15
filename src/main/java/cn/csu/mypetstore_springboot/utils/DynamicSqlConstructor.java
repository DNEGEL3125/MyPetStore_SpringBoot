package cn.csu.mypetstore_springboot.utils;


import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DynamicSqlConstructor {

    /**
     * For example
     *
     * @param sql
     * @param colObject `lineItem.product.petBreed.name` like
     * @return
     */
    public static String constructMemberSql(String sql, String colObject, Class<?> clazz) throws NoSuchFieldException {
        String[] parts = colObject.split("\\.");
        LinkedList<String> sqlParts = new LinkedList<>(List.of(sql.split(" ")));
        int i;
        for (i = 0; i < sqlParts.size(); ++i) {
            if (sqlParts.get(i).equalsIgnoreCase("from")) {
                break;
            }
        }

        // 不存在from
        if (i == sqlParts.size()) {
            throw new RuntimeException("Can't find `FROM` in sql");
        }

        int insertInd = 2 + i;
        String curTableName;
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            curTableName = CamelToSnakeConverter.camelToSnake(clazz.getSimpleName());
        } else {
            curTableName = table.name();
        }

        for (i = 0; i < parts.length - 1; ++i, ++insertInd) {
            String part = parts[i];
            Field declaredField = clazz.getDeclaredField(part);
            declaredField.setAccessible(true);
            JoinColumn joinColumn = declaredField.getAnnotation(JoinColumn.class);
            String addingToSql = """
                    INNER JOIN %s ON %s.%s = %s.%s
                    """;
            table = declaredField.getAnnotation(Table.class);
            String joinTableName;
            if (table == null) {
                joinTableName = CamelToSnakeConverter.camelToSnake(declaredField.getType().getSimpleName());
            } else {
                joinTableName = table.name();
            }

            clazz = declaredField.getType();
            Field idField = findIdField(clazz);

            assert idField != null;
            sqlParts.add(insertInd, addingToSql.formatted(
                    joinTableName,
                    joinTableName,
                    CamelToSnakeConverter.camelToSnake(idField.getName()),
                    curTableName,
                    joinColumn.name()
            ));
            curTableName = joinTableName;
        }

        if (parts.length == 1) {
            return String.join(" ", sqlParts).formatted(parts[0]);
        }

        String ownerCol = CamelToSnakeConverter.camelToSnake(parts[parts.length - 2]);
        String attrCol = CamelToSnakeConverter.camelToSnake(parts[parts.length - 1]);

        // If parts = [category, name], parts[parts.length - 2] + '.' + parts[parts.length - 1] = category.name
        return String.join(" ", sqlParts)
                .formatted(ownerCol + '.' + attrCol);
    }

    private static Field findIdField(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                return field;
            }
        }

        return null;
    }
}
