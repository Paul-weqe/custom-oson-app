package org.weqe.app;

import org.onosproject.net.*;
import org.weqe.app.virtualelements.VirtualDevice;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class VRRPGroup extends AbstractElement {

    @Override
    public ElementId id() {
        return id;
    }

    private VirtualDevice virtualRouter = null;
    private Set<Device> groupDevices = new HashSet<>();


    public VRRPGroup(VirtualDevice virtualDevice){
        this.virtualRouter = virtualDevice;
    }

    public VRRPGroup(VirtualDevice virtualDevice, Set<Device> devices){
        this.virtualRouter = virtualDevice;
        if (devices != null) {
            this.groupDevices = devices;
        }
    }

    public VirtualDevice getVirtualRouter(){ return virtualRouter; }
    public Set<Device> getGroupDevices(){ return groupDevices; }

    public void addDevice(Device device){
        groupDevices.add(device);
    }

    public void removeDevice(Device device){
        groupDevices.remove(device);
    }

    public Device nextDevice(){
        int size = groupDevices.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for (Device device: groupDevices){
            if (i == item) return device;
            i++;
        }
        return null;
    }

}
