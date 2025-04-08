package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import model.categories.Category;

public class MatchUtils {
    private MatchUtils() {
    }

    public static List<Category> findCommonInterests(List<Category> list1, List<Category> list2) {
        System.out.println("DEBUG: Finding common interests between " + list1 + " and " + list2);
        List<Category> commonInterests = new ArrayList<>(list1);
        commonInterests.retainAll(list2);
        return commonInterests;
    }

    public static String formatInterests(List<Category> interests) {
        return interests.stream()
                .map(Category::toString)
                .map(s -> s.toLowerCase(Locale.ROOT).replace("_", " "))
                .collect(Collectors.joining(", "));
    }
}
