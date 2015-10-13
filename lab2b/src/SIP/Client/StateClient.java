package SIP.Client;

import SIP.SIPHandler;
import SIP.Server.State;

/**
 * Created by Julia on 2015-10-13.
 */
public abstract class StateClient {
    public abstract ClientHandler.StateEvent getStateName();

    public StateClient sentInvite() {
        return this;
    }
    public StateClient sentEnd() {
        return this;
    }
}
