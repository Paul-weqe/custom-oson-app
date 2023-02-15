package org.customproject.customapp;

import org.onlab.packet.Ethernet;
import org.onosproject.cfg.ComponentConfigService;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.net.flow.FlowRule;
import org.onosproject.net.flow.FlowRuleEvent;
import org.onosproject.net.flow.FlowRuleListener;
import org.onosproject.net.packet.PacketContext;
import org.onosproject.net.packet.PacketProcessor;
import org.onosproject.net.packet.PacketService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Skeletal ONOS application component.
 */
@Component(immediate = true,
           service = {SomeInterface.class},
           property = {
               "someProperty=Some Default String Value",
           })
public class AppComponent implements SomeInterface {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final int PRIORITY = 128;

    /** Some configurable property. */
    private String someProperty;
    private ApplicationId appId;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected ComponentConfigService cfgService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected PacketService packetService;

    private final PacketProcessor packetProcessor = new CustomAppProcessor();

    @Activate
    protected void activate() {
//        cfgService.registerProperties(getClass());
        appId = coreService.registerApplication("org.customproject.customapp",
                () -> log.info("CUSTOM APP ACTIVATED"));
        packetService.addProcessor(packetProcessor, PRIORITY);

        log.info("Started");
    }

    private class CustomAppProcessor implements PacketProcessor {
        @Override
        public void process(PacketContext context) {

            Ethernet eth = context.inPacket().parsed();
            log.info("RECEIVED ETH " + eth);
            System.out.println(eth.getEtherType());

            String filename = "root/custom.txt";
            File file = new File(filename);

            try {
                file.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
                writer.append(eth.toString());
                writer.append("............");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    @Deactivate
    protected void deactivate() {
        cfgService.unregisterProperties(getClass(), false);
        log.info("Stopped");
    }

    @Override
    public void someMethod() {
        log.info("Invoked");
    }



}
