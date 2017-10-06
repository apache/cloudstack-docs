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

.. CloudStack Documentation documentation master file, created by
   sphinx-quickstart on Sat Nov  2 11:17:30 2013.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.


Welcome to CloudStack Documentation !
=====================================

.. figure:: /_static/images/acslogo.png
   :align: center


.. toctree::


Introduction
------------

If you are new to CloudStack you should go through this short introduction on 
concepts and terminology before proceeding to the installation or 
administration guides.

.. toctree::
   :maxdepth: 2

   concepts


Navigating the docs
-------------------

Now that you have gone over the basic concepts of CloudStack you are ready to 
dive into installation and operation documentation.

-  `Installation Guide 
   <http://docs.cloudstack.apache.org/projects/cloudstack-installation>`_

-  `Administration Guide 
   <http://docs.cloudstack.apache.org/projects/cloudstack-administration>`_

-  `Release Notes 
   <http://docs.cloudstack.apache.org/projects/cloudstack-release-notes>`_

Below you will find very specific documentation on advanced networking_ which 
you can skip if you are just getting started. Developers will also find below 
a short developers_ guide. 


.. _integrations:

Integration Guides
------------------

.. toctree::
   :maxdepth: 2

   integration/cloudian-connector.rst


.. _networking:


Advanced Networking Guides
--------------------------

.. toctree::
   :maxdepth: 2

   networking/nicira-plugin
   networking/nuage-plugin
   networking/midonet
   networking/vxlan.rst
   networking/ovs-plugin
   networking/ipv6
   networking/autoscale_without_netscaler.rst


.. _developers:


Developers Guide
----------------

.. toctree::
   :maxdepth: 2

   developer_guide
   dev
   plugins
   alloc.rst
   ansible
   get_help
