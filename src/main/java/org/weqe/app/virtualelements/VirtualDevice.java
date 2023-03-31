package org.weqe.app.virtualelements;

import org.onlab.packet.ChassisId;
import org.onosproject.net.DefaultDevice;
import org.onosproject.net.DeviceId;
import org.onosproject.net.provider.ProviderId;
import org.weqe.app.VRRPGroup;

public class VirtualDevice extends DefaultDevice {

    private static final String MANUFACTURER = "weqe.Lab";
    private static final String HW_VERSION = "HW-v1";
    private static final String SW_VERSION = "SW-v1";
    private static final String SERIAL = "SERIAL-v1";
    private static final ChassisId CHASSIS = new ChassisId();
    private VRRPGroup vrrpGroup;

    /**
     * Creates a network element attributed to the specified provider.
     *
     * @param providerId   identity of the provider
     * @param deviceId           device identifier
     */
    public VirtualDevice(ProviderId providerId, DeviceId deviceId) {
        super(providerId, deviceId, Type.VIRTUAL, MANUFACTURER, HW_VERSION, SW_VERSION, SERIAL, CHASSIS);
    }

    public void setVrrpGroup(VRRPGroup vrrpGroup){
        this.vrrpGroup = vrrpGroup;
    }

}
