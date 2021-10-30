package be.jslm.liquidpanter;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Generated;
import javax.validation.constraints.NotEmpty;

@Data
@Table("events")
public class Event {

    @Id
    private Long id;

    @NotEmpty
    private String title, location, when;

}
