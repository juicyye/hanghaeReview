package hanghae.review.mock;

import hanghae.review.global.serivce.port.TimeRandomHolder;
import java.time.LocalDateTime;

public class FakeTimeRandomHolder implements TimeRandomHolder {
    private final LocalDateTime value;

    public FakeTimeRandomHolder(LocalDateTime value) {
        this.value = value;
    }

    @Override
    public LocalDateTime getCurrentTime() {
        return value;
    }
}
