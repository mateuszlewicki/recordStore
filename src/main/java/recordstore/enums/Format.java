package recordstore.enums;

import lombok.Getter;

@Getter
public enum Format {
    TWELVE("12\""),
    TEN("10\""),
    SEVEN("7\""),
    LP("LP");

    private final String type;

    Format(String type) {
        this.type = type;
    }
}
