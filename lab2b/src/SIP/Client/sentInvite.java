package SIP.Client;

/**
 * Created by Julia on 2015-10-13.
 */
public class sentInvite extends StateClient{
    @Override
    public ClientHandler.StateEvent getStateName() {
        return ClientHandler.StateEvent.INVITE;
    }
}
