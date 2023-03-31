package org.weqe.app;

import org.onosproject.net.*;
import org.weqe.app.virtualelements.VirtualDevice;

import java.util.HashSet;
import java.util.Set;

public class VRRPGroup extends AbstractElement {

    @Override
    public ElementId id() {
        return id;
    }

    private VirtualDevice groupLeader = null;
    private Set<Device> groupDevices = new HashSet<>();

    public VRRPGroup(VirtualDevice virtualDevice){
        this.groupLeader = virtualDevice;
    }

    public VRRPGroup(VirtualDevice virtualDevice, Set<Device> devices){
        this.groupLeader = virtualDevice;
        this.groupDevices = devices;
    }

    public VirtualDevice getLeader(){ return groupLeader; }
    public Set<Device> getGroupDevices(){ return groupDevices; }

    public void addDevice(Device device){
        groupDevices.add(device);
    }

    public void removeDevice(Device device){
        groupDevices.remove(device);
    }

}
