package org.kaha.com.travelbot.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Country {
    private String name;
    private String capital;
    private float latitude;
    private float longitude;
    private List<String> officialLanguages;
    private String drivingSide;
}
