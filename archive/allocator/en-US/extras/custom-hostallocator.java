      /**
        * Checks if the VM can be upgraded to the specified ServiceOffering
        * @param UserVm vm
        * @param ServiceOffering offering
        * @return boolean true if the VM can be upgraded
      **/

      publicboolean isVirtualMachineUpgradable(final UserVm vm, final ServiceOffering offering);

      /**
        * Determines which physical hosts are suitable to allocate the guest virtual machines on
        *
        * @paramVirtualMachineProfile vmProfile
        * @paramDeploymentPlan plan
        * @paramType type
        * @paramExcludeList avoid
        * @paramint returnUpTo
        * @returnList<Host>List of hosts that are suitable for VM allocation
      **/

      publicList<Host> allocateTo( VirtualMachineProfile<?extendsVirtualMachine> vmProfile,  DeploymentPlan plan, Type type, ExcludeList avoid, intreturnUpTo);
