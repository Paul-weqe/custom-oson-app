package org.weqe.app.device;

import com.google.j2objc.annotations.Property;
import org.onosproject.net.DeviceId;
import org.onosproject.net.MastershipRole;
import org.onosproject.net.PortNumber;
import org.onosproject.net.device.DefaultDeviceDescription;
import org.onosproject.net.device.DeviceDescription;
import org.onosproject.net.device.DeviceProvider;
import org.onosproject.net.device.DeviceProviderRegistry;
import org.onosproject.net.provider.ProviderId;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class VirtualDeviceProvider implements DeviceProvider {
    Logger log = LoggerFactory.getLogger(getClass());

    private static final String DEFAULT_SCHEME = "virtualDevice";
    private static final String DEFAULT_ID = "org.weqe.virtualswitch.id";
    private ProviderId providerId;
    private VirtualDevice virtualDevice;
    private DeviceDescription deviceDescription;

    private DeviceProviderRegistry deviceProviderRegistry;

    @Property(value = DEFAULT_SCHEME)
    private static final String schemeProperty = DEFAULT_SCHEME;

    @Property(value = DEFAULT_ID)
    private static final String idProperty = DEFAULT_ID;

    @Activate
    public void activate(ComponentContext context) {
        providerId = new ProviderId(schemeProperty, idProperty);
    }

    @Override
    public void triggerProbe(DeviceId deviceId) {
    }

    @Override
    public void roleChanged(DeviceId deviceId, MastershipRole newRole) {

    }

    @Override
    public boolean isReachable(DeviceId deviceId) {
        return true;
    }

    @Override
    public void changePortState(DeviceId deviceId, PortNumber portNumber, boolean enable) {
    }

    @Override
    public ProviderId id() {
        return providerId;
    }
    
    private void registerToDeviceProvider() {
        long millis = Instant.now().toEpochMilli();
        DeviceId deviceId = DeviceId.deviceId(schemeProperty + ":" + millis);
        log.info("Registered {}", deviceId);
        
        virtualDevice = new VirtualDevice(deviceId, this.id());
        
        deviceDescription = new DefaultDeviceDescription(
                virtualDevice.id().uri(),
                virtualDevice.type(), virtualDevice.manufacturer(),
                virtualDevice.hwVersion(), virtualDevice.swVersion(), virtualDevice.serialNumber(),
                virtualDevice.chassisId()
        );
        
    }
}
