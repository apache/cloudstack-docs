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


Apache CloudStack
=================

Apache CloudStack is an Apache project, see <http://cloudstack.apache.org> for
more information.


Website
=======

These docs are on-line at <http://docs.cloudstack.apache.org/en/latest/>


Translation
===========

Clean the build

::
   make clean

Generate the .pot files

::
   make gettext

Generate the .tx/config files with:

::
   sphinx-intl update-txconfig-resources --pot-dir source/locale/pot --transifex-project-name apache-cloudstack-rtd --locale-dir source/locale

Push the .pot files to transifex with:

::
   tx push -s

Download the translated strings, for example Japanese (ja):

::
   tx pull -l ja

Build the translated docs:

::
   sphinx-intl build --locale-dir source/locale
   make -e SPHINXOPTS="-D language='ja'" html


Feedback
========

Please send feedback to the mailing list at <dev@cloudstack.apache.org>,
or the JIRA at <https://issues.apache.org/jira/browse/CLOUDSTACK>.


Contributing to the documentation
=================================

Refer to the README.rst file in the root directory of this project for instructions on how to contribute to this documentation.
