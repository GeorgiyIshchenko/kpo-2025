package hse.kpo.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingCompletedEvent {
    private Integer customerId;
    private String trainingType;
}
