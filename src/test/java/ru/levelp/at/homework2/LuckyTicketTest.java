package ru.levelp.at.homework2;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LuckyTicketTest {
    private LuckyTicket luckyTicket;

    @BeforeClass(groups = {"positive", "negative"})
    public void beforeClass() {
        luckyTicket = new LuckyTicket();
    }

    @Test(groups = {"positive"}, dataProvider = "luckyTicketDataProvider",
          dataProviderClass = LuckyTicketDataProvider.class)
    public void testLuckyTicket(Integer number) {
        assertEquals(luckyTicket.isLuckyTicket(number), true,
            "Ошибка проверки на счастливый билет");
    }

    @Test(groups = {"negative"}, dataProvider = "notLuckyTicketDataProvider",
          dataProviderClass = LuckyTicketDataProvider.class)
    public void testNotLuckyTicket(Integer number) {
        assertEquals(luckyTicket.isLuckyTicket(number), false,
            "Ошибка проверки на не счастливый билет");
    }

    @Test(groups = {"negative"}, dataProvider = "sizeNumberDataProvider",
          dataProviderClass = LuckyTicketDataProvider.class, expectedExceptions = {IllegalArgumentException.class})
    public void testSizeNumber(Integer number) {
        luckyTicket.isLuckyTicket(number);
    }
}
