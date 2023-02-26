package org.weqe.app.device;

import org.onosproject.event.ListenerService;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.device.PortDescription;
import java.util.List;

public interface VirtualDeviceService extends ListenerService<VirtualDeviceEvent, VirtualDeviceListener> {

    /**
     * @param port - the port that we are aiming to add to the device.
     * @return PortNumber
     */
    ConnectPoint addPort(ConnectPoint port);

    /**
     * Get list of all ports on the device
     *
     * @return
     */
    List<PortDescription> getPorts();

}
