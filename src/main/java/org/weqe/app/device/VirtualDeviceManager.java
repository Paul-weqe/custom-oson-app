package org.weqe.app.device;

import org.jvnet.hk2.annotations.Service;
import org.onlab.util.KryoNamespace;
import org.onosproject.event.AbstractListenerManager;
import org.onosproject.net.*;
import org.onosproject.net.device.DefaultPortDescription;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.device.PortDescription;
import org.onosproject.net.edge.EdgePortService;
import org.onosproject.store.service.ConsistentMap;
import org.onosproject.store.service.Serializer;
import org.onosproject.store.service.StorageService;
import org.onosproject.store.service.Versioned;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Listen for edge and port changes in the underlying
 * data path and exposes a virtual device abstraction.
 */
@Component(immediate = true)
@Service
public class VirtualDeviceManager
        extends AbstractListenerManager<VirtualDeviceEvent, VirtualDeviceListener>
        implements VirtualDeviceService {

    private static final Logger log = LoggerFactory.getLogger(VirtualDeviceManager.class);

    private static final String REALIZED_BY = "virtual-device:realizedBy";
    private static final String PORT_MAP = "virtual-device-port-map";
    private static final String PORT_COUNTER = "virtual-device-port-counter";
    private final Serializer serializer = Serializer.using(KryoNamespace.newBuilder().build());
    private ConsistentMap<ConnectPoint, Long> portMap;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected DeviceService deviceService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected EdgePortService edgePortService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected StorageService storageService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected PortDescription portDescription;

    @Override
    public ConnectPoint addPort(ConnectPoint port) {
        portMap.keySet().add(port);
        return port;
    }

    @Override
    public List<PortDescription> getPorts() {
        return portMap.keySet().stream()
                .map(cp -> toVirtualPortDescription(cp))
                .filter(cp -> cp != null)
                .collect(Collectors.toList());
    }


    public PortDescription toVirtualPortDescription(ConnectPoint cp){
        Port p = deviceService.getPort(cp.deviceId(), cp.port());
        if (p == null){
            return null;
        }

        // copy annotation
        DefaultAnnotations.Builder annot = DefaultAnnotations.builder();
        p.annotations().keys().forEach(
                k -> annot.set(k, p.annotations().value(k)));
        annot.set(REALIZED_BY, String.format("%s/%s", cp.deviceId().toString(), cp.port().toString()));

        Long vPortNo = Versioned.valueOrNull(portMap.get(cp));
        if (vPortNo == null){
            return null;
        }

        PortNumber portNumber = PortNumber.portNumber(vPortNo);

        DefaultPortDescription.Builder builder = DefaultPortDescription.builder();

        builder.withPortNumber(portNumber)
                .isEnabled(p.isEnabled())
                .type(p.type())
                .portSpeed(p.portSpeed())
                .annotations(annot.build());

        return builder.build();
    }
}























