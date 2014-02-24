Working with usage data
=======================

The Usage Server provides aggregated usage records which you can use to
create billing integration for the CloudStack platform. The Usage Server
works by taking data from the events log and creating summary usage
records that you can access using the listUsageRecords API call.

The usage records show the amount of resources, such as VM run time or
template storage space, consumed by guest instances. In the special case
of bare metal instances, no template storage resources are consumed, but
records showing zero usage are still included in the Usage Server's
output.

The Usage Server runs at least once per day. It can be configured to run
multiple times per day. Its behavior is controlled by configuration
settings as described in the CloudStack Administration Guide.

Usage Record Format
-------------------

Virtual Machine Usage Record Format
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

For running and allocated virtual machine usage, the following fields
exist in a usage record:

-  

   account – name of the account

-  

   accountid – ID of the account

-  

   domainid – ID of the domain in which this account resides

-  

   zoneid – Zone where the usage occurred

-  

   description – A string describing what the usage record is tracking

-  

   usage – String representation of the usage, including the units of
   usage (e.g. 'Hrs' for VM running time)

-  

   usagetype – A number representing the usage type (see Usage Types)

-  

   rawusage – A number representing the actual usage in hours

-  

   virtualMachineId – The ID of the virtual machine

-  

   name – The name of the virtual machine

-  

   offeringid – The ID of the service offering

-  

   templateid – The ID of the template or the ID of the parent template.
   The parent template value is present when the current template was
   created from a volume.

-  

   usageid – Virtual machine

-  

   type – Hypervisor

-  

   startdate, enddate – The range of time for which the usage is
   aggregated; see Dates in the Usage Record

Network Usage Record Format
~~~~~~~~~~~~~~~~~~~~~~~~~~~

For network usage (bytes sent/received), the following fields exist in a
usage record.

-  

   account – name of the account

-  

   accountid – ID of the account

-  

   domainid – ID of the domain in which this account resides

-  

   zoneid – Zone where the usage occurred

-  

   description – A string describing what the usage record is tracking

-  

   usagetype – A number representing the usage type (see Usage Types)

-  

   rawusage – A number representing the actual usage in hours

-  

   usageid – Device ID (virtual router ID or external device ID)

-  

   type – Device type (domain router, external load balancer, etc.)

-  

   startdate, enddate – The range of time for which the usage is
   aggregated; see Dates in the Usage Record

IP Address Usage Record Format
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

For IP address usage the following fields exist in a usage record.

-  

   account - name of the account

-  

   accountid - ID of the account

-  

   domainid - ID of the domain in which this account resides

-  

   zoneid - Zone where the usage occurred

-  

   description - A string describing what the usage record is tracking

-  

   usage - String representation of the usage, including the units of
   usage

-  

   usagetype - A number representing the usage type (see Usage Types)

-  

   rawusage - A number representing the actual usage in hours

-  

   usageid - IP address ID

-  

   startdate, enddate - The range of time for which the usage is
   aggregated; see Dates in the Usage Record

-  

   issourcenat - Whether source NAT is enabled for the IP address

-  

   iselastic - True if the IP address is elastic.

Disk Volume Usage Record Format
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

For disk volumes, the following fields exist in a usage record.

-  

   account – name of the account

-  

   accountid – ID of the account

-  

   domainid – ID of the domain in which this account resides

-  

   zoneid – Zone where the usage occurred

-  

   description – A string describing what the usage record is tracking

-  

   usage – String representation of the usage, including the units of
   usage (e.g. 'Hrs' for hours)

-  

   usagetype – A number representing the usage type (see Usage Types)

-  

   rawusage – A number representing the actual usage in hours

-  

   usageid – The volume ID

-  

   offeringid – The ID of the disk offering

-  

   type – Hypervisor

-  

   templateid – ROOT template ID

-  

   size – The amount of storage allocated

-  

   startdate, enddate – The range of time for which the usage is
   aggregated; see Dates in the Usage Record

Template, ISO, and Snapshot Usage Record Format
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

-  

   account – name of the account

-  

   accountid – ID of the account

-  

   domainid – ID of the domain in which this account resides

-  

   zoneid – Zone where the usage occurred

-  

   description – A string describing what the usage record is tracking

-  

   usage – String representation of the usage, including the units of
   usage (e.g. 'Hrs' for hours)

-  

   usagetype – A number representing the usage type (see Usage Types)

-  

   rawusage – A number representing the actual usage in hours

-  

   usageid – The ID of the the template, ISO, or snapshot

-  

   offeringid – The ID of the disk offering

-  

   templateid – – Included only for templates (usage type 7). Source
   template ID.

-  

   size – Size of the template, ISO, or snapshot

-  

   startdate, enddate – The range of time for which the usage is
   aggregated; see Dates in the Usage Record

Load Balancer Policy or Port Forwarding Rule Usage Record Format
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

-  

   account - name of the account

-  

   accountid - ID of the account

-  

   domainid - ID of the domain in which this account resides

-  

   zoneid - Zone where the usage occurred

-  

   description - A string describing what the usage record is tracking

-  

   usage - String representation of the usage, including the units of
   usage (e.g. 'Hrs' for hours)

-  

   usagetype - A number representing the usage type (see Usage Types)

-  

   rawusage - A number representing the actual usage in hours

-  

   usageid - ID of the load balancer policy or port forwarding rule

-  

   usagetype - A number representing the usage type (see Usage Types)

-  

   startdate, enddate - The range of time for which the usage is
   aggregated; see Dates in the Usage Record

Network Offering Usage Record Format
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

-  

   account – name of the account

-  

   accountid – ID of the account

-  

   domainid – ID of the domain in which this account resides

-  

   zoneid – Zone where the usage occurred

-  

   description – A string describing what the usage record is tracking

-  

   usage – String representation of the usage, including the units of
   usage (e.g. 'Hrs' for hours)

-  

   usagetype – A number representing the usage type (see Usage Types)

-  

   rawusage – A number representing the actual usage in hours

-  

   usageid – ID of the network offering

-  

   usagetype – A number representing the usage type (see Usage Types)

-  

   offeringid – Network offering ID

-  

   virtualMachineId – The ID of the virtual machine

-  

   virtualMachineId – The ID of the virtual machine

-  

   startdate, enddate – The range of time for which the usage is
   aggregated; see Dates in the Usage Record

VPN User Usage Record Format
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

-  

   account – name of the account

-  

   accountid – ID of the account

-  

   domainid – ID of the domain in which this account resides

-  

   zoneid – Zone where the usage occurred

-  

   description – A string describing what the usage record is tracking

-  

   usage – String representation of the usage, including the units of
   usage (e.g. 'Hrs' for hours)

-  

   usagetype – A number representing the usage type (see Usage Types)

-  

   rawusage – A number representing the actual usage in hours

-  

   usageid – VPN user ID

-  

   usagetype – A number representing the usage type (see Usage Types)

-  

   startdate, enddate – The range of time for which the usage is
   aggregated; see Dates in the Usage Record


Usage Types
-----------

The following table shows all usage types.

+------------------+-----------------------------------+-----------------------+
| Type ID          | Type Name                         | Description           |
+==================+===================================+=======================+
| 1                | RUNNING\_VM                       | Tracks the total      |
|                  |                                   | running time of a VM  |
|                  |                                   | per usage record      |
|                  |                                   | period. If the VM is  |
|                  |                                   | upgraded during the   |
|                  |                                   | usage period, you     |
|                  |                                   | will get a separate   |
|                  |                                   | Usage Record for the  |
|                  |                                   | new upgraded VM.      |
+------------------+-----------------------------------+-----------------------+
| 2                | ALLOCATED\_VM                     | Tracks the total time |
|                  |                                   | the VM has been       |
|                  |                                   | created to the time   |
|                  |                                   | when it has been      |
|                  |                                   | destroyed. This usage |
|                  |                                   | type is also useful   |
|                  |                                   | in determining usage  |
|                  |                                   | for specific          |
|                  |                                   | templates such as     |
|                  |                                   | Windows-based         |
|                  |                                   | templates.            |
+------------------+-----------------------------------+-----------------------+
| 3                | IP\_ADDRESS                       | Tracks the public IP  |
|                  |                                   | address owned by the  |
|                  |                                   | account.              |
+------------------+-----------------------------------+-----------------------+
| 4                | NETWORK\_BYTES\_SENT              | Tracks the total      |
|                  |                                   | number of bytes sent  |
|                  |                                   | by all the VMs for an |
|                  |                                   | account. Cloud.com    |
|                  |                                   | does not currently    |
|                  |                                   | track network traffic |
|                  |                                   | per VM.               |
+------------------+-----------------------------------+-----------------------+
| 5                | NETWORK\_BYTES\_RECEIVED          | Tracks the total      |
|                  |                                   | number of bytes       |
|                  |                                   | received by all the   |
|                  |                                   | VMs for an account.   |
|                  |                                   | Cloud.com does not    |
|                  |                                   | currently track       |
|                  |                                   | network traffic per   |
|                  |                                   | VM.                   |
+------------------+-----------------------------------+-----------------------+
| 6                | VOLUME                            | Tracks the total time |
|                  |                                   | a disk volume has     |
|                  |                                   | been created to the   |
|                  |                                   | time when it has been |
|                  |                                   | destroyed.            |
+------------------+-----------------------------------+-----------------------+
| 7                | TEMPLATE                          | Tracks the total time |
|                  |                                   | a template (either    |
|                  |                                   | created from a        |
|                  |                                   | snapshot or uploaded  |
|                  |                                   | to the cloud) has     |
|                  |                                   | been created to the   |
|                  |                                   | time it has been      |
|                  |                                   | destroyed. The size   |
|                  |                                   | of the template is    |
|                  |                                   | also returned.        |
+------------------+-----------------------------------+-----------------------+
| 8                | ISO                               | Tracks the total time |
|                  |                                   | an ISO has been       |
|                  |                                   | uploaded to the time  |
|                  |                                   | it has been removed   |
|                  |                                   | from the cloud. The   |
|                  |                                   | size of the ISO is    |
|                  |                                   | also returned.        |
+------------------+-----------------------------------+-----------------------+
| 9                | SNAPSHOT                          | Tracks the total time |
|                  |                                   | from when a snapshot  |
|                  |                                   | has been created to   |
|                  |                                   | the time it have been |
|                  |                                   | destroyed.            |
+------------------+-----------------------------------+-----------------------+
| 11               | LOAD\_BALANCER\_POLICY            | Tracks the total time |
|                  |                                   | a load balancer       |
|                  |                                   | policy has been       |
|                  |                                   | created to the time   |
|                  |                                   | it has been removed.  |
|                  |                                   | Cloud.com does not    |
|                  |                                   | track whether a VM    |
|                  |                                   | has been assigned to  |
|                  |                                   | a policy.             |
+------------------+-----------------------------------+-----------------------+
| 12               | PORT\_FORWARDING\_RULE            | Tracks the time from  |
|                  |                                   | when a port           |
|                  |                                   | forwarding rule was   |
|                  |                                   | created until the     |
|                  |                                   | time it was removed.  |
+------------------+-----------------------------------+-----------------------+
| 13               | NETWORK\_OFFERING                 | The time from when a  |
|                  |                                   | network offering was  |
|                  |                                   | assigned to a VM      |
|                  |                                   | until it is removed.  |
+------------------+-----------------------------------+-----------------------+
| 14               | VPN\_USERS                        | The time from when a  |
|                  |                                   | VPN user is created   |
|                  |                                   | until it is removed.  |
+------------------+-----------------------------------+-----------------------+


Example response from listUsageRecords
--------------------------------------

All CloudStack API requests are submitted in the form of a HTTP GET/POST
with an associated command and any parameters. A request is composed of
the following whether in HTTP or HTTPS:

::

                <listusagerecordsresponse>
                      <count>1816</count>
                     <usagerecord>
                        <account>user5</account>
                        <accountid>10004</accountid>
                        <domainid>1</domainid>
                        <zoneid>1</zoneid>
                            <description>i-3-4-WC running time (ServiceOffering: 1) (Template: 3)</description>
                        <usage>2.95288 Hrs</usage>
                           <usagetype>1</usagetype>
                        <rawusage>2.95288</rawusage>
                           <virtualmachineid>4</virtualmachineid>
                        <name>i-3-4-WC</name>
                           <offeringid>1</offeringid>
                        <templateid>3</templateid>
                        <usageid>245554</usageid>
                        <type>XenServer</type>
                        <startdate>2009-09-15T00:00:00-0700</startdate>
                        <enddate>2009-09-18T16:14:26-0700</enddate>
                      </usagerecord>

                   … (1,815 more usage records)
                </listusagerecordsresponse>

Dates in the Usage Record
-------------------------

Usage records include a start date and an end date. These dates define
the period of time for which the raw usage number was calculated. If
daily aggregation is used, the start date is midnight on the day in
question and the end date is 23:59:59 on the day in question (with one
exception; see below). A virtual machine could have been deployed at
noon on that day, stopped at 6pm on that day, then started up again at
11pm. When usage is calculated on that day, there will be 7 hours of
running VM usage (usage type 1) and 12 hours of allocated VM usage
(usage type 2). If the same virtual machine runs for the entire next
day, there will 24 hours of both running VM usage (type 1) and allocated
VM usage (type 2).

Note: The start date is not the time a virtual machine was started, and
the end date is not the time when a virtual machine was stopped. The
start and end dates give the time range within which usage was
calculated.

For network usage, the start date and end date again define the range in
which the number of bytes transferred was calculated. If a user
downloads 10 MB and uploads 1 MB in one day, there will be two records,
one showing the 10 megabytes received and one showing the 1 megabyte
sent.

There is one case where the start date and end date do not correspond to
midnight and 11:59:59pm when daily aggregation is used. This occurs only
for network usage records. When the usage server has more than one day's
worth of unprocessed data, the old data will be included in the
aggregation period. The start date in the usage record will show the
date and time of the earliest event. For other types of usage, such as
IP addresses and VMs, the old unprocessed data is not included in daily
aggregation.

Globally Configured Limits
--------------------------

In a zone, the guest virtual network has a 24 bit CIDR by default. This
limits the guest virtual network to 254 running instances. It can be
adjusted as needed, but this must be done before any instances are
created in the zone. For example, 10.1.1.0/22 would provide for ~1000
addresses.

The following table lists limits set in the Global Configuration:

+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| Parameter Name            | Definition                                                                                                                                                                                                                                                                                       |
+===========================+==================================================================================================================================================================================================================================================================================================+
| max.account.public.ips    | Number of public IP addresses that can be owned by an account                                                                                                                                                                                                                                    |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| max.account.snapshots     | Number of snapshots that can exist for an account                                                                                                                                                                                                                                                |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| max.account.templates     | Number of templates that can exist for an account                                                                                                                                                                                                                                                |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| max.account.user.vms      | Number of virtual machine instances that can exist for an account                                                                                                                                                                                                                                |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| max.account.volumes       | Number of disk volumes that can exist for an account                                                                                                                                                                                                                                             |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| max.template.iso.size     | Maximum size for a downloaded template or ISO in GB                                                                                                                                                                                                                                              |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| max.volume.size.gb        | Maximum size for a volume in GB                                                                                                                                                                                                                                                                  |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| network.throttling.rate   | Default data transfer rate in megabits per second allowed per user (supported on XenServer)                                                                                                                                                                                                      |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| snapshot.max.hourly       | Maximum recurring hourly snapshots to be retained for a volume. If the limit is reached, early snapshots from the start of the hour are deleted so that newer ones can be saved. This limit does not apply to manual snapshots. If set to 0, recurring hourly snapshots can not be scheduled     |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| snapshot.max.daily        | Maximum recurring daily snapshots to be retained for a volume. If the limit is reached, snapshots from the start of the day are deleted so that newer ones can be saved. This limit does not apply to manual snapshots. If set to 0, recurring daily snapshots can not be scheduled              |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| snapshot.max.weekly       | Maximum recurring weekly snapshots to be retained for a volume. If the limit is reached, snapshots from the beginning of the week are deleted so that newer ones can be saved. This limit does not apply to manual snapshots. If set to 0, recurring weekly snapshots can not be scheduled       |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| snapshot.max.monthly      | Maximum recurring monthly snapshots to be retained for a volume. If the limit is reached, snapshots from the beginning of the month are deleted so that newer ones can be saved. This limit does not apply to manual snapshots. If set to 0, recurring monthly snapshots can not be scheduled.   |
+---------------------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

To modify global configuration parameters, use the global configuration
screen in the CloudStack UI. See Setting Global Configuration Parameters

