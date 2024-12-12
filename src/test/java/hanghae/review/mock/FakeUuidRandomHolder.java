package hanghae.review.mock;

import hanghae.review.global.serivce.port.UUIDRandomHolder;

public class FakeUuidRandomHolder implements UUIDRandomHolder {
    private final String value;

    public FakeUuidRandomHolder(String value) {
        this.value = value;
    }

    @Override
    public String getRandom() {
        return value;
    }
}
