/**
 * Created by Julia on 2015-10-13.
 */
public class StateClosing extends State{
    @Override
    public SIPHandler.StateEvent getStateName() {
        return SIPHandler.StateEvent.CLOSING;
    }

    @Override
    public State receivedBye() {
        return new StateWaiting();
    }
}
