package org.weqe.app;

import org.onlab.packet.ChassisId;
import org.onosproject.cluster.ClusterService;
import org.onosproject.net.Device;
import org.onosproject.net.DeviceId;
import org.onosproject.net.device.DeviceEvent;
import org.onosproject.net.device.DeviceListener;
import org.onosproject.net.device.DeviceProvider;
import org.onosproject.net.provider.Provider;
import org.onosproject.net.provider.ProviderId;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weqe.app.virtualelements.VirtualDevice;

import java.util.HashSet;
import java.util.Set;

@Component(immediate = true)

public class VRRPProvider {

    private static final Logger log = LoggerFactory.getLogger(VRRPProvider.class);
    private ProviderId providerId;
    private DeviceListener listener = new InternalVRRPListener();

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected VRRPService vrrpService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected ClusterService clusterService;

    private final String schemeProp = "virtual-router";
    private final String idProp = "org.weqe.app";

    @Activate
    public void activate(ComponentContext context){
        log.info("VRRP ACTIVATED!!!!");
        providerId = new ProviderId(schemeProp, idProp);
        vrrpService.addListener(listener);
    }

    private class InternalVRRPListener implements DeviceListener {
        @Override
        public void event(DeviceEvent event) {
            log.info("Something has happened!!!");
            switch (event.type()){
                case DEVICE_ADDED:
                    log.info("VRRP NEW DEVICE HAS BEEN ADDED!!!!!!!!!!!!!!");
                    createVRRPGroup(new HashSet<>());
                    break;
                case DEVICE_REMOVED:
                    log.info("device has been removed");
                    break;
            }
        }
    }

    private void createVRRPGroup(Set<Device> devices) {
        DeviceId deviceId = DeviceId.deviceId(schemeProp + ":" + clusterService.getLocalNode().ip());
        VirtualDevice virtualDevice = new VirtualDevice(providerId, deviceId);

        VRRPGroup vrrpGroup = new VRRPGroup(virtualDevice, devices);
        vrrpService.addVrrpGroup(vrrpGroup);
    }

}
