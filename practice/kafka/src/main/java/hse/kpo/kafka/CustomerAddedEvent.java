package hse.kpo.kafka;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public record CustomerAddedEvent(int customerId,
                                 String customerName,
                                 int handPower,
                                 int legPower,
                                 int iq) {
}