public enum TextStrings {
    HELLO("Слава Україні!"),
    RUSSIAN("Виявлено російську мову! Що таке \"паляниця\"?"),
    RUSSIAN_LETTERS("[ёъыэ]"),
    RIGHT_ANSWER("Правильна відповідь! Сподіваюсь, Ви випадково не змінили розкладку клавіатури ;)"),
    WRONG_ANSWER("Ошибочка... Руский корабль иди... Bye-bye!"),
    BYE("Приємно було поспілкуватись! До побачення! Гарного дня!");

    private final String message;

    TextStrings(String message) {
        this.message = message;
    }

    public String getTextString() {
        return message;
    }
}
