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


Apache CloudStack Administration Guide
======================================

Backups
-------


Monitoring
----------


SNMP
----

CloudStack will send alerts for a number of 


Syslog
------


AMQP
----


JMX
---


API Queries
-----------


Usage
-----


Tuning
------


Configuration Parameters
------------------------


System Reliability and Availability
-----------------------------------


HA for Management Server
------------------------

The CloudStack Management Server should be deployed in a multi-node 
configuration such that it is not susceptible to individual server failures. 
The Management Server itself (as distinct from the MySQL database) is 
stateless and may be placed behind a load balancer.

Normal operation of Hosts is not impacted by an outage of all Management 
Serves. All guest VMs will continue to work.

When the Management Server is down, no new VMs can be created, and the end 
user and admin UI, API, dynamic load distribution, and HA will cease to work.


Management Server Load Balancing
--------------------------------

CloudStack can use a load balancer to provide a virtual IP for multiple 
Management Servers. The administrator is responsible for creating the load 
balancer rules for the Management Servers. The application requires 
persistence or stickiness across multiple sessions. The following chart lists 
the ports that should be load balanced and whether or not persistence is 
required.

============ ======================== ============== ======================
Source port   Destination port        Protocol       Persistence Required?
============ ======================== ============== ======================
80 or 443    8080 (or 20400 with AJP) HTTP (or AJP)  Yes
8250         8250                     TCP            Yes
============ ======================== ============== ======================

In addition to above settings, the administrator is responsible for setting 
the 'host' global config value from the management server IP to load balancer 
virtual IP address. If the 'host' value is not set to the VIP for Port 8250 
and one of your management servers crashes, the UI is still available but the 
system VMs will not be able to contact the management server.


Limiting the Rate of API Requests
---------------------------------

You can limit the rate at which API requests can be placed for each account. 
This is useful to avoid malicious attacks on the Management Server, prevent 
performance degradation, and provide fairness to all accounts.

If the number of API calls exceeds the threshold, an error message is returned 
for any additional API calls. The caller will have to retry these API calls at 
another time.


Configuring the API Request Rate
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To control the API request rate, use the following global configuration 
settings:

-  api.throttling.enabled - Enable/Disable API throttling. By default, this 
   setting is false, so API throttling is not enabled.

-  api.throttling.interval (in seconds) - Time interval during which the 
   number of API requests is to be counted. When the interval has passed, the 
   API count is reset to 0.

-  api.throttling.max - Maximum number of APIs that can be placed within the 
   api.throttling.interval period.

-  api.throttling.cachesize - Cache size for storing API counters. Use a value 
   higher than the total number of accounts managed by the cloud. One cache 
   entry is needed for each account, to store the running API total for that 
   account.


Limitations on API Throttling
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The following limitations exist in the current implementation of this feature:

-  In a deployment with multiple Management Servers, the cache is not 
   synchronized across them. In this case, CloudStack might not be able to 
   ensure that only the exact desired number of API requests are allowed. In 
   the worst case, the number of API calls that might be allowed is ``(number 
   of Management Servers) * (api.throttling.max)``.

-  The API commands resetApiLimit and getApiLimit are limited to the 
   Management Server where the API is invoked.


HA-Enabled Virtual Machines
---------------------------

The user can specify a virtual machine as HA-enabled. By default, all virtual 
router VMs and Elastic Load Balancing VMs are automatically configured as 
HA-enabled. When an HA-enabled VM crashes, CloudStack detects the crash and 
restarts the VM automatically within the same Availability Zone. HA is never 
performed across different Availability Zones. CloudStack has a conservative 
policy towards restarting VMs and ensures that there will never be two 
instances of the same VM running at the same time. The Management Server 
attempts to start the VM on another Host in the same cluster.

VM HA is not supported when the VM is using local storage. 


Dedicated HA Hosts
~~~~~~~~~~~~~~~~~~

One or more hosts can be designated for use only by HA-enabled VMs that are 
restarting due to a host failure. Setting up a pool of such dedicated HA hosts 
as the recovery destination for all HA-enabled VMs is useful to:

#. Make it easier to determine which VMs have been restarted as part of the 
   CloudStack high-availability function. If a VM is running on a dedicated HA 
   host, then it must be an HA-enabled VM whose original host failed. (With 
   one exception: It is possible for an administrator to manually migrate any 
   VM to a dedicated HA host.).

#. Keep HA-enabled VMs from restarting on hosts which may be reserved for 
   other purposes.

The dedicated HA option is set through a special host tag when the host is 
created. To allow the administrator to dedicate hosts to only HA-enabled VMs, 
set the global configuration variable ha.tag to the desired tag (for example, 
"ha_host"), and restart the Management Server. Enter the value in the Host 
Tags field when adding the host(s) that you want to dedicate to HA-enabled VMs.


Primary Storage Outage and Data Loss
------------------------------------

When a primary storage outage occurs the hypervisor immediately stops all VMs 
stored on that storage device. Guests that are marked for HA will be restarted 
as soon as practical when the primary storage comes back on line. With NFS, 
the hypervisor may allow the virtual machines to continue running depending on 
the nature of the issue. For example, an NFS hang will cause the guest VMs to 
be suspended until storage connectivity is restored.Primary storage is not 
designed to be backed up. Individual volumes in primary storage can be backed 
up using snapshots.


Secondary Storage Outage and Data Loss
--------------------------------------

For a Zone that has only one secondary storage server, a secondary storage 
outage will have feature level impact to the system but will not impact 
running guest VMs. It may become impossible to create a VM with the selected 
template for a user. A user may also not be able to save snapshots or 
examine/restore saved snapshots. These features will automatically be 
available when the secondary storage comes back online.

Secondary storage data loss will impact recently added user data including 
templates, snapshots, and ISO images. Secondary storage should be backed up 
periodically. Multiple secondary storage servers can be provisioned within 
each zone to increase the scalability of the system.


Managing System VMs
-------------------

CloudStack uses several types of system virtual machines to perform tasks in 
the cloud. In general CloudStack manages these system VMs and creates, starts, 
and stops them as needed based on scale and immediate needs. However, the 
administrator should be aware of them and their roles to assist in debugging 
issues.

You can configure the system.vm.random.password parameter to create a random 
system VM password to ensure higher security. If you reset the value for 
system.vm.random.password to true and restart the Management Server, a random 
password is generated and stored encrypted in the database. You can view the 
decrypted password under the system.vm.password global parameter on the 
CloudStack UI or by calling the listConfigurations API.


The System VM Template
----------------------

The System VMs come from a single template. The System VM has the following 
characteristics:

-  Debian 6.0 ("Squeeze"), 2.6.32 kernel with the latest security patches from 
   the Debian security APT repository

-  Has a minimal set of packages installed thereby reducing the attack surface

-  32-bit for enhanced performance on Xen/VMWare

-  pvops kernel with Xen PV drivers, KVM virtio drivers, and VMware tools for 
   optimum performance on all hypervisors

-  Xen tools inclusion allows performance monitoring

-  Latest versions of HAProxy, iptables, IPsec, and Apache from debian 
   repository ensures improved security and speed

-  Latest version of JRE from Sun/Oracle ensures improved security and speed


Accessing System VMs
--------------------

It may sometimes be necessary to access System VMs for diagnostics of certain 
issues, for example if you are experiencing SSVM (Secondary Storage VM) 
connection issues. Use the steps below in order to connect to the SSH console 
of a running System VM.

Accessing System VMs over the network requires the use of private keys and 
connecting to System VMs SSH Daemon on port 3922. XenServer/KVM Hypervisors 
store this key at /root/.ssh/id_rsa.cloud on each CloudStack agent. To access 
System VMs running on ESXi, the key is stored on the management server at 
/var/lib/cloudstack/management/.ssh/id_rsa.


#. Find the details of the System VM

   #. Log in with admin privileges to the CloudStack UI.

   #. Click Infrastructure, then System VMs, and then click the name of a 
      running VM.

   #. Take a note of the 'Host', 'Private IP Address' and 'Link Local IP 
      Address' of the System VM you wish to access.

#. XenServer/KVM Hypervisors

   #. Connect to the Host of which the System VM is running.

   #. SSH to the 'Link Local IP Address' of the System VM from the Host on 
      which the VM is running.

      Format: ssh -i <path-to-private-key> <link-local-ip> -p 3922

      Example: root@faith:~# ssh -i /root/.ssh/id_rsa.cloud 169.254.3.93 -p 3922

#. ESXi Hypervisors

   #. Connect to your CloudStack Management Server.

   #. ESXi users should SSH to the private IP address of the System VM.

      Format: ssh -i <path-to-private-key> <vm-private-ip> -p 3922

      Example: root@management:~# ssh -i /var/lib/cloudstack/management/.ssh/id_rsa 172.16.0.250 -p 3922


Multiple System VM Support for VMware
-------------------------------------

Every CloudStack zone has single System VM for template processing tasks such 
as downloading templates, uploading templates, and uploading ISOs. In a zone 
where VMware is being used, additional System VMs can be launched to process 
VMware-specific tasks such as taking snapshots and creating private templates. 
The CloudStack management server launches additional System VMs for 
VMware-specific tasks as the load increases. The management server monitors 
and weights all commands sent to these System VMs and performs dynamic load 
balancing and scaling-up of more System VMs.


Console Proxy
-------------

The Console Proxy is a type of System Virtual Machine that has a role in 
presenting a console view via the web UI. It connects the user’s browser to 
the VNC port made available via the hypervisor for the console of the guest. 
Both the administrator and end user web UIs offer a console connection.

Clicking a console icon brings up a new window. The AJAX code downloaded into 
that window refers to the public IP address of a console proxy VM. There is 
exactly one public IP address allocated per console proxy VM. The AJAX 
application connects to this IP. The console proxy then proxies the connection 
to the VNC port for the requested VM on the Host hosting the guest.

The console proxy VM will periodically report its active session count to the 
Management Server. The default reporting interval is five seconds. This can be 
changed through standard Management Server configuration with the parameter 
consoleproxy.loadscan.interval.

Assignment of guest VM to console proxy is determined by first determining if 
the guest VM has a previous session associated with a console proxy. If it 
does, the Management Server will assign the guest VM to the target Console 
Proxy VM regardless of the load on the proxy VM. Failing that, the first 
available running Console Proxy VM that has the capacity to handle new 
sessions is used.

Console proxies can be restarted by administrators but this will interrupt 
existing console sessions for users.


Using a SSL Certificate for the Console Proxy
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The console viewing functionality uses a dynamic DNS service under the domain 
name realhostip.com to assist in providing SSL security to console sessions. 
The console proxy is assigned a public IP address. In order to avoid browser 
warnings for mismatched SSL certificates, the URL for the new console window 
is set to the form of https://aaa-bbb-ccc-ddd.realhostip.com. You will see 
this URL during console session creation. CloudStack includes the 
realhostip.com SSL certificate in the console proxy VM. Of course, CloudStack 
cannot know about the DNS A records for our customers' public IPs prior to 
shipping the software. CloudStack therefore runs a dynamic DNS server that is 
authoritative for the realhostip.com domain. It maps the aaa-bbb-ccc-ddd part 
of the DNS name to the IP address aaa.bbb.ccc.ddd on lookups. This allows the 
browser to correctly connect to the console proxy's public IP, where it then 
expects and receives a SSL certificate for realhostip.com, and SSL is set up 
without browser warnings.


Changing the Console Proxy SSL Certificate and Domain
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

If the administrator prefers, it is possible for the URL of the customer's 
console session to show a domain other than realhostip.com. The administrator 
can customize the displayed domain by selecting a different domain and 
uploading a new SSL certificate and private key. The domain must run a DNS 
service that is capable of resolving queries for addresses of the form 
aaa-bbb-ccc-ddd.your.domain to an IPv4 IP address in the form aaa.bbb.ccc.ddd, 
for example, 202.8.44.1. To change the console proxy domain, SSL certificate, 
and private key:

#. Set up dynamic name resolution or populate all possible DNS names in your 
   public IP range into your existing DNS server with the format 
   aaa-bbb-ccc-ddd.company.com -> aaa.bbb.ccc.ddd.

#. Generate the private key and certificate signing request (CSR). When you 
   are using openssl to generate private/public key pairs and CSRs, for the 
   private key that you are going to paste into the CloudStack UI, be sure to 
   convert it into PKCS#8 format.
   
   #. Generate a new 2048-bit private key

      ::

         openssl genrsa -des3 -out yourprivate.key 2048

   #. Generate a new certificate CSR

      ::

         openssl req -new -key yourprivate.key -out yourcertificate.csr

   #. Head to the website of your favorite trusted Certificate Authority, 
      purchase an SSL certificate, and submit the CSR. You should receive a 
      valid certificate in return
   
   #. Convert your private key format into PKCS#8 encrypted format.

      ::

         openssl pkcs8 -topk8 -in yourprivate.key -out yourprivate.pkcs8.encrypted.key

   #. Convert your PKCS#8 encrypted private key into the PKCS#8 format that is 
      compliant with CloudStack

      ::

         openssl pkcs8 -in yourprivate.pkcs8.encrypted.key -out yourprivate.pkcs8.key

#. In the Update SSL Certificate screen of the CloudStack UI, paste the following:

   -  The certificate you've just generated.
   
   -  The private key you've just generated.
   
   -  The desired new domain name; for example, company.com

#. The desired new domain name; for example, company.com
   This stops all currently running console proxy VMs, then restarts them with 
   the new certificate and key. Users might notice a brief interruption in 
   console availability.

The Management Server generates URLs of the form "aaa-bbb-ccc-ddd.company.com" 
after this change is made. The new console requests will be served with the 
new DNS domain name, certificate, and key.


Virtual Router
--------------

The virtual router is a type of System Virtual Machine. The virtual router is 
one of the most frequently used service providers in CloudStack. The end user 
has no direct access to the virtual router. Users can ping the virtual router 
and take actions that affect it (such as setting up port forwarding), but 
users do not have SSH access into the virtual router.

Virtual routers can be restarted by administrators, but this will interrupt 
public network access and other services for end users. A basic test in 
debugging networking issues is to attempt to ping the virtual router from a 
guest VM. Some of the characteristics of the virtual router are determined by 
its associated system service offering..


Configuring the Virtual Router
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

You can set the following:

-  IP range

-  Supported network services

-  Default domain name for the network serviced by the virtual router

-  Gateway IP address

-  How often CloudStack fetches network usage statistics from CloudStack 
   virtual routers. If you want to collect traffic metering data from the 
   virtual router, set the global configuration parameter 
   router.stats.interval. If you are not using the virtual router to gather 
   network usage statistics, set it to 0.


Upgrading a Virtual Router with System Service Offerings
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

When CloudStack creates a virtual router, it uses default settings which are 
defined in a default system service offering. See Section 8.2, “System Service 
Offerings”. All the virtual routers in a single guest network use the same 
system service offering. You can upgrade the capabilities of the virtual 
router by creating and applying a custom system service offering.
Define your custom system service offering.

Associate the system service offering with a network offering.
Apply the network offering to the network where you want the virtual routers 
to use the new system service offering.


Best Practices for Virtual Routers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

-  Restarting a virtual router from a hypervisor console deletes all the 
   iptables rules. To work around this issue, stop the virtual router and 
   start it from the CloudStack UI.

-  Do not use the destroyRouter API when only one router is available in the 
   network, because restartNetwork API with the cleanup=false parameter can't 
   recreate it later. If you want to destroy and recreate the single router 
   available in the network, use the restartNetwork API with the cleanup=true 
   parameter.


Secondary Storage VM
--------------------

In addition to the hosts, CloudStack’s Secondary Storage VM mounts and writes 
to secondary storage. Submissions to secondary storage go through the 
Secondary Storage VM. The Secondary Storage VM can retrieve templates and ISO 
images from URLs using a variety of protocols. The secondary storage VM 
provides a background task that takes care of a variety of secondary storage 
activities: downloading a new template to a Zone, copying templates between 
Zones, and snapshot backups. The administrator can log in to the secondary 
storage VM if needed.


Storage Administration
----------------------


Hypervisor Host Management
--------------------------


Maintenance mode
----------------

Maintenance mode makes a host unavailable to have new virtual machines 
allocated to it. It also starts a process by which running virtual machines 
are live migrated to other available hosts within the same cluster. It should 
be noted that the live migration is not universally perfect, and you may end 
up with recalcitrant virtual machines which are unable to be live migrated. 
This can be due to lack of hypervisor-specific tooling or other problems. 


vCenter and Maintenance mode
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

To enter maintenance mode on a vCenter host, both vCenter and CloudStack must 
be used in concert. CloudStack and vCenter have separate maintenance modes 
that work closely together.

#. Place the host into CloudStack's "scheduled maintenance" mode. This does 
   not invoke the vCenter maintenance mode, but only causes VMs to be migrated 
   off the host When the CloudStack maintenance mode is requested, the host 
   first moves into the Prepare for Maintenance state. In this state it cannot 
   be the target of new guest VM starts. Then all VMs will be migrated off the 
   server. Live migration will be used to move VMs off the host. This allows 
   the guests to be migrated to other hosts with no disruption to the guests. 
   After this migration is completed, the host will enter the Ready for 
   Maintenance mode.

#. Wait for the "Ready for Maintenance" indicator to appear in the UI.

#. Now use vCenter to perform whatever actions are necessary to maintain the 
   host. During this time, the host cannot be the target of new VM allocations.

#. When the maintenance tasks are complete, take the host out of maintenance 
   mode as follows:

   #. First use vCenter to exit the vCenter maintenance mode. This makes the 
      host ready for CloudStack to reactivate it.

   #. Then use CloudStack's administrator UI to cancel the CloudStack 
      maintenance mode When the host comes back online, the VMs that were 
      migrated off of it may be migrated back to it manually and new VMs can 
      be added.


XenServer Maintenance Mode
~~~~~~~~~~~~~~~~~~~~~~~~~~

XenServer, you can take a server offline temporarily by using the Maintenance 
Mode feature in XenCenter. When you place a server into Maintenance Mode, all 
running VMs are automatically migrated from it to another host in the same 
pool. If the server is the pool master, a new master will also be selected for 
the pool. While a server is Maintenance Mode, you cannot create or start any 
VMs on it.


To place a XenServer host in Maintenace Mode
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

#. In the Resources pane, select the server, then do one of the following:

   -  Right-click, then click Enter Maintenance Mode on the shortcut menu.

   -  On the Server menu, click Enter Maintenance Mode.

#. Click Enter Maintenance Mode.

The server's status in the Resources pane shows when all running VMs have been 
successfully migrated off the server.


To take a Xenserver host out of Maintenance mode
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

#. In the Resources pane, select the server, then do one of the following:

   -  Right-click, then click Exit Maintenance Mode on the shortcut menu.

   -  On the Server menu, click Exit Maintenance Mode.

#. Click Exit Maintenance Mode.


Disabling and enabling Zones, Pods, and Clusters
------------------------------------------------

You can enable or disable a zone, pod, or cluster without permanently removing 
it from the cloud. This is useful for maintenance or when there are problems 
that make a portion of the cloud infrastructure unreliable. No new allocations 
will be made to a disabled zone, pod, or cluster until its state is returned 
to Enabled. When a zone, pod, or cluster is first added to the cloud, it is 
Disabled by default.

To disable and enable a zone, pod, or cluster:

#. Log in to the CloudStack UI as administrator

#. In the left navigation bar, click Infrastructure.

#. In Zones, click View More.

#. If you are disabling or enabling a zone, find the name of the zone in the 
   list, and click the Enable/Disable button.  

#. If you are disabling or enabling a pod or cluster, click the name of the 
   zone that contains the pod or cluster.

#. Click the Compute tab.

#. In the Pods or Clusters node of the diagram, click View All.

#. Click the pod or cluster name in the list.

#. Click the Enable/Disable button.  


Removing hypervisor hosts
-------------------------

Hosts can be removed from the cloud as needed. The procedure to remove a host 
depends on the hypervisor type.


Removing XenServer and KVM Hosts
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
A node cannot be removed from a cluster until it has been placed in 
maintenance mode. This will ensure that all of the VMs on it have been 
migrated to other Hosts. To remove a Host from CloudStack:

#. Place the node in maintenance mode.

#. For KVM, stop the cloud-agent service.

#. Use the UI option to remove the node.

#. Then you may power down the Host, re-use its IP address, re-install it, etc


Removing vSphere Hosts
~~~~~~~~~~~~~~~~~~~~~~
To remove this type of host, first place it in maintenance mode, as described 
above. Then use CloudStack to remove the host. CloudStack will not direct 
commands to a host that has been removed using CloudStack. However, the host 
may still exist in the vCenter cluster.


Changing hypervisor host password
---------------------------------
The password for a XenServer Node, KVM Node, or vSphere Node may be changed in 
the database. Note that all Nodes in a Cluster must have the same password.

To change a hosts password:

#. Identify all hosts in the cluster.

#. Change the password on all hosts in the cluster. Now the password for the 
   host and the password known to CloudStack will not match. Operations on the 
   cluster will fail until the two passwords match.

#. Get the list of host IDs for the host in the cluster where you are changing 
   the password. You will need to access the database to determine these host 
   IDs. For each hostname "h" (or vSphere cluster) that you are changing the 
   password for, execute:

   ::
 
      mysql> select id from cloud.host where name like '%h%';

#. Update the passwords for the host in the database. In this example, we 
   change the passwords for hosts with IDs 5, 10, and 12 to "password".

   :: 

      mysql> update cloud.host set password='password' where id=5 or id=10 or id=12;


Overprovisioning and Service Offering Limits
--------------------------------------------

CPU and memory (RAM) over-provisioning factors can be set for each cluster to 
change the number of VMs that can run on each host in the cluster. This helps 
optimize the use of resources. By increasing the over-provisioning ratio, more 
resource capacity will be used. If the ratio is set to 1, no over-provisioning 
is done.

The administrator can also set global default over-provisioning ratios in the 
cpu.overprovisioning.factor and mem.overprovisioning.factor global 
configuration variables. The default value of these variables is 1: 
over-provisioning is turned off by default.
Over-provisioning ratios are dynamically substituted in CloudStack's capacity 
calculations. For example:

::

   Capacity = 2 GB
   Over-provisioning factor = 2
   Capacity after over-provisioning = 4 GB
   With this configuration, suppose you deploy 3 VMs of 1 GB each:
   Used = 3 GB
   Free = 1 GB

The administrator can specify a memory over-provisioning ratio, and can 
specify both CPU and memory over-provisioning ratios on a per-cluster basis.

In any given cloud, the optimum number of VMs for each host is affected by 
such things as the hypervisor, storage, and hardware configuration. These may 
be different for each cluster in the same cloud. A single global 
over-provisioning setting can not provide the best utilization for all the 
different clusters in the cloud. It has to be set for the lowest common 
denominator. The per-cluster setting provides a finer granularity for better 
utilization of resources, no matter where the CloudStack placement algorithm 
decides to place a VM.

The overprovisioning settings can be used along with dedicated resources 
(assigning a specific cluster to an account) to effectively offer different 
levels of service to different accounts. For example, an account paying for a 
more expensive level of service could be assigned to a dedicated cluster with 
an over-provisioning ratio of 1, and a lower-paying account to a cluster with 
a ratio of 2.

When a new host is added to a cluster, CloudStack will assume the host has the 
capability to perform the CPU and RAM over-provisioning which is configured 
for that cluster. It is up to the administrator to be sure the host is 
actually suitable for the level of over-provisioning which has been set.


Limitations on over-provisioning in KVM and XenServer
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

In XenServer, due to a constraint of this hypervisor, you can not use an 
over-provisioning factor greater than 4.

KVM can not manage memory allocation to VMs dynamically. CloudStack sets the 
minimum and maximum amount of memory that a VM can use. The hypervisor adjusts 
the memory within the set limits based on the memory contention.


Requirements for Over-Provisioning
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Several prerequisites are required in order for over-provisioning to function 
properly. The feature is dependent on the OS type, hypervisor capabilities, 
and certain scripts. It is the administrator's responsibility to ensure that 
these requirements are met.


Balloon Driver
^^^^^^^^^^^^^^

All VMs should have a balloon driver installed in them. The hypervisor 
communicates with the balloon driver to free up and make the memory available 
to a VM.


XenServer
'''''''''

The balloon driver can be found as a part of xen pv or PVHVM drivers. The xen 
pvhvm drivers are included in upstream linux kernels 2.6.36+.


VMware
''''''

The balloon driver can be found as a part of the VMware tools. All the VMs 
that are deployed in a over-provisioned cluster should have the VMware tools 
installed.


KVM
'''

All VMs are required to support the virtio drivers. These drivers are 
installed in all Linux kernel versions 2.6.25 and greater. The administrator 
must set CONFIG_VIRTIO_BALLOON=y in the virtio configuration.


Hypervisor capabilities
^^^^^^^^^^^^^^^^^^^^^^^

The hypervisor must be capable of using the memory ballooning.


XenServer
'''''''''

The DMC (Dynamic Memory Control) capability of the hypervisor should be 
enabled. Only XenServer Advanced and above versions have this feature.


VMware, KVM
'''''''''''

Memory ballooning is supported by default.


Setting Over-Provisioning Rations
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

There are two ways the root admin can set CPU and RAM over-provisioning ratios. 
First, the global configuration settings cpu.overprovisioning.factor and 
mem.overprovisioning.factor will be applied when a new cluster is created. 
Later, the ratios can be modified for an existing cluster.

Only VMs deployed after the change are affected by the new setting. If you 
want VMs deployed before the change to adopt the new over-provisioning ratio, 
you must stop and restart the VMs. When this is done, CloudStack recalculates 
or scales the used and reserved capacities based on the new over-provisioning 
ratios, to ensure that CloudStack is correctly tracking the amount of free 
capacity.

To change the over-provisioning ratios for an existing cluster:

#. Log in as administrator to the CloudStack UI.

#. In the left navigation bar, click Infrastructure.

#. Under Clusters, click View All.

#. Select the cluster you want to work with, and click the Edit button.

#. Fill in your desired over-provisioning multipliers in the fields CPU 
   overcommit ratio and RAM overcommit ratio. The value which is intially 
   shown in these fields is the default value inherited from the global 
   configuration settings.


Service Offering Limits and Over-Provisioning
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Service offering limits (e.g. 1 GHz, 1 core) are strictly enforced for core 
count. For example, a guest with a service offering of one core will have only 
one core available to it regardless of other activity on the Host.

Service offering limits for gigahertz are enforced only in the presence of 
contention for CPU resources. For example, suppose that a guest was created 
with a service offering of 1 GHz on a Host that has 2 GHz cores, and that 
guest is the only guest running on the Host. The guest will have the full 
2 GHz available to it. When multiple guests are attempting to use the CPU a 
weighting factor is used to schedule CPU resources. The weight is based on the 
clock speed in the service offering. Guests receive a CPU allocation that is 
proportionate to the GHz in the service offering. For example, a guest created 
from a 2 GHz service offering will receive twice the CPU allocation as a guest 
created from a 1 GHz service offering. CloudStack does not perform memory 
over-provisioning.
