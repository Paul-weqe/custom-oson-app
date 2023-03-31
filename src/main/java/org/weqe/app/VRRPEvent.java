package org.weqe.app;

import org.onosproject.event.AbstractEvent;
import org.onosproject.net.device.DeviceDescription;
import org.onosproject.net.device.PortDescription;

public class VRRPEvent extends AbstractEvent<VRRPEvent.Type, DeviceDescription> {

    public enum Type {
        PACKET_IN
    }

    protected VRRPEvent(Type type, DeviceDescription subject, long time) {
        super(type, subject, time);
    }

    protected VRRPEvent(Type type, DeviceDescription subject) {
        super(type, subject);
    }

}
