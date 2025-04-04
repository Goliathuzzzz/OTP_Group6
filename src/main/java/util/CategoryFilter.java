package util;

import model.categories.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter {

    public static <T extends Enum<T> & Category> List<T> filterEnums(List<Category> categories, Class<T> enumClass) {
        return categories.stream()
                .filter(enumClass::isInstance)
                .map(enumClass::cast)
                .collect(Collectors.toList());
    }

    private CategoryFilter() {
    }
}
