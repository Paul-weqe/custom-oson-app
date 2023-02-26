package org.weqe.app.device;

import org.onosproject.event.AbstractEvent;
import org.onosproject.net.device.PortDescription;

public class VirtualDeviceEvent extends AbstractEvent<VirtualDeviceEvent.Type, PortDescription> {

    public enum Type {
        PORT_ADDED,
        PORT_REMOVED,
        PORT_UPDATED
    }

    /**
     * @param type      event type
     * @param subject   the port description
     */
    public VirtualDeviceEvent(Type type, PortDescription subject) {
        super(type, subject);
    }

    /**
     * Creates a new big switch event.
     *
     * @param type event type
     * @param subject the port description
     * @param time occurence time
     */
    public VirtualDeviceEvent(Type type, PortDescription subject, long time) {
        super(type, subject, time);
    }

}
