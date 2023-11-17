package spharos.reservation.reservations.axon.event;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientRegistrationEvent {

    private String clientId;

    public ClientRegistrationEvent(String clientId) {
        this.clientId = clientId;
    }

    public ClientRegistrationEvent() {
    }
}
