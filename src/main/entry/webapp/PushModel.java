package main.entry.webapp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushModel {

    private TModel data;
    private String eventTime;
    private String serviceId;
    private String serviceType;

}