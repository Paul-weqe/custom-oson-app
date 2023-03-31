package org.weqe.app.virtualelements;

import org.onosproject.net.Annotations;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.DefaultLink;
import org.onosproject.net.provider.ProviderId;

public class VirtualLink extends DefaultLink {

    /**
     * Creates an infrastructure link using the supplied information.
     *
     * @param providerId  provider identity
     * @param src         link source
     * @param dst         link destination
     * @param type        link type
     * @param state       link state
     * @param annotations optional key/value annotations
     */
    protected VirtualLink(
            ProviderId providerId, ConnectPoint src, ConnectPoint dst, Type type,
            State state, Annotations... annotations
    ) {
        super(providerId, src, dst, type, state, annotations);
    }
}
