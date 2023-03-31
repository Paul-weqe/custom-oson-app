package org.weqe.app.virtualelements;

import org.onosproject.net.Annotations;
import org.onosproject.net.DefaultPort;
import org.onosproject.net.Element;
import org.onosproject.net.PortNumber;

public class VirtualPort extends DefaultPort {
    public VirtualPort(Element element, PortNumber number, Annotations... annotations) {
        super(element, number, true, annotations);
    }
}
