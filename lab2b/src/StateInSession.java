/**
 * Created by Julia on 2015-10-13.
 */
public class StateInSession extends State{
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.INSESSION;
    }
}
