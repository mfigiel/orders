package com.testing.api.resource;

import com.testing.logging.Loggable;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonApi {

    @NonNull
    private Date creationDate;
    @Loggable
    @NonNull
    private String Name;
    @Loggable
    @NonNull
    private String Surname;
    private PersonAdressApi adress;
}
