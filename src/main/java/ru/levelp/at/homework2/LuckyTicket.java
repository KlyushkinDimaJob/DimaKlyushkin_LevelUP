package ru.levelp.at.homework2;

public class LuckyTicket {
    /**
     * Определение счастливого билета. Билет IT'шника (начинается с 0).
     * Если меньше 6 цифр, то дополняется слева 0
     *
     * @param number - номер билета
     *
     * @return boolean - результат проверки на счастливый билет
     */
    public boolean isLuckyTicket(int number) {
        if (number >= 0 && number < 1000000) {
            var leftSum = number / 100000 + (number / 10000 % 10) + (number / 1000 % 10);
            var rightSum = (number / 100 % 10) + (number / 10 % 10) + (number % 10);
            return leftSum == rightSum;
        }
        return false;
    }
}
