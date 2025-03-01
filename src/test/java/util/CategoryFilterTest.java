package util;

import model.categories.Category;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryFilterTest {

    private enum TestCategory implements Category {
        KAMPPAILULAJIT, MEDITOINTI, TÄHTITIEDE
    }

    @Test
    void testFilterEnums_WithMatchingEnums() {
        Category category1 = TestCategory.KAMPPAILULAJIT;
        Category category2 = TestCategory.MEDITOINTI;
        Category category3 = mock(Category.class);

        List<Category> categories = Arrays.asList(category1, category2, category3);
        List<TestCategory> filtered = CategoryFilter.filterEnums(categories, TestCategory.class);

        assertEquals(2, filtered.size());
        assertTrue(filtered.contains(TestCategory.KAMPPAILULAJIT));
        assertTrue(filtered.contains(TestCategory.MEDITOINTI));
    }

    @Test
    void testFilterEnums_NoMatchingEnums() {
        Category category1 = mock(Category.class);
        Category category2 = mock(Category.class);

        List<Category> categories = Arrays.asList(category1, category2);
        List<TestCategory> filtered = CategoryFilter.filterEnums(categories, TestCategory.class);

        assertTrue(filtered.isEmpty());
    }

    @Test
    void testFilterEnums_EmptyList() {
        List<Category> categories = Arrays.asList();
        List<TestCategory> filtered = CategoryFilter.filterEnums(categories, TestCategory.class);

        assertTrue(filtered.isEmpty());
    }
}
