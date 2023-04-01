package org.weqe.app;

import org.onosproject.event.ListenerService;
import org.onosproject.net.Device;
import org.onosproject.net.ElementId;
import org.onosproject.net.device.DeviceEvent;
import org.onosproject.net.device.DeviceListener;
import org.weqe.app.virtualelements.VirtualDevice;

import java.util.Set;

public interface VRRPService extends ListenerService<DeviceEvent, DeviceListener> {

    /**
     * gives list of all the devices that are part of the VRRP configuration at that moment
     */
    Set<Device> getDevices(ElementId vrrpGroupId);

    /**
     * Chooses the next device that will handle the given packet in a VRRP GROUP
     */
    Device getNextDevice(ElementId vrrpGroupId);


    /**
     * @param vrrpGroupId group id for the VRRP group that you would want to get the lead virtual router for
     * @return VirtualDevice that is currently leading the VRRPGROUP
     */
    VirtualDevice getVirtualRouter(ElementId vrrpGroupId);

    void addVrrpGroup(VRRPGroup vrrpGroup);

}