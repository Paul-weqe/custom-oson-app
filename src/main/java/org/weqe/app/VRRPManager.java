package org.weqe.app;

import org.onosproject.event.AbstractListenerManager;
import org.onosproject.net.Device;
import org.onosproject.net.ElementId;
import org.onosproject.net.device.DeviceEvent;
import org.onosproject.net.device.DeviceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weqe.app.virtualelements.VirtualDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VRRPManager
        extends AbstractListenerManager<DeviceEvent, DeviceListener>
        implements VRRPService {

    private static final Logger log = LoggerFactory.getLogger(VRRPManager.class);
    List<VRRPGroup> vrrpGroups = new ArrayList<>();

    private VRRPGroup getVRRPGroup(ElementId vrrpGroupId) {
        for (VRRPGroup group: vrrpGroups){
            if (group.id().equals(vrrpGroupId)){
                return group;
            }
        }
        log.error("unable to find devices for VRRP group " + vrrpGroupId);
        return null;
    }

    @Override
    public Set<Device> getDevices(ElementId vrrpGroupId) {
        VRRPGroup group = getVRRPGroup(vrrpGroupId);
        assert group != null;
        log.info("successfully found devices in group " + vrrpGroupId);
        return group.getGroupDevices();
    }

    @Override
    public Device getNextDevice(ElementId vrrpGroupId) {
        VRRPGroup group = getVRRPGroup(vrrpGroupId);
        assert group != null;
        log.info("next device for VRRP group " + vrrpGroupId + " is ");
        return group.nextDevice();
    }

    @Override
    public VirtualDevice getVirtualRouter(ElementId vrrpGroupId) {
        VRRPGroup group = getVRRPGroup(vrrpGroupId);
        assert group != null;
        log.info("looking for virtual router for group " + vrrpGroupId);
        return group.getVirtualRouter();
    }

    @Override
    public void addVrrpGroup(VRRPGroup vrrpGroup) {
        vrrpGroups.add(vrrpGroup);
    }
}
