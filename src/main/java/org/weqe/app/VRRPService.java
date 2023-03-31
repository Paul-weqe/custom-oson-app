package org.weqe.app;

import org.onosproject.event.ListenerService;
import org.onosproject.net.Device;
import org.weqe.app.virtualelements.VirtualDevice;

import java.util.List;

public interface VRRPService extends ListenerService<VRRPEvent, VRRPListener> {

    /**
     * gives list of all the devices that are part of the VRRP configuration at that moment
     */
    List<Device> getDevices();

    /**
     * Chooses the next device that will handle the given packet
     */
    Device getNextDevice();

    VirtualDevice getVirtualRouter();

}