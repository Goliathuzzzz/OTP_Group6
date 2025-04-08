package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import model.categories.Category;
import org.junit.jupiter.api.Test;

class MatchUtilsTest {

    @Test
    void testFindCommonInterests() {
        Category category1 = mock(Category.class);
        Category category2 = mock(Category.class);
        Category category3 = mock(Category.class);

        List<Category> list1 = Arrays.asList(category1, category2);
        List<Category> list2 = Arrays.asList(category2, category3);

        List<Category> common = MatchUtils.findCommonInterests(list1, list2);

        assertEquals(1, common.size());
        assertTrue(common.contains(category2));
    }

    @Test
    void testFindCommonInterests_NoCommon() {
        Category category1 = mock(Category.class);
        Category category2 = mock(Category.class);
        Category category3 = mock(Category.class);
        Category category4 = mock(Category.class);

        List<Category> list1 = Arrays.asList(category1, category2);
        List<Category> list2 = Arrays.asList(category3, category4);

        List<Category> common = MatchUtils.findCommonInterests(list1, list2);

        assertTrue(common.isEmpty());
    }

    @Test
    void testFormatInterests() {
        Category category1 = mock(Category.class);
        Category category2 = mock(Category.class);
        when(category1.toString()).thenReturn("TÄHTIENTARKKAILU");
        when(category2.toString()).thenReturn("MEDITOINTI");

        List<Category> interests = Arrays.asList(category1, category2);
        String formatted = MatchUtils.formatInterests(interests);

        assertEquals("tähtientarkkailu, meditointi", formatted);
    }

    @Test
    void testFormatInterests_EmptyList() {
        List<Category> interests = Arrays.asList();
        String formatted = MatchUtils.formatInterests(interests);

        assertEquals("", formatted);
    }
}
