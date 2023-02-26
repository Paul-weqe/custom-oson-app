package org.weqe.app.device;


import org.onlab.packet.ChassisId;
import org.onosproject.net.DefaultDevice;
import org.onosproject.net.DeviceId;
import org.onosproject.net.provider.ProviderId;

public class VirtualDevice extends DefaultDevice {
    private static final String MANUFACTURER = "ORG.weqe";
    private static final String HW_VERSION = "HW 1.0-";
    private static final String SW_VERSION = "SW 1.0-";
    private static final String SERIAL = "v1";
    private static final ChassisId CHASSIS_ID = new ChassisId();

    /**
     * Creates a network element attributed to the specified provider.
     *
     * @param providerId   identity of the provider
     * @param id           device identifier
     */
    public VirtualDevice(DeviceId id, ProviderId providerId) {

        super(providerId, id, Type.VIRTUAL, MANUFACTURER, HW_VERSION, SW_VERSION,
                SERIAL, CHASSIS_ID);

    }
}