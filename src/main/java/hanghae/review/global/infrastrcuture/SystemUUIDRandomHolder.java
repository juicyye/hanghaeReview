package hanghae.review.global.infrastrcuture;

import hanghae.review.global.serivce.port.UUIDRandomHolder;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SystemUUIDRandomHolder implements UUIDRandomHolder {
    @Override
    public String getRandom() {
        return UUID.randomUUID().toString();
    }
}
