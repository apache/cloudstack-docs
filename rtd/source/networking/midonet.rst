The MidoNet Plugin
==================

Introduction to the MidoNet Plugin
----------------------------------

The MidoNet plugin allows CloudStack to use the MidoNet virtualized
networking solution as a provider for CloudStack networks and services. For
more information on MidoNet and how it works, see
http://www.midokura.com/midonet/.

Features of the MidoNet Plugin
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. note::    In CloudStack 4.2.0 only the KVM hypervisor is supported for use in
    combination with MidoNet.

In CloudStack release 4.2.0 this plugin supports several services in the
Advanced Isolated network mode.

When tenants create new isolated layer 3 networks, instead of spinning
up extra Virtual Router VMs, the relevant L3 elements (routers etc) are
created in the MidoNet virtual topology by making the appropriate calls
to the MidoNet API. Instead of using VLANs, isolation is provided by
MidoNet.

Aside from the above service (Connectivity), several extra features are
supported in the 4.2.0 release:

-  DHCP

-  Firewall (ingress)

-  Source NAT

-  Static NAT

-  Port Forwarding

The plugin has been tested with MidoNet version 12.12. (Caddo).

Using the MidoNet Plugin
------------------------

Prerequisites
~~~~~~~~~~~~~

In order to use the MidoNet plugin, the compute hosts must be running
the MidoNet Agent, and the MidoNet API server must be available. Please
consult the MidoNet User Guide for more information. The following
section describes the CloudStack side setup.

1. CloudStack needs to have at least one physical network with the
   isolation method set to "MIDO". This network should be enabled for
   the Guest and Public traffic types.

2. Next, we need to set the following CloudStack settings under "Global
   Settings" in the UI:

   +-----------------------------+------------------------------------------------------------------------+--------------------------------------------+
   | Setting Name                | Description                                                            | Example                                    |
   +=============================+========================================================================+============================================+
   | midonet.apiserver.address   | Specify the address at which the Midonet API server can be contacted   | http://192.168.1.144:8081/midolmanj-mgmt   |
   +-----------------------------+------------------------------------------------------------------------+--------------------------------------------+
   | midonet.providerrouter.id   | Specifies the UUID of the Midonet provider router                      | d7c5e6a3-e2f4-426b-b728-b7ce6a0448e5       |
   +-----------------------------+------------------------------------------------------------------------+--------------------------------------------+

   Table: CloudStack settings

3. We also want MidoNet to take care of public traffic, so in
   *componentContext.xml* we need to replace this line:

   ::

       <bean id="PublicNetworkGuru" class="com.cloud.network.guru.PublicNetworkGuru">
         

   With this:

   ::

       <bean id="PublicNetworkGuru" class="com.cloud.network.guru.MidoNetPublicNetworkGuru">
         

.. note::    On the compute host, MidoNet takes advantage of per-traffic type VIF
    driver support in CloudStack KVM.

    In agent.properties, we set the following to make MidoNet take care
    of Guest and Public traffic:

    ::

        libvirt.vif.driver.Guest=com.cloud.network.resource.MidoNetVifDriver
        libvirt.vif.driver.Public=com.cloud.network.resource.MidoNetVifDriver

    This is explained further in MidoNet User Guide.

Enabling the MidoNet service provider via the UI
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To allow CloudStack to use the MidoNet Plugin the network service provider
needs to be enabled on the physical network.

The steps to enable via the UI are as follows:

1. In the left navbar, click Infrastructure

2. In Zones, click View All

3. Click the name of the Zone on which you are setting up MidoNet

4. Click the Physical Network tab

5. Click the Name of the Network on which you are setting up MidoNet

6. Click Configure on the Network Service Providers box

7. Click on the name MidoNet

8. Click the Enable Provider button in the Network tab

Enabling the MidoNet service provider via the API
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To enable via the API, use the following API calls:

*addNetworkServiceProvider*

-  name = "MidoNet"

-  physicalnetworkid = <the uuid of the physical network>

*updateNetworkServiceProvider*

-  id = <the provider uuid returned by the previous call>

-  state = "Enabled"

Revision History
----------------

0-0 Wed Mar 13 2013 Dave Cahill dcahill@midokura.com Documentation
created for 4.2.0 version of the MidoNet Plugin
