.. CloudStack Documentation documentation master file, created by
   sphinx-quickstart on Sat Nov  2 11:17:30 2013.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Welcome to CloudStack Documentation !
=====================================

.. figure:: /_static/images/acslogo.png
    :align: center

Introduction
------------

.. toctree::
    :maxdepth: 2

    concepts

Navigating the docs
-------------------

Now that you have gone over the basic concepts of CloudStack you are ready to dive into the installation and operation.

See the `Installation Guide <http://docs.cloudstack.apache.org/projects/cloudstack-installation>`_

See the `Administration Guide <http://docs.cloudstack.apache.org/projects/cloudstack-administration>`_

See the `Release Notes <http://docs.cloudstack.apache.org/projects/cloudstack-release-notes>`_

Below you will find very specific documentation on advanced networking_, which you can skip if you are just getting started.

Developers will also find below a short developers_ guide. 

`Release Notes <http://docs.cloudstack.apache.org/projects/cloudstack-release-notes>`_
--------------------------------------------------------------------------------------

`Installation Guide <http://docs.cloudstack.apache.org/projects/cloudstack-installation>`_
------------------------------------------------------------------------------------------

`Administration Guide <http://docs.cloudstack.apache.org/projects/cloudstack-administration>`_
----------------------------------------------------------------------------------------------

.. _networking:

Advanced Networking Guides
--------------------------

.. toctree::
    :maxdepth: 2

    networking/nicira-plugin 
    networking/midonet
    networking/vxlan.rst
    networking/ovs-plugin
    networking/ipv6
    networking/autoscale_without_netscaler.rst
    networking/troubleshoot_internet_traffic.rst

.. _developer:

Developers Guide
----------------

.. toctree::
    :maxdepth: 2

    developer_guide
    dev
    plugins
    alloc.rst
    ansible

