import java.util.Arrays;

public enum PartDetailType {

    MOUNTED_ON("Montuojama"),
    HEIGHT("Auk\u0161tis, mm"),
    THICKNESS("Storis, mm"),
    CENTER_DIAMETER("Centr.skersmuo, mm"),
    OUTER_DIAMETER("I\u0161orinis skersmuo, mm"),
    HOLES_COUNT("Angos"),
    ALLOWED_MINIMUM("Leistinas minimumas, mm"),
    TYPE("Stabd\u017ei\u0173 disko tipas"),
    INNER_DIAMETER("\u012edubimo skersmuo, mm"),
    BRAND("Prek\u0117s \u017eenklas"),
    EAN_CODE("EAN kodas"),
    REQUIRED_AMMOUNT("Reikiamas kiekis"),
    DIAMETER("Skersmuo, mm"),
    SURFACE("Pavir\u0161ius"),
    BREAK_SYSTEM("Stabd\u017ei\u0173 sistema"),
    NOTES("Pastabos"),
    NO_MATCH("Nerasta");

    private final String value;

    PartDetailType(String value) {
        this.value = value;
    }

    public static PartDetailType fromText(String text) {
        for (PartDetailType detail : PartDetailType.values()) {
            if (detail.getValue().equals(text)) {
                return detail;
            }
        }

        return NO_MATCH;
    }

    public static String[] getAllValues() {
        return Arrays
                .stream(values())
                .map(PartDetailType::getValue)
                .toArray(String[]::new);
    }

    public String getValue() {
        return value;
    }
}
