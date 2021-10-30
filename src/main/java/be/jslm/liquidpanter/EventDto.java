package be.jslm.liquidpanter;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class EventDto {

    @NotEmpty
    @Size(max = 50)
    private String title;

    @NotEmpty
    private String when;

    @NotEmpty
    private String location;

}
