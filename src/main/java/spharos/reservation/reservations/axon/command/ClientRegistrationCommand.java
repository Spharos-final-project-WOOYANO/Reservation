package spharos.reservation.reservations.axon.command;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientRegistrationCommand {
    private String clientId;
  //  private String clientName;

    public ClientRegistrationCommand(String clientId) {
        this.clientId = clientId;
    }

    public ClientRegistrationCommand() {
    }
}
