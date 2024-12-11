package hanghae.review.global.infrastrcuture;

import hanghae.review.global.serivce.port.TimeRandomHolder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class SystemTimeRandomHolder implements TimeRandomHolder {
    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
