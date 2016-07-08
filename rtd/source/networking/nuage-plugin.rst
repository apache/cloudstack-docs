.. Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information#
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.


The Nuage VSP Plugin
====================

Introduction
------------

The Nuage VSP plugin is the Nuage Networks SDN
implementation in CloudStack, which integrates with Release 3.2 of the
Nuage Networks Virtualized Services Platform.
The plugin can be used by CloudStack to leverage the scalability and rich features of Advanced SDN and to implement:

* Isolated Guest Networks
* Virtual Private Clouds (VPCs)
* Shared Networks

For more information about Nuage Networks, visit www.nuagenetworks.net.


Features
--------

The following table lists the CloudStack network services provided by
the Nuage VSP Plugin.

.. cssclass:: table-striped table-bordered table-hover

+----------------------+----------------------+
| Network Service      | CloudStack version   |
+======================+======================+
| Virtual Networking   | >= 4.5               |
+----------------------+----------------------+
| VPC                  | >= 4.5               |
+----------------------+----------------------+
| Source NAT           | >= 4.5               |
+----------------------+----------------------+
| Static NAT           | >= 4.5               |
+----------------------+----------------------+
| Firewall             | >= 4.5               |
+----------------------+----------------------+
| Network ACL          | >= 4.5               |
+----------------------+----------------------+
| User Data (*)        | >= 4.7               |
+----------------------+----------------------+

(*) Through the use of VR Provider

Table: Supported Services

.. note::
   The Virtual Networking service was originally called 'Connectivity'
   in CloudStack 4.0

The following hypervisors are supported by the Nuage VSP Plugin.

.. cssclass:: table-striped table-bordered table-hover

+--------------+----------------------+
| Hypervisor   | CloudStack version   |
+==============+======================+
| VmWare ESXi  | >= 4.5               |
+--------------+----------------------+
| KVM          | >= 4.7               |
+--------------+----------------------+

Table: Supported Hypervisors


Configuring the Nuage-VSP Plugin
--------------------------------

Prerequisites
~~~~~~~~~~~~~

Before building and using the Nuage plugin for ACS 4.7, verify that the platform you intend to use is supported.

.. Note:: Only the release notes for Nuage VSP contain the most up-to-date information on supported versions. Please check them to verify that the information below is current.

Supported Versions
~~~~~~~~~~~~~~~~~~

* Nuage VSP 3.2
* Apache CloudStack 4.7
* KVM on Enterprise Linux 7.x

Required VSD Configuration
~~~~~~~~~~~~~~~~~~~~~~~~~~

When configuring Nuage VSP as the network service provider, Nuage VSD must be added as a CSP user, and this user must be added to the CMS group. See `Enabling the Service Provider`_.

Zone Configuration
~~~~~~~~~~~~~~~~~~

Select VSP Isolation Method During Zone Creation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The Nuage VSP solution is NOT supported in Basic zone provisioning mode. 

1. When adding a zone, the ACS administrator should select **Advanced** mode in the zone wizard. 
2. When laying out the physical network configuration during zone provisioning, the **Guest** network traffic should be put in a separate physical network of its own.
3. This physical network carrying the **Guest** traffic should have **VSP** as the **Isolation Method**.


Update Traffic Labels
~~~~~~~~~~~~~~~~~~~~~

**Guest Traffic Type**

Select **Edit** on the **Guest** traffic type panel and update the Traffic Label: for KVM, use **alubr0** as the **KVM Traffic Label**.

Enabling the Service Provider
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Nuage VSP must be added to ACS as a Network Service Provider before it can be used. 

:Step 1: Select **Infrastructure > Zone > [zone name] > Physical Network 2 > Configure Network Service Providers > Nuage Vsp > +**, which brings up the **Add Nuage Virtualized Services Directory (VSD)** panel. 

:Step 2: Enter the VSD **Host Name**, **Username** and **Password** that was previously created. 

:Step 3: Specify the VSD API version by entering the API version in the appropriate field (format: ``v3_2``).

:Step 4: *EITHER* Add **Nuage VSD** and click the **OK** button,

         *OR* use API calls to configure Nuage VSP as the Network Provider; see `Nuage VSD API`_ in the Appendix of the current document.

:Step 5: Go to **Infrastructure > Zones > [zone name] > Physical Network 2 > Network Service Providers > Nuage Vsp > Devices > Details** tab as shown in the figure "Enabling Nuage VSP" below. This indicates the state of Nuage VSP. Enable Nuage VSP by clicking **Enable**.

:Step 6: (Optional) View the Nuage VSP status on the list of Network Service Providers on the **Infrastructure > Zones > [zone name] > Physical Network 2 > Network Service Providers** page;

Network Offerings
~~~~~~~~~~~~~~~~~

There are two types of Network Offerings that can be created:

-  If Isolated Networks are required, then create a network offering for use with Isolated Networks.
-  If VPC deployments are required, then create a new network offering for that.

Create and Enable Isolated Network Offering
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1. Select **Service Offerings > Select Offering: Network Offerings > Add network offering**.

2. In the **Supported Services** field select each of the following services - DHCP, Firewall, Source NAT, Static NAT, Virtual Networking and select Nuage VSP as the Provider.

3. If User Data service is desired in an Isolated Network, choose **VirtualRouter** as the User Data provider. **Per Zone** MUST be selected for the Source NAT Type for the Source NAT service.

4. Click OK to create the offering. 

5. After the offering has been successfully created, enable it from the Service Offerings list.

Create and Enable VPC Network Offering
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1. Select **Service Offerings > Select Offering**: **Network Offerings > Add network offering**.

2. Select the **VPC checkbox**. In the Supported Services field, select each of the following services and then select Nuage VSP as the Provider.

   *	DHCP
   *	Source NAT
   *  Static NAT
   *	Virtual Networking 

3. (Optional) Select **VpcVirtualRouter** as the UserData provider if password reset or metadata feature is desired.

4. (Optional) If network ACL is required, select **NuageVsp** as the network ACL provider. 

   a) Ensure the *Persistent* checkbox is selected.
   b) As the *Supported Source NAT Type*, select **Per Zone**.

5.  After the offering has been successfully created, enable it from the Service Offerings list.

Dedicated Features That Come with Nuage VSP Plugin
--------------------------------------------------

Domain Template Support for CloudStack in VSP
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Overview
~~~~~~~~

VSP's CloudStack plugin can be configured to use a VSD template when instantiating domains. The parameters and abstractions contained in the template are reused every time a new domain instance is created in CloudStack, and thus all the constructs defined in the template are available to the domain. 

Configuration
~~~~~~~~~~~~~

Details of the global variables that have been added to support domain templates are listed below: 

:nuagevsp.isolatedntwk.domaintemplate.name: (Type: string) Name of the template to use for creation of domains for isolated networks.

:nuagevsp.vpc.domaintemplate.name: (Type: boolean) Name of the template to use for creation of domains for VPC.

To configure a domain template for use by CloudStack, use VSD to create a domain template, using the global CloudStack parameters listed above.

.. Note:: There will be only a single domain instance for ``nuagevsp.vpc.domaintemplate.name``.

Networks created in CloudStack will then use domain instances created from the template to which the name points.

Appendix
--------
Nuage VSD API
~~~~~~~~~~~~~

To add Nuage VSP as Network Service Provider, 

1.  Add the specified network service provider:

::

        cloudmonkey add networkserviceprovider name=NuageVsp physicalnetworkid=<physicalNetworkId>

2.  Add the specified Nuage VSD:

::

    cloudmonkey add nuagevspdevice physicalnetworkid=<physicalNetworkId> hostname=<hostnameOfNuageVsp> username=<usernameOfNuageVspUser> password=<passwordOfNuageVspUser> port=<portUsedByNuageVsp> apiversion=<apiVersionOfNuageVsp> retrycount=<nrOfRetriesOnFailure> retryinterval=<intervalBetweenRetries>
