/*
 * Copyright 2023-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.weqe.app;

import org.onlab.packet.*;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.DeviceId;
import org.onosproject.net.device.DeviceEvent;
import org.onosproject.net.device.DeviceListener;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.flow.FlowRule;
import org.onosproject.net.flow.FlowRuleEvent;
import org.onosproject.net.flow.FlowRuleListener;
import org.onosproject.net.host.HostEvent;
import org.onosproject.net.host.HostListener;
import org.onosproject.net.host.HostService;
import org.onosproject.net.packet.PacketContext;
import org.onosproject.net.packet.PacketProcessor;
import org.onosproject.net.packet.PacketService;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shaded.org.apache.http.Header;

/**
 * Skeletal ONOS application component.
 */
@Component(immediate = true)
public class AppComponent implements SomeInterface {

    private static Logger log = LoggerFactory.getLogger(AppComponent.class);
    private final PacketProcessor packetProcessor = new HttpPacketProcessor();
    private ApplicationId appId;
    private static final int PRIORITY = 128;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected PacketService packetService;

    @Activate
    public void activate(){
        appId = coreService.registerApplication("org.weqe.app",
                () -> log.info("Coming up comrade"));
        packetService.addProcessor(packetProcessor, PRIORITY);

        log.info("foo app started");
    }

    @Deactivate
    public void deactivate(){
        packetService.removeProcessor(packetProcessor);
        log.info("foo app stopped");
    }

    private static class HttpPacketProcessor implements PacketProcessor {
        @Override
        public void process(PacketContext context) {

            Ethernet eth = context.inPacket().parsed();


            if (
                    eth.getEtherType() == Ethernet.TYPE_IPV4 &&
                    ((IPv4) eth.getPayload()).getProtocol() == IPv4.PROTOCOL_TCP
            ){

                System.out.println("..............");
                IPv4 payload = (IPv4) eth.getPayload();
                String ipAddress = IPv4.fromIPv4Address(payload.getSourceAddress());
                IPacket pkt = eth.getPayload();
                System.out.println(((TCP) pkt.getPayload()).getSourcePort());

            }
        }
    }

    private class InternalFlowListener implements FlowRuleListener {
        @Override
        public void event(FlowRuleEvent event) {
            FlowRule flowRule = event.subject();
            if (event.type() == FlowRuleEvent.Type.RULE_ADDED && flowRule.appId() == appId.id()){
                log.info("A FLOW HAS BEEN ADDED");
            }
        }
    }

    @Override
    public void someMethod() {
        System.out.println("This is done");
    }
}
